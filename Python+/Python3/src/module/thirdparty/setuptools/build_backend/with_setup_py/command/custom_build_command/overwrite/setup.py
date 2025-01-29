from setuptools import setup, find_packages, Command, Distribution


class MyBuildCommand(Command):
    user_options = []

    def __init__(self, dist: Distribution):
        super().__init__(dist)
        self.build_base = 'build'
        self.build_lib = 'build/lib'
        self.plat_name = 'linux'
        self.build_scripts = None

    def initialize_options(self):
        print("===== Initializing options...")

    def finalize_options(self):
        print("===== Finalizing options...")

    def run(self):
        print("===== Running the command...")


setup(
    name="CustomBuildCommand",
    version="0.7.7",
    packages=find_packages(),
    cmdclass={
        'build': MyBuildCommand,
    },
)
