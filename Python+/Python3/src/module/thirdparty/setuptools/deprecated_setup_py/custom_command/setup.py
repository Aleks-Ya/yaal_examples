import setuptools
from setuptools import Command


# Run: python setup.py custom1 custom2
class MyCustom1Command(Command):
    user_options = []

    def initialize_options(self):
        print("1: Initializing options...")

    def finalize_options(self):
        print("1: Finalizing options...")

    def run(self):
        print("1: Running the command...")


class MyCustom2Command(Command):
    user_options = []

    def initialize_options(self):
        print("2: Initializing options...")

    def finalize_options(self):
        print("2: Finalizing options...")

    def run(self):
        print("2: Running the command...")


setuptools.setup(
    cmdclass={
        'custom1': MyCustom1Command,
        'custom2': MyCustom2Command,
    },
)
