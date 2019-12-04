function listarEstudiantes() {
    $.get( "http://localhost:4567/estudiantes/", function( data ) {
        alert(data);
        console.log(data);
    }, "json" );
}

function crear() {
    var estudiante = {
        matricula: $("#matricula").val(),
        nombre:$("#nombre").val(),
        correo:$("#correo").val(),
        carrera:$("#carrera").val()
    }

    $('#target').html('enviando..');

    $.ajax({
        url: 'http://localhost:4567/rest/estudiantes/',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $('#target').html(data.msg);
        },
        data: JSON.stringify(estudiante)
    });
}