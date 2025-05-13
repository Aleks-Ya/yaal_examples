from apps.libre_office_draw_search.data_types import FodgPath
from apps.libre_office_draw_search.file_discoverer import FileDiscoverer


def test_discover(real_root_dir: FodgPath, real_buildings_file: FodgPath, real_fodg_parser_file: FodgPath):
    files: list[FodgPath] = FileDiscoverer.find_draw_files(real_root_dir)
    assert files == [real_fodg_parser_file, real_buildings_file]
