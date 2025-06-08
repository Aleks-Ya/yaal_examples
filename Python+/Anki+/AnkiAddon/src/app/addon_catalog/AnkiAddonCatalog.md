# Anki Addon Catalog for Programmers

## TODO
- [ ] Add new fields
    - [ ] Anki Forum page
    - [ ] GitHub
        - [ ] Does repo have tests?

## Create a new version
1. Generate the dataset dir: `python addon_catalog.py`
2. Upload the dataset dir as a new version:
   `kaggle datasets version -p /home/aleks/anki-addon-catalog/dataset -m "Update" -r zip`

## Dataset structure
- anki-web
    - html
        - addons.html
        - addon
            - {addon-id}.html
    - json
        - addon
            - {addon-id}.json
- github
    - {user}
        - {repo}
            - languages.json
- enricher
    - addon
        - {addon-id}.json
- overrides.yaml
- anki-addon-catalog.json
- anki-addon-catalog.md
- anki-addon-catalog.xlsx