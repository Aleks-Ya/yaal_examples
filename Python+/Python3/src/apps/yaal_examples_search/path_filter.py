from pathlib import Path


class PathFilter:
    def __init__(self, base_dir: Path):
        self.__base_dir = base_dir

    @staticmethod
    def filter(all_files: list[Path]) -> list[Path]:
        temp_dir_patterns: set[str] = {'.git', '.idea', '.venv', '.gradle', 'build', 'out', 'target', '__pycache__',
                                       'node_modules', 'cdk.out'}
        print(f'Skip dirs: {temp_dir_patterns}')
        return [d for d in all_files if not any(part in temp_dir_patterns for part in d.parts)]
