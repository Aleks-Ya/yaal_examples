import os

from setuptools import setup, find_packages, Command, Distribution


class MyWheelCommand(Command):
    user_options = [
        ("dist-dir=", "d", "directory to put final built distributions in"),
        # ("bdist-dir=", "b", "temporary directory for creating the distribution")
    ]

    def __init__(self, dist: Distribution):
        super().__init__(dist)
        self.dist_dir = 'dist'

    def initialize_options(self):
        print("===== Initializing options...")

    def finalize_options(self):
        print("===== Finalizing options...")

    def run(self):
        print("===== Running the command...")
        # os.makedirs('build/bdist.linux-x86_64', exist_ok=True)
        # os.makedirs('build/lib/my_package_2', exist_ok=True)
        with open('CustomWheelCommand-0.7.7-py3-none-any.whl', 'w') as file:
            pass


setup(
    name="CustomSdistCommand",
    version="0.7.7",
    packages=find_packages(),
    cmdclass={
        'bdist_wheel': MyWheelCommand,
    },
)
