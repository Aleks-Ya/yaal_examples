import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()


def parse_requirements(filename: str) -> list[str]:
    with open(filename) as f:
        return f.read().splitlines()


requirements = parse_requirements('requirements.txt')
print(f"Requirements: {requirements}")
setuptools.setup(
    name="example_pkg_yaal",
    version="0.0.2",
    author="Example Author",
    author_email="author@example.com",
    description="A small example package",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/pypa/sampleproject",
    packages=setuptools.find_packages(),
    install_requires=requirements,
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ]
)
