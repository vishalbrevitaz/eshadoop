package com.packtpub.esh.tweets2es;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import java.io.IOException;

public class Driver {
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        // ElasticSearch Server nodes to point to
        conf.set("es.nodes", "localhost:9200");
        // ElasticSearch index and type name in {indexName}/{typeName} format
        conf.set("es.resource", "esh/tweets");

        // Create Job instance
        Job job = new Job(conf, "tweets to es mapper");
        // set Driver class
        job.setJarByClass(Driver.class);
        job.setMapperClass(Tweets2EsMapper.class);
        // set OutputFormat to EsOutputFormat provided by ElasticSearch-Hadoop jar
        job.setOutputFormatClass(EsOutputFormat.class);
        job.setNumReduceTasks(0);
        FileInputFormat.addInputPath(job, new Path(args[0]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
