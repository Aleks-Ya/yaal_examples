import os
from pathlib import Path


class DirReader:
    # Directories skipped during the walk: build output, caches, VCS and IDE metadata.
    EXCLUDED_DIRS: set[str] = {'.git', '.idea', '.venv', 'venv', '.gradle', 'build', 'out', 'target',
                               '__pycache__', 'node_modules', 'cdk.out', '.tox', 'bundled_dependencies',
                               '.pytest_cache', '.metadata', '.recommenders'}

    def __init__(self, base_dir: Path):
        self.__base_dir = base_dir

    def get_all_files(self) -> list[Path]:
        all_files: list[Path] = []
        for dir_path, dir_names, file_names in os.walk(self.__base_dir):
            # In-place assignment is what makes os.walk skip descending into these.
            dir_names[:] = [d for d in dir_names if d not in self.EXCLUDED_DIRS]
            current_dir: Path = Path(dir_path)
            all_files.extend(current_dir / file_name for file_name in file_names)
        return all_files
