# Change log format in basicConfig()
import logging

logging.basicConfig(format='%(levelname)s: %(asctime)-15s - %(message)s')

root_logger = logging.getLogger()
root_logger.error("You can't see me")
root_logger.warning("Line 2")
