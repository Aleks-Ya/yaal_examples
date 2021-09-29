set -e
kinit -kt $KEYTAB sclient@LOCAL.REALM
sclient ss-sserver.kerberos.yaal.ru 5968 sserver