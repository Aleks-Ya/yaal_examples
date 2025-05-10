from apps.libre_office_draw_search.data_types import FodgPath
from apps.libre_office_draw_search.file_discoverer import FileDiscoverer


def test_discover(root_dir_real: FodgPath):
    files: list[FodgPath] = FileDiscoverer.find_draw_files(root_dir_real)
    assert files == [
        root_dir_real / "fodg_parser_test.fodg",
        root_dir_real / "nested" / "buildings.fodg",
    ]
