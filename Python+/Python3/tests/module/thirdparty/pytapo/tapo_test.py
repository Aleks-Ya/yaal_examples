from pytapo import Tapo


def test_get_basic_info(tapo: Tapo) -> None:
    info: dict = tapo.getBasicInfo()
    device_info: dict = info["device_info"]
    basic_info: dict = device_info["basic_info"]
    print(basic_info)


def test_get_privacy_mode(tapo: Tapo) -> None:
    info: dict = tapo.getPrivacyMode()
    enabled: str = info["enabled"]
    print(enabled)
