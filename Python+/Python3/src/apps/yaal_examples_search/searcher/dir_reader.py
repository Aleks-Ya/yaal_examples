import subprocess
from pathlib import Path
from subprocess import CompletedProcess


class DirReader:
    def __init__(self, base_dir: Path):
        self.__base_dir = base_dir

    def get_all_files(self) -> list[Path]:
        result: CompletedProcess[str] = subprocess.run(
            ['plocate', '--regexp', f'^{self.__base_dir}/'],
            capture_output=True,
            text=True,
            check=True
        )
        files: list[str] = result.stdout.strip().split('\n')
        return [Path(f) for f in files if f]  # PathFilter out empty strings
