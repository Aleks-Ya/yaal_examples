# Change log format by updating exists handlers
import logging
from logging import Logger, Formatter

logging.basicConfig()

root_logger: Logger = logging.getLogger()
root_logger.setLevel(logging.DEBUG)

formatter: Formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
for handler in root_logger.handlers:
    handler.setFormatter(formatter)

root_logger.error("You can't see me")
root_logger.debug("Debug message")
