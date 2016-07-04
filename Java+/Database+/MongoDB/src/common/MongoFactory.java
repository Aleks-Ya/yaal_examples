package common;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

/**
 * @author Yablokov Aleksey
 */
public class MongoFactory {
    private MongoDatabase db;
    private MongoClient mongoClient;
    private MongodProcess mongod;
    private MongodExecutable mongodExecutable;
    private MongodStarter starter;
    private IMongodConfig mongodConfig;
    private int port = 20029;
    private String localhost = "localhost";
    private String databaseName = "test";

    public MongoFactory() throws IOException {
        mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();

        mongoClient = new MongoClient(localhost, port);
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

    public IMongodConfig getMongodConfig() {
        return mongodConfig;
    }

    public int getPort() {
        return port;
    }

    public String getLocalhost() {
        return localhost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void stop() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
