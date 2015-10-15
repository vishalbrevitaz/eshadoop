#!/usr/bin/env bash
curl -X﻿POST http://localhost:9200/hrms/candidate/_search?pretty -d '{
  "query": {
    "filtered": {
      "filter": {
        "bool": {
          "must": [
            {
              "range": {
          "experience": {
            "gte": 5,
            "lte": 10
          }
        }
            },
            {
              "terms": {
                "skills": [
                  "elasticsearch",
                  "kibana",
                  "lucene"
                ]
              }
            }
          ]
        }

      }
    }
  },
  "aggs": {
    "by_city": {
      "terms": {
        "field": "address.city",
        "size": 5
      },
      "aggs": {
        "by_skill": {
          "terms": {
            "field": "skills",
            "size": 5
       },
        "aggs":{
          "average": {
            "avg": {
                "field": "salary"
            }
           }
         }
       }
     }
    }
  },
  "size": 0
}'