from pathlib import Path

from seedir import seedir

folder: Path = Path.cwd().parent
seedir(folder)
