class MyException(Exception):
    def __init__(self, location):
        super().__init__(f'Can not connect to {location}')


raise MyException('database')
