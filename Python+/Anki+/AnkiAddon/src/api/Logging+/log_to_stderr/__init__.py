import sys

from ._common.disable import enabled

if enabled():
    sys.stderr.write("std err\n")
