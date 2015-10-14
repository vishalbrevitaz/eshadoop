package com.packtpub.esh.streaming;



import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("serial")
public class TweetsCollectorSpout extends BaseRichSpout {

    SpoutOutputCollector collector;
    LinkedBlockingQueue<Status> queue = null;
    TwitterStream twitterStream;

    // TODO: Initialize twitter credentials.
    String consumerKey = "<<YOUR_CONSUMER_KEY>>";
    String consumerSecret = "<<YOUR_CONSUMER_SECRET>>";
    String accessToken = "<<YOUR_ACCESS_TOKEN>>";
    String accessTokenSecret = "<<YOUR_TOKEN_SECRET>>";
    String[] keyWords = {};

    public TweetsCollectorSpout() {
    }

    @Override
    public void open(Map conf, TopologyContext context,	SpoutOutputCollector collector) {
        queue = new LinkedBlockingQueue<Status>(1000);
        this.collector = collector;

        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                queue.offer(status);
            }

            public void onDeletionNotice(StatusDeletionNotice sdn) {
            }

            public void onTrackLimitationNotice(int i) {
            }

            public void onScrubGeo(long l, long l1) {
            }

            public void onException(Exception ex) {
            }

            public void onStallWarning(StallWarning arg0) {

            }

        };

        twitterStream = new TwitterStreamFactory(
                new ConfigurationBuilder().setJSONStoreEnabled(true).build())
                .getInstance();

        twitterStream.addListener(listener);
        twitterStream.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken token = new AccessToken(accessToken, accessTokenSecret);
        twitterStream.setOAuthAccessToken(token);

        if (keyWords.length == 0) {
            twitterStream.sample();
        }
        else {
            FilterQuery query = new FilterQuery().track(keyWords);
            twitterStream.filter(query);
        }

    }

    public void nextTuple() {
        Status status = queue.poll();
        if (status == null) {
            Utils.sleep(50);
        } else {
            collector.emit(new Values(status));
        }
    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tweet"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config config = new Config();
        config.setMaxTaskParallelism(1);
        return config;
    }

    @Override
    public void close() {
        twitterStream.shutdown();
    }


    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }


}
