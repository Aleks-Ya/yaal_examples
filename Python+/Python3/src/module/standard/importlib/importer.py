import importlib

init_module = importlib.import_module('library_module')
library_module = importlib.import_module('library_module.library')

func_from_init = getattr(init_module, 'get_number')
func_from_library = getattr(library_module, 'get_text')

assert func_from_init() == 3
assert func_from_library() == "abc"
