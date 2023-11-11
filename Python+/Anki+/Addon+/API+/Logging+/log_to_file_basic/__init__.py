import logging
import os

addon_dir = os.path.dirname(__file__)
addon_name: str = addon_dir.name
log_file = os.path.join(addon_dir, f"{addon_name}.log")
logging.basicConfig(filename=log_file, level=logging.DEBUG,
                    format='%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s')

logging.info(f'Logger is configured: file={log_file}')

logging.info('Info Anki Plugin!')
logging.debug('Debug Anki Plugin!')
logging.error('Error Anki Plugin!')
logging.critical('Critical Anki Plugin!')
logging.warning('Warning Anki Plugin!')
