from pathlib import Path

import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()

requirements_path: Path = Path(__file__).parent / 'requirements.txt'
requirements: list[str] = requirements_path.read_text().splitlines()
print(f"Requirements: {requirements}")

setuptools.setup(
    name="my_package_1",
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
