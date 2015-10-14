REGISTER hdfs://localhost:9000/lib/elasticsearch-hadoop-2.1.1.jar;

ES = LOAD 'esh_pig/crimes' using org.elasticsearch.hadoop.pig.EsStorage('{"query" : { "term" : { "primaryType" : "theft" } } }');

dump ES;