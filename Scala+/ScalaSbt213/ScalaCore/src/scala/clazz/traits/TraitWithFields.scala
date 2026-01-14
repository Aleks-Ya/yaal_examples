package scala.clazz.traits

trait TraitWithFields {
  protected val city: String = s"London ${getClass.getSimpleName}"
  protected lazy val street: String = s"Oxford ${getClass.getSimpleName}"
  protected implicit lazy val district: String = s"Westminster ${getClass.getSimpleName}"
}
