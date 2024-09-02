from datetime import datetime
from zoneinfo import ZoneInfo

zone_info: ZoneInfo = ZoneInfo("localtime")
local_time: datetime = datetime.now(zone_info)
tzname: str = local_time.tzname()
print(tzname)
