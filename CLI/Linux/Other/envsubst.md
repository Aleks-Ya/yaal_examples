# envsubst CLI

Help: `envsubst --help`
Version: `envsubst --version`

Substitute env vars into a string: `echo "User is $USER, dir is ${HOME}." | envsubst`
Read file, substiture env vars, and save to another file: `envsubst < input_template.txt > output.txt`
