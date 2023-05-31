<?php
$con = mysqli_connect("localhost", "id20557596_gasolapp_root", "@Quadjaime123", "id20557596_gasolapp");

$usuario = $_POST["usuario"];

$statement = mysqli_prepare($con, "DELETE FROM usuarios WHERE usuario = ?");
mysqli_stmt_bind_param($statement, "s", $usuario);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = false;

if (mysqli_affected_rows($con) > 0) {
    $response["success"] = true;
}

echo json_encode($response);
?>