import logging
from logging import Logger

logging.basicConfig(format='[%(threadName)s] %(message)s')

log: Logger = logging.getLogger()
log.error("Message 1")
