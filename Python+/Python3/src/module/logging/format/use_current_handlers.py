# Change log format by updating exists handlers
import logging

logging.basicConfig()

root_logger = logging.getLogger()
root_logger.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
for handler in root_logger.handlers:
    handler.setFormatter(formatter)

root_logger.error("You can't see me")
root_logger.debug("Debug message")
