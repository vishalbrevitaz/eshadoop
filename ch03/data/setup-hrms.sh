#!/bin/bash

curl -XPUT http://localhost:9200/hrms

curl -XPUT http://localhost:9200/hrms/candidate/_mapping?pretty -d '{
  "properties": {
               "experience": {
                  "type": "float"
               },
               "firstName": {
                  "type": "string"
               },
               "lastName": {
                  "type": "string"
               },
               "birthDate" : {                 
                  "type" : "date",                 
                  "format" : "dd/MM/YYYY" 
               },
               "salary" : {                 
                  "type" : "double" 
               },
               "skills": {
                  "type": "string"
               },
               "address" : {
                        "type" : "object",
                        "properties" : {
                            "street" : {"type" : "string"},
                            "city" : {
"type" : "string", 
"index" : "not_analyzed"
},
                            "region" : {"type" : "string"},
                            "geo":{"type":"geo_point"}
                        }
                }
  }       
}'

curl -XPOST  http://localhost:9200/hrms/candidate -d '{
  "firstName": "Emerson",
  "lastName": "Atkins",
  "skills": ["Java","Hadoop","ElasticSearch","Kibana"],
  "experience": 8.5,
  "birthDate":"30/04/1987",
  "address" :{
                "street" : "Ap #576-619 Tincidunt Rd.",
                "city" : "Nagpur",
                "region": "MH",
                "geo": "15.97, 76.82"
              },
   "salary":"120000"
}'
curl -XPOST  http://localhost:9200/hrms/candidate -d '
{ 
    "firstName": "Jorden",
    "lastName": "Mclean",
    "birthDate": "11/03/1980",
    "experience": 19,
    "skills": ["Java","Hadoop","ElasticSearch","Kibana"],
    "address" :{
    "street": "2751 Ut Rd.",
    "city": "Purral",
    "region": "SJ",
    "geo": "-80.61395, 21.93988"
    },
    "comments":"Passionate Java and BigData developer",
   "salary":"150000"
    
  }'
curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Aimee",
    "lastName": "Ramirez",
    "birthDate": "14/11/1973",
    "experience": 6,
    "skills": ["PHP","Magento","ElasticSearch","Kibana"],
    "address" :{
      "street": "477-3861 Feugiat. Road",
      "city": "La Roche-sur-Yon",
      "region": "Pays de la Loire",
      "geo": "23.15, 72.33"
    },
    "achievements":"Ethical hacking certification.",
   "salary":"80000"

  }'
curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Rebekah",
    "lastName": "Chang",
    "birthDate": "12/04/1984",
    "experience": 13,
    "skills": ["Java","Spring","ElasticSearch"],
    "address" :{
      "street": "138-8420 Semper Rd.",
      "city": "Mumbai",
      "region": "MH",
      "geo": "18.97, 72.82"
    },
    "comments":"Ethical hacking certification.",
   "salary":"125000"
  }'

curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Gray",
    "lastName": "Carson",
    "birthDate": "20/11/1989",
    "experience": 11,
    "skills": ["Python","R","Machine learning"],
    "address" :{
    "street": "Ap #261-8043 Magna. Rd.",
    "city": "Parramatta",
    "region": "New South Wales",
    "geo": "24.15, 73.33"
    },
    "comments":"Ethical hacking certification.",
   "salary":"115000"
  }'
curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Eagan",
    "lastName": "Riddle",
    "birthDate": "03/12/1979",
    "experience": 17,
    "skills": ["Linux","Networking","VMWare","DevOps","Docker"],
    "address" :{
    "street": "7138 Amet Avenue",
    "city": "New South Wales",
    "region": "OV",
    "geo": "-89.52962, -117.05619"
    },
   "salary":"180000"
  }'
curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Hamish",
    "lastName": "Mendez",
    "birthDate": "22/05/1988",
    "experience": 9,
    "skills": ["Ruby","Linux","Puppet","Chef","DevOps","Ansible","Shell Script","Groovy"],
    "address" :{
    "street": "P.O. Box 452, 4375 Nam Road",
    "city": "Parramatta",
    "region": "Şa",
    "geo": "42.5772, 9.88647",
   "salary":"100000"
    }
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Bradley",
    "lastName": "Stark",
    "birthDate": "23/06/1981",
    "experience": 1,
    "skills": ["HTML","CSS","Photoshop","AngularJS","SQL"],
    "address" :{
    "street": "Ap #695-608 Aliquet. St.",
    "city": "Newcastle",
    "region": "New South Wales",
    "geo": "3.00917, -152.95787"
    },
    "achievements":"Secured 1st rank in university in Masters",
   "salary":"60000"
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Quemby",
    "lastName": "Cunningham",
    "birthDate": "02/09/1987",
    "experience": 5,
    "skills": ["Lucene","ElasticSearch","Java"],
    "address" :{
    "street": "P.O. Box 751, 6709 Cras St.",
    "city": "Akron",
    "region": "Galicia",
    "geo": "70.68905, 56.43336"
    },
   "salary":"80000"
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Elton",
    "lastName": "Harper",
    "birthDate": "30/11/1978",
    "experience": 10,
    "skills": ["Hadoop","Spark","Java","Linux"],
    "address" :{
    "street": "8854 Fermentum Road",
    "city": "New South Wales",
    "region": "Noord Brabant",
    "geo": "67.74365, -31.22381"
    },
   "salary":"110000"
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Hyacinth",
    "lastName": "Melendez",
    "birthDate": "08/01/1979",
    "experience": 11,
    "skills": ["Kibana","ElasticSearch","Java","Linux"],
    "address" :{
    "street": "688-7523 Diam Rd.",
    "city": "Akron",
    "region": "Ohio",
    "geo": "40.68995, -123.71124"
    },
   "salary":"120000"
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Forrest",
    "lastName": "Lawson",
    "birthDate": "13/03/1978",
    "experience": 4,
    "skills": ["Solr","Lucene","Java","Full-text search"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Istanbul",
    "region": "Ist",
    "geo": "-10, 155"
    },
   "salary":"70000"
  }'


  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "David",
    "lastName": "Lawson",
    "birthDate": "13/03/1968",
    "experience": 30,
    "skills": ["Lucene"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Istanbul",
    "region": "Ist",
    "geo": "-9, 150"
    },
   "salary":"200000"
  }'
  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "David",
    "lastName": "Lawson",
    "birthDate": "13/03/1968",
    "experience": 40,
    "skills": ["Lucene"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Istanbul",
    "region": "Ist",
    "geo": "-9.68931, 151.66362"
    },
   "salary":"250000"
  }'

  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "David",
    "lastName": "Lawson",
    "birthDate": "13/03/1968",
    "experience": 30,
    "skills": ["Lucene"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Istanbul",
    "region": "Ist",
    "geo": "-9.68931, 151.66362"
    },
   "salary":"210000"
  }'

  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Kiran",
    "lastName": "Suthar",
    "birthDate": "13/03/1968",
    "experience": 35,
    "skills": ["Lucene"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Istanbul",
    "region": "Ist",
    "geo": "-9.68931, 151.66362"
    },
   "salary":"300000"
  }'

  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "David",
    "lastName": "Mackwan",
    "birthDate": "13/03/1968",
    "experience": 0,
    "skills": ["Java"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Kota",
    "region": "Ist",
    "geo": "-9.68931, 151.66362"
    },
   "salary":"40000"
  }'

  curl -XPOST  http://localhost:9200/hrms/candidate -d '
  {
    "firstName": "Pratik",
    "lastName": "Patel",
    "birthDate": "13/03/1968",
    "experience": 1,
    "skills": ["Java"],
    "address" :{
    "street": "P.O. Box 146, 3183 Amet Avenue",
    "city": "Kota",
    "region": "Ist",
    "geo": "-9.68931, 151.66362"
    },
   "salary":"65000"
  }'

