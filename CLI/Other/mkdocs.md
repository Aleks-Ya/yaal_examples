# mkdocs CLI

Help:
```
mkdocs --help
mkdocs build --help
```
Version:
```
mkdocs --version
```
Create new folder with a project:
```
# In current folder
mkdocs new .
# In "my-project" folder
mkdocs new my-project
```
Run the preview server (in directory with mkdocs.yml) on http://127.0.0.1:8000:
```
mkdocs serve
```
Build documentation site in "site" folder:
```
# Build
mkdocs build
# Build and remote old files
mkdocs build --clean
```
