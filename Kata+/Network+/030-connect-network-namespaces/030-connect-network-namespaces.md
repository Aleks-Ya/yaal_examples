# 030-connect-network-namespaces

## Task
Create two Network Namespaces. 
Run an HTTP server in the 1st Network Namespace.
Run an HTTP client in the 2nd Network Namespace.
Connect from the client to the server.

## Steps
1. Open two new terminals (for server and client)
2. Set environment variables
   ```shell
   set -x
   export NS1=kata-ns-1
   export NS2=kata-ns-2
   export VETH1=kata-veth-1
   export VETH2=kata-veth-2
   ```
3. Create Network Namespaces: 
	1. Create: 
		```shell
		sudo ip netns add $NS1
		sudo ip netns add $NS2
		```
	2. List: `ip netns list`
4. Run an HTTP server:
	1. Create a Virtual Ethernet pair:
		```shell
		sudo ip link add $VETH1 type veth peer name $VETH2
		sudo ip link set $VETH1 netns $NS1
		sudo ip link set $VETH2 netns $NS2
		```
	2. Assign IP Addresses:
		```shell
		sudo ip -n $NS1 addr add 10.200.1.1/24 dev $VETH1
		sudo ip -n $NS2 addr add 10.200.1.2/24 dev $VETH2
		sudo ip -n $NS1 link set $VETH1 up
		sudo ip -n $NS2 link set $VETH2 up
		```
	3. Run server: `sudo ip netns exec $NS1 python3 -m http.server 8000`
	4. Test server (in another terminal): `sudo ip netns exec $NS2 curl 10.200.1.1:8000`

## Cleanup
1. Stop the HTTP server (Ctrl-C)
2. Delete Network Namespaces: 
	```shell
	sudo ip netns del $NS1
	sudo ip netns del $NS2
	```
3. Close the 2 terminals

## History
- 2026-02-11 success
