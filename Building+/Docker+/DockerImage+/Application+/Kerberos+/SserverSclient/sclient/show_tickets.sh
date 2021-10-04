set -e
kinit -kt $KEYTAB sclient@LOCAL.REALM
kvno sserver/ss-sserver.kerberos.yaal.ru@LOCAL.REALM
kvno sclient@LOCAL.REALM
klist -f
