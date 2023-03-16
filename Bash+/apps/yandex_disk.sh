set -e
dir=/media/aleks/ADATA/yandex-disk
cmd=$1
echo "Target dir: $dir"
echo "Command: $cmd"
if [ ! -z "$(ls -A $dir)" ]; then
	size=$(du -sb $dir | cut -f1)
	echo "Target dir '$dir' size is $size bytes"
	if (( $size < 1000000000 )); then
		echo "Target dir '$dir' is not mounted (small size $size bytes)"
		exit 1
	fi		
	echo "Running Yandex Disk..."
	yandex-disk $cmd --dir=$dir --exclude-dirs=Lubov,no_sync/Lubov_2021-07-16
	echo "Yandex Disk has finished." 
else 
	echo "Target dir '$dir' is not mounted (empty)"
	exit 1
fi