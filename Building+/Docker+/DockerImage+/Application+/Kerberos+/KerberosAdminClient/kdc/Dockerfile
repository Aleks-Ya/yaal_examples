# Run KDC (Key Destribution Center)

FROM debian-updated:stable

RUN apt-get install -y krb5-admin-server krb5-config

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=${LOCALSTATEDIR}/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD kadm5.acl ${LOCALSTATEDIR}/kadm5.acl
ADD entrypoint.sh /tmp/entrypoint.sh

RUN kdb5_util create -r ATHENA.MIT.EDU -s -P the_db_master_pass && \
    kadmin.local -q "addprinc -pw the_admin_pass admin/admin@ATHENA.MIT.EDU" -p root/admin@ATHENA.MIT.EDU && \
    kadmin.local -q "addprinc -pw the_john_pass john@ATHENA.MIT.EDU" -p root/admin@ATHENA.MIT.EDU && \
    kadmin.local change_password -pw the_kadmin_pass kadmin/admin@ATHENA.MIT.EDU && \
    kadmin.local change_password -pw the_changepw_pass kadmin/changepw@ATHENA.MIT.EDU

EXPOSE 749
EXPOSE 750
EXPOSE 88

CMD krb5kdc && kadmind && /tmp/entrypoint.sh
