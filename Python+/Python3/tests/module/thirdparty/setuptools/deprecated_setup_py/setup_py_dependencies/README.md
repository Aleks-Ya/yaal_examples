# Build a Python Distribution Package with dependent packages

NOT FINISHED!!!

Source: [Packaging Python Projects](https://packaging.python.org/tutorials/packaging-projects/)

```
# Choose working directory (directory where "setup.py" is located)
cd src/module/thirdparty/setuptools/include_dependencies

# Create a virtual environment
sudo apt install python3-venv #try "pip install venv" instead
python3 -m venv venv1
source venv1/bin/activate

# Install tools
pip install --upgrade pip setuptools wheel twine build

# Build a distribution package
rm -rf build dist *.egg-info
python setup.py sdist bdist_wheel

# Clean up
rm -rf build dist *.egg-info venv1
```
