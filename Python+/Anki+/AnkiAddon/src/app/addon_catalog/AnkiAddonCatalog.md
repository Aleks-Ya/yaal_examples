# Anki Addon Catalog for Programmers

## TODO
- [ ] Add new fields
    - [ ] Anki Forum page
    - [ ] GitHub
        - [ ] GitHub repo stars number
        - [ ] Last commit to GitHub repo
        - [ ] Does repo have tests?

## Dataset structure
- anki-web
    - html
        - addons.html
        - addon
            - {addon-id}.html
    - json
        - addons.json
        - addon
            - {addon-id}.json
- github
    - {user}
        - {repo}.json
- overrides.yaml
- anki-addon-catalog.json
- anki-addon-catalog.md
- anki-addon-catalog.xml