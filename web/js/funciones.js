$(document).ready(function() {
	
	$("#guardarFot").click(function() {
		$("#divInsFot").show();
		$("#divVerTodos").hide();
		$("#divVerFotDirector").hide();
		$('#tabla').empty();
		$('#mensajes').empty();
	});
	
	$("#todosFot").click(function() {
		$("#divInsFot").hide();
		$("#divVerTodos").show();
		$("#divVerFotDirector").hide();
		$('#tabla').empty();
		$('#mensajes').empty();
	});
	
	$("#fotDirector").click(function() {
		$("#divInsFot").hide();
		$("#divVerTodos").hide();
		$("#divVerFotDirector").show();
		$('#tabla').empty();
		$('#mensajes').empty();
	});
	
	$('#verTodos').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), mostrarTabla);
	});
	
	peticionAjax("/ap1mongodb/sugerirDirector", "", rellenarSelect);
	
	$('#verDirector').submit(function(event){
		event.preventDefault();
		peticionAjax($(this).attr('action'), $(this).serialize(), mostrarTabla);
	});
	
	$('#insertarFot').submit(function(event){
		event.preventDefault();
		var form = document.getElementById('insertarFot');
        var data = new FormData(form);
        var file = $("input[name='archivo']").val();
		var ext = file.substring(file.lastIndexOf("."));
		if(ext != ".jpg" && ext != ".png" && ext != ".bmp") {
			valido = false;
		} else {
		    valido = true;
		}  
		if(valido == false) {
		    alert("La extensión " + ext + " no es una imagen permitida.");
		} else {
			peticionAjax2($(this).attr('action'), data, mensajes);
		}
	});
});

//Petición ajax que recibe la url a la cual debe dirigirse, los datos a enviar y la función que debe procesar los
//datos de respuesta
var peticionAjax = function(url, datos, llegadaDatos){
	$.ajax({
     async:true,
     type: "POST",
     dataType: "json", //tipo de dato que se va ha recibir
     contentType: "application/x-www-form-urlencoded", //como se envia los datos
     url:url,
     data:datos, //cadena, los datos de la petición
     beforeSend: inicioEnvio,
     success: llegadaDatos, //función que recupera los datos devueltos
     timeout: 4000, //tiempo máximo a esperar para la respuesta
     error: problemas //Si hay algún problema, se ejecuta
 });
}

//Petición ajax, para enviar archivos, que recibe la url a la cual debe dirigirse, los datos a enviar y la función que debe procesar los
//datos de respuesta
var peticionAjax2 = function(url, datos, llegadaDatos){
	$.ajax({
		type: "POST",
		dataType: "json", //tipo de dato que se va ha recibir
		contentType: false, //como se envia los datos
		processData: false,
		url:url,
		data:datos, //cadena, los datos de la petición
		cache : false,
		beforeSend: inicioEnvio,
		success: llegadaDatos, //función que recupera los datos devueltos
		timeout: 40000, //tiempo máximo a esperar para la respuesta
		error: problemas //Si hay algún problema, se ejecuta
	});
}
function mostrarTabla(datos){
	console.log(datos);
	if(Array.isArray(datos)){
		if (datos.length>0) {
        var a="<table class='table table-hover'><tr><th>Título</th><th>Director</th><th>Año de Estreno</th><th>Género</th><th>Fotograma</th></tr>";
        	$.each(datos, function(index,value){
                a+= "<tr>";
                a+= "<td>"+datos[index].titulo+"</td>";
                a+= "<td>"+datos[index].director+"</td>";
                a+= "<td>"+datos[index].year+"</td>";
                a+= "<td>"+datos[index].genero+"</td>";
                a+= "<td><img src='fotogramas/"+datos[index].archivo+"' alt='"+datos[index].archivo+"' style='max-height:100px'></td>";
                a+="</tr>";
                });
        	a+="</table>";
        	$('#tabla').html(a);
		} else $('#mensajes').append("<h4 class='text-center bg-info text-white'>No hay fotogramas que mostrar</h4>");
	} else {
		$("#mensajes").append("<h4 class='bg-danger'>Hay un problema en la aplicación, intentelo más tarde.</h4>");
	}
}

function rellenarSelect(datos){
	console.log(datos);
	$.each(datos, function(index,value){
        $("#selDirector").append($("<option>").text(datos[index]));
      });
}

function mensajes(datos) {
	console.log(datos);
	$('#mensajes').empty();
	$('#mensajes').append("<h4 class='text-center bg-info text-white'>Fotograma guardado</h4>");
	resetForm($('#insertarFot'));
	
}

function resetForm($form) {
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
         .removeAttr('checked').removeAttr('selected');
}

function inicioEnvio() {
    //no se hace nada
}

//Función que recibe los datos de la petición ajax cuando hay algún error
function problemas() {
	console.log("Hay un problema en el servidor.");
	$("#mensajes").append("<h4 class='bg-danger'>Hay un problema en la aplicación, intentelo más tarde.</h4>");
	
}