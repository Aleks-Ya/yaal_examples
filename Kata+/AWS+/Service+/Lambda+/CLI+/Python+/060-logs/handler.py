import logging
from logging import Logger

def lambda_handler(event, context):
	logger: Logger = logging.getLogger()
	logger.setLevel(logging.DEBUG)
	logger.critical('Critical logging message')
	logger.error('Error logging message')
	logger.warning('Warning logging message')
	logger.info('Info logging message')
	logger.debug('Debug logging message')
	return "Finished"