<?php
    $con =mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");
    $nombre = $_POST["nombre"];
    $usuario = $_POST["usuario"];
    $contrasena = $_POST["contrasena"];
    $email = $_POST["email"];
    $telefono = $_POST["telefono"];
    $rol = $_POST["rol"];
    $statement =mysqli_prepare($con,"INSERT INTO usuarios (nombre, usuario, contrasena, email, telefono, rol)
    VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement,"ssssis", $nombre, $usuario, $contrasena, $email, $telefono, $rol);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>