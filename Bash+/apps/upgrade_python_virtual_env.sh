# Upgrade packages in a Python virtual environment
# Run: "./upgrade_python_virtual_env.sh python3-examples-3.12.12"

set -e

PYENV_NAME="${1}" 
echo "Upgrading Python packages in virtual env '$PYENV_NAME'..."
eval "$(pyenv init - bash)"
pyenv activate $PYENV_NAME
pip install -U -q pip -r /home/aleks/pr/home/yaal_examples/Python+/Python3/requirements.txt
pyenv deactivate
echo 
