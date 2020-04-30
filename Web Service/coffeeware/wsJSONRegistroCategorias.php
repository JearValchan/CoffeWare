<?php

$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

if(isset($_GET["ID"]) && isset($_GET["nombre"])){
    $idCategoria=$_GET['ID'];
    $nombre=$_GET['nombre'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);
    $insert="INSERT INTO `categorias` (`ID`, `nombre`) VALUES ('{$idCategoria}', '{$nombre}')";
    $resultado_insert=mysqli_query($conexion,$insert);

    if($resultado_insert){
      $consulta="SELECT * FROM categorias WHERE ID = '{$idCategoria}'";
      $resultado=mysqli_query($conexion,$consulta);

      if($registro=mysqli_fetch_array($resultado)){
        $json['categoria'][]=$registro;
      }
      mysqli_close($conexion);
      echo json_encode($json);
    }
    else {
      $resulta["ID"]=0;
      $resulta["nombre"]='No registra';
      $json['categoria'][]=$resulta;
      echo json_encode($json);
    }
  }
  else{
    $resulta["ID"]=0;
    $resulta["nombre"]='WS No retorna';
    $json['categoria'][]=$resulta;
    echo json_encode($json);
  }

  /*http://192.168.0.13/coffeeware/wsJSONRegistroCategorias.php?ID=4&nombre=Pito*/

 ?>
