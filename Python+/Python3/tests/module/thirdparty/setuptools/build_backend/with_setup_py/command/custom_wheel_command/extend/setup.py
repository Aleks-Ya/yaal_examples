from setuptools import setup, find_packages, Command
from setuptools.command.bdist_wheel import bdist_wheel as _bdist_wheel


class MyWheelCommand(_bdist_wheel, Command):
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
    name="CustomWheelCommand",
    version="0.7.7",
    packages=find_packages(),
    cmdclass={
        'bdist_wheel': MyWheelCommand,
    },
)
