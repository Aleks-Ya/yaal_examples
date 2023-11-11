import logging
from logging import Logger, FileHandler, Formatter
from pathlib import Path

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name

logger: Logger = logging.getLogger(addon_name)
logger.setLevel(logging.DEBUG)

log_file: Path = Path(addon_dir, addon_name + '.log')
handler: FileHandler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(Formatter('%(asctime)s %(levelname)s %(message)s'))
logger.addHandler(handler)

logger.info('Info Anki Plugin!')
logger.debug('Debug Anki Plugin!')
logger.error('Error Anki Plugin!')
logger.critical('Critical Anki Plugin!')
logger.warning('Warning Anki Plugin!')
