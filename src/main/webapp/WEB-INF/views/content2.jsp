<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/excel/excel-2007.css" />
	<script src="${pageContext.request.contextPath}/resources/excel/jquery.resizableColumns.min.js"></script>
																	
	<script src="${pageContext.request.contextPath}/resources/excel/store.min.js"></script>
<script type="text/javascript">
function changeOperation() {
	var select = $("#operation");
	console.log("operation : " + select.val());
	hideAll();
	if (select.val() == "splitColumn") {
		console.log("choose operation splitColumn");
		$("#operation_split").show();
	} else if (select.val() == "joinColumn") {
		console.log("choose operation joinColumn");
		$("#operation_join").show();
		$("#divNewColName").show();
	} else if (select.val() == "exportCSV") {
		$("#form_operation").attr('action', 'exportCSV');
		$("#export_csv").show();
	} else if (select.val() == "upperCase") {
		console.log("choose operation upperCase");
		$("#operation_simple").show();
	} else if (select.val() == "lowerCase") {
		console.log("choose operation lowerCase");
		$("#operation_simple").show();
	} else if (select.val() == "firstLetterUpperCase") {
		console.log("choose operation firstLetterUpperCase");
		$("#operation_simple").show();
	} else if (select.val() == "firstLetterEachWordUpperCase") {
		console.log("choose operation firstLetterEachWordUpperCase");
		$("#operation_simple").show();
	} else if (select.val() == "delete") {
		console.log("choose operation delete");
		$("#operation_simple").show();
	} else if (select.val() == "copyColumn") {
		console.log("choose operation copyColumn");
		$("#operation_copy").show();
		$("#divNewColName").show();
	} else if (select.val() == "changeColName") {
		console.log("choose operation change col name");
		$("#operation_copy").show();
		$("#divNewColName").show();
	} else if (select.val() == "replaceMotif") {
		console.log("choose operation replaceMotif");
		$("#operation_simple").show();
		$("#divSearchMotif").show();
		$("#divReplaceMotif").show();
	} else if (select.val() == "deleteMotif") {
		console.log("choose operation deleteMotif");
		$("#operation_simple").show();
		$("#divSearchMotif").show();
	} else if (select.val() == "addMotif") {
		console.log("choose operation addMotif");
		$("#operation_simple").show();
		$("#divAddMotif").show();
	}
	
}
function hideAll() {
	$("#operation_join").hide();
	$("#operation_split").hide();
	$("#export_csv").hide();
	$("#operation_simple").hide();
	$("#operation_copy").hide();
	$("#divNewColName").hide();
	$("#divReplaceMotif").hide();
	$("#divSearchMotif").hide();
	$("#divAddMotif").hide();
	
}
</script>
</head>
<body>

	<div class="container theme-showcase" role="main">

		<div class="page-header">
			<h1>Contenu du fichier</h1>
		</div>
		
		
		<div id="general">
			<div id="div_form_operation">
				<div class="row">
						<div class="col-md-12">
					<form id="form_operation" method="get" action="operation" onchange="changeOperation()">
						operation: <select id="operation" name="operation">
							<option value=""></option>
							<option value="splitColumn">Séparer une colonne</option>
							<option value="joinColumn">Fusionner des colonnes</option>
							<option value="copyColumn">Copier une colonne</option>
							<option value="upperCase">Passer en majuscule</option>
							<option value="lowerCase">Passer en minuscule</option>
							<option value="firstLetterUpperCase">Passer la première lettre en majuscule</option>
							<option value="firstLetterEachWordUpperCase">Passer la première lettre de chaque mot en majuscule</option>
							<option value="delete">Supprimer colonne</option>
							<option value="replaceMotif">Remplacer un motif</option>
							<option value="deleteMotif">Supprimer un motif</option>
							<option value="addMotif">Ajouter un motif</option>
							<option value="changeColName">Changer le nom d'une colonne</option>
							<option value="exportCSV">Export CSV</option>
						</select>
						<div id="operation_split" style="display:none">
							Colonne à séparer : <select id="colonneToSplit" name="colonneToSplit">
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<option value="${headerStatus.index}">${currentHeader}</option>
								</c:forEach>
							</select>
							Séparateur : <input type ="text" size="2" id="colonneToSplit_separator" name="colonneToSplit_separator" />
							Nom nouvelle colonne 1 : <input type ="text" size="20" id="newColName1" name="newColName1" />
							Nom nouvelle colonne 2 : <input type ="text" size="10" id="newColName2" name="newColName2" />
						</div>
						<div id="operation_copy" style="display:none">
							Colonne � copier : <select id="colonneToCopy" name="colonneToCopy">
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<option value="${headerStatus.index}">${currentHeader}</option>
								</c:forEach>
							</select>
						</div>
						<div id="operation_join" style="display:none">
							Colonnes � fusionner : <select id="colonne1ToJoin" name="colonne1ToJoin">
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<option value="${headerStatus.index}">${currentHeader}</option>
								</c:forEach>
							</select>
							<select id="colonne2ToJoin" name="colonne2ToJoin">
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<option value="${headerStatus.index}">${currentHeader}</option>
								</c:forEach>
							</select>
							S�parateur : <input type ="text" size="2" id="colonneToJoin_separator" name="colonneToJoin_separator" />
						</div>
						<div id="divNewColName" style="display:none">
							Nom nouvelle colonne : <input type ="text" size="20" id="newColName" name="newColName" />
						</div>
						<div id="operation_simple" style="display:none">
							Colonnes � traiter : <select id="colonneToProcessSimple" name="colonneToProcessSimple">
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<option value="${headerStatus.index}">${currentHeader}</option>
								</c:forEach>
							</select>
						</div>
						<div id="export_csv" style="display:none">
						S�parateur : <input type ="text" size="2" id="separator" name="separator" />
						</div>
						<div id="divSearchMotif" style="display:none">
							Motif à rechercher : <input type ="text" size="10" id="motif" name="motif" />
						</div>
						<div id="divReplaceMotif" style="display:none">
							Motif à remplacer : <input type ="text" size="10" id="replace" name="replace" />
						</div>
						<div id="divAddMotif" style="display:none">
							Motif à ajouter : <input type ="text" size="10" id="addMotif" name="addMotif" />
							Ajouter le motif : <select id="whereToAddMotif" name="whereToAddMotif"><option value="before">Avant</option><option value="after">Après</option></select>
						</div>
						<input type="submit" value="Go !" />
					</form>
				</div>
			</div>
		</div>
			<div class="row">
				<div class="col-md-12">
				<!-- table class="table table-striped"-->
				<table class="ExcelTable2007" data-resizable-columns-id="demo-table" id="demo-table">
						<thead>
							<tr>
								<th class="heading"  data-resizable-column-id="#"/>
								<c:forEach items="${fileHeaders}" var="currentHeader" varStatus="headerStatus">
									<th id="th_${headerStatus.index}"  data-resizable-column-id="th_${headerStatus.index}">${currentHeader}
									</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${fileContent2}" var="line" varStatus="line2">
							<tr>
								<td class="heading">${line2.index}</td>
								<c:forEach items="${line}" var="cell" varStatus="cell2">
								<td><c:out value="${cell}" escapeXml="true"/></td>
								</c:forEach>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div> <!-- end col -->
			</div> <!-- end row -->
		</div> <!-- end General -->
	</div>
	<script>
$(function(){
  $("table").resizableColumns({
    store: window.store
  });
});
</script>

</body>
</html>
