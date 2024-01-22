# klist CLI

Install: `sudo apt install -y krb5-user`

## Keytab

View Keytab file content:
```
klist -k my.keytab
klist -kteK my.keytab #show all fields
```

## Credential Cache

Show cache content:
```
klist # default cache $
klist -c /tmp/krb5cc #specific cache
klist -f  #show flags
```
Show specific cache