<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

	if(isset($_GET["ID"])){
		$idCategoria=$_GET["ID"];

		$conexion = mysqli_connect($hostname,$username,$password,$database);

		$sql="DELETE FROM categorias WHERE ID = ? ";
		$stm=$conexion->prepare($sql);
		$stm->bind_param('i',$idCategoria);

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
  /*http://192.168.0.13/coffeeware/wsJSONEliminarCategoria.php?ID=4*/
?>
