<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

	if(isset($_GET["ID"])){
		$idOrden=$_GET["ID"];

		$conexion = mysqli_connect($hostname,$username,$password,$database);

		$sql="DELETE FROM ordenes WHERE ID = ? ";
		$stm=$conexion->prepare($sql);
		$stm->bind_param('i',$idOrden);

		if($stm->execute()){
			echo "elimina";
		}else{
			echo "noElimina";
		}

		mysqli_close($conexion);
	}
	else{
		echo "noExiste";
	}
  /*http://192.168.0.13/coffeeware/wsJSONEliminarOrden.php?ID=3*/
?>
