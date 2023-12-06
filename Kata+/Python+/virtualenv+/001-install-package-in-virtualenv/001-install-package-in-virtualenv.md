# 001-install-package-in-virtualenv

## Setup
1. Install `virtualenv` package: `pip install virtualenv`
2. Create new virtual env: `virtualenv venvA`
3. Activate virtual env: `source venvA/bin/activate`
4. List installed packages: `pip list`
5. Install new package: `pip install psutil`
6. Check installed packages: `pip list`

## Cleanup
1. Deactivate virtual env: `deactivate`
2. Delete the virtual env: `rm -rf venvA`
