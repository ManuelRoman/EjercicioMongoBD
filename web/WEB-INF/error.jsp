<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Página de gestión de Errores</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
	<div class="container"><br />
		<div class="text-center" class="col-md-6 col-md-offset-3">
			<p>Página del error: <c:out value="${param.pagOrigen}" default="Error" /></p>
			<p>Error: <%=exception.toString()%></p>
			<a href="ap1mongodb/index.jsp" class="btn btn-default">Ir al menú</a>
		</div>
	</div>
</body>
</html>