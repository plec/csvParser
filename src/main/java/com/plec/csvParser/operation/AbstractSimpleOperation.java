package com.plec.csvParser.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;

public abstract class AbstractSimpleOperation {
	private static final Logger LOGGER = Logger.getLogger(AbstractComplexOperation.class);

	public Data process(Operation operation, Data data) {
		List<List<String>> operatedLines = new ArrayList<List<String>>();
		List<String> newHeaders = new ArrayList<String>();
		//get the colId
		int colId  = Integer.parseInt((String) operation.getParameters().get(Constants.OPERATION_PARAMETER_COL_ID));
		
		//headers
		LOGGER.info("Processing headers");
		for (int indexHeader = 0; indexHeader < data.getHeaders().size(); indexHeader++) {
			if (indexHeader == colId) {
				processHeader(newHeaders, data.getHeaders().get(indexHeader));
			} else {
				newHeaders.add(data.getHeaders().get(indexHeader));
			}
		}
		LOGGER.info("Processing headers done");
		//lines
		LOGGER.info("Processing lines");
		for (List<String> line : data.getContent()) {
			List<String> operatedCols = new ArrayList<String>();
			for (int i = 0; i< line.size(); i++ ) {
				if (i == colId) {
					processLines(operatedCols, line.get(i));
				} else {
					operatedCols.add(line.get(i));
				}
			}
			operatedLines.add(operatedCols);
		}
		LOGGER.info("Processing lines done");
		data.setContent(operatedLines);
		data.setHeaders(newHeaders);
		return data;
	}
	public abstract void processHeader(List<String> newHeaders, String currentHeader);
	public abstract void processLines(List<String> operatedCols, String currentCell);
}
