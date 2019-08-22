#!/bin/bash

echo "DB=$DB"
echo "SCHEMA_FROM=$SCHEMA_FROM"
echo "SCHEMA_TO=$SCHEMA_TO"
echo "TABLE_FROM=$TABLE_FROM"
echo "TABLE_TO=$TABLE_TO"
echo "USER=$USER"
echo "PGPASSWORD=$PGPASSWORD"

# Without intermediate file
pg_dump $DB -h localhost -U $USER --table ${SCHEMA_FROM}.${TABLE_FROM} \
| sed "s/${SCHEMA_FROM}.${TABLE_FROM}/${SCHEMA_TO}.${TABLE_TO}/g" \
| psql -U $USER -d $DB


# With intermediate file
#file=/tmp/dump_table.sql
#pg_dump $DB -h localhost -U $USER -t ${SCHEMA_FROM}.${TABLE_FROM} -f $file
#cat $file
#sed -i "s/${SCHEMA_FROM}.${TABLE_FROM}/${SCHEMA_TO}.${TABLE_TO}/g" $file
#cat $file
#psql $DB -U $USER -f $file

