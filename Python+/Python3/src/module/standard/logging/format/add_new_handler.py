# Change log format by adding new handler
import logging

logging.basicConfig()

root_logger = logging.getLogger()
root_logger.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
console_handler = logging.StreamHandler()
console_handler.setFormatter(formatter)

root_logger.addHandler(console_handler)
for handler in root_logger.handlers:
    root_logger.removeHandler(handler)

root_logger.error("You can't see me")
root_logger.debug("Debug message")
