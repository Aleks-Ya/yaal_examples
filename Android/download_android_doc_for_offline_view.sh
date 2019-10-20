
httrack --continue "https://developer.android.com/docs" --ext-depth=1 --can-go-down \
	-O "/media/aleks/DATA/aleks/httrack_offline/Android_Documentation" \
	"-*" \
	"+developer.android.com/docs/*" \
	"+developer.android.com/guide/*" \
	"+developer.android.com/training/*" \
	"+developer.android.com/jetpack/*" \
	"+developer.android.com/topic/*" \
	"-developer.android.com/reference/*" \
	"-developer.android.com/reference/java/*" \
	"-developer.android.com/reference/javax/*" \
	"-developer.android.com/reference/kotlin/*" \
	"+developer.android.com/samples/*" \
	"+developer.android.com/design/*" \
	"+www.gstatic.com/*" \
	"+i.ytimg.com/*" \
	"+fonts.googleapis.com/*"