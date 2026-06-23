from anytree import Node
from anytree.exporter import DictExporter


def test_descendants(tree: Node, marc: Node, lian: Node, dan: Node, jet: Node, jan: Node, joe: Node):
    act_descendants: tuple[Node, ...] = tree.descendants
    exp_descendants: tuple[Node, ...] = (marc, lian, dan, jet, jan, joe)
    assert act_descendants == exp_descendants


def test_custom_attr():
    vehicle: Node = Node("Vehicle", year=2022)
    car: Node = Node("Car", parent=vehicle, year=2021)
    Node("Motorcycle", parent=vehicle, year=2020)
    assert DictExporter().export(vehicle) == {'children': [{'name': 'Car', 'year': 2021},
                                                           {'name': 'Motorcycle', 'year': 2020}],
                                              'name': 'Vehicle',
                                              'year': 2022}
    # noinspection PyUnresolvedReferences
    assert car.year == 2021
    action_car_year: int = getattr(car, "year")
    assert action_car_year == 2021


def test_set_parent_later():
    vehicle: Node = Node("Vehicle")
    car: Node = Node("Car")
    motorcycle: Node = Node("Motorcycle")
    car.parent = vehicle
    motorcycle.parent = vehicle
    assert DictExporter().export(vehicle) == {'children': [{'name': 'Car'},
                                                           {'name': 'Motorcycle'}],
                                              'name': 'Vehicle'}
