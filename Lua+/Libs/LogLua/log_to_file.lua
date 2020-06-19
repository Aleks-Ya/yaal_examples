-- Log to console and file
log = require "log"
log.outfile='app.log'
log.level='trace'

log.trace('The trace!')
log.debug('The debug!')
log.info('The info!')
log.warn('The warn!')
log.error('The error!')
log.fatal('The fatal!')