# Change log format by updating exists handlers
import logging
from logging import Logger, Formatter

logging.basicConfig()

log: Logger = logging.getLogger()
log.setLevel(logging.DEBUG)

formatter: Formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
for handler in log.handlers:
    handler.setFormatter(formatter)

log.error("Error message")
log.debug("Debug message")
