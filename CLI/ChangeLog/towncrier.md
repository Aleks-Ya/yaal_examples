# towncrier CLI

Site: https://towncrier.readthedocs.io
GitHub: https://github.com/twisted/towncrier

Install: `pip install towncrier`

Help: `towncrier --help`
Help for command: `towncrier create --help`
Version: `towncrier --version`

Add a news fragment without issue ID ("orphan fragment"): `towncrier create +.feature.rst`
Add a news fragment with given content: `towncrier create -c "Add new abilities" +.feature.rst`

Find news fragments in current dir: `towncrier check`

Print a draft of change log: `towncrier build --draft --version 1.0.0 --date 2024-06-07`
