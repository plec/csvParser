package com.plec.csvParser.operation.impl;

import java.util.List;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.AbstractComplexOperation;

public class JoinOperation extends AbstractComplexOperation {
	
	private int colId1 = -1;
	private int colId2 = -1;
	private String newName = "";
	private String joinSeparator = "";
	@Override
	public void init(Operation operation) {
		colId1 = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_JOIN_ID_COLUMN1);
		colId2 = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_JOIN_ID_COLUMN2);
		newName = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_JOIN_NEW_NAME_COL);
		joinSeparator = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_JOIN_SEPARATOR);
	}
	@Override
	public void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId) {
		
		if (currentHeaderId == colId1) {
			newHeaders.add(newName);
		} else if (currentHeaderId == colId2) {
			//nothing to do
		} else {
			newHeaders.add(data.getHeaders().get(currentHeaderId));
		}
		
	}
	@Override
	public void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId) {

		// colId1 must be minus than colId2 so check and toggle cols id
		int colIdTmp = -1;
		if (colId2 < colId1) {
			colIdTmp = colId1;
			colId1 = colId2;
			colId2 = colIdTmp;
		}
		
		if (currentColId == colId1) {
			String dataToJoin1 = currentLine.get(currentColId);
			String dataToJoin2 = currentLine.get(colId2);
			StringBuilder builder = new StringBuilder();
			builder.append(dataToJoin1).append(joinSeparator).append(dataToJoin2);
			operatedCols.add(builder.toString());
		} else if (currentColId == colId2) {
			//nothing to do
		} else {
			operatedCols.add(currentLine.get(currentColId));
		}
		
	}
}
