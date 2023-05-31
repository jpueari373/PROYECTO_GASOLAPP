<?php

$con = mysqli_connect("localhost","id20557596_gasolapp_root","@Quadjaime123","id20557596_gasolapp");

$statement = mysqli_prepare($con, "SELECT * FROM usuarios");
mysqli_stmt_execute($statement);
$result = mysqli_stmt_get_result($statement);

$response = array();
$response["success"] = false;
$response["data"] = array();

while ($row = mysqli_fetch_assoc($result)) {
    $response["success"] = true;
    $response["data"][] = $row;
}

echo json_encode($response);
?>