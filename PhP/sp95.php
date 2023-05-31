<?php
$con = mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");

$statement = mysqli_prepare($con, "SELECT AVG(Precio_gas) AS media_Precio_gas, AVG(Precio_g_3) AS media_Precio_g_3, AVG(Precio_g_5) AS media_Precio_g_5, AVG(Precio_g_6) AS media_Precio_g_6, AVG(Precio_g_7) AS media_Precio_g_7 FROM ubicaciones");
mysqli_stmt_execute($statement);

$result = mysqli_stmt_get_result($statement);

$response = array();
$response["success"] = false;
$response["media"] = array();

if ($row = mysqli_fetch_assoc($result)) {
    $response["success"] = true;
    $response["media"] = $row;
}

echo json_encode($response);
?>

