package com.packtpub.esh.cascading;


import cascading.flow.FlowConnector;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.expression.ExpressionFunction;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import org.elasticsearch.hadoop.cascading.EsTap;

import java.util.Properties;


public class CascadingEsWriter {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.setProperty("es.mapping.id", "id");
        FlowConnector flow = new HadoopFlowConnector(props);

        Fields inFields = new Fields("id", "caseNumber", "eventDate", "block",
                "iucr", "primaryType", "description","location","arrest","domestic",
                "lat", "lon"
        );
        TextDelimited scheme = new TextDelimited(inFields, false, ",","\"");
        Tap in = new Hfs(scheme, "/ch07/crimes_dataset.csv");

        String expression = "lat + \", \" + lon";
        Fields location = new Fields( "geoLocation" );
        ExpressionFunction locationFunction = new ExpressionFunction( location, expression, String.class );

        Pipe toEs = new Pipe("to-Es");
        toEs = new Each(toEs, locationFunction,Fields.ALL);

        Fields outFields = new Fields("id", "caseNumber", "eventDate", "block",
                "iucr", "primaryType", "description","location","arrest","domestic",
                "geoLocation"
        );
        Tap out = new EsTap("localhost",9200, "esh_cascading/crimes",  outFields);


        flow.connect(in, out, toEs).complete();
    }
}