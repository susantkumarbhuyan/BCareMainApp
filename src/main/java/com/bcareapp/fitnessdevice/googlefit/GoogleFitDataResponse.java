package com.bcareapp.fitnessdevice.googlefit;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GoogleFitDataResponse {
	private ArrayList<Bucket> bucket;

	public ArrayList<Bucket> getBucket() {
		return bucket;
	}

	public void setBucket(ArrayList<Bucket> bucket) {
		this.bucket = bucket;
	}

}

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class Bucket {
	private long startTimeMillis;
	private long endTimeMillis;
	private ArrayList<Dataset> dataset;

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(String startTimeMillis) {
		this.startTimeMillis = Long.parseLong(startTimeMillis);
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(String endTimeMillis) {
		this.endTimeMillis = Long.parseLong(endTimeMillis);
	}

	public ArrayList<Dataset> getDataset() {
		return dataset;
	}

	public void setDataset(ArrayList<Dataset> dataset) {
		this.dataset = dataset;
	}

}

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class Dataset {
	private String dataSourceId;
	private ArrayList<Point> point;

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public ArrayList<Point> getPoint() {
		return point;
	}

	public void setPoint(ArrayList<Point> point) {
		this.point = point;
	}

}

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class Point {
	private String startTimeNanos;
	private String endTimeNanos;
	private String dataTypeName;
	private String originDataSourceId;
	private ArrayList<Value> value;

	public String getStartTimeNanos() {
		return startTimeNanos;
	}

	public void setStartTimeNanos(String startTimeNanos) {
		this.startTimeNanos = startTimeNanos;
	}

	public String getEndTimeNanos() {
		return endTimeNanos;
	}

	public void setEndTimeNanos(String endTimeNanos) {
		this.endTimeNanos = endTimeNanos;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public String getOriginDataSourceId() {
		return originDataSourceId;
	}

	public void setOriginDataSourceId(String originDataSourceId) {
		this.originDataSourceId = originDataSourceId;
	}

	public ArrayList<Value> getValue() {
		return value;
	}

	public void setValue(ArrayList<Value> value) {
		this.value = value;
	}

}

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class Value {
	private long intVal;
	private ArrayList<Object> mapVal;
	private double fpVal;

	public long getIntVal() {
		return intVal;
	}

	public void setIntVal(long intVal) {
		this.intVal = intVal;
	}

	public ArrayList<Object> getMapVal() {
		return mapVal;
	}

	public void setMapVal(ArrayList<Object> mapVal) {
		this.mapVal = mapVal;
	}

	public double getFpVal() {
		return fpVal;
	}

	public void setFpVal(double fpVal) {
		this.fpVal = fpVal;
	}

}