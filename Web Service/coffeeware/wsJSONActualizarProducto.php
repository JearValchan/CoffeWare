<?PHP
  $hostname="localhost";
  $database="coffeeware";
  $username="root";
  $password="";

  $conexion = mysqli_connect($hostname,$username,$password,$database);

  if(isset($_POST["ID"]) && isset($_POST["producto_type"]) && isset($_POST["nombre"]) && isset($_POST["preciobase"]) && isset($_POST["id_categoria"])){
    $idProducto = $_POST["ID"];
  	$tipoProducto = $_POST["producto_type"];
  	$nombreProducto = $_POST["nombre"];
  	$precioBaseProducto = $_POST["preciobase"];
    $categoriaProducto = $_POST["id_categoria"];

    $sql="UPDATE `productos` SET `producto_type` = ?, `nombre` = ?, `preciobase` = ?, `id_categoria` = ? WHERE `productos`.`ID` = ?";
  	$stm=$conexion->prepare($sql);
  	$stm->bind_param('ssdii',$tipoProducto,$nombreProducto,$precioBaseProducto,$categoriaProducto,$idProducto);

  	if($stm->execute()){
  		echo "actualiza";
  	}else{
  		echo "noActualiza";
  	}
  	mysqli_close($conexion);
  }
?>
