import logging

root_logger = logging.getLogger()
root_logger.error("I'm the root logger")

ab_logger = logging.getLogger("a.b")
ab_logger.error("I'm the a.b logger")
