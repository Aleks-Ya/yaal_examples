# Use Optional

from typing import Optional

notNoneOpt: Optional[int] = 2
assert notNoneOpt is not None
assert notNoneOpt * 3 == 6

noneOpt: Optional[int] = None
assert noneOpt is None
