package jedis;

import redis.clients.jedis.JedisPool;

public class Factory {
    public static JedisPool newPool(){
        return new JedisPool("localhost", 6379, null, "pass1");
    }
}
