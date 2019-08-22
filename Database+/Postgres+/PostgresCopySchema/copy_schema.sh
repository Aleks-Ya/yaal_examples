#!/bin/bash

echo "USER=$USER"
echo "FROM=$FROM"
echo "TO=$TO"
echo "DB=$DB"
echo "PGPASSWORD=$PGPASSWORD"

# Without intermediate file
pg_dump -h localhost -U $USER --schema="$FROM" $DB \
| sed "s/ ${FROM}\./ ${TO}./g" \
| sed "s/SCHEMA ${FROM}/SCHEMA ${TO}/g" \
| psql -U $USER -d $DB


# With intermediate file
#file=/tmp/dump.sql
#pg_dump $DB -h localhost -U $USER --schema="$FROM" -f $file
#cat $file
#sed -i "s/ ${FROM}\./ ${TO}./g" $file
#sed -i "s/SCHEMA ${FROM}/SCHEMA ${TO}/g" $file
#cat $file
#psql $DB -U $USER -f $file

