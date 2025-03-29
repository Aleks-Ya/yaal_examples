import re


class StringCleaner:
    __replacements: dict[str, str] = {
        r"<br>\s*<div": "<div",
        r"</div>\s*<br>": "</div>",
        r"<br>\s*</div>": "</div>",
        r"</li>\s*<br>": "</li>",
        r"<br>\s*<li>": "<li>",
        r"<br>\s*<ol>": "<ol>",
        r"</ol>\s*<br>": "</ol>",
        r"<h5>近义词[:：]?</h5>": "<div>Synonyms:</div>",
        r"<h5>反义词[:：]?</h5>": "<div>Antonyms:</div>",
        r"<h5>联想词</h5>": "<div>Associated words:</div>",
        r"<h5>解析[:：]?</h5>": "<div>Analysis:</div>",
        r"&nbsp;": " ",
        r"<em><em>": "<em>",
        r"</em></em>": "</em>",
        r'<div\s*class="sen_cn">.*</div>': "",  # Chinese characters
        r"[\u4e00-\u9fff\u3400-\u4dbf\u2e80-\u2eff\u3000-\u303f\uff00-\uffef]": "",  # Chinese characters
        r'<br>\s*<br>\s*<i>': "<br><i>",
        r'<br/>\s*<br/>\s*<i>': "<br/><i>",
        r"<div></div>": "",
        r"“.*”": "",
        r"‘.*’": "",
        r"”.*“": "",
        r"—": "",
        r'<div\s*class="sen_en">\d{1,2}\.': '<div class="sen_en">',
        r"<br>\s*<br>\s*</div>": "<br></div>",
        r"<div>\s*<br>\s*</div>": "",
        r"<br>\s*<h2>": "<h2>",
        r"</h2>\s*<br>": "</h2>",
        r"<br>\s*<ul>": "<ul>",
        r"</ul>\s*<br>": "</ul>"
    }

    def clean(self, old_value: str) -> str:
        new_value: str = old_value
        for regex, replacement in self.__replacements.items():
            new_value = re.sub(regex, replacement, new_value).strip()
        return new_value
