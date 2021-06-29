import logging

# Get root logger
root_logger: logging.RootLogger = logging.getLogger()

# Get logger
a_logger: logging.Logger = logging.getLogger('a')
ab_logger: logging.Logger = logging.getLogger('a.b')

# Get child logger
child_logger = a_logger.getChild("b")
assert child_logger == ab_logger

# Get all loggers
manager: logging.Manager = logging.Logger.manager
all_loggers = manager.loggerDict
print(all_loggers)
