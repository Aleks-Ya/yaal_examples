#!/usr/bin/env bats

@test "Print string to stdout" {
  echo Hello stupid
  echo "Hello stupid 2"
}

@test "Print an empty string" {
  echo; echo
}

@test "Variable names' syntax" {
  MESSAGE="Hello, var!"
  echo "1: $MESSAGE"
  echo "2: ${MESSAGE}"
  echo 3: $MESSAGE
  echo 4: ${MESSAGE}
}

@test "Keep spaces" {
  hello="A B  C   D"
  [[ $hello == "A B  C   D" ]]
  [ "$hello" == "A B  C   D" ]
}

@test "Dollar $ escaping" {
  esc=Escape
  NO_ESCAPE=`echo var1: $esc`
  ESCAPE=`echo var2: '\$esc'`
  [[ $NO_ESCAPE == "var1: Escape" ]]
  [[ $ESCAPE == "var2: \$esc" ]]
}

@test "Quotes are required" {
  quotes_need="1 2 3"
  echo $quotes_need
  quotes_need2="Всем привет"
  echo $quotes_need2
}

@test "Initialized variable?" {
  v=abc
  if [ "$v" ]
  then
    echo "Initialized"
  else
    exit 1
  fi
}

@test "Uninitialized variable?" {
  if [ -z "$unassigned" ]
  then
    echo "\$unassigned is NULL."
  else
    exit 1
  fi
}

@test "Read the environment variables" {
  echo Path 1: $PATH
  p=$PATH
  echo Path 2: $p
}
