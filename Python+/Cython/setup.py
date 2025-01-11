from setuptools import setup
from Cython.Build import cythonize

setup(
    test_suite="tests",
    ext_modules=cythonize(["src/helloworld.pyx", "src/formatter/strings.pyx"])
)
