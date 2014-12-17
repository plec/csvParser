package com.plec.csvParser.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;

public abstract class AbstractComplexOperation {
	private static final Logger LOGGER = Logger.getLogger(AbstractComplexOperation.class);
	public Data process(Operation operation, Data data) {
		LOGGER.info("init processor");
		init(operation);
		List<List<String>> operatedLines = new ArrayList<List<String>>();
		List<String> newHeaders = new ArrayList<String>();
		//headers
		LOGGER.info("Processing headers");
		for (int indexHeader = 0; indexHeader < data.getHeaders().size(); indexHeader++) {
			processHeader(newHeaders, operation, data, indexHeader);
		}
		LOGGER.info("Processing headers done");
		
		//lines
		LOGGER.info("Processing lines");
		for (List<String> line : data.getContent()) {
			List<String> operatedCols = new ArrayList<String>();
			for (int i = 0; i< line.size(); i++ ) {
				processLines(operatedCols, line, operation, data, i);
				
			}
			operatedLines.add(operatedCols);
		}
		LOGGER.info("Processing lines done");
		data.setContent(operatedLines);
		data.setHeaders(newHeaders);
		return data;
	}
	public abstract void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId);
	public abstract void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId);
	public abstract void init(Operation operation);
}
