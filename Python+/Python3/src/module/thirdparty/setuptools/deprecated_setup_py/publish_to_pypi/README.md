# Package and upload a Python Distribution Package
Source: [Packaging Python Projects](https://packaging.python.org/tutorials/packaging-projects/)

```
# Choose working directory (directory where "setup.py" is located)
cd example_pkg_yaal/example_pkg_yaal

# Create a virtual environment
sudo apt-get install python3-venv #try "pip install venv" instead
python3 -m venv venv1
source venv1/bin/activate

# Install tools
pip install --upgrade wheel twine

# Increment version (redeploy in PyPI is disabled)
nano setup.py

# Build a distribution package
rm -rf build dist *.egg-info
python setup.py sdist bdist_wheel

# Upload the distribution package to Test PyPI
python -m twine upload --repository-url https://test.pypi.org/legacy/ dist/*

# Test install the distribution package
cd ..
pip install --index-url https://test.pypi.org/simple/ example_pkg_yaal
pip list #see "example_pkg_yaal"

# Clean up
rm -rf build dist *.egg-info venv1
```
