from ._common.disable import enabled

if enabled():
    # noinspection PyUnresolvedReferences
    from . import profile_will_close_hook

if enabled():
    # noinspection PyUnresolvedReferences
    from . import error_exit_anki_editor_hidden