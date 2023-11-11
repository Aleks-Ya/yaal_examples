# Add a dynamic field (based on https://ankiweb.net/shared/info/1747470707).
import time

from anki import hooks
from anki.template import TemplateRenderContext


def _avganstime(ctx):
    """The average answer time in seconds of the card in "review" status."""
    item = ctx._col.db.scalar("select avg(time) from revlog where cid = ? and type = 1", ctx._card.id)
    return round(item / 1000, 1) if item else 0


def _tottime(ctx):
    """The total number of minutes spent reviewing the card."""
    item = ctx._col.db.scalar("select sum(time) from revlog where cid = ?", ctx._card.id)
    return round(item / 60000, 1) if item else 0


def _id_mark(id):
    """Show the card creation time in human format."""
    format = "<b>%Y-%m-%d</b> @ %H:%M:%S"
    return time.strftime(format, time.localtime(id / 1000))


def _type_mark(type):
    """Show the card type in human format."""
    types = {0: 'new', 1: 'learn', 2: 'review', 3: 'relearn'}
    return types[type]


def dynFields(text: str, field_name: str, filter_name: str, ctx: TemplateRenderContext) -> str:
    """Set dynamic fields as template filter names."""
    dynamic_fields = {
        # Dynamic fields showing program parameters of the card:
        '@factor': str(ctx._card.factor),
        '@id': str(ctx._card.id),

        # Dynamic fields showing user-defined parameters of the card:
        '@avganstime': str(_avganstime(ctx)),
        '@tottime': str(_tottime(ctx)),

        # Dynamic fields showing marks based on the card parameters:
        '@id!': _id_mark(ctx._card.id),
        '@type!': _type_mark(ctx._card.type)
    }
    return dynamic_fields.get(filter_name, text)


hooks.field_filter.append(dynFields)
