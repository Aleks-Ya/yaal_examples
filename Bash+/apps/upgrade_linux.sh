# Upgrade Linux packages
# Run: ./upgrade_linux.sh

set -e

echo "Updating APT..."
sudo apt update
sudo apt upgrade -y
sudo apt autoremove -y
echo

echo "Updating SNAP..."
sudo snap refresh
echo

echo "Updating BREW..."
brew upgrade
echo

echo "Updating FlatPak..."
flatpak update -y
echo

echo "Updating SdkMan..."
source "$SDKMAN_DIR/bin/sdkman-init.sh"
sdk selfupdate
sdk update
echo

echo "Updating PIP..."
python -m pip install --upgrade pip
echo

# Skip because of "The package was installed by brew. You should check if it can uninstall the package."
#echo "Updating PIP3..."
#python3 -m pip install --upgrade pip --break-system-packages
#echo 

/home/aleks/pr/home/yaal_examples/Bash+/apps/upgrade_python_virtual_env.sh python3-examples-3.12.12
# Skip because "No matching distribution found for tensorflow"
#/home/aleks/pr/home/yaal_examples/Bash+/apps/upgrade_python_virtual_env.sh python3-examples-3.14.2 

echo "Updating Coursier..."
cs update
echo

echo "DONE"
