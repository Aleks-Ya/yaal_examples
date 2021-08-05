# Create symbolic links to "Code Ready Containers" (CRC) directories located on flash drive
set -e

crc_dir_src=/home/aleks/.crc
cache_dir_link="${crc_dir_src}/cache"
machines_dir_link="${crc_dir_src}/machines"

crc_dir_dest=/media/aleks/ADATA/CRC/.crc
cache_dir_dest="${crc_dir_dest}/cache"
machines_dir_dest="${crc_dir_dest}/machines"

if [ ! -d "$crc_dir_dest" ]
then
	echo "FAIL: '$crc_dir_dest' absents. Insert the flash drive."
	exit 1
fi

if [ ! -d "$cache_dir_link" ]
then
  echo "Creating symlink from '$cache_dir_dest' to '$crc_dir_src'"
	ln -s $cache_dir_dest $crc_dir_src
fi

if [ ! -d "$machines_dir_link" ]
then
  echo "Creating symlink from '$machines_dir_dest' to '$crc_dir_src'"
	ln -s $machines_dir_dest $crc_dir_src
fi

echo "Symlinks: $(ls -l $crc_dir_src)"
