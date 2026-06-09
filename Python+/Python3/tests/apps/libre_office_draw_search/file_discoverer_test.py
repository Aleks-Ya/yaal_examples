from apps.libre_office_draw_search.data_types import OdgPath
from apps.libre_office_draw_search.file_discoverer import FileDiscoverer


def test_discover(real_root_dir: OdgPath, real_buildings_file: OdgPath, real_odg_parser_file: OdgPath):
    files: list[OdgPath] = FileDiscoverer.find_draw_files(real_root_dir)
    assert files == [real_odg_parser_file, real_buildings_file]
