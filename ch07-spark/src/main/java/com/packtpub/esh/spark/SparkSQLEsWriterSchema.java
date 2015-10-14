package com.packtpub.esh.spark;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vishalshukla on 22/08/15.
 */
public class SparkSQLEsWriterSchema {
    public static void main(String args[]) {
        SparkConf conf = new SparkConf().setAppName("esh-spark").setMaster("local[4]");
        conf.set("es.index.auto.create", "true");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> textFile = context.textFile("hdfs://localhost:9000/ch07/crimes_dataset.csv");
        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(context);

        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("id", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("caseNumber", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("eventDate", DataTypes.TimestampType, true));
        fields.add(DataTypes.createStructField("block", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("iucr", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("primaryType", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("description", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("location", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("arrest", DataTypes.BooleanType, true));
        fields.add(DataTypes.createStructField("domestic", DataTypes.BooleanType, true));


        List<StructField> geoFields = new ArrayList<>();
        geoFields.add(DataTypes.createStructField("lat", DataTypes.DoubleType, true));
        geoFields.add(DataTypes.createStructField("lon", DataTypes.DoubleType, true));
        StructType geoLocationSchema = DataTypes.createStructType(geoFields);
        fields.add(DataTypes.createStructField("geoLocation", geoLocationSchema, true));

        StructType schema = DataTypes.createStructType(fields);

        JavaRDD<Row> rowRDD = textFile.map(line -> {
                        CSVParser parser = CSVParser.parse(line, CSVFormat.RFC4180);
                        CSVRecord record = parser.getRecords().get(0);
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mm");
                        Date eventDate = format.parse(record.get(2));

                        Row geo = RowFactory.create(StringUtils.isEmpty(record.get(10)) ? null : Double.parseDouble(record.get(10)),
                                StringUtils.isEmpty(record.get(11)) ? null : Double.parseDouble(record.get(11)));
                        return RowFactory.create(record.get(0), record.get(1),
                                new Timestamp(eventDate.getTime()), record.get(3), record.get(4),
                                record.get(5), record.get(6), record.get(7),
                                Boolean.parseBoolean(record.get(8)),
                                Boolean.parseBoolean(record.get(9)),
                                geo
                        );
                    });

        DataFrame df = sqlContext.createDataFrame(rowRDD, schema);
        df.registerTempTable("crime");
        JavaEsSparkSQL.saveToEs(df, "esh_sparksql/crimes_schema");
    }

}
