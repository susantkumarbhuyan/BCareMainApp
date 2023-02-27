package com.bcareapp.util;

import java.io.IOException;
import org.bson.Document;
import com.bcareapp.constants.MongoDBConstant;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {
	private static MongoClient mongoClient = null;
	private static MongoDatabase db = null;
	private static DB jongoDB = null;
	static {
		initializeDB();
	}

	@SuppressWarnings("deprecation")
	public static void initializeDB() {
		ServerAddress serverAddresses = MongoDBConstant.SERVER_ADDRESSES;
		if (serverAddresses != null) {
			MongoClientOptions options = new MongoClientOptions.Builder().maxConnectionIdleTime(60000)
					.heartbeatSocketTimeout(10000).build();
			mongoClient = new MongoClient(serverAddresses, options);
			mongoClient.slaveOk();
		}
		if (db == null) {
			db = mongoClient.getDatabase(MongoDBConstant.DB_NAME);
		}
		if (jongoDB == null) {
			jongoDB = new DB(mongoClient, MongoDBConstant.DB_NAME);
		}
	}
//	static ServerAddress serveeDrAddresses = new ServerAddress("localhost", 27017);
//	static MongoClientOptions options = new MongoClientOptions.Builder().maxConnectionIdleTime(60000)
//			.heartbeatSocketTimeout(10000).build();
//	private static MongoClient mongoClient = new MongoClient(serveeDrAddresses, options);
//	private static MongoDatabase db = mongoClient.getDatabase("BCareApp");
//	@SuppressWarnings("deprecation")
//	private static DB jongoDB = new DB(mongoClient, "BCareApp");

	private MongoDBUtil() {

	}

	public static DB getDB() {
		if (jongoDB == null) {
			initializeDB();
		}
		return jongoDB;
	}

	public static MongoDatabase getMongoDataBase() {
		if (db == null) {
			initializeDB();
		}
		return db;
	}

	public static MongoCollection<Document> getCollection(String collectionName) {
		return getMongoDataBase().getCollection(collectionName);
	}

	public static void closeCursor(MongoCursor<Document> cursor) {
		try {
			if (cursor != null) {
				cursor.close();
			}
		} catch (MongoException e) {
			// logger.error("Exception occurred while closing Mongo Cursor connection", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void closeJongoCursor(org.jongo.MongoCursor cursor) {
		try {
			if (cursor != null) {
				cursor.close();
			}
		} catch (MongoException | IOException e) {
			// logger.error("Exception occurred while closing jongo connection", e);
		}
	}

	public static void closeConnections() {
		try {
			if (db != null) {
				db = null;
			}
			if (jongoDB != null) {
				jongoDB = null;
			}
			if (mongoClient != null) {
				mongoClient.close();
			}
			if (mongoClient != null) {
				mongoClient = null;
			}
		} catch (Exception e) {
			// logger.error("Exception occurred while closing mongo connections", e);
		}
	}
}
