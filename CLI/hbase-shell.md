# HBase Shell CLI

Docs: https://hbase.apache.org/book.html#shell

General

Help: help
Cluster state: status
Show current user and groups: whoami

Namespace

Show namespaces: list_namespace
Show tables in namespace: list_namespace_tables 'my_name_space'

DDL

Show all tables in HBase: list
Describe table: describe 'MY_NAMESPACE:MY_TABLE'
Create table with single column family: create 'MY_NAMESPACE:MY_TABLE', 'column_family_1'

DML

Select all rows from table: scan 'MY_NAMESPACE:MY_TABLE'
Select 5 rows from table: scan 'MY_NAMESPACE:MY_TABLE', {LIMIT => 5}
Count rows in table: count 'MY_NAMESPACE:MY_TABLE'



