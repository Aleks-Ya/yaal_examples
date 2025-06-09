# Anki Addon Catalog for Programmers

## TODO
- [ ] Add new fields
    - [ ] GitHub
        - [ ] Does repo have tests?
        - [ ] Does repo have actions?
- [ ] Extract codebase to a dedicated repo
- [ ] Use HEAD requests for checking resource updates
- [ ] Create a support page in Anki Forum

## Create a new version
1. Generate the dataset dir: `python addon_catalog.py`
2. Upload the dataset dir as a new version:
   `kaggle datasets version -p /home/aleks/anki-addon-catalog/dataset -m "Update" -r zip`

## Dataset structure
- dataset-metadata.json
- anki-addon-catalog.json
- anki-addon-catalog.md
- anki-addon-catalog.xlsx
- raw
    - 1-anki-web
        - html
            - addons.html
            - addon
                - {addon-id}.html
        - json
            - addon
                - {addon-id}.json
    - 2-github
        - {user}
            - {repo}
                - languages.json
    - 3-enricher
        - addon
            - {addon-id}.json
    - 4-overrider
        - overrides.yaml