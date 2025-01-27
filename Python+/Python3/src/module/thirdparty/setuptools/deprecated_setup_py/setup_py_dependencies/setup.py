import setuptools

with open("my_package_1/README.md", "r") as fh:
    long_description = fh.read()


def parse_requirements(filename: str) -> list[str]:
    with open(filename) as f:
        return f.read().splitlines()


def get_branch() -> str:
    from git import Repo
    repo: Repo = Repo("my_package_1", search_parent_directories=True)
    return repo.active_branch.name


requirements = parse_requirements('requirements-setup.txt')
print(f"Requirements: {requirements}")
setuptools.setup(
    name="my_package_1",
    version="0.0.2",
    author="Example Author",
    author_email="author@example.com",
    description=f"Current branch: {get_branch()}",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/pypa/sampleproject",
    packages=setuptools.find_packages(),
    # install_requires=requirements,
    install_requires=["GitPython"],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ]
)
