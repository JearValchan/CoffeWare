<?php
$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  if(isset($_GET["ID"])){
    $idCategoria=$_GET['ID'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);

    $consulta="SELECT nombre FROM categorias WHERE ID = {$idCategoria}";
    $resultado_consulta=mysqli_query($conexion,$consulta);

    if($registro=mysqli_fetch_array($resultado_consulta)){
      $json['categoria'][]=$registro;
    }
    else {
      $resulta["ID"]=0;
      $resulta["nombre"]='No registra';
      $json['categoria'][]=$resulta;
    }
    mysqli_close($conexion);
    echo json_encode($json);
  }
  else{
    $resulta["ID"]=0;
    $resulta["nombre"]='WS No retorna';
    $json['categoria'][]=$resulta;
    echo json_encode($json);
  }

  /*http://192.168.0.13/coffeeware/wsJSONConsultarCategoria.php?ID=4*/
 ?>
