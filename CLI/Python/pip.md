# pip CLI

## Install
Install: `sudo apt install -y python3-pip`

Upgrade all installed packages:
1. Install: `pip install pip-review`
2. Upgrade: `pip-review --local --auto`

## Commands
Help: `pip -h`
Help about a command: `pip search -h`
Version: `pip --version`
Quite (accepts `-q`, `-qq`, `-qqq`): `pip install -q <package>`
Show version of an installed package: `pip show Fake`
Show installed packages folder: `pip show pip` 
Remove a package: `pip uninstall Faker`
List all installed packages: `pip list`
List all versions of a package: `pip index versions env_logger`

### Install
Downgrade package to older version: `pip install 'pyspark==2.1.2' --force-reinstall`
Install a package: `pip install mem_top`
Install dependencies from file: `pip install -r requirements.txt -r requirements-dev.txt`
Upgrade package to the last version: `pip install -U pyspark`
Upgrade PIP: `pip3 install -U pip`

### Download
Download binary distribution of a package:
- Given package to current dir: `pip download numpy`
- Packages from a file to current dir: `pip download -r requirements.txt`
- Given package to given dir (created if absent): `pip download -d out1 numpy`
Download source distribution of a package: 
- Given package: `pip download --no-binary :all: numpy`

## Error
### Externally managed environment
Coomand: `pip install --update pip`
Message: `error: externally-managed-environment`
Solution: 
Add to `~/.config/pip/pip.conf`:
```
[global]
break-system-packages = true
```
