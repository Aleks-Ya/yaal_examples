set -e
dir=/media/aleks/ADATA/yandex-disk
if [ ! -z "$(ls -A $dir)" ]; then
	size=$(du -sb $dir | cut -f1)
	echo "Target dir '$dir' size is $size bytes"
	if (( $size < 1000000000 )); then
		echo "Target dir '$dir' is not mounted (small size $size bytes)"
		exit 1
	fi		
	echo "Running Yandex Disk sync..."
	yandex-disk sync --dir=$dir --exclude-dirs=Lubov
	echo "Yandex Disk synced." 
else 
	echo "Target dir '$dir' is not mounted (empty)"
	exit 1
fi