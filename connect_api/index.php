<?php 
	if (isset($_POST['tag']) && $_POST['tag'] != '') {
		$tag = $_POST['tag'];
		require_once 'include/DB_Functions.php';
		$db = new DB_Functions();
		$response = array("tag" => $tag, "success" => 0, "error" => 0);
	
		if ($tag == 'login') {
			$email = $_POST['email'];
			$password = $_POST['password'];
	
			$user = $db->getUserByEmailAndPassword($email, $password);
			if ($user != false) {
				$response["success"] = 1;
				$response["user"]["id"] = $user["id"];
				$response["user"]["email"] = $user["email"];
				$response["user"]["data1"] = $user["data1"];
				$response["user"]["data2"] = $user["data2"];
				echo json_encode($response);
			} else {
				$response["error"] = 1;
				$response["error_msg"] = "Incorrect email or password!";
				echo json_encode($response);
			}
		} else if ($tag == 'register') {
			$name = $_POST['name'];
			$email = $_POST['email'];
			$password = $_POST['password'];
	
			if ($db->isUserExisted($email)) {
				$response["error"] = 2;
				$response["error_msg"] = "User already existed";
				echo json_encode($response);
			} else {
				$user = $db->storeUser($name, $email, $password);
				if ($user) {
					$response["success"] = 1;
					$response["user"]["id"] = $user["id"];
					$response["user"]["email"] = $user["email"];
					$response["user"]["data1"] = $user["data1"];
					$response["user"]["data2"] = $user["data2"];
					echo json_encode($response);
				} else {
					$response["error"] = 1;
					$response["error_msg"] = "Error occured in Registartion";
					echo json_encode($response);
				}
			}
		} else if ($tag == 'addPlace') {
			$name = $_POST['name'];
			$addresspos = array();
			for($i = 0; $i < $_POST; $i++) {
				$o = array_keys($_POST[$i]);
				if(strpos($o[0], "address") !== false)
					array_push($addresspos, $i);
			} if(count($addresspos) > 1) {
				$address = array();
				for($i = 0; $i < count($addresspos); $i++)
					$address[$i] = $_POST[$addresspos[$i]];
			} else
				$address = $addresspos[0];
			$place = $db->storePlace($name, $address, "", "", "", "");
			if ($place) {
				$response["success"] = 1;
				$response["user"]["id"] = $place["id"];
				$response["user"]["data1"] = $place["data1"];
				$response["user"]["data2"] = $place["data2"];
				echo json_encode($response);
			} else {
				$response["error"] = 1;
				$response["error_msg"] = "An Error occured";
				echo json_encode($response);
			}
			/*$email = $_POST['email'];
			$password = $_POST['password'];
	
			if ($db->isUserExisted($email)) {
				$response["error"] = 2;
				$response["error_msg"] = "User already existed";
				echo json_encode($response);
			} else {
				$user = $db->storeUser($name, $email, $password);
				if ($user) {
					$response["success"] = 1;
					$response["user"]["id"] = $user["id"];
					$response["user"]["email"] = $user["email"];
					$response["user"]["data1"] = $user["data1"];
					$response["user"]["data2"] = $user["data2"];
					echo json_encode($response);
				} else {
					$response["error"] = 1;
					$response["error_msg"] = "Error occured in Registartion";
					echo json_encode($response);
				}
			}*/
		} else {
			echo "Invalid Request";
		}
	} else {
		echo "Access Denied";
	} 
?>