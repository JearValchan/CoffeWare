<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

  $conexion = mysqli_connect($hostname,$username,$password,$database);

  if(isset($_POST["ID"]) && isset($_POST["nombre"])){
      $idCategoria=$_POST['ID'];
      $nombre=$_POST['nombre'];

    $sql="UPDATE `categorias` SET `nombre` = ? WHERE `categorias`.`ID` = ?";
  	$stm=$conexion->prepare($sql);
  	$stm->bind_param('si',$nombre,$idCategoria);

  	if($stm->execute()){
  		echo "actualiza";
  	}else{
  		echo "noActualiza";
  	}
  	mysqli_close($conexion);
  }
?>
