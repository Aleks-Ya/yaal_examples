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

echo "Updating PIP..."
pip install --upgrade pip
echo

echo "Updating PIP3..."
pip3 install --upgrade pip
echo 

echo "Updating SdkMan..."
source "$SDKMAN_DIR/bin/sdkman-init.sh"
sdk selfupdate
sdk update
echo

echo "Updating Coursier..."
cs update
echo

echo "DONE"
