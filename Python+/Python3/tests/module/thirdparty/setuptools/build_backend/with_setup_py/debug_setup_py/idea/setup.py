from setuptools import setup, find_packages

import pydevd_pycharm

pydevd_pycharm.settrace('localhost', port=5678, stdoutToServer=True, stderrToServer=True)

setup(
    name="Name From Setup Py",
    version="0.7.7",
    packages=find_packages()
)
