#!/usr/bin/env bash

curl -XPUT 'http://localhost:9200/es-storm/.percolator/1' -d '{
     "query": {
                  "match": {
                     "tweet": "bigdata analytics hadoop spark elasticsearch nosql graphdb cassandra mongo mongodb datascience pig hive solar cloudera hortonworks iot"
                  }
               }
}';


curl -XPUT 'http://localhost:9200/es-storm/.percolator/2' -d '{
  "query": {
     "match": {
        "tweet": "relational mysql postgres oracle "
     }
  }
}';


curl -XPUT 'http://localhost:9200/es-storm/.percolator/3' -d '{
    "query": {
       "match": {
         "tweet": "football socker tennis snooker chess cricket sports"
       }
    }
  }
}';


curl -XPUT 'http://localhost:9200/es-storm/.percolator/4' -d '{
    "query": {
      "match": {
        "tweet": "agile scrum xp "
      }
    }
  }
}';

curl -XPUT 'http://localhost:9200/es-storm/.percolator/5' -d '{
    "query": {
          "match": {
            "tweet": "business entrepreneur entrepreneurship  biz designthinking startup"
          }
        }
    }
}';

