externalResolvers := Seq(
  Resolver.bintrayRepo("givers", "maven")
)

addSbtPlugin("givers.vuefy" % "sbt-vuefy" % "4.0.0")
