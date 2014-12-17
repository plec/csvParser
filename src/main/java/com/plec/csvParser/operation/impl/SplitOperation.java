package com.plec.csvParser.operation.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.AbstractComplexOperation;

public class SplitOperation extends AbstractComplexOperation {
	private String separator = "";
	private int colId = -1;
	private String newName1 = "";
	private String newName2 = "";
	
	@Override
	public void init(Operation operation) {
		separator = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_SPLIT_SEPARATOR);
		colId = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_SPLIT_ID_COLUMN);
		newName1 = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_SPLIT_NEW_NAME_COL1);
		newName2 = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_SPLIT_NEW_NAME_COL2);
		
	}
	
	@Override
	public void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId) {
		
		if (currentHeaderId == colId) {
			newHeaders.add(newName1);
			newHeaders.add(newName2);
		} else {
			newHeaders.add(data.getHeaders().get(currentHeaderId));
		}
		
	}
	@Override
	public void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId) {
		if (currentColId == colId) {
			String dataToSplit = currentLine.get(currentColId);
			String[] splitedData = StringUtils.split(dataToSplit, separator, 2);
			operatedCols.addAll(Arrays.asList(splitedData));
			if (splitedData.length != 2) {
				operatedCols.add("");
			}
		} else {
			operatedCols.add(currentLine.get(currentColId));
		}
	}

}
