package com.plec.csvParser.operation.impl;

import java.util.List;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.AbstractComplexOperation;

public class CopyOperation extends AbstractComplexOperation {
	private int colId = -1;
	private String newName = "";
	
	@Override
	public void init(Operation operation) {
		colId = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_COL_ID);
		newName = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_COPY_NEW_NAME_COL);
		
	}
	
	@Override
	public void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId) {
		
		if (currentHeaderId == colId) {
			newHeaders.add(data.getHeaders().get(currentHeaderId));
			newHeaders.add(newName);
		} else {
			newHeaders.add(data.getHeaders().get(currentHeaderId));
		}
		
	}
	@Override
	public void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId) {
		if (currentColId == colId) {
			operatedCols.add(currentLine.get(currentColId));
			operatedCols.add(currentLine.get(currentColId));
		} else {
			operatedCols.add(currentLine.get(currentColId));
		}
	}

}
