package com.plec.csvParser.operation.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.AbstractComplexOperation;

public class AddMotifOperation extends AbstractComplexOperation {
	private int colId = -1;
	private String motif = "";
	private String whereToAdd = "";
	
	@Override
	public void init(Operation operation) {
		colId = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_COL_ID);
		motif = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_ADD_MOTIF);
		whereToAdd = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_WHERE_TO_ADD_MOTIF);
		
	}
	
	@Override
	public void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId) {
		newHeaders.add(data.getHeaders().get(currentHeaderId));
		
	}
	@Override
	public void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId) {
		if (currentColId == colId) {
			String currentData = currentLine.get(currentColId);
			if (whereToAdd.equalsIgnoreCase("after")) {
				operatedCols.add(currentData+motif);
			} else {
				operatedCols.add(motif+currentData);
			}
		} else {
			operatedCols.add(currentLine.get(currentColId));
		}
	}

}
