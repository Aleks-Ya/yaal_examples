# 020-create-network-namespace

## Task
Create a Network Namespace and run an HTTP server within it.

## Steps
1. Open a new terminal
2. Set environment variables
   ```shell
   set -x
   export NS=kata-ns-create-network-namespace
   ```
3. Create a Network Namespace: 
	1. Create: `sudo ip netns add $NS`
	2. List: `ip netns list`
4. Run an HTTP server:
	1. Enable Loopback in the Network Namespace: `sudo ip netns exec $NS ip link set lo up`
	2. Run server: `sudo ip netns exec $NS python3 -m http.server 8000`
	3. Test server (in another terminal): `sudo ip netns exec $NS curl localhost:8000`

## Cleanup
1. Stop the HTTP server (Ctrl-C)
2. Delete Network Namespace: `sudo ip netns del $NS`
3. Close the 2 terminals

## History
- 2026-02-11 success
