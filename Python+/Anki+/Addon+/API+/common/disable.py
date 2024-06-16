from logging import Logger
import os

from aqt.addons import AddonManager


# Set "enabled(True)" to enable addon
def enabled(addon_enabled: bool = False) -> bool:
    addon_name: str = os.path.basename(os.path.dirname(os.path.dirname(__file__)))
    log: Logger = AddonManager.get_logger(addon_name)
    log.info(f"\n\n\n{'#' * 100}\nAddon {addon_name} is {'enabled' if addon_enabled else 'disabled'}")
    return addon_enabled
