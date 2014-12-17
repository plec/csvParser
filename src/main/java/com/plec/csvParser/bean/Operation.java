package com.plec.csvParser.bean;

import java.util.HashMap;
import java.util.Map;

public class Operation {
	OperationName name;
	Map<String, Object> parameters = new HashMap<String, Object>();

	public Operation(OperationName name) {
		this.name = name;
	}
	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @return the name
	 */
	public OperationName getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(OperationName name) {
		this.name = name;
	}
}
