# HomeAssistant

Timezone: `Asia/Bangkok`
Config dir (created by me): `/home/aleks/home-assistant-config`

1. Run:
	```shell
	docker run -d \
  	--name homeassistant \
  	--privileged \
  	--restart=unless-stopped \
  	-e TZ=Asia/Bangkok \
  	-v /home/aleks/home-assistant-config:/config \
  	-v /run/dbus:/run/dbus:ro \
  	--network=host \
  	ghcr.io/home-assistant/home-assistant:stable
	```
2. Open: http://localhost:8123
