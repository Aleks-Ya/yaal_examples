package controllers

import play.api.Configuration

private object ServerConfiguration {
  val SERVER_CONFIGURATION_OVERRIDE: Configuration = Configuration(
    "https.port" -> "9443",
    "http.port" -> "disabled",
    "play.server.https.keyStore.path" -> getClass.getClassLoader.getResource("server_keystore.jks").getFile,
    "play.server.https.keyStore.type" -> "JKS",
    "play.server.https.keyStore.password" -> "098765"
  )
}
