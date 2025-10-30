# Change log format in basicConfig()
import logging
from logging import Logger

logging.basicConfig(format='%(levelname)s: %(asctime)-15s - %(message)s')

log: Logger = logging.getLogger()
log.error("Message 1")
