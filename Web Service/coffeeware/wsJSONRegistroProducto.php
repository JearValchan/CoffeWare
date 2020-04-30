<?php

$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  if(isset($_GET["ID"]) && isset($_GET["producto_type"]) && isset($_GET["nombre"]) && isset($_GET["preciobase"]) && isset($_GET["id_categoria"])){
    $idProducto=$_GET['ID'];
    $tipoProducto=$_GET['producto_type'];
    $nombreProducto=$_GET['nombre'];
    $precioBaseProducto=$_GET['preciobase'];
    $categoriaProducto=$_GET['id_categoria'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);

    $insert="INSERT INTO productos (ID, producto_type, nombre, preciobase, id_categoria) VALUES('{$idProducto}','{$tipoProducto}','{$nombreProducto}','{$precioBaseProducto}','{$categoriaProducto}')";
    $resultado_insert=mysqli_query($conexion,$insert);

    if($resultado_insert){
      $consulta="SELECT * FROM productos WHERE ID = '{$idProducto}'";
      $resultado=mysqli_query($conexion,$consulta);

      if($registro=mysqli_fetch_array($resultado)){
        $json['producto'][]=$registro;
      }
      mysqli_close($conexion);
      echo json_encode($json);
    }
    else {
      $resulta["ID"]=0;
      $resulta["producto_type"]='No registra';
      $resulta["nombre"]='No registra';
      $resulta["preciobase"]=0;
      $resulta["id_categoria"]=0;
      $json['producto'][]=$resulta;
      echo json_encode($json);
    }
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
