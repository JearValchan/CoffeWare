<?php
$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  $conexion = mysqli_connect($hostname, $username, $password, $database);

  $consulta="SELECT * FROM `productos`";
  $resulta=mysqli_query($conexion,$consulta);

  while ($registro=mysqli_fetch_array($resulta)) {
    $json['producto'][]=$registro;
  }
  mysqli_close($conexion);
  echo json_encode($json);

  /*http://192.168.0.13/coffeeware/wsConsultarListaOrdenes.php*/
 ?>
