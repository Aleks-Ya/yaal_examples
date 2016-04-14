#!/bin/bash

text="Commit >>>master lalala"
#text="Commit >>>master"
echo "$(echo ${text} | egrep -o ">>>.* ?")_"
echo "$(echo ${text} | grep -Po ">>>.*[\s$]" | xargs)_"