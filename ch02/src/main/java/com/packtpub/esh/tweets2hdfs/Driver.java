package com.packtpub.esh.tweets2hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.elasticsearch.hadoop.mr.EsInputFormat;

import java.io.IOException;

public class Driver {
    private static String query = "{\n" +
            "  \"query\": {\n" +
            "    \"bool\": {\n" +
            "      \"should\": [\n" +
            "        {\n" +
            "          \"term\": {\n" +
            "            \"text\": {\n" +
            "              \"value\": \"elasticsearch\"\n" +
            "            }\n" +
            "          \n" +
            "          }\n" +
            "        },{\n" +
            "          \"term\": {\n" +
            "            \"text\": {\n" +
            "              \"value\": \"kibana\"\n" +
            "            }\n" +
            "          \n" +
            "          }\n" +
            "        },{\n" +
            "          \"term\": {\n" +
            "            \"text\": {\n" +
            "              \"value\": \"analysis\"\n" +
            "            }\n" +
            "          \n" +
            "          }\n" +
            "        },{\n" +
            "          \"term\": {\n" +
            "            \"text\": {\n" +
            "              \"value\": \"visualize\"\n" +
            "            }\n" +
            "          \n" +
            "          }\n" +
            "        },{\n" +
            "          \"term\": {\n" +
            "            \"text\": {\n" +
            "              \"value\": \"realtime\"\n" +
            "            }\n" +
            "          \n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "      ,\"minimum_number_should_match\": 2\n" +
            "    }\n" +
            "    \n" +
            "  }\n" +
            "}";

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        // ElasticSearch Server nodes to point to
        conf.set("es.nodes", "localhost:9200");
        // ElasticSearch index and type name in {indexName}/{typeName} format
        conf.set("es.resource", "esh/tweets");
        conf.set("es.query", query);

        // Create Job instance
        Job job = new Job(conf, "tweets to hdfs mapper");
        // set Driver class
        job.setJarByClass(Driver.class);
        job.setMapperClass(Tweets2HdfsMapper.class);
        // set OutputFormat to EsOutputFormat provided by ElasticSearch-Hadoop jar
        job.setInputFormatClass(EsInputFormat.class);
        job.setNumReduceTasks(0);
        FileOutputFormat.setOutputPath(job, new Path(args[0]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
