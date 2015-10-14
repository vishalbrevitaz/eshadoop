export LINGUAL_PLATFORM=hadoop
# register {es} as a provider
lingual catalog --init
lingual catalog --provider --add /opt/lib/elasticsearch-hadoop-2.1.1.jar
# add a custom schema (called 'titles') for querying

lingual catalog --schema esh --add
lingual catalog --schema esh --stereotype crimes --add \
    --columns id,caseNumber,eventDate,block,iucr,primaryType,description,location,arrest,domestic,lat,lon --types string,string,string,string,string,string,string,string,string,string,string,string

lingual catalog --schema esh --format es --add --provider es
lingual catalog --schema esh --protocol es --add --provider es \
    --properties=host=localhost
lingual catalog --schema esh --table crimes --stereotype crimes \
    -add esh_cascading/crimes --format es --provider es --protocol es