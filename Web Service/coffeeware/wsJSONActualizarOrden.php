<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

  $conexion = mysqli_connect($hostname,$username,$password,$database);

  if(isset($_POST["ID"]) && isset($_POST["cliente"]) && isset($_POST["ESTADO"]) && isset($_POST["preciofinal"])){
      $idOrden=$_POST['ID'];
      $cliente=$_POST['cliente'];
      $estadoOrden=$_POST['ESTADO'];
      $precioFinalOrden=$_POST['preciofinal'];

    $sql="UPDATE `ordenes` SET `cliente` = ?, `ESTADO` = ?, `preciofinal` = ? WHERE `ordenes`.`ID` = ?";
  	$stm=$conexion->prepare($sql);
  	$stm->bind_param('ssdi',$cliente,$estadoOrden,$precioFinalOrden,$idOrden);

  	if($stm->execute()){
  		echo "actualiza";
  	}else{
  		echo "noActualiza";
  	}
  	mysqli_close($conexion);
  }
?>
