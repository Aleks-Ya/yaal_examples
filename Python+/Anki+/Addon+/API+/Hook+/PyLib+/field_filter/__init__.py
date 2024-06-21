# Add a dynamic field (based on https://ankiweb.net/shared/info/1747470707).
import time

from anki import hooks
from anki.template import TemplateRenderContext

from ._common.disable import enabled


def __avganstime(ctx):
    """The average answer time in seconds of the card in "review" status."""
    item = ctx._col.db.scalar("select avg(time) from revlog where cid = ? and type = 1", ctx._card.id)
    return round(item / 1000, 1) if item else 0


def __tottime(ctx):
    """The total number of minutes spent reviewing the card."""
    item = ctx._col.db.scalar("select sum(time) from revlog where cid = ?", ctx._card.id)
    return round(item / 60000, 1) if item else 0


def __id_mark(id):
    """Show the card creation time in human format."""
    format = "<b>%Y-%m-%d</b> @ %H:%M:%S"
    return time.strftime(format, time.localtime(id / 1000))


def __type_mark(type):
    """Show the card type in human format."""
    types = {0: 'new', 1: 'learn', 2: 'review', 3: 'relearn'}
    return types[type]


def __dynFields(text: str, field_name: str, filter_name: str, ctx: TemplateRenderContext) -> str:
    """Set dynamic fields as template filter names."""
    dynamic_fields = {
        # Dynamic fields showing program parameters of the card:
        '@factor': str(ctx._card.factor),
        '@id': str(ctx._card.id),

        # Dynamic fields showing user-defined parameters of the card:
        '@avganstime': str(__avganstime(ctx)),
        '@tottime': str(__tottime(ctx)),

        # Dynamic fields showing marks based on the card parameters:
        '@id!': __id_mark(ctx._card.id),
        '@type!': __type_mark(ctx._card.type)
    }
    return dynamic_fields.get(filter_name, text)


if enabled():
    hooks.field_filter.append(__dynFields)
