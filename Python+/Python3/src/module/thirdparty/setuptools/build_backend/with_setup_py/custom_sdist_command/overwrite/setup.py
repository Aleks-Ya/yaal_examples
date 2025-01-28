import os
import shutil
import tarfile

from setuptools import setup, find_packages, Command, Distribution


class MySdistCommand(Command):
    user_options = [
        ('formats=', None, "formats for source distribution (comma-separated list)"),
        ('dist-dir=', 'd', "directory to put the source distribution archive(s) in [default: dist]"),
    ]

    def __init__(self, dist: Distribution):
        super().__init__(dist)
        self.formats = 'gztar'
        self.dist_dir = 'dist'

    def initialize_options(self):
        print("===== Initializing options...")

    def finalize_options(self):
        print("===== Finalizing options...")
        if not self.dist_dir:
            self.dist_dir = 'dist'
        os.makedirs(self.dist_dir, exist_ok=True)

    def make_archive_1(self, base_name, format, root_dir):
        """Create an archive."""
        print(f"Creating archive: {base_name}.{format} in directory: {root_dir}")
        if format == 'gztar':
            with tarfile.open(f"{base_name}.tar.gz", "w:gz") as tar:
                tar.add(root_dir, arcname=os.path.basename(root_dir))
        else:
            raise ValueError(f"Unsupported format: {format}")

    def run(self):
        packages = find_packages()
        print(f"Found packages: {packages}")

        # Prepare source distribution folder
        dist_name = f"{self.distribution.get_name()}-{self.distribution.get_version()}"
        base_dir = os.path.join(self.dist_dir, dist_name)

        if os.path.exists(base_dir):
            shutil.rmtree(base_dir)
        os.makedirs(base_dir, exist_ok=True)

        # Add packages to the source distribution
        for package in packages:
            package_dir = package.replace(".", os.sep)  # Convert package name to directory structure
            if os.path.isdir(package_dir):
                target_dir = os.path.join(base_dir, package_dir)
                os.makedirs(os.path.dirname(target_dir), exist_ok=True)
                shutil.copytree(package_dir, target_dir)
                print(f"Copied package directory: {package_dir} --> {target_dir}")

        # Copy essential files like setup.py and README
        for essential_file in ["setup.py", "README.md", "LICENSE"]:
            if os.path.exists(essential_file):
                shutil.copy2(essential_file, base_dir)
                print(f"Copied essential file: {essential_file} --> {base_dir}")

        # Create archive
        output_filename = os.path.join(self.dist_dir, dist_name)
        self.make_archive_1(output_filename, self.formats, base_dir)
        # self.make_archive(output_filename, self.formats, base_dir)
        print(f"Source distribution created: {output_filename}.{self.formats}")


setup(
    name="CustomSdistCommand",
    version="0.7.7",
    packages=find_packages(),
    cmdclass={
        'sdist': MySdistCommand
    },
)
