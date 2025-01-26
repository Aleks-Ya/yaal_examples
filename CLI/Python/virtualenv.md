# virtualenv CLI

Install: `pip install virtualenv`
Help: `virtualenv -h`
Version: `virtualenv --version`

Create new virtual environment (installed packages are NOT inherited): `virtualenv venvA`
Create new virtual environment (installed packages are inherited): `virtualenv --system-site-packages venvA`
Activate virtual environment: `source venvA/bin/activate`
Deactivate virtual environment: `deactivate`
