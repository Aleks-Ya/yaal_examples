from pathlib import Path

from apps.libre_office_draw_search.data_types import OdgPath


class FileDiscoverer:

    @staticmethod
    def find_draw_files(root_dir: Path) -> list[OdgPath]:
        return [OdgPath(directory) for directory in list(root_dir.glob('**/*.odg'))]

    @staticmethod
    def is_root_available(root_dir: Path) -> bool:
        try:
            if not root_dir.is_dir():
                return False
            return next(root_dir.glob('**/*.odg'), None) is not None
        except OSError:
            return False
