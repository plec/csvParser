<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
</head>
<body>

	<div class="container theme-showcase" role="main">

		<div class="page-header">
			<h1>Contenu du fichier</h1>
		</div>
		
		
		<div id="general">
			<div class="row">
				<div class="col-md-12">
								<form method="get" action="update">
				<table border="0">
                <tr>
                    <td>Separator</td>
                    <td><input type="text" name="split" size="2" /></td>
                </tr>
				<tr>
                    <td colspan="2" align="center"><input type="submit" value="Update" /></td>
                </tr>
                </table>
				</form>
				
				<table class="table table-striped">
						<thead>
							<tr>
								<th>Num. Ligne</th>
								<th>${initialFileHeader}</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${fileContent}" var="line" varStatus="line2">
							<tr>
								<td>${line2.index}</td>
								<td>${line}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div> <!-- end col -->
			</div> <!-- end row -->
		</div> <!-- end General -->

	</div>
</body>
</html>
