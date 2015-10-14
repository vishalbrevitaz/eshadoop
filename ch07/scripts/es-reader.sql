DROP TABLE IF EXISTS theft_crimes;

CREATE EXTERNAL TABLE IF NOT EXISTS theft_crimes (
   id      STRING,
   caseNumber STRING,
   eventDate DATE,
   block STRING,
   iucr STRING,
   primaryType STRING,
   description STRING,
   location STRING,
   arrest BOOLEAN,
   domestic BOOLEAN,
   geoLocation   STRUCT<lat:DOUBLE, lon:DOUBLE>)
STORED BY 'org.elasticsearch.hadoop.hive.EsStorageHandler'
TBLPROPERTIES('es.resource' = 'esh_hive/crimes', 'es.query' = '{"query" : { "term" : { "primarytype" : "theft" } } }');

-- stream data from Elasticsearch
SELECT location, count(*) as noOfCrimes FROM theft_crimes group by location;
