from configparser import ConfigParser

config = ConfigParser()
config.read('config.ini')

name = config['DEFAULT']['person.name']
age = config['DEFAULT'].getint('person.age')
department = config['DEFAULT']['department']
opened = config['DEFAULT'].getboolean('opened')

assert name == 'John'
assert age == 30
assert department == 'Sales'
assert opened
