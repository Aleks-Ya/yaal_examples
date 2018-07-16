import logging

logging.basicConfig()

root_logger = logging.getLogger()
root_logger.debug("You can't see me")

root_logger.setLevel(logging.DEBUG)
root_logger.debug("Debug message")
