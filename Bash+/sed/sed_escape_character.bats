#!/usr/bin/env bats

# Use escape characters

@test "Escape character" {
  text='Hello, ${JAVA_HOME} and ${JAVA_HOME} world!'
  replaced=$(echo $text | sed 's/${JAVA_HOME}/abc/g')
  [ "${replaced}" = "Hello, abc and abc world!" ]
}

@test "Replace from Environment variable" {
  text='Hello, ${JAVA_HOME} and ${JAVA_HOME} world!'
  replacer='123'
  replaced=$(echo $text | sed "s/\${JAVA_HOME}/${replacer}/g")
  [ "${replaced}" = "Hello, 123 and 123 world!" ]
}

@test "Replace dots (all matches)" {
  text="insert into schema.table_schema_1; insert into schema.table_schema_2;"
  replaced=$(echo $text | sed 's/schema\./schema2./g')
  [ "${replaced}" = "insert into schema2.table_schema_1; insert into schema2.table_schema_2;" ]
}

@test "Replace spaces (all matches)" {
  text="insert into schema.table_schema; insert into schema.table_schema;"
  replaced=$(echo $text | sed 's/ schema/ schema2/g')
  [ "${replaced}" = "insert into schema2.table_schema; insert into schema2.table_schema;" ]
}

@test "Replace schema in SQL (all matches)" {
  text="insert into schema.table_schema_1; insert into schema.table_schema_2;"
  replaced=$(echo $text | sed 's/ schema\./ schema2./g')
  [ "${replaced}" = "insert into schema2.table_schema_1; insert into schema2.table_schema_2;" ]
}