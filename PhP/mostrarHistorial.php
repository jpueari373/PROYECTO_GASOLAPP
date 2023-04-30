<?php

    $con = mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");
    
    $usuario = $_POST["usuario"];  

   
    $statement = mysqli_prepare($con, "SELECT * FROM historial WHERE usuario = ?");
    mysqli_stmt_bind_param($statement, "s", $usuario);
    mysqli_stmt_execute($statement);
    
    $result = mysqli_stmt_get_result($statement);
    
    $response = array();
    $response["success"] = false;
    
    while ($row = mysqli_fetch_assoc($result)) {
        $response["success"] = true;
        $response["data"][] = $row;
    }
    
    echo json_encode($response);
?>