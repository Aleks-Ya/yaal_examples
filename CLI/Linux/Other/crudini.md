# crudini CLI

Install: `sudo apt install crudini`

Help: `crudini --help`

List sections: `crudini --get ~/.aws/credentials`
List keys in a section: `crudini --get ~/.aws/credentials Programmer`
Get value of a key: `crudini --get ~/.aws/credentials Programmer aws_access_key_id`

Set parameter value: `crudini --set /tmp/my.ini Colors allowed green`
Delete a parameter: `crudini --del /tmp/my.ini Colors allowed`
Delete a section: `crudini --del /tmp/my.ini Colors`
