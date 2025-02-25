from setuptools import setup
from Cython.Build import cythonize

setup(
    ext_modules=cythonize(
        ["src/helloworld.pyx", "src/formatter/*.pyx"],
        compiler_directives={'language_level': "3"}
    )
)
