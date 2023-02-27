package com.bcareapp.util;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.jongo.Jongo;

import com.bcareapp.constants.MongoCollectionConstants;

public class ServerSideScriptsUtil implements Serializable {
	private static final Logger logger = Logger.getLogger(ServerSideScriptsUtil.class);
	private static final long serialVersionUID = 3919479175994125066L;

	public static long generateUniqueLongVal(String name) {
		try {
			new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_COUNTERS).update("{ _id:# }", name)
					.with("{$inc:{seq:1}}");
			Counters counters = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_COUNTERS)
					.findOne("{ _id: #}", name).as(Counters.class);
			if (counters != null) {
				return Double.valueOf(counters.getSeq()).longValue();
			} else {
				logger.error("Exception occurred while generating the counter for " + name);
				return 0;
			}
		} catch (Exception e) {
			logger.error("Exception occured while generating sequence in util", e);
		}
		return 0l;
	}

	public static long callServerSideScript(String name) {
		return generateUniqueLongVal(name);
	}
}

class Counters {
	private String _id;
	private double seq;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public double getSeq() {
		return seq;
	}

	public void setSeq(double seq) {
		this.seq = seq;
	}

}
