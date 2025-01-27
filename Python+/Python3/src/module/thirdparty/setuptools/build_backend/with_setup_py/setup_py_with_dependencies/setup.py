import humanize
from setuptools import setup, find_packages

size: str = humanize.naturalsize(1_000_000)

setup(
    name=f"SetupPyDependencies-{size}",
    version="0.3.3",
    packages=find_packages(),
)
