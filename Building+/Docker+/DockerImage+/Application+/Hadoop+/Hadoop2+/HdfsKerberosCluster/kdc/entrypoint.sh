#!/bin/bash
set -e

KERBEROS_SHARED=/tmp/kerberos
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"

NN_KEYTAB="$KERBEROS_SHARED/nn.keytab"
DN2_KEYTAB="$KERBEROS_SHARED/dn2.keytab"
DN3_KEYTAB="$KERBEROS_SHARED/dn3.keytab"
CLIENT_KEYTAB="$KERBEROS_SHARED/client.keytab"
SSERVER_KEYTAB="$KERBEROS_SHARED/sserver.keytab"
SCLIENT_KEYTAB="$KERBEROS_SHARED/sclient.keytab"

kadmin.local -q "xst -norandkey -k $NN_KEYTAB $NN_MASTER_PRINCIPAL"
kadmin.local -q "ktadd -k $NN_KEYTAB $DN_MASTER_PRINCIPAL"
kadmin.local -q "ktadd -k $NN_KEYTAB $HOST_MASTER_PRINCIPAL"
kadmin.local -q "ktadd -k $NN_KEYTAB $HTTP_MASTER_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $DN2_KEYTAB $DN_SLAVE1_PRINCIPAL"
kadmin.local -q "ktadd -k $DN2_KEYTAB $HOST_SLAVE1_PRINCIPAL"
kadmin.local -q "ktadd -k $DN2_KEYTAB $HTTP_SLAVE1_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $DN3_KEYTAB $DN_SLAVE2_PRINCIPAL"
kadmin.local -q "ktadd -k $DN3_KEYTAB $HOST_SLAVE2_PRINCIPAL"
kadmin.local -q "ktadd -k $DN3_KEYTAB $HTTP_SLAVE2_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $CLIENT_KEYTAB $CLIENT_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $SSERVER_KEYTAB $SSERVER_PRINCIPAL"
kadmin.local -q "xst -norandkey -k $SCLIENT_KEYTAB $SCLIENT_PRINCIPAL"

cp "$KRB5_CONFIG" "$KERBEROS_SHARED"
chmod a+r "$KERBEROS_SHARED"/*.keytab #TODO use group permission
ls -l "$KERBEROS_SHARED"

klist -kte "$NN_KEYTAB"
klist -kte "$DN2_KEYTAB"
klist -kte "$DN3_KEYTAB"
klist -kte "$CLIENT_KEYTAB"
klist -kte "$SSERVER_KEYTAB"
klist -kte "$SCLIENT_KEYTAB"

bash
