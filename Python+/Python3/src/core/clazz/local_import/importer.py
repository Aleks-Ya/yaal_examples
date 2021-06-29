# Plain and local imports
from .imported_files.for_plain_import import plain_import_var

print(plain_import_var)


def import_local():
    from .imported_files.for_local_import import local_import_var
    print(local_import_var)
