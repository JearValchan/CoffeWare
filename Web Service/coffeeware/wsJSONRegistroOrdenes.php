<?php

$hostname="localhost";
$database="coffeeware";
$username="root";
$password="";

$json=array();

if(isset($_GET["ID"]) && isset($_GET["cliente"]) && isset($_GET["ESTADO"]) && isset($_GET["preciofinal"])){
    $idOrden=$_GET['ID'];
    $cliente=$_GET['cliente'];
    $estadoOrden=$_GET['ESTADO'];
    $precioFinalOrden=$_GET['preciofinal'];

    $conexion=mysqli_connect($hostname,$username,$password,$database);
    $insert="INSERT INTO `ordenes` (`ID`, `cliente`, `ESTADO`, `preciofinal`) VALUES ('{$idOrden}', '{$cliente}', '{$estadoOrden}', '{$precioFinalOrden}')";
    $resultado_insert=mysqli_query($conexion,$insert);

    if($resultado_insert){
      $consulta="SELECT * FROM ordenes WHERE ID = '{$idOrden}'";
      $resultado=mysqli_query($conexion,$consulta);

      if($registro=mysqli_fetch_array($resultado)){
        $json['orden'][]=$registro;
      }
      mysqli_close($conexion);
      echo json_encode($json);
    }
    else {
      $resulta["ID"]=0;
      $resulta["cliente"]='No registra';
      $resulta["ESTADO"]='No registra';
      $resulta["preciofinal"]=0;
      $json['orden'][]=$resulta;
      echo json_encode($json);
    }
  }
  else{
    $resulta["ID"]=0;
    $resulta["cliente"]='WS No retorna';
    $resulta["ESTADO"]='WS No retorna';
    $resulta["preciofinal"]=0;
    $json['orden'][]=$resulta;
    echo json_encode($json);
  }

  /* http://192.168.0.13/coffeeware/wsJSONRegistroOrdenes.php?ID=3&cliente=Armando&ESTADO=Pagado&preciofinal=500*/
 ?>
