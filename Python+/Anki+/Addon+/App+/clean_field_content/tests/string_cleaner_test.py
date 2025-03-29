import pytest

from ..string_cleaner import StringCleaner


@pytest.fixture
def cleaner() -> StringCleaner:
    return StringCleaner()


def test_clean(cleaner: StringCleaner):
    old_value: str = """
    <div class="se_div">
        <div class="sentenceCon">
            <div id="sentenceSeg"><ol><li><div class="se_li1">
        <div class="sen_en">1.A criminal <mark class="cross-field-highlighter">delinquent</mark> in his youth, Billy spent most of his formative years in and out of foster homes.</div>
        <div class="sen_cn">比利年少时有过一次流氓犯罪记录，导致在他大部分的成长的岁月都辗转于各个不同的收容家庭。</div>
    </div></li><li><div class="se_li1">"""
    act_clean_value: str = cleaner.clean(old_value)
    exp_clean_value: str = """\
<div class="se_div">
        <div class="sentenceCon">
            <div id="sentenceSeg"><ol><li><div class="se_li1">
        <div class="sen_en">A criminal <mark class="cross-field-highlighter">delinquent</mark> in his youth, Billy spent most of his formative years in and out of foster homes.</div>
        
    </div></li><li><div class="se_li1">"""
    assert act_clean_value == exp_clean_value


def test_clean_mix_chinese_and_english(cleaner: StringCleaner):
    old_value: str = """
    <div class="se_div">
        <div class="sentenceCon">
            <div id="sentenceSeg"><ol><li><div class="se_li1">
        <div class="sen_en">1.A criminal <mark class="cross-field-highlighter">delinquent</mark> in his youth.</div>
        <div class="sen_cn">比利年少时有过一次流氓犯罪记录，English 导致在他大部分的成长的岁月都辗转于各个不同的收容家庭。</div>
    </div></li><li><div class="se_li1">"""
    act_clean_value: str = cleaner.clean(old_value)
    exp_clean_value: str = """\
<div class="se_div">
        <div class="sentenceCon">
            <div id="sentenceSeg"><ol><li><div class="se_li1">
        <div class="sen_en">A criminal <mark class="cross-field-highlighter">delinquent</mark> in his youth.</div>
        
    </div></li><li><div class="se_li1">"""
    assert act_clean_value == exp_clean_value


def test_clean_synonyms_generated(cleaner: StringCleaner):
    old_value: str = """<div class="expDiv" id="SYNChild"><!--word-thumbnail-image--><h5>近义词</h5><i class="eudic_wordtype">n.</i> <div class="eudic_wordtype_cont"><a href="http://dict.eudic.net/dicts/en/flower">flower</a></div><br><h5>联想词</h5><div class="eudic_wordtype_cont"><w><a href="http://dict.eudic.net/dicts/en/flower">flower</a><span>花;</span></w><w><a href="http://dict.eudic.net/dicts/en/sunflower">sunflower</a><span>向日葵;</span></w><w><a href="http://dict.eudic.net/dicts/en/opium">opium</a><span>鸦片;</span></w><w><a href="http://dict.eudic.net/dicts/en/buckwheat">buckwheat</a><span>荞麦;</span></w><w><a href="http://dict.eudic.net/dicts/en/lavender">lavender</a><span>淡紫色;</span></w><w><a href="http://dict.eudic.net/dicts/en/sesame">sesame</a><span>芝麻;</span></w><w><a href="http://dict.eudic.net/dicts/en/mustard">mustard</a><span>芥菜;</span></w><w><a href="http://dict.eudic.net/dicts/en/seed">seed</a><span>种子;</span></w><w><a href="http://dict.eudic.net/dicts/en/tulip">tulip</a><span>郁金香;</span></w><w><a href="http://dict.eudic.net/dicts/en/lilac">lilac</a><span>淡紫色;</span></w><w><a href="http://dict.eudic.net/dicts/en/pomegranate">pomegranate</a><span>石榴;</span></w></div><br></div>"""
    act_clean_value: str = cleaner.clean(old_value)
    exp_clean_value: str = """<div class="expDiv" id="SYNChild"><!--word-thumbnail-image--><div>Synonyms:</div><i class="eudic_wordtype">n.</i> <div class="eudic_wordtype_cont"><a href="http://dict.eudic.net/dicts/en/flower">flower</a></div><div>Associated words:</div><div class="eudic_wordtype_cont"><w><a href="http://dict.eudic.net/dicts/en/flower">flower</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/sunflower">sunflower</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/opium">opium</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/buckwheat">buckwheat</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/lavender">lavender</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/sesame">sesame</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/mustard">mustard</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/seed">seed</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/tulip">tulip</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/lilac">lilac</a><span>;</span></w><w><a href="http://dict.eudic.net/dicts/en/pomegranate">pomegranate</a><span>;</span></w></div></div>"""
    assert act_clean_value == exp_clean_value
