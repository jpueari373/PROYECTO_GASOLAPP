<?php
    $con =mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");
    $field1 = $_POST["field1"];
    $provincia = $_POST["provincia"];
    $municipio = $_POST["municipio"];
    $localidad = $_POST["localidad"];
    $rotulo = $_POST["rotulo"];
    $direccion = $_POST["direccion"];
    $carburante = $_POST["carburante"];
    $precio_carburante = $_POST["precio_carburante"];
    $litros_repostados = $_POST["litros_repostados"];
    $coste_total = $_POST["coste_total"];
    $fecha = $_POST["fecha"];
    $usuario = $_POST["usuario"];
    $statement =mysqli_prepare($con,"INSERT INTO historial (field1, provincia, municipio, localidad, rotulo, direccion, carburante
    ,precio_carburante, litros_repostados, coste_total, fecha, usuario)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement,"issssssdddss", $field1, $provincia, $municipio, $localidad, $rotulo, $direccion, $carburante,  $precio_carburante
    ,$litros_repostados, $coste_total, $fecha, $usuario);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>