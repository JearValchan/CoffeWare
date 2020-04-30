<?php
$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  $conexion = mysqli_connect($hostname, $username, $password, $database);

  $consulta="SELECT * FROM ordenes";
  $resulta=mysqli_query($conexion,$consulta);

  while ($registro=mysqli_fetch_array($resulta)) {
    $json['orden'][]=$registro;
  }
  mysqli_close($conexion);
  echo json_encode($json);
 ?>
