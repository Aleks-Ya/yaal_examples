import json
import os
import shutil
import subprocess
from pathlib import Path
from subprocess import CompletedProcess

from setuptools import setup
from Cython.Build import cythonize
from setuptools import Command
from git import TagReference, Repo, Commit


def _read_long_description():
    with open("README.md", "r") as f:
        return f.read()


addon_dir: str = "cython_addon"


def _read_version() -> str:
    version_file: str = os.path.join(os.path.dirname(__file__), addon_dir, 'version.txt')
    with open(version_file, 'r') as f:
        return f.read().strip()


_version = _read_version()
_author = "Alexey Yablokov"


class MakeDistributionCommand(Command):
    user_options = []
    project_dir: Path
    build_dir: Path

    def initialize_options(self):
        self.project_dir: Path = Path(__file__).parent
        self.build_dir: Path = Path(self.project_dir, 'dist')
        if self.build_dir.exists():
            shutil.rmtree(self.build_dir)

    def finalize_options(self):
        # Nothing to finalize
        pass

    def run(self):
        self.__run_unit_tests()
        self.__run_integration_tests()
        self.__package_zip(addon_dir)

    @staticmethod
    def __run_unit_tests():
        print("Running unit tests...")
        result: CompletedProcess[str] = subprocess.run(['tox'], capture_output=True, text=True)
        if result.returncode != 0:
            print(result.stderr)
            print(result.stdout)
            raise SystemExit(result.returncode)

    @staticmethod
    def __run_integration_tests():
        print("Running integration tests...")
        result: CompletedProcess[str] = subprocess.run(
            ['pytest', '-m', 'integration'], capture_output=True, text=True)
        if result.returncode != 0:
            print(f"Integration tests failed with exit code {result.returncode}")
            print(result.stderr)
            print(result.stdout)
            raise SystemExit(result.returncode)

    def __package_zip(self, addon_dir: str):
        print("Packaging...")
        note_size_package_dir: Path = Path(self.project_dir, addon_dir)
        dest_subdir: Path = Path(self.build_dir, addon_dir)
        shutil.copytree(note_size_package_dir, dest_subdir,
                        ignore=shutil.ignore_patterns("*.log", "__pycache__", "meta.json"))
        self.__generate_manifest(dest_subdir)
        self.__copy_file_to_build("LICENSE", dest_subdir)
        self.__copy_file_to_build("README.md", dest_subdir)
        self.__copy_file_to_build("CHANGELOG.md", dest_subdir)
        output_zip: Path = Path(self.build_dir, f'{addon_dir}-{_version}')
        actual_output_zip: Path = Path(shutil.make_archive(str(output_zip), 'zip', dest_subdir))
        renamed_output_zip: Path = Path(actual_output_zip.parent, f"{actual_output_zip.stem}.ankiaddon")
        os.rename(actual_output_zip, renamed_output_zip)
        print(f'Output ZIP: {renamed_output_zip}')

    def __copy_file_to_build(self, filename: str, dest_subdir):
        src: Path = Path(self.project_dir, filename)
        dest: Path = Path(dest_subdir, filename)
        shutil.copyfile(src, dest)

    @staticmethod
    def __generate_manifest(dest_subdir: Path):
        repo: Repo = Repo(".", search_parent_directories=True)
        version: str = f"v{_version}"
        tag: TagReference = repo.tag(version)
        commit: Commit = tag.commit if tag in repo.tags else repo.head.commit
        commit_epoch_sec: int = int(commit.committed_datetime.timestamp())
        draft: dict[str, any] = {
            "name": f"{addon_dir} {version}",
            "package": "1188705668",
            "author": _author,
            "min_point_version": 240401,
            "max_point_version": 241100,
            "human_version": version,
            "homepage": "https://ankiweb.net/shared/info/1188705668",
            "mod": commit_epoch_sec
        }
        path: Path = Path(dest_subdir, 'manifest.json')
        with open(path, 'w') as fp:
            # noinspection PyTypeChecker
            json.dump(draft, fp, indent=2)


setup(
    name="cython_formatter",
    version="1",
    ext_modules=cythonize(
        ["cython_addon/formatter/*.pyx"],
        compiler_directives={'language_level': "3"}
    ),
    packages=list(),
    test_suite="tests",
    cmdclass={
        'dist': MakeDistributionCommand,
    },
)
