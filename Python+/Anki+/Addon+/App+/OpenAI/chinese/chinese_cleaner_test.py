from typing import Any

from common.config import LanguageAiConfig


def test_clean_text():
    config_dict: dict[str, Any] = {
        "openai-client": {
            "token-path": "$HOME/.openai/token.txt",
            "model": "gpt-4o",
            "timeout_sec": 120
        }}
    config: LanguageAiConfig = LanguageAiConfig(config_dict)
    from chinese.chinese_cleaner import ChineseCleaner
    cleaner: ChineseCleaner = ChineseCleaner(config)
    text: str = """
                1. The workshop has shut down and the workers are unemployed.
                工厂关闭后工人失业了。
                2. Workshop conditions are often noisy and dirty.
                工场的环境经常是既嘈杂又肮脏。
                """
    from anki.notes import NoteId
    cleaned_text: str = cleaner.clean_field(text, "English", "English", NoteId(1))
    print(cleaned_text)
    assert "The workshop has shut down and the workers are unemployed." in cleaned_text
    assert "Workshop conditions are often noisy and dirty." in cleaned_text
    assert "工厂关闭后工人失业了" not in cleaned_text
    assert "工场的环境经常是既嘈杂又肮脏。" not in cleaned_text
