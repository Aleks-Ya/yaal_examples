# Kerberos

## Build and run

`docker-compose down -v && docker-compose build && docker-compose up`

## Testing with `sserver` and `sclient`

1. Run server:  
   Each connection stops the sever (start it again).  
   `docker exec -it sserver bash -c "sserver -S \$KEYTAB -p 5968 -s sserver"`
2. Run client:  
   `docker exec -it sclient bash -c "kinit -kt \$KEYTAB sclient@LOCAL.REALM && sclient sserver.kerberos.yaal.ru 5968 sserver"`

## KDC

Connect by `kadmin` to KDC:
`docker exec -it kdc kadmin.local`
Show KDC logs:
`docker exec -it kdc less /var/log/krb5kdc.log`