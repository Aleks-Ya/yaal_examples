# envsubst CLI

Help: `envsubst --help`
Version: `envsubst --version`

Substitute env vars into a string, print to console: `echo "User is $USER, dir is ${HOME}." | envsubst`

Read file, substitute env vars, print to console: 
1. Option 1: `cat input_template.txt | envsubst`
2. Option 2: `envsubst < input_template.txt`

Read file, substiture env vars, and save to another file: 
1. Option 1: `cat input_template.txt | envsubst > output.txt`
2. Option 2: `envsubst < input_template.txt > output.txt`
