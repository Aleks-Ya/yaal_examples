# Open LDAP

## Docs
Docker: https://github.com/osixia/docker-openldap

## Run
Run: `docker run --rm --name openldap -p 389:389 -p 636:636 osixia/openldap:1.5.0`
Run with debug log level (default is info): `docker run --rm --name openldap osixia/openldap:1.5.0 --loglevel debug`
Run with custom domain: 
```
docker run --rm --name openldap \
	--env LDAP_ORGANISATION="My company" \
	--env LDAP_DOMAIN="my-company.com" \
	--env LDAP_ADMIN_PASSWORD="JonSn0w" \
	-p 389:389 -p 636:636 \
	osixia/openldap:1.5.0
```

Ports: `389`, `636`
Admin: `cn=admin,dc=example,dc=org`/`admin`

## Search
### Inside container
`docker exec openldap ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin`
### Outside of container
Terminal: `ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin`
JXplorer: ,
	- Host: `localhost`
	- Port: `389`
	- Protocol: `LDAP v3`
	- DSML Service: empty
	- Base DN: empty
	- Security:
		+ Level: `User + Password`
		+ User DN: `cn=admin,dc=example,dc=org`
		+ Password: `admin`
