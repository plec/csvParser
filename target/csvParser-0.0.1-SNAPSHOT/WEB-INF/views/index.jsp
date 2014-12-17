<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
</head>
<body>

	<div class="container theme-showcase" role="main">

		<div class="page-header">
			<h1>Chargement d'un fichier CSV</h1>
		</div>
		
		
		<div id="general">
			<div class="row">
				<div class="col-md-12">
					<form method="post" action="upload" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>Charger un fichier csv:</td>
                    <td><input type="file" name="file" size="50" /></td>
                </tr>
                <tr>
                    <td>Charset</td>
                    <td>
	                    <select id="charset" name="charset">
								<option value="UTF-8">UTF-8</option>
								<option value="ISO-8859-1">ISO-8859-1</option>
						</select>
					</td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                </tr>
            </table>
        </form>
				</div> <!-- end col -->
			</div> <!-- end row -->
		</div> <!-- end General -->

	</div>
</body>
</html>
