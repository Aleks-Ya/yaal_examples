# Change log format in basicConfig()
import logging
from logging import Logger

logging.basicConfig(format='message=%(message)s\n'
                           'name=%(name)s\n'
                           'filename=%(filename)s\n'
                           'funcName=%(funcName)s\n'
                           'module=%(module)s\n'
                           'pathname=%(pathname)s\n')


def root_logger_function():
    root_logger: Logger = logging.getLogger()
    root_logger.error("From root_logger")


def name_logger_function():
    name_logger: Logger = logging.getLogger(__name__)
    name_logger.error("From name_logger")


def package_logger_function():
    package_logger: Logger = logging.getLogger(__package__)
    package_logger.error("From package_logger")


def file_logger_function():
    file_logger: Logger = logging.getLogger(__file__)
    file_logger.error("From file_logger")


root_logger_function()
name_logger_function()
package_logger_function()
file_logger_function()
