# Python 3

Create virtual environment:

1. Install Ubuntu packages
    1. `krb5-config` for `phoenixdb`: `sudo apt install -y libkrb5-dev`
2. Find the latest version: `pyenv install -l` -> `3.12.5`
3. Install the latest version: `pyenv install 3.12.5`
4. Create a virtual environment: `pyenv virtualenv 3.12.5 python3-examples`
5. Activate the virtual environment: `pyenv activate python3-examples`
6. Install packages: `pip install -r requirements.txt`
7. Configure Idea project to use the virtual environment
