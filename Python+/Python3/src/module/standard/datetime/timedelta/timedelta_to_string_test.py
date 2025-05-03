from datetime import timedelta


def test_str():
    assert str(timedelta(weeks=2)) == '14 days, 0:00:00'
    assert str(timedelta(days=2)) == '2 days, 0:00:00'
    assert str(timedelta(hours=15)) == '15:00:00'
    assert str(timedelta(minutes=15)) == '0:15:00'
    assert str(timedelta(seconds=3)) == '0:00:03'
    assert str(timedelta(milliseconds=5)) == '0:00:00.005000'
    assert str(timedelta(microseconds=5)) == '0:00:00.000005'
    assert str(timedelta()) == '0:00:00'


def test_repr():
    deltas: list[timedelta] = [
        timedelta(weeks=2),
        timedelta(days=2),
        timedelta(hours=15),
        timedelta(minutes=15),
        timedelta(seconds=3),
        timedelta(milliseconds=5),
        timedelta(microseconds=5),
        timedelta()
    ]
    assert str(deltas) == ('[datetime.timedelta(days=14), datetime.timedelta(days=2), '
                           'datetime.timedelta(seconds=54000), datetime.timedelta(seconds=900), '
                           'datetime.timedelta(seconds=3), datetime.timedelta(microseconds=5000), '
                           'datetime.timedelta(microseconds=5), datetime.timedelta(0)]')


def test_without_microseconds():
    td: timedelta = timedelta(weeks=2, days=3, hours=4, minutes=5, seconds=6, milliseconds=7, microseconds=8)
    assert str(td) == '17 days, 4:05:06.007008'
    no_ms_td: timedelta = timedelta(seconds=int(td.total_seconds()))
    assert str(no_ms_td) == '17 days, 4:05:06'


def __format_timedelta(td: timedelta) -> str:
    """Formats a timedelta object into W,D,H,M,S representation."""

    total_seconds: int = int(td.total_seconds())
    weeks, remainder = divmod(total_seconds, 604800)  # 7 days * 24 hours * 60 mins * 60 secs
    days, remainder = divmod(remainder, 86400)  # 24 hours * 60 mins * 60 secs
    hours, remainder = divmod(remainder, 3600)  # 60 mins * 60 secs
    minutes, seconds = divmod(remainder, 60)

    parts = []
    if weeks:
        parts.append(f"{weeks}W")
    if days:
        parts.append(f"{days}D")
    if hours:
        parts.append(f"{hours}H")
    if minutes:
        parts.append(f"{minutes}M")
    if seconds:
        parts.append(f"{seconds}S")

    if not parts:  # Handle zero timedelta
        return "0S"
    return "".join(parts)


def test_short():
    td: timedelta = timedelta(weeks=2, days=3, hours=4, minutes=5, seconds=6, milliseconds=7, microseconds=8)
    s: str = __format_timedelta(td)
    assert s == '2W3D4H5M6S'
