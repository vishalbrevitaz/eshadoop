package com.packtpub.esh.cascading;


import cascading.flow.FlowConnector;
import cascading.flow.local.LocalFlowConnector;
import cascading.pipe.Pipe;
import cascading.tap.Tap;
import cascading.tap.local.StdOutTap;
import org.elasticsearch.hadoop.cascading.EsTap;

import java.util.Properties;


public class CascadingEsReader {
    public static void main(String[] args) throws InterruptedException {

        Tap in = new EsTap("localhost",9200, "esh_cascading/crimes",
            "{\"query\" : { \"term\" : { \"primaryType\" : \"theft\" } } }");

        Properties props = new Properties();
        props.setProperty("es.nodes","localhost");
        Tap out = new StdOutTap(new cascading.scheme.local.TextLine());
        FlowConnector flow = new LocalFlowConnector();

        Pipe fromEs = new Pipe("search-from-es");
        flow.connect(in, out, fromEs).complete();
    }
}