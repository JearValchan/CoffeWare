<?php
$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

  if(isset($_GET["ID"])){
    $idOrden=$_GET['ID'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);

    $consulta="SELECT cliente, ESTADO, preciofinal FROM ordenes WHERE ID = {$idOrden}";
    $resultado_consulta=mysqli_query($conexion,$consulta);

    if($registro=mysqli_fetch_array($resultado_consulta)){
      $json['orden'][]=$registro;
    }
    else {
      $resulta["ID"]=0;
      $resulta["cliente"]='No registra';
      $resulta["ESTADO"]='No registra';
      $resulta["preciofinal"]=0;
      $json['orden'][]=$resulta;
    }
    mysqli_close($conexion);
    echo json_encode($json);
  }
  else{
    $resulta["ID"]=0;
    $resulta["cliente"]='WS No retorna';
    $resulta["ESTADO"]='WS No retorna';
    $resulta["preciofinal"]=0;
    $json['orden'][]=$resulta;
    echo json_encode($json);
  }

  /*http://192.168.0.13/coffeeware/wsJSONConsultarOrden.php?ID=1*/
 ?>
