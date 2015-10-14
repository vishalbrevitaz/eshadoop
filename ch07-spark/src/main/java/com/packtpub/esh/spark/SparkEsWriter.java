package com.packtpub.esh.spark;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishalshukla on 14/08/15.
 */
public class SparkEsWriter {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("esh-spark").setMaster("local[4]");
        conf.set("es.index.auto.create", "true");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> textFile = context.textFile("hdfs://localhost:9000/ch07/crimes_dataset.csv");

        JavaRDD<Crime> dataSplits = textFile.map(line -> {
                CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
                Crime c = new Crime();
                CSVRecord record = parser.getRecords().get(0);
                c.setId(record.get(0));
                c.setCaseNumber(record.get(1));
                c.setEventDate(record.get(2));
                c.setBlock(record.get(3));
                c.setIucr(record.get(4));
                c.setPrimaryType(record.get(5));
                c.setDescription(record.get(6));
                c.setLocation(record.get(7));
                c.setArrest(Boolean.parseBoolean(record.get(8)));
                c.setDomestic(Boolean.parseBoolean(record.get(9)));
                String lat = record.get(10);
                String lon = record.get(11);

                Map<String, Double> geoLocation = new HashMap<>();
                geoLocation.put("lat", StringUtils.isEmpty(lat)?null:Double.parseDouble(lat));
                geoLocation.put("lon", StringUtils.isEmpty(lon)?null:Double.parseDouble(lon));
                c.setGeoLocation(geoLocation);
                return c;
            });

        JavaEsSpark.saveToEs(dataSplits, "esh_spark/crimes");

    }
}
