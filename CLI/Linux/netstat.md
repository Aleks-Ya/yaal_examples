# netstat

## Show routing table
```
netstat -r  #with DNS
netstat -rn #without DNS
```

## Find application by port
`sudo netstat -ltnp | grep -w '6379'`