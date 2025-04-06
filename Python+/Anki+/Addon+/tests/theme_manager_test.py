from aqt import ProfileManager
from aqt.theme import ThemeManager, Theme
from aqt import colors


def test_apply_style(theme_manager: ThemeManager):
    theme_manager.apply_style()


def test_get_night_mode(theme_manager: ThemeManager):
    assert not theme_manager.get_night_mode()
    assert not theme_manager.night_mode


def test_set_night_mode(theme_manager: ThemeManager, profile_manager: ProfileManager):
    assert not theme_manager.get_night_mode()
    profile_manager.set_theme(Theme.DARK)
    assert not theme_manager.get_night_mode()
    theme_manager.apply_style()
    assert theme_manager.get_night_mode()


def test_var(theme_manager: ThemeManager):
    color: str = theme_manager.var(colors.BORDER_SUBTLE)
    assert color == "#e4e4e4"
