# cythonize CLI

Install: `pip install Cython`

## Commands
Help: `cythonize --help`

Compile a `*.pyx` file to `*.c`: `cythonize hello.pyx`
Compile a `*.py` file to `*.c`: `cythonize hello.py`

Compile a `*.pyx` file to `*.c` and `*.so`: `cythonize -i hello.pyx`
Compile a `*.py` file to `*.c` and `*.so`: `cythonize -i hello.py`
