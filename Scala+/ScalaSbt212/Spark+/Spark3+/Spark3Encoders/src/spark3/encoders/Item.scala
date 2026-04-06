package spark3.encoders

sealed trait Item {
  def name: String
}

object Item {
  case class Defect(name: String, priority: Int) extends Item

  case class Feature(name: String, size: Int) extends Item

  case class Story(name: String, points: Int) extends Item
}
