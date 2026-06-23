import pytest
from anytree import Node


@pytest.fixture
def udo() -> Node:
    return Node("Udo")


@pytest.fixture
def marc(udo: Node) -> Node:
    return Node("Marc", parent=udo)


@pytest.fixture
def lian(marc: Node) -> Node:
    return Node("Lian", parent=marc)


@pytest.fixture
def dan(udo: Node) -> Node:
    return Node("Dan", parent=udo)


@pytest.fixture
def jet(dan: Node) -> Node:
    return Node("Jet", parent=dan)


@pytest.fixture
def jan(dan: Node) -> Node:
    return Node("Jan", parent=dan)


@pytest.fixture
def joe(dan: Node) -> Node:
    return Node("Joe", parent=dan)


@pytest.fixture
def tree(udo: Node, marc: Node, lian: Node, dan: Node, jet: Node, jan: Node, joe: Node) -> Node:
    return udo
