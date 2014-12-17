<html>
 <head>
 <meta http-equiv="content-type" content="text/html; charset=utf-8" />
 <title>Manipulation de fichiers CSV</title>
	<!-- Bootstrap -->
	<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
		media="screen">
	<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"
		media="screen">
	<link href="${pageContext.request.contextPath}/resources/bootstrap/css/custom.css" rel="stylesheet"
		media="screen">
	<link href="${pageContext.request.contextPath}/resources/jquery/jquery-ui.min.css" rel="stylesheet"
		media="screen">
	<script src="${pageContext.request.contextPath}/resources/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery-ui.min.js"></script>

	
	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<style type="text/css">
.customLogo img {
  height: 50px; !important
}
	</style>
 <sitemesh:write property='head'/>
 
 </head>
 
 <body>
   <!-- Fixed navbar header -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="navbar-inner">
      <div class="container">
		<!-- LOGO du MCC -->
        <div class="navbar-header">
			<a class="customLogo" href="#"><img alt="logo Minist�re de la culture et de la Communication" src="${pageContext.request.contextPath}/resources/logo_mcc.jpg"></a>
        </div>
		<!-- Nom de l'application -->
        <div class="navbar-header">
     		   <a class="navbar-brand" href="${pageContext.request.contextPath}/csv/">Csv Parser</a>
        </div>
        <div class="navbar-collapse collapse">
        </div><!--/.nav-collapse -->
      </div>
      </div>
    </div>
 
 <sitemesh:write property='body'/>
 </body>
</html>