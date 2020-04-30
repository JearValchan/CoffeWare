<?php
$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  if(isset($_GET["ID"])){
    $idProducto=$_GET['ID'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);

    $consulta="SELECT nombre, producto_type, preciobase, id_categoria FROM productos WHERE ID = {$idProducto}";
    $resultado_consulta=mysqli_query($conexion,$consulta);

    if($registro=mysqli_fetch_array($resultado_consulta)){
      $json['producto'][]=$registro;
    }
    else {
      $resulta["ID"]=0;
      $resulta["producto_type"]='No registra';
      $resulta["nombre"]='No registra';
      $resulta["preciobase"]=0;
      $resulta["id_categoria"]=0;
      $json['producto'][]=$resulta;
    }
    mysqli_close($conexion);
    echo json_encode($json);
  }
  else{
    $resulta["ID"]=0;
    $resulta["producto_type"]='WS No retorna';
    $resulta["nombre"]='WS No retorna';
    $resulta["preciobase"]=0;
    $resulta["id_categoria"]=0;
    $json['producto'][]=$resulta;
    echo json_encode($json);
  }
 ?>
