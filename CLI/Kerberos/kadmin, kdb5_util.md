# kadmin, kdb5_util CLI

Principal

List principals:
```
kadmin.local listprincs
```
Show principal details:
```
kadmin.local -q "getprinc john@ATHENA.MIT.EDU"
```
Add a principal:
```
kadmin.local -q "addprinc -pw the_john_pass john@ATHENA.MIT.EDU" -p root/admin@ATHENA.MIT.EDU
```
Change password for principal:
```
kadmin.local change_password -pw the_kadmin_pass kadmin/admin@ATHENA.MIT.EDU
```

Keytab

Create a keytab:
```
kadmin.local -q "xst -norandkey -k /tmp/john.keytab john@ATHENA.MIT.EDU"
```
Add principal to existing keytab:
```
kadmin.local -q "ktadd -k /tmp/john.keytab mary@ATHENA.MIT.EDU"
```
Remove principal from existing keytab:
```
kadmin.local -q "ktremove -k /tmp/john.keytab mary@ATHENA.MIT.EDU"
```

Database

Create new database in "ATHENA.MIT.EDU"  realm:
```
kdb5_util create -r ATHENA.MIT.EDU -s -P the_db_master_pass
```
List master keys in a database:
```
kdb5_util list_mkeys -d the_database
```

Policy

List policies:
```
kadmin.local -q list_policies
```
Add a policy:
```
kadmin.local -q "add_policy -maxlife 180days my_policy"
```
Show policy details:
```
kadmin.local -q "get_policy my_policy"
```

Other

(woks?) Check connection to KDC admin port:
```
kadmin kdc-host:749
telnet kdc-host 749
```
