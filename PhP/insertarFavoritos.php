<?php
    $con =mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");
    $field1 = $_POST["Field1"];
    $provincia = $_POST["Provincia"];
    $municipio = $_POST["Municipio"];
    $localidad = $_POST["Localidad"];
    $codigo_po = $_POST["Codigo_po"];
    $direccion = $_POST["Direccion"];
    $longitud = $_POST["Longitud"];
    $latitud = $_POST["Latitud"];
    $precio_gas = $_POST["Precio_gas"];
    $precio_g_3 = $_POST["Precio_g_3"];
    $precio_g_5 = $_POST["Precio_g_5"];
    $precio_g_6 = $_POST["Precio_g_6"];
    $precio_g_7 = $_POST["Precio_g_7"];
    $rotulo = $_POST["Rotulo"];
    $horario = $_POST["Horario"];
    $fecha = $_POST["fecha"];
    $usuario = $_POST["usuario"];
    $statement =mysqli_prepare($con,"INSERT INTO favoritos (Field1, Provincia, Municipio, Localidad, Codigo_po, Direccion, Longitud
    ,Latitud, Precio_gas, Precio_g_3, Precio_g_5, Precio_g_6, Precio_g_7, Rotulo, Horario, fecha, usuario)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement,"isssisdddddddssss", $field1, $provincia, $municipio, $localidad, $codigo_po, $direccion, $longitud, $latitud, $precio_gas, $precio_g_3, $precio_g_5
    ,$precio_g_6, $precio_g_7, $rotulo, $horario, $fecha, $usuario);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>





<?php
    $con = mysqli_connect("localhost", "id20557596_gasolapp_root", "@Quadjaime123", "id20557596_gasolapp");

    $field1 = $_POST["Field1"];
    $provincia = $_POST["Provincia"];
    $municipio = $_POST["Municipio"];
    $localidad = $_POST["Localidad"];
    $codigo_po = $_POST["Codigo_po"];
    $direccion = $_POST["Direccion"];
    $longitud = $_POST["Longitud"];
    $latitud = $_POST["Latitud"];
    $precio_gas = $_POST["Precio_gas"];
    $precio_g_3 = $_POST["Precio_g_3"];
    $precio_g_5 = $_POST["Precio_g_5"];
    $precio_g_6 = $_POST["Precio_g_6"];
    $precio_g_7 = $_POST["Precio_g_7"];
    $rotulo = $_POST["Rotulo"];
    $horario = $_POST["Horario"];
    $fecha = $_POST["fecha"];
    $usuario = $_POST["usuario"];

    // comprobar si esta ya la gasolinera
    $checkQuery = "SELECT Field1 FROM favoritos WHERE Field1 = ?";
    $checkStatement = mysqli_prepare($con, $checkQuery);
    mysqli_stmt_bind_param($checkStatement, "i", $Field1);
    mysqli_stmt_execute($checkStatement);
    mysqli_stmt_store_result($checkStatement);
    
    // si la gasolinera no esta, inserta en la bd
    if (mysqli_stmt_num_rows($checkStatement) == 0) {
        $insertQuery = "INSERT INTO favoritos (Field1, Provincia, Municipio, Localidad, Codigo_po, Direccion, Longitud
        ,Latitud, Precio_gas, Precio_g_3, Precio_g_5, Precio_g_6, Precio_g_7, Rotulo, Horario, fecha, usuario)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        $insertStatement = mysqli_prepare($con, $insertQuery);
        mysqli_stmt_bind_param($statement,"isssisdddddddssss", $field1, $provincia, $municipio, $localidad, $codigo_po, $direccion, $longitud, $latitud, $precio_gas, $precio_g_3, $precio_g_5
        ,$precio_g_6, $precio_g_7, $rotulo, $horario, $fecha, $usuario);
        mysqli_stmt_execute($insertStatement);

        $response = array();
        $response["success"] = true;
        echo json_encode($response);
    } else {
        $response = array();
        $response["success"] = false;
        $response["message"] = "la gasolinera ya esta en favoritos";
        echo json_encode($response);
    }
?>