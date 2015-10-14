package com.packtpub.esh.nwlogs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;


public class Driver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
        // ElasticSearch Server nodes to point to
		conf.set("es.nodes", "localhost:9200");
        // ElasticSearch index and type name in {indexName}/{typeName} format
		conf.set("es.resource", "esh_network/network_logs_{action}");

        // Create Job instance
		Job job = new Job(conf, "network monitor mapper");
        // set Driver class
		job.setJarByClass(Driver.class);
        job.setMapperClass(NetworkLogsMapper.class);
        // set OutputFormat to EsOutputFormat provided by ElasticSearch-Hadoop jar
	    job.setOutputFormatClass(EsOutputFormat.class);
        job.setNumReduceTasks(0);
        FileInputFormat.addInputPath(job, new Path(args[0]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}