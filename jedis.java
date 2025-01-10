import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisExample {

    public static void main(String[] args) {
        // Redis 连接配置
        String redisHost = "localhost";  // Redis 服务器地址
        int redisPort = 6379;           // Redis 端口号
        String redisPassword = null;    // Redis 密码，如果没有密码则设为 null
        int redisDatabase = 0;          // Redis 数据库索引（默认为 0）

        // 创建 JedisPool 配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);  // 设置最大连接数
        poolConfig.setMaxIdle(64);    // 设置最大空闲连接数
        poolConfig.setMinIdle(16);    // 设置最小空闲连接数

        // 创建 Jedis 连接池
        try (JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 2000, redisPassword)) {
            // 从连接池获取 Jedis 实例
            try (Jedis jedis = jedisPool.getResource()) {
                // 设置一个键值对
                jedis.set("myKey", "Hello Redis");

                // 获取该键的值
                String value = jedis.get("myKey");
                System.out.println("Stored value in Redis: " + value);
            } catch (Exception e) {
                System.err.println("Error interacting with Redis: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error connecting to Redis: " + e.getMessage());
        }
    }
}
