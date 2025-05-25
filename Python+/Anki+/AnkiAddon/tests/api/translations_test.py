from anki.collection import Collection


def test_translate(col: Collection):
    msg: str = col.tr.addons_browse_addons()
    assert msg == "Browse Add-ons"
