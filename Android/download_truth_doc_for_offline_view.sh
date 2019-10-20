
httrack --continue "https://truth.dev/" --ext-depth=1 --can-go-down \
	-O "/media/aleks/DATA/aleks/httrack_offline/Truth_Documentation" \
	"-*" \
	"+truth.dev/*" \
	"+fonts.googleapis.com/*"