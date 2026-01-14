package spray

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val format: RootJsonFormat[City] = jsonFormat2(City.apply)
}
