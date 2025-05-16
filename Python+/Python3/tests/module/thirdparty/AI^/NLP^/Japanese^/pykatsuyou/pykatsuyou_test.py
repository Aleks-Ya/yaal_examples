from tabulate import tabulate
from pykatsuyou import getInflections
from igo.tagger import Tagger


def test_pykatsuyou():
    tt: Tagger = Tagger()  # Optional

    data = getInflections('する', jsonIndent=2, tt=tt)
    print(data['json'])
    print(data['list'])

    table = getInflections('行く', dataframe=True)
    print(tabulate(table, headers='keys', tablefmt='pretty'))
