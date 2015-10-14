package com.packtpub.esh.streaming;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import org.elasticsearch.storm.EsBolt;

import java.util.HashMap;
import java.util.Map;


public class Topology {

    public static void main(String[] args) throws InterruptedException {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("tweets-collector", new TweetsCollectorSpout(),1);
        builder.setBolt("tweets-parser-bolt", new TweetsParserBolt())
                .shuffleGrouping("tweets-collector");

        Map config = new HashMap();
        config.put("es.nodes","localhost:9200");
        config.put("es.storm.bolt.flush.entries.size",100);
        builder.setBolt("es-bolt", new EsBolt("es-storm/storm-tweets",config))
                .shuffleGrouping("tweets-parser-bolt")
                .addConfiguration(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 2);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("twitter-test", null, builder.createTopology());
    }
}