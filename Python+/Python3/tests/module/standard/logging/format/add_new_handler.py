# Change log format by adding new handler
import logging
from logging import Logger, Formatter, StreamHandler

logging.basicConfig()

log: Logger = logging.getLogger()
log.setLevel(logging.DEBUG)

formatter: Formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
console_handler: StreamHandler = logging.StreamHandler()
console_handler.setFormatter(formatter)

log.addHandler(console_handler)
for handler in log.handlers:
    log.removeHandler(handler)

log.error("You can't see me")
log.debug("Debug message")
