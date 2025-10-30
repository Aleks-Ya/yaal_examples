# Change log format in basicConfig() with extra variables
import logging
from logging import Logger

FORMAT: str = '%(asctime)-15s %(clientip)s %(user)-8s %(message)s'
logging.basicConfig(format=FORMAT)
d: dict[str, str] = {'clientip': '192.168.0.1', 'user': 'fbloggs'}
log: Logger = logging.getLogger('tcpserver')
log.warning('Protocol problem: %s', 'connection reset', extra=d)
