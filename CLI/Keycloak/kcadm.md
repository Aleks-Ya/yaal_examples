# kcadm CLI

## Info
Docs: https://www.keycloak.org/docs/latest/server_admin/index.html#admin-cli

## Commands
Help: `kcadm.sh help`
Help for a command: `kcadm.sh config credentials --help`

### Realm
List realms: `kcadm.sh get realms`

### User
List users: `kcadm.sh get users`
Create a user (set password separately):
	1. Create a user: `kcadm.sh create users -r master -s username=testuser -s enabled=true`
	2. Set password for a user: `kcadm.sh set-password -r master --username testuser -p tu1`
Create user (with password): `kcadm.sh create users -r master -s username=testuser -s enabled=true -s credentials='[{"type":"password","value":"tu1","temporary":false}]'`
Set password for a user: `kcadm.sh set-password -r master --username testuser -p tu1`
Delete user: 
	1. Find ID of the user: `kcadm.sh get users -r master -F id,username`
	2. Delete the user: `kcadm.sh delete -r master users/a7d62fc6-f4d6-4992-9266-5140247670d1`

### Client
List clients: `kcadm.sh get clients`
Create a client from a JSON: `kcadm.sh create clients -r master -b '{"clientId": "client1"}'`
Create a client from fields: `kcadm.sh create clients -r master -s clientId=client1 -s secret=cs1`
Get details about clients: `kcadm.sh get clients -r master -F id,clientId,secret`
Delete a client:
	1. Find ID of the client: `kcadm.sh get clients -r master -F id,clientId`
	2. Delte the client: `kcadm.sh delete -r master clients/cbceb0b7-7ba4-43b3-8b36-52d63215824f`

### Role
List roles: `kcadm.sh get roles`
