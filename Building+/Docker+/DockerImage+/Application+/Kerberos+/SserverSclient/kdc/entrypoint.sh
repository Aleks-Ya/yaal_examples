#!/bin/bash
set -e

export KERBEROS_SHARED=/tmp/kerberos
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sserver.keytab $SSERVER_PRINCIPAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/sserver.keytab $SCLIENT_PRINCIPAL"

cp $KERBEROS_SHARED/sserver.keytab $KERBEROS_SHARED/sclient.keytab

bash
