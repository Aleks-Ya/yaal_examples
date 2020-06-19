-- Setup logging
log = require "log"
log.outfile='app.log'
log.level='trace'


function main()
	log.info('Hello, Logger!')
end