import os

from setuptools import setup

print("====================================== BEFORE ======================================")
print(f"Current file: {__file__}")
print(f"Current dir: {os.getcwd()}")

setup(
    name="PrintFromSetup",
    version="0.1"
)

print("====================================== AFTER ======================================")
