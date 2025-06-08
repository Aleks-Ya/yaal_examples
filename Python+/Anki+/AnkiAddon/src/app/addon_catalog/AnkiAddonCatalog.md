# Anki Addon Catalog for Programmers

## TODO
- [ ] Add new fields
    - [ ] Anki Forum page
    - [ ] GitHub
        - [ ] Last commit to GitHub repo
        - [ ] Does repo have tests?

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
- anki-addon-catalog.xml