#!/usr/bin/env bash

#Substring is a literal
string='My long string'
if [[ $string == *"My long"* ]]; then
  echo "It's there!"
fi

#Substring is variable
string='My long string'
substring='long'
if [[ $string == *"${substring}"* ]]; then
  echo "It's there!"
fi
