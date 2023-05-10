# Joplin

Doc: https://joplinapp.org/terminal

## Install
`sudo snap install joplin`

## Commands
### Info
Help: `joplin help`
Help about a command: `joplin help e2ee`
Status: `joplin status`
Display note content: `joplin cat "060 Что делает команда git add?"`
Create new note (in current notebook): `joplin mknote "Created by terminal"`

## List
List all notebooks: `joplin ls /`
List all notebooks with details: `joplin ls -l /`
List notes in a notebook:
```
joplin use Git
joplin ls
joplin ls *add*
```

### Encryption
E2EE status: `joplin e2ee status`
Decrypt all: `joplin e2ee decrypt`

### Convert HTML to MD
Export single note (JEX format): `joplin export --format jex --note <note> my.jex`
Export single note (HTML format): `joplin export --format Markdown --note <note>`
Export single notebook to MD: `joplin export --format md --notebook Git Git_html`
Export full database: `joplin export --format jex my.jex`
Import as MD: `joplin import --format jex --output-format md my.jex <notebook>`

### Config
Show config properties: `joplin config`

### Sync
Start sync: `joplin sync`