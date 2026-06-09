from pathlib import Path

from apps.libre_office_draw_search.data_types import OdgPath


class FileDiscoverer:

    @staticmethod
    def find_draw_files(root_dir: Path) -> list[OdgPath]:
        return [OdgPath(directory) for directory in list(root_dir.glob('**/*.odg'))]