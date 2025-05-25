# CythonAddon

Build SO files: `python setup.py build_ext --inplace`
Build distributive: `python setup.py build_ext --inplace dist`

## Errors

### ModuleNotFoundError

Command: start Anki
Message: `ModuleNotFoundError: No module named 'Cython-CythonAddon-src.formatter.strings'`
Cause: so-files are not built
Solution: build so-files `python setup.py build_ext --inplace`

