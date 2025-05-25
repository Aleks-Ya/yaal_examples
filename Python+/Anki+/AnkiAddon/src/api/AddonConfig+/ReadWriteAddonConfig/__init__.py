# Addon config
import datetime
from typing import Optional, Any, List, Dict

from aqt import mw
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __show_addon_config():
    config: Optional[dict[str, Any]] = mw.addonManager.getConfig(__name__)
    title: str = config['title']
    enabled: bool = config['enabled']
    room: int = config['room']
    fruits: List[str] = config['fruits']
    address: Dict[str, str] = config['address']
    city: str = config['address']['city']
    show_info(f"""
        Title: {title}
        Enabled: {enabled}
        Room: {room}
        Fruits: {fruits}
        Address: {address}
        City: {city}
    """)


def __update_addon_config():
    property_name: str = 'updated'
    module: str = __name__
    config_old: Optional[dict[str, Any]] = mw.addonManager.getConfig(module)
    old_value: str = config_old[property_name]
    config_old[property_name] = str(datetime.datetime.now().replace(microsecond=0))
    mw.addonManager.writeConfig(module, config_old)
    config_new: Optional[dict[str, Any]] = mw.addonManager.getConfig(module)
    new_value: str = config_new[property_name]
    show_info(f"""
        Old value: {old_value}
        New value: {new_value}
    """)


if enabled():
    menu.add_mw_menu_item("Show addon config", __show_addon_config)
    menu.add_mw_menu_item("Update addon config", __update_addon_config)
