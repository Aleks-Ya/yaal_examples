# python CLI

List global site-packages ("dist-packages") directories:
```
# In sys.path:
python -m site
# Another method:
python -c "import site; print(site.getsitepackages())"
```
Show installed version of a module:
```
python3 -m pip show my-module
```
Install specific version of a module:
```
python3 -m pip install mkdocs-material==5.1.5 --force-reinstall
```
Install package using NOT installed PIP from a package:
```
python pip-20.1-py2.py3-none-any.whl/pip install --no-index setuptools-46.1.3.zip
```
