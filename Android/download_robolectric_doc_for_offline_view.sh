
httrack --continue "http://robolectric.org/" --ext-depth=1 --can-go-down \
	-O "/media/aleks/DATA/aleks/httrack_offline/Robolectric_Site" \
	"-*" \
	"+robolectric.org/*"  \
	"+fonts.googleapis.com/*" \
	"+cdnjs.cloudflare.com/*"