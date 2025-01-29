from setuptools import setup, find_packages, Command
from setuptools.command.build import build as _build


class MyBuildCommand(_build, Command):
    def __init__(self, dist):
        super().__init__(dist)

    def initialize_options(self):
        super().initialize_options()
        print("===== Initializing options...")

    def finalize_options(self):
        super().finalize_options()
        print("===== Finalizing options...")

    def run(self):
        super().run()
        print("===== Running the command...")


setup(
    name="CustomBuildCommand",
    version="0.7.7",
    packages=find_packages(),
    cmdclass={
        'build': MyBuildCommand,
    },
)
