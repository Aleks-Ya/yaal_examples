from logging import Logger

from aqt import mw
from aqt.addons import AddonManager

from ._common.disable import enabled

if enabled():
    # Output file: /home/aleks/.local/share/Anki2/logs/addons/Logging-AddonManager/Logging-AddonManager.log
    module: str = __name__
    logger: Logger = AddonManager.get_logger(module)
    logger.error(f"Addon logger:\n"
                 f"name={logger.name},\n"
                 f"level={logger.level},\n"
                 f"handlers={logger.handlers}")

    # Debug level
    assert not mw.addonManager.is_debug_logging_enabled(module)
    mw.addonManager.toggle_debug_logging(module, True)
    assert mw.addonManager.is_debug_logging_enabled(module)
    logger.debug(f"Debug message enabled")
