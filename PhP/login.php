<?php

    $con =mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");
    
    $usuario = $_POST["usuario"];
    $contrasena = $_POST["contrasena"];
   
    $statement =mysqli_prepare($con,"SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?");
    mysqli_stmt_bind_param($statement,"ss", $usuario, $contrasena);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $nombre, $usuario, $contrasena, $email, $telefono);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["nombre"] = $nombre;
        $response["usuario"] = $usuario;
        $response["contrasena"] = $contrasena;
        $response["email"] = $email;
        $response["telefono"] = $telefono;
    }

    echo json_encode($response);
?>