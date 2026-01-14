package jackson.yaml

import jackson.yaml.Types.{Age, Name}

object Types {
  type Name = String
  type Age = Int
}

case class PersonTyped(name: Name, age: Age)