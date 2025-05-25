from logging import Logger
import os

from aqt.addons import AddonManager


def get_addon_logger() -> Logger:
    addon_name: str = os.path.basename(os.path.dirname(os.path.dirname(__file__)))
    log: Logger = AddonManager.get_logger(addon_name)
    log.info(f"Logger initialized for addon {addon_name}")
    return log
