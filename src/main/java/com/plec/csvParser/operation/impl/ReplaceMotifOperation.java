package com.plec.csvParser.operation.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.AbstractComplexOperation;

public class ReplaceMotifOperation extends AbstractComplexOperation {
	private int colId = -1;
	private String searchMotif = "";
	private String replaceMotif = "";
	@Override
	public void init(Operation operation) {
		colId = (Integer) operation.getParameters().get(Constants.OPERATION_PARAMETER_COL_ID);
		searchMotif = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_CHANGE_MOTIF_SEARCH);
		replaceMotif = (String) operation.getParameters().get(Constants.OPERATION_PARAMETER_CHANGE_MOTIF_REPLACE);
		
	}
	
	@Override
	public void processHeader(List<String> newHeaders, Operation operation, Data data, int currentHeaderId) {
		newHeaders.add(data.getHeaders().get(currentHeaderId));
	}
	@Override
	public void processLines(List<String> operatedCols, List<String> currentLine, Operation operation, Data data, int currentColId) {
		if (currentColId == colId) {
			String currentData = currentLine.get(currentColId);
			operatedCols.add(StringUtils.replace(currentData, searchMotif, replaceMotif));
		} else {
			operatedCols.add(currentLine.get(currentColId));
		}
	}

}
