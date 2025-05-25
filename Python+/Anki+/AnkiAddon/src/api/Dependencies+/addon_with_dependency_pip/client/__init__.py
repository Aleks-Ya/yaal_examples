import yaml
from aqt.utils import show_info


def use_from_client():
    show_info("pyyaml from numpy_client: %s" % yaml.safe_load("person: John"))
