# Upgrade Linux packages
# Run: ./upgrade_linux.sh

set -e

# Ask password upfront
sudo -v

echo "Delete large logs..."
sudo truncate -s 0 /var/log/syslog /var/log/kern.log
echo

echo "Updating APT..."
sudo apt update
sudo apt upgrade -y
sudo apt autoremove -y --purge
sudo apt clean
echo

echo "Updating SNAP..."
sudo snap refresh
echo

echo "Updating FlatPak..."
flatpak update -y
flatpak uninstall --unused -y
sudo flatpak repair
echo

echo "Updating SdkMan..."
source "$SDKMAN_DIR/bin/sdkman-init.sh"
sdk selfupdate
sdk update
sdk flush
echo

echo "Updating Postman"
~/pr/home/yaal_examples/CLI/Postman/deploy_postman_git_locally.sh
echo

echo "Updating PIP..."
python -m pip install --upgrade pip
python -m pip cache purge
echo

# Skip because of "The package was installed by brew. You should check if it can uninstall the package."
#echo "Updating PIP3..."
#python3 -m pip install --upgrade pip --break-system-packages
python3 -m pip cache purge
#echo 

/home/aleks/pr/home/yaal_examples/Bash+/apps/upgrade_python_virtual_env.sh python3-examples-3.12.12
# Skip because "No matching distribution found for tensorflow"
#/home/aleks/pr/home/yaal_examples/Bash+/apps/upgrade_python_virtual_env.sh python3-examples-3.14.2 

echo "Updating Coursier..."
cs update
echo

echo "Updating HuggingFace..."
hf update
hf skills update
echo

echo "Updating BREW..."
yes | brew upgrade
echo

echo "DONE"
