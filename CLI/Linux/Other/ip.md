# ip CLI

## Network Interface
Show network interfaces: `ip link show`
List Virtual Ethernet pairs: `ip -d link show type veth`
Create a Virtual Ethernet pair: `sudo ip link add veth0 type veth peer name veth1`
Delete a Virtual Ethernet pair: `sudo ip link del veth0`

## IP Address
Show IP addresses: `ip addr show`

## Network namespace
List network namespaces: `ip netns list`
Create a network namespace: `sudo ip netns add ns1`
Execute a command within a network namespace: `sudo ip netns exec ns1 ip link`
Delete a network namespace: `sudo ip netns del ns1`
