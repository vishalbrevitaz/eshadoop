package com.packtpub.esh.streaming;


import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.*;


public class TweetsParserBolt extends BaseRichBolt {

    private static final long serialVersionUID = 3938843121119464326L;
    private OutputCollector collector;
    private static transient ElasticSearchService service = new ElasticSearchService();

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        String user = null;
        String userHandle = null;

        String location = null;
        String country = null;
        List<String> hashtagList = new ArrayList<String>();
        List<String> mentionList = new ArrayList<String>();

        Status status = (Status) input.getValueByField("tweet");

        String tweet = status.getText();
        String source = status.getSource();
        Date createdDate = status.getCreatedAt();
        HashtagEntity entities[] = status.getHashtagEntities();
        long retweetCount = status.getRetweetCount();
        long favoriteCount = status.getFavoriteCount();
        UserMentionEntity mentions[] = status.getUserMentionEntities();
        String lang = status.getLang();

        // Extract hashtags
        if (entities != null) {
            for (HashtagEntity entity : entities) {
                String hashTag = entity.getText();
                hashtagList.add(hashTag);
            }
        }

        if (status.getPlace() != null) {
            if (status.getPlace().getName() != null) {
                location = status.getPlace().getName();
            }
            if (status.getPlace().getCountry() != null) {
                country = status.getPlace().getCountry();
            }
        }

        if (status.getUser() != null && status.getUser().getName() != null) {
            user = status.getUser().getName();
            userHandle = status.getUser().getScreenName();
        }

        if (mentions != null) {
            for (UserMentionEntity mention : mentions) {
                String mentionName = mention.getScreenName();
                mentionList.add(mentionName);
            }
        }

        String strHashtag = hashtagList.toString().replace("[", "").replace("]", "");
        String strUserMention = mentionList.toString().replace("[", "").replace("]", "");

        if ("en".equalsIgnoreCase(lang)) {
            System.out.println("Emitting : " + userHandle + " -> " + tweet);
            String categories = classify(tweet);
            collector.emit(input, new Values(user, userHandle, tweet, createdDate, location, country, strHashtag, source, lang, retweetCount, favoriteCount, strUserMention, categories));
        }
    }

    private String classify(String tweet) {
        StringBuilder categoriesBuilder = new StringBuilder();

        Map<String, Object> main = new HashMap<String, Object>();
        Map<String, Object> doc = new HashMap<String, Object>();
        doc.put("tweet", tweet);
        main.put("doc", doc);
        List<String> ids = service.percolate(main);
        for (String id : ids) {
            categoriesBuilder.append(getCategoryName(id) + " ");
        }
        return categoriesBuilder.toString();
    }

    public String getCategoryName(String id) {
        switch (id) {
            case "1":
                return "BigData";
            case "2":
                return "Relational Database";
            case "3":
                return "Sports";
            case "4":
                return "Agile";
            case "5":
                return "Business";
            default:
                return "Other";
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("user", "userHandle", "tweet",
                "time", "location", "country", "hashtags", "source",
                "lang", "retweetCount", "favoriteCount", "mentions", "categories"));
    }

}
