# huggingface-cli CLI

Install: `pip install -U "huggingface_hub[cli]"`

Help about a command: `huggingface-cli upload-large-folder --help`

Login: `huggingface-cli login`

Upload a folder (small): `huggingface-cli upload Ya-Alex/anki-addons . --repo-type=dataset`
Upload a folder (large): `huggingface-cli upload-large-folder Ya-Alex/anki-addons . --repo-type=dataset --num-workers=16`

Clone repo: `git clone https://huggingface.co/datasets/Ya-Alex/anki-addons`