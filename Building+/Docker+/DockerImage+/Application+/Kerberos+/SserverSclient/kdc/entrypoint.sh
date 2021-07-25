#!/bin/bash
set -e

export KERBEROS_SHARED=/tmp/kerberos
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sserver.keytab $SSERVER_PRINCIPAL"
kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sclient.keytab $SCLIENT_PRINCIPAL"

klist -kte "$KERBEROS_SHARED/sserver.keytab"
klist -kte "$KERBEROS_SHARED/sclient.keytab"

bash
