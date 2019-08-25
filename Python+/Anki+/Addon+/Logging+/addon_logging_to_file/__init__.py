import logging
import os
from pathlib import Path

init_py_file = Path(__file__)
addon_dir=init_py_file.parent
addon_name = addon_dir.name

logger=logging.getLogger(addon_name)
logger.setLevel(logging.DEBUG)

log_file = Path(addon_dir, addon_name + '.log')
handler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
logger.addHandler(handler)

logger.info('Info Anki Plugin!')
logger.debug('Debug Anki Plugin!')
logger.error('Error Anki Plugin!')
logger.critical('Critical Anki Plugin!')
logger.warning('Warning Anki Plugin!')
