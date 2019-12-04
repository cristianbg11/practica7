<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Listado de estudiantes</title>
    <link href="css/stylesheet.css" type="text/css" rel="stylesheet">
</head>
<body>

<header id="header">
    <nav class="links" style="--items: 5;">
        <a href="/">Inicio</a>
        <a href="/listar" id="crear">Crear</a>
        <a href="/listar">Listar</a>
        <a href="#">Sign out</a>
        <span class="line"></span>
    </nav>
</header>

<div class="container">
    <table>
        <thead>
        <tr>
            <th>Matricula</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Telefono</th>
        </tr>
        </thead>
        <tbody>
        <#list listado as estudiante>
            <tr>
                <td>${estudiante.matricula?c}</td>
                <td>${estudiante.nombre}</td>
                <td>${estudiante.apellido}</td>
                <td>${estudiante.telefono}</td>
                <td id='select'>
                    <a id='button' href='/view?id=${estudiante?index}'>
                        Ver
                    </a>
                    <a id='button' href='/edit?id=${estudiante?index}'>
                        Editar
                    </a>
                    <a id='button' href='/delete?id=${estudiante?index}'>
                        Eliminar
                    </a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        // click on button submit
        $("#crear").on('click', function(){
            console.log("s");
            // send ajax
            $.ajax({
                url: 'http://localhost:4567/rest/estudiantes/', // url where to submit the request
                type : "POST", // type of action POST || GET
                dataType : 'json', // data type
                data : "{ 'nombre': 'Cristian', 'correo': 'cristianPrueba@gmail.com', 'carrera': 'ISC', 'matricula': '20151256' }", // post data || get data
                success : function(result) {
                    // you can see the result from the console
                    // tab of the developer tools
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })
        });
    });

</script>
<script src="/js/cliente.js"></script>
</body>
</html>