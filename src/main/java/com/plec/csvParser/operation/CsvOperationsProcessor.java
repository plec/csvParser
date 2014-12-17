package com.plec.csvParser.operation;

import org.apache.log4j.Logger;

import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.operation.impl.AddMotifOperation;
import com.plec.csvParser.operation.impl.ChangeColNameOperation;
import com.plec.csvParser.operation.impl.CopyOperation;
import com.plec.csvParser.operation.impl.DeleteMotifOperation;
import com.plec.csvParser.operation.impl.DeleteOperation;
import com.plec.csvParser.operation.impl.FirstLetterEachWordUpperCaseOperation;
import com.plec.csvParser.operation.impl.FirstLetterUpperCaseOperation;
import com.plec.csvParser.operation.impl.JoinOperation;
import com.plec.csvParser.operation.impl.LowerCaseOperation;
import com.plec.csvParser.operation.impl.ReplaceMotifOperation;
import com.plec.csvParser.operation.impl.SplitOperation;
import com.plec.csvParser.operation.impl.UpperCaseOperation;

public class CsvOperationsProcessor {
	private static final Logger LOGGER = Logger.getLogger(CsvOperationsProcessor.class);
	public static void applyOneOperation(Operation operation, Data data) {
		LOGGER.info("Processing operation " + operation.getName());
		switch (operation.getName()) {
		case Split:
			new SplitOperation().process(operation, data);
			break;
		case Join:
			new JoinOperation().process(operation, data);
			break;
		case UpperCase:
			new UpperCaseOperation().process(operation, data);
			break;
		case LowerCase:
			new LowerCaseOperation().process(operation, data);
			break;
		case FirstLetterUpperCase:
			new FirstLetterUpperCaseOperation().process(operation, data);
			break;
		case FirstLetterEachWordUpperCase:
			new FirstLetterEachWordUpperCaseOperation().process(operation, data);
			break;
		case Copy:
			new CopyOperation().process(operation, data);
			break;
		case ChangeColName:
			new ChangeColNameOperation().process(operation, data);
			break;
		case Delete:
			new DeleteOperation().process(operation, data);
			break;
		case DeleteMotif:
			new DeleteMotifOperation().process(operation, data);
			break;
		case ReplaceMotif:
			new ReplaceMotifOperation().process(operation, data);
			break;
		case addMotif:
			new AddMotifOperation().process(operation, data);
			break;
		default:
			break;
		}
	}
}
