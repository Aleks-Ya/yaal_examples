# Pod access a ConfigMap as a file

1. Create: `kubectl create -f config_map.yaml`
1. Show Pod logs: `kubectl logs config-map-as-file-pod`
1. Print config maps:
`kubectl exec -it config-map-as-file-pod -- sh -c 'echo Game: $(cat /config/game.properties); echo UI: $(cat /config/ui.properties)'`
1. Delete: `kubectl delete -f config_map.yaml`