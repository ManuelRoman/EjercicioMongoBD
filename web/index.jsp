<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="WEB-INF/error.jsp?pagOrigen=index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menú</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="js/jquery320.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/funciones.js"></script>
<style>
	#divInsFot{
	display: none;
	}
	#divVerTodos{
	display: none;
	}
	#divVerFotDirector{
	display: none;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="page-header">
    		<h1 class="text-center">Ejercicio Fotogramas con MongoDB</h1>      
  		</div>
  		<div class="row">
  		<div class="col-md-4 col-md-offset-4">
			<div class="text-center" id="general">
				<p>Seleccione una acción:</p>
				<button id="guardarFot" class="btn btn-default">Guardar un fotograma</button><br/><br/>
				<button id="todosFot" class="btn btn-default">Ver todos los fotogramas</button><br/><br/>
				<button id="fotDirector" class="btn btn-default">Ver fotogramas de un director</button><br/>
			</div>
		</div>
		<div class="col-md-6 col-md-offset-3">
			<!--Formulario Insertar Fotogramas-->
			<div id="divInsFot">
				<h3 class="text-center">Guardar un fotograma</h3>
				<form action="/ap1mongodb/insertarFotogramas" method="post" enctype="multipart/form-data" id="insertarFot">
					<div class="form-group">
						<label for="titPelicula">Título de la película:</label>
						<input class="form-control" id="titPelicula" type="text" name="titPelicula" required="required" pattern="\S*"><br>
					</div>
					<div class="form-group">
						<label for="nomDirector">Nombre del director:</label>
						<input class="form-control" id="nomDirector" type="text" name="nomDirector" required="required" pattern="\S*"><br>
					</div>
					<div class="form-group">
						<label for="genero">Género de la película:</label>
						<input class="form-control" id="genero" type="text" name="genero" required="required" pattern="\S*"><br>
					</div>
					<div class="form-group">
						<label for="estreno">Año de Estreno:</label>
						<input class="form-control" id="estreno" type="number" name="estreno" required="required" min="1895" max="2017"><br>
					</div>
					<div class="form-group">
    					<label for="archivo">Fotograma</label>
    					<input type="file" id="archivo" name="archivo" required="required">
    					<p class="help-block">Adjunte un archivo de imagen.</p>
  					</div>
					<div class="text-center">
						<input type="submit" value="Guardar" class="btn btn-default">
					</div>
				</form>
			</div>
		</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<!--Formulario Ver Todos Los Fotogramas-->
				<div id="divVerTodos">
					<h3 class="text-center">Ver todos los fotogramas</h3>
					<form action="/ap1mongodb/listarFotogramas" method="post" id="verTodos">
						<div class="text-center">
							<input type="submit" value="Ver" class="btn btn-default">
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<!--Formulario Ver Fotogramas/Director-->
			<div id="divVerFotDirector">
				<h3 class="text-center">Ver fotogramas de un director</h3>
				<form action="/ap1mongodb/listarFotogramasDirector" method="post" id="verDirector">
					<div class="form-group">
						<label for="selDirector">Nombre del director:</label>
						<select class="form-control" id="selDirector" name="selDirector"></select><br>
					</div>
					<div class="text-center">
						<input type="submit" value="Ver" class="btn btn-default">
					</div>
				</form>
			</div>
		</div>
		</div>
		<div class="row">
		<!--Tabla de fotogramas-->
		<div class="col-md-8 col-md-offset-2" id="tabla"></div>
		</div>
		<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<!--Div de mensajes-->
			<div id="mensajes"></div>
		</div>
		</div>
	</div>
</body>
</html>