package org.generated.project.application;

public class ConsumerData {

	private String topic;
	
	private String key;
	
	private String value;

	
	
	public ConsumerData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ConsumerData(String topic, String key, String value) {
		super();
		this.topic = topic;
		this.key = key;
		this.value = value;
	}



	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
