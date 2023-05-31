<?php
    $con = mysqli_connect("localhost", "id20557596_gasolapp_root", "@Quadjaime123", "id20557596_gasolapp");

    $nombre = $_POST["nombre"];
    $usuario = $_POST["usuario"];
    $contrasena = $_POST["contrasena"];
    $email = $_POST["email"];
    $telefono = $_POST["telefono"];
    $rol = $_POST["rol"];

    // comprobar si existe el usuario
    $checkQuery = "SELECT usuario FROM usuarios WHERE usuario = ?";
    $checkStatement = mysqli_prepare($con, $checkQuery);
    mysqli_stmt_bind_param($checkStatement, "s", $usuario);
    mysqli_stmt_execute($checkStatement);
    mysqli_stmt_store_result($checkStatement);
    
    // si el usuario no existe, insertarlo en la bd
    if (mysqli_stmt_num_rows($checkStatement) == 0) {
        $insertQuery = "INSERT INTO usuarios (nombre, usuario, contrasena, email, telefono, rol) VALUES (?, ?, ?, ?, ?, ?)";
        $insertStatement = mysqli_prepare($con, $insertQuery);
        mysqli_stmt_bind_param($insertStatement, "ssssis", $nombre, $usuario, $contrasena, $email, $telefono, $rol);
        mysqli_stmt_execute($insertStatement);

        $response = array();
        $response["success"] = true;
        echo json_encode($response);
    } else {
        $response = array();
        $response["success"] = false;
        $response["message"] = "El usuario ya existe";
        echo json_encode($response);
    }
?>


