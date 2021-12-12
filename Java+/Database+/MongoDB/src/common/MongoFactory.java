package common;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class MongoFactory {
    private final MongoDatabase db;
    private final MongoClient mongoClient;
    private final com.mongodb.client.MongoClient mongoClient2;
    private final MongodProcess mongod;
    private final MongodExecutable mongodExecutable;
    private final MongodStarter starter;
    private final ImmutableMongodConfig mongodConfig;
    private final int port = 20029;
    private final String ip = "localhost";
    private final String databaseName = "test";

    public MongoFactory() throws IOException {
        mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();

        mongoClient = new MongoClient(ip, port);
        mongoClient2 = MongoClients.create(String.format("mongodb://%s:%d", ip, port));
        db = mongoClient.getDatabase(databaseName);
    }

    public MongoDatabase getDb() {
        return db;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongodProcess getMongod() {
        return mongod;
    }

    public MongodExecutable getMongodExecutable() {
        return mongodExecutable;
    }

    public MongodStarter getStarter() {
        return starter;
    }

    public ImmutableMongodConfig getMongodConfig() {
        return mongodConfig;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void stop() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    public com.mongodb.client.MongoClient getMongoClient2() {
        return mongoClient2;
    }
}
