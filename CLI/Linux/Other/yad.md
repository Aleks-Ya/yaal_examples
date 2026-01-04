# yad CLI

Install: `sudo apt install yad`

## Show message
HTML formatting: 
```shell
yad --fullscreen --on-top --undecorated --button=OK \
	--text-align=center \
	--text="<span font=\"48\">Idle 5 minutes</span>" 
```

## Show a picture
Full screen: `yad --picture --fullscreen --size=orig --filename=/home/aleks/Pictures/Focus2.png`
