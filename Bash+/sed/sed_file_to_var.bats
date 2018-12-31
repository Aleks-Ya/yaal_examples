#!/usr/bin/env bats

# Replace substring from a file and store result in a variable.

@test "Replace first match" {
  file='/tmp/sed_first.txt'
  echo "Hello, Sed Sed world!" > $file
  replaced=$(sed 's/Sed/Nano/' $file)
  [ "${replaced}" = "Hello, Nano Sed world!" ]
}

@test "Replace all matches" {
  file='/tmp/sed_all.txt'
  echo "Hello, Sed Sed world!" > $file
  replaced=$(sed 's/Sed/Nano/g' $file)
  [ "${replaced}" = "Hello, Nano Nano world!" ]
}

@test "Escape character" {
  file='/tmp/sed_escape.txt'
  echo 'Hello, ${JAVA_HOME} and ${JAVA_HOME} world!' > $file
  origin=$(<$file)
  [ "${origin}" = 'Hello, ${JAVA_HOME} and ${JAVA_HOME} world!' ]
  replaced=$(sed 's/${JAVA_HOME}/abc/g' $file)
  [ "${replaced}" = "Hello, abc and abc world!" ]
}

@test "Escape characte (replacer from env)" {
  file='/tmp/sed_escape_env.txt'
  echo 'Hello, ${JAVA_HOME} and ${JAVA_HOME} world!' > $file
  origin=$(<$file)
  [ "${origin}" = 'Hello, ${JAVA_HOME} and ${JAVA_HOME} world!' ]
  replacer='123'
  replaced=$(sed "s/\${JAVA_HOME}/${replacer}/g" $file)
  [ "${replaced}" = "Hello, 123 and 123 world!" ]
}

@test "Another delimiter" {
  file='/tmp/sed_delimiter.txt'
  echo "Hello, /opt/hadoop /opt/hadoop world!" > $file
  replaced=$(sed 's@/opt/hadoop@Nano@g' $file)
  [ "${replaced}" = "Hello, Nano Nano world!" ]
}
