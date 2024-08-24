from anki.cards import Card
from aqt import gui_hooks
from aqt.utils import show_info

from ._common.disable import enabled


def __on_event(card: Card) -> None:
    show_info(f"Card: {card.id}")


if enabled():
    gui_hooks.reviewer_did_show_question.append(__on_event)
