import yaml
from aqt.utils import showInfo


def use_from_client():
    showInfo("pyyaml from numpy_client: %s" % yaml.safe_load("person: John"))
