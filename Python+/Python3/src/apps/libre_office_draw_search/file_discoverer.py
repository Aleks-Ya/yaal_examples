from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath


class FileDiscoverer:

    @staticmethod
    def find_draw_files(root_dir: Path) -> list[FodgPath]:
        return [FodgPath(directory) for directory in list(root_dir.glob('**/*.fodg'))]
