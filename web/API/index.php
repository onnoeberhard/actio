<?php 
	error_reporting(0);
	if (isset($_POST['tag']) && $_POST['tag'] != '') {
		$tag = $_POST['tag'];
		mysql_connect('db4.cwsurf.de', 'actio', 'cwcitY22321');
		//mysql_connect('localhost', 'actio', '1234');
        mysql_select_db("actio");
		$response = array("tag" => $tag, "success" => 0, "error" => 0);
		if ($tag == 'EXEC') {
			$sql = $_POST['sql'];
			$result = mysql_query($sql);
			if($result !== false)
				$response["success"] = 1;
			else
				$response["error"] = 1;
			$response["sql"] = $sql;
			echo json_encode($response);
		} else if ($tag == 'EXPLODE') {
			$string = $_POST['string'];
			$result = mysql_query($sql);
			$response["values"] = explode("{{;}}", $string);
			$keys = array();
			$values = array();
			for($i = 0; $i < count($response["values"]); $i++) {
				$o = explode("{{:}}", $response["values"][$i]);
				array_push($keys, $o[0]);
				$values[$i] = array();
				for($ii = 0; $ii < count($o); $ii++) {
					if(strpos($o[$ii], "{{,}}") !== false) {
						$oii = explode("{{,}}", $o[$ii]);
						$nextLevel = false;
						for($iii = 0; $iii < count($oii); $iii++) {
							if(strpos($oii[$iii], "{{.}}") !== false) {
								$nextLevel = true;
								$oiii = explode("{{.}}", $oii[$iii]);
								$nextLevel2 = false;
								for($iv = 0; $iv < count($oiii); $iv++) {
									if(strpos($oiii[$iv], "{{|}}") !== false) {
										$nextLevel2 = true;
										$oiv = explode("{{|}}", $oiii[$iv]);
										$values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($values[$i], $o[$ii]);
					}
				}
			}
			$response["values"] = array_combine($keys, $values);
			$response["success"] = 1;
			echo json_encode($response);
		} else if ($tag == 'GET') {
			$table = $_POST['table'];
			$id_name = $_POST['id_name'];
			$id_value = $_POST['id_value'];
			$column = $_POST['column'];
			
			$sql = "SELECT * FROM `".$table."` WHERE `".$id_name."` = '".$id_value."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$columns = $rows[0];
			if($columns[$column] == NULL) {
				$response["error"] = 1;
			} else {
				$response["success"] = 1;
			}
			$response["name"] = $column;
			$response["value"] = $columns[$column];
			$response["values"] = explode("{{;}}", $columns[$column]);
			$keys = array();
			$values = array();
			for($i = 0; $i < count($response["values"]); $i++) {
				$o = explode("{{:}}", $response["values"][$i]);
				array_push($keys, $o[0]);
				$values[$i] = array();
				for($ii = 0; $ii < count($o); $ii++) {
					if(strpos($o[$ii], "{{,}}") !== false) {
						$oii = explode("{{,}}", $o[$ii]);
						$nextLevel = false;
						for($iii = 0; $iii < count($oii); $iii++) {
							if(strpos($oii[$iii], "{{.}}") !== false) {
								$nextLevel = true;
								$oiii = explode("{{.}}", $oii[$iii]);
								$nextLevel2 = false;
								for($iv = 0; $iv < count($oiii); $iv++) {
									if(strpos($oiii[$iv], "{{|}}") !== false) {
										$nextLevel2 = true;
										$oiv = explode("{{|}}", $oiii[$iv]);
										$values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($values[$i], $o[$ii]);
					}
				}
			}
			$response["values"] = array_combine($keys, $values);
			$response["row"] = $columns;
			echo json_encode($response);
		} else if ($tag == 'GETALL') {
			$table = $_POST['table'];
			$column = $_POST['column'];
			$id_name = $_POST['id_name'];
			$id_value = $_POST['id_value'];

			$sql = "SELECT * FROM `".$table."`";
			if($id_name != "")
				$sql = "SELECT * FROM `".$table."` WHERE `".$id_name."` = '".$id_value."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$value = array();
			for($i = 0; $i < count($rows); $i++)
				$__values[$i] = $rows[$i][$column];
			if($rows == NULL || count($rows) == 0) {
				$response["error"] = 1;
			} else {
				$response["success"] = 1;
			}
			$response["rows"] = $rows;
			$response["value"] = $__values;
			$_values = array();
			for($oo = 0; $oo < count($__values); $oo++) {
				$_value = $__values[$oo];
				$value = explode("{{;}}", $_value);
				$keys = array();
				$values = array();
				for($i = 0; $i < count($value); $i++) {
					$o = explode("{{:}}", $value[$i]);
					array_push($keys, $o[0]);
					$values[$i] = array();
					for($ii = 0; $ii < count($o); $ii++) {
						if(strpos($o[$ii], "{{,}}") !== false) {
							$oii = explode("{{,}}", $o[$ii]);
							$nextLevel = false;
							for($iii = 0; $iii < count($oii); $iii++) {
								if(strpos($oii[$iii], "{{.}}") !== false) {
									$nextLevel = true;
									$oiii = explode("{{.}}", $oii[$iii]);
									$nextLevel2 = false;
									for($iv = 0; $iv < count($oiii); $iv++) {
										if(strpos($oiii[$iv], "{{|}}") !== false) {
											$nextLevel2 = true;
											$oiv = explode("{{|}}", $oiii[$iv]);
											$values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
										} else
											$values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
									}
								} else
									$values[$i][$oii[0]][$iii] = $oii[$iii];
							}
						} else {
							array_push($values[$i], $o[$ii]);
						}
					}
				}
				$_values[$oo] = array_combine($keys, $values);
			}
			$response["values"] = $_values;
			echo json_encode($response);
		} else if ($tag == 'SMARTINUP') {
			$table = $_POST['table'];
			$doit = true;
			$pairs = array();
			for($i = 0; $i < count($_POST)-1; $i++) {
				if($_POST['name_'.$i] != "") {
					$pairs[$i]["name"] = $_POST['name_'.$i];
					$pairs[$i]["value"] = $_POST['value_'.$i];
				}
				if($_POST['name_'.$i] == "ABORT" || $_POST['value_'.$i] == "ABORT")
					$doit = false;
			}
			$sql = "SELECT * FROM `".$table."` WHERE `".$pairs[0]["name"]."` = '".$pairs[0]["value"]."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$columns = $rows[0];
			$sql = "";
			if($columns["id"] == NULL && $pairs[1]["value"] != "DELETE") {
				$response["action"] = "insert";
				$sql = "INSERT INTO `".$table."` (";
				for($i = 0; $i < count($pairs); $i++) {
					$sql .= "`".$pairs[$i]["name"]."`";
					if($i < (count($pairs) - 1))
						$sql .= ", ";
				}
				$sql .= ")VALUES(";
				for($i = 0; $i < count($pairs); $i++) {
					$sql .= "'".$pairs[$i]["value"]."'";
					if($i < (count($pairs) - 1))
						$sql .= ", ";
				}
				$sql .= ")";
			} else if($pairs[1]["value"] != "" && $pairs[1]["value"] != "DELETE") {
				$response["action"] = "update";
				$sql = "UPDATE `".$table."` SET ";
				for($i = 1; $i < count($pairs); $i++) {
					$sql .= "`".$pairs[$i]["name"]."` = '".$pairs[$i]["value"]."'";
					if($i < (count($pairs) - 1))
						$sql .= ", ";
				}
				$sql .= " WHERE `".$pairs[0]["name"]."` = '".$pairs[0]["value"]."'";
			} else {
				$response["action"] = "delete";
				$sql = "DELETE FROM `".$table."` WHERE `".$pairs[0]["name"]."` = '".$pairs[0]["value"]."'";
			}
			$result = false;
			if($doit)
				$result = mysql_query($sql);
			if($result !== false)
				$response["success"] = 1;
			else
				$response["error"] = 1;
			$response["sql1"] = $sql;
			$sql = "SELECT * FROM `".$table."` WHERE `".$pairs[0]["name"]."` = '".$pairs[0]["value"]."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$columns = $rows[0];
			$response["row"] = $columns;
			echo json_encode($response);
		} else if ($tag == 'INUP_DATA') {
			$table = $_POST['table'];
			$id_name = $_POST['id_name'];
			$id_value = $_POST['id_value'];
			$column = $_POST['column'];
			$level1 = $_POST['level1'];
			$level2 = $_POST['level2'];
			$level3 = $_POST['level3'];
			$level4 = $_POST['level4'];
			$value = $_POST['value'];
			$sql = "SELECT * FROM `".$table."` WHERE `".$id_name."` = '".$id_value."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$columns = $rows[0];
			$data = explode("{{;}}", $columns[$column]);
			$keys = array();
			$values = array();
			for($i = 0; $i < count($data); $i++) {
				$o = explode("{{:}}", $data[$i]);
				array_push($keys, $o[0]);
				$values[$i] = array();
				for($ii = 0; $ii < count($o); $ii++) {
					if(strpos($o[$ii], "{{,}}") !== false) {
						$oii = explode("{{,}}", $o[$ii]);
						$nextLevel = false;
						for($iii = 0; $iii < count($oii); $iii++) {
							if(strpos($oii[$iii], "{{.}}") !== false) {
								$nextLevel = true;
								$oiii = explode("{{.}}", $oii[$iii]);
								$nextLevel2 = false;
								for($iv = 0; $iv < count($oiii); $iv++) {
									if(strpos($oiii[$iv], "{{|}}") !== false) {
										$nextLevel2 = true;
										$oiv = explode("{{|}}", $oiii[$iv]);
										$values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($values[$i], $o[$ii]);
					}
				}
			}
			$data = array_combine($keys, $values);
			$levels = 4 - (($level1 == "") ? 1 : 0) - (($level2 == "") ? 1 : 0) - (($level3 == "") ? 1 : 0) - (($level4 == "") ? 1 : 0);
			if($levels == 4)
				$data[$level1][$level2][$level3][$level4] = $value;
			if($levels == 3)
				$data[$level1][$level2][$level3] = $value;
			if($levels == 2)
				$data[$level1][$level2] = $value;
			if($levels == 1)
				$data[$level1] = $value;
			if($levels == 0)
				$data = $value;
			$keysI = array_keys($data);
			for($i = 0; $i < count($data); $i++) {
				if(is_array($data[$keysI[$i]])) {
					$keysII = array_keys($data[$keysI[$i]]);
					for($ii = 0; $ii < count($data[$keysI[$i]]); $ii++) {
						if(is_array($data[$keysI[$i]][$keysII[$ii]])) {
							$keysIII = array_keys($data[$keysI[$i]][$keysII[$ii]]);
							for($iii = 0; $iii < count($data[$keysI[$i]][$keysII[$ii]]); $iii++) {
								if(is_array($data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]])) {
									$keysIV = array_keys($data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
									for($iv = 0; $iv < count($data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]); $iv++) {
										if(is_array($data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]])) {
											$data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]] = 
												implode("{{|}}", $data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]]);
										}
									}
									$data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]] = implode("{{.}}", $data[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
								}
							}
							$data[$keysI[$i]][$keysII[$ii]] = implode("{{,}}", $data[$keysI[$i]][$keysII[$ii]]);
						}
					}
					$data[$keysI[$i]] = implode("{{:}}", $data[$keysI[$i]]);
				}
			}
			$data = implode("{{;}}", array_filter($data));
			$sql = "UPDATE `".$table."` SET `".$column."` = '".$data."' WHERE `".$id_name."` = '".$id_value."'";
			$result = mysql_query($sql);
			if($result !== false)
				$response["success"] = 1;
			else
				$response["error"] = 1;
			$response["sql1"] = $sql;
			$sql = "SELECT * FROM `".$table."` WHERE `".$id_name."` = '".$id_value."'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$columns = $rows[0];
			$response["row"] = $columns;
			echo json_encode($response);
		} else {
			echo "Invalid Request";
		}
	} else {
		echo "Access Denied";
	} 
?>