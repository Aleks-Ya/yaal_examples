import importlib
from types import ModuleType


def test_import():
    init_module: ModuleType = importlib.import_module('library_module')
    library_module: ModuleType = importlib.import_module('library_module.library')

    func_from_init = getattr(init_module, 'get_number')
    func_from_library = getattr(library_module, 'get_text')

    assert func_from_init() == 3
    assert func_from_library() == "abc"
