# pip CLI

Help: `pip -h`
Version: `pip --version`
Downgrade package to older version: `pip install 'pyspark==2.1.2' --force-reinstall`
Install dependencies from requirements.txt: `pip install -r requirements.txt`
Show version of an installed package: `pip show Fake`
Show installed packages folder: `pip show pip` 
Remove a package: `pip uninstall Faker`
List all installed packages: `pip list`
Upgrade package to the last version: `pip install -U pyspark`
Upgrade PIP: `pip3 install -U pip`

Upgrade all installed packages:
1. Install: `pip install pip-review`
2. Upgrade: `pip-review --local --auto`
