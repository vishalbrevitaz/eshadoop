package com.packtpub.esh.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishalshukla on 22/08/15.
 */
public class SparkSQLEsReader {
    public static void main(String args[]) {
        SparkConf conf = new SparkConf().setAppName("esh-spark").setMaster("local[4]");
        conf.set("es.index.auto.create", "true");
        JavaSparkContext context = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(context);
        Map<String, String> options = new HashMap<>();
        options.put("pushdown","true");
        options.put("es.nodes","localhost");

        DataFrame df = sqlContext.read()
                .options(options)
                .format("org.elasticsearch.spark.sql").load("esh_sparksql/crimes_reflection");

        df.registerTempTable("crimes");

        DataFrame theftCrimes = sqlContext.sql("SELECT * FROM crimes WHERE primaryType='THEFT'");
        for(Row row: theftCrimes.javaRDD().collect()){
            System.out.println(row);
        }
    }

}
