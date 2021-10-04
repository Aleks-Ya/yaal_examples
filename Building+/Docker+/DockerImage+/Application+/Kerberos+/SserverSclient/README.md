# Testing Kerberos with built-in SServer and SClient

## Run cluster

Build and run: `docker-compose down -v && docker-compose build && docker-compose up`

## Testing with `sserver` and `sclient`

1. Run server: `docker exec -it ss-sserver bash -c /tmp/run_sserver.sh`  
   Each connection stops the sever (start it again).
2. Run client: `docker exec -it ss-sclient bash -c /tmp/run_sclient.sh`
3. Show tickets: `docker exec -it ss-sclient bash -c /tmp/show_tickets.sh`

## KDC

Connect by `kadmin` to KDC: `docker exec -it ss-kdc kadmin.local`
Show KDC logs: `docker exec -it ss-kdc less /var/log/krb5kdc.log`
