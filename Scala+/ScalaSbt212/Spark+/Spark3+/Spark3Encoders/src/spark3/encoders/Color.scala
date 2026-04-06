package spark3.encoders

sealed trait Color

object Color {
  case object Red extends Color

  case object Green extends Color

  case object Blue extends Color
}