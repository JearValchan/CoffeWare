<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

	if(isset($_GET["ID"])){
		$idProducto=$_GET["ID"];

		$conexion = mysqli_connect($hostname,$username,$password,$database);

		$sql="DELETE FROM productos WHERE ID = ? ";
		$stm=$conexion->prepare($sql);
		$stm->bind_param('i',$idProducto);

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

?>
