# Execute code on program exit
import atexit


def exit_handler():
    print('My application is ending!')


atexit.register(exit_handler)

print("The app is working")
