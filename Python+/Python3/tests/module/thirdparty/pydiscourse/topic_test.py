from pydiscourse import DiscourseClient


def test_hot_topics(anonymous_anki_forum_client: DiscourseClient):
    topics: dict = anonymous_anki_forum_client.hot_topics()

    assert topics is not None
    assert "topic_list" in topics
    topic_attributes: dict = topics["topic_list"]
    assert "topics" in topic_attributes
    topics_2: list[dict] = topic_attributes["topics"]
    assert len(topics_2) > 0

    print("\nLatest topics from Anki forums:")
    for topic in topics_2[:5]:
        print(f"- {topic['title']} (ID: {topic['id']}, Last post: {topic['last_posted_at']})")


def test_topic_by_id(anonymous_anki_forum_client: DiscourseClient):
    slug: str = "note-size-addon-support"
    topic_id: int = 46001
    topic: dict = anonymous_anki_forum_client.topic(slug, topic_id)
    assert topic is not None
    print(f"- {topic['title']} (ID: {topic['id']}, Last post: {topic['last_posted_at']})")


def test_number_of_posts_in_topic(anonymous_anki_forum_client: DiscourseClient):
    slug: str = "note-size-addon-support"
    topic_id: int = 46001
    topic: dict = anonymous_anki_forum_client.topic(slug, topic_id)
    num_posts: int = topic["posts_count"]
    print(f"Number of posts in topic {topic_id}: {num_posts}")
    assert num_posts > 0
