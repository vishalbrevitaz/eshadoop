#!/bin/bash
curl -XPUT http://localhost:9200/esh_complaints
curl -XPUT http://localhost:9200/esh_complaints/complaints/_mapping -d '
{
         "complaints": {
            "properties": {
               "company": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "companyResponse": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "complaintId": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "consumerDisputed": {
                  "type": "boolean"
               },
               "dateReceived": {
                  "type": "date",
                  "format": "MM/dd/yyyy||MM/dd/yy"
               },
               "dateSent": {
                  "type": "date",
                  "format": "MM/dd/yyyy||MM/dd/yy"
               },
               "issue.raw": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "issue": {
                  "type": "string"
               },
               "location": {
                  "type": "geo_point"
               },
               "product": {
                  "type": "string",
                  "index": "not_analyzed",
                  "fields": {
                     "analyzed": {
                        "type": "string"
                     }
                  }
               },
               "state": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "subissue": {
                  "type": "string",
                  "index": "not_analyzed",
                  "fields": {
                     "analyzed": {
                        "type": "string"
                     }
                  }
               },
               "submittedVia": {
                  "type": "string",
                  "index": "not_analyzed"
               },
               "subproduct": {
                  "type": "string",
                  "index": "not_analyzed",
                  "fields": {
                     "analyzed": {
                        "type": "string"
                     }
                  }
               },
               "timelyResponse": {
                  "type": "boolean"
               },
               "zip": {
                  "type": "string",
                  "index": "not_analyzed"
               }
            }
         }
}'