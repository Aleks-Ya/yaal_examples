package kafka.admin

import java.util.{Collections, Properties}

import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, NewTopic}

/** Create, delete, list topics with AdminClient. */
object TopicAdminClient {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

    val topic = "topic_admin_client"

    val adminClient = AdminClient.create(props)
    if (isTopicExists(adminClient, topic)) {
      deleteTopic(adminClient, topic)
    }
    createTopic(adminClient, topic)
    assert(isTopicExists(adminClient, topic))
    deleteTopic(adminClient, topic)
    adminClient.close()
  }

  private def isTopicExists(adminClient: AdminClient, topic: String): Boolean = {
    adminClient.listTopics().names().get().contains(topic)
  }

  private def deleteTopic(adminClient: AdminClient, topic: String): Unit = {
    adminClient.deleteTopics(Collections.singletonList(topic))
  }

  private def createTopic(adminClient: AdminClient, topic: String): Unit = {
    val newTopic = new NewTopic(topic, 1, 1.toShort)
    adminClient.createTopics(Collections.singletonList(newTopic))
  }

}
