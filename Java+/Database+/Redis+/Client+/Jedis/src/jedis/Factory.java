package jedis;

import redis.clients.jedis.JedisPool;

public class Factory {
    public static JedisPool newPool(int port, String password) {
        return new JedisPool("localhost", port, null, password);
    }
}
