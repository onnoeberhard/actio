<?php
	class Place {
		public $id;
		public $columns = array();
		public $data1 = array();
		public $data2 = array();
		public $data3 = array();
		function buildFromId($_id) {
			$this->id = $_id;
			$sql = "SELECT * FROM `places` WHERE `id` = $this->id";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$this->columns = $rows[0];
			$this->data1 = explode("{{;}}", $this->columns['data1']);
			$data1_keys = array();
			$data1_values = array();
			for($i = 0; $i < count($this->data1); $i++) {
				$o = explode("{{:}}", $this->data1[$i]);
				array_push($data1_keys, $o[0]);
				$data1_values[$i] = array();
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
										$data1_values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$data1_values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$data1_values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($data1_values[$i], $o[$ii]);
					}
				}
			}
			$this->data1 = array_combine($data1_keys, $data1_values);
			$this->data2 = explode("{{;}}", $this->columns['data2']);
			$data2_keys = array();
			$data2_values = array();
			for($i = 0; $i < count($this->data2); $i++) {
				$o = explode("{{:}}", $this->data2[$i]);
				array_push($data2_keys, $o[0]);
				$data2_values[$i] = array();
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
										$data2_values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$data2_values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$data2_values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($data2_values[$i], $o[$ii]);
					}
				}
			}
			$this->data2 = array_combine($data2_keys, $data2_values);
			$data3_keys = array();
			$data3_values = array();
			for($i = 0; $i < count($this->data3); $i++) {
				$o = explode("{{:}}", $this->data3[$i]);
				array_push($data3_keys, $o[0]);
				$data3_values[$i] = array();
				for($ii = 0; $ii < count($o); $ii++)
					array_push($data3_values[$i], $o[$ii]);
			}
			$this->data3 = array_combine($data3_keys, $data3_values);
		}
		function updateDB() {
			$data1 = $this->data1;
			$keysI = array_keys($data1);
			for($i = 0; $i < count($data1); $i++) {
				if(is_array($data1[$keysI[$i]])) {
					$keysII = array_keys($data1[$keysI[$i]]);
					for($ii = 0; $ii < count($data1[$keysI[$i]]); $ii++) {
						if(is_array($data1[$keysI[$i]][$keysII[$ii]])) {
							$keysIII = array_keys($data1[$keysI[$i]][$keysII[$ii]]);
							for($iii = 0; $iii < count($data1[$keysI[$i]][$keysII[$ii]]); $iii++) {
								if(is_array($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]])) {
									$keysIV = array_keys($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
									for($iv = 0; $iv < count($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]); $iv++) {
										if(is_array($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]])) {
											$data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]] = 
												implode("{{|}}", $data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]]);
										}
									}
									$data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]] = implode("{{.}}", $data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
								}
							}
							$data1[$keysI[$i]][$keysII[$ii]] = implode("{{,}}", $data1[$keysI[$i]][$keysII[$ii]]);
						}
					}
					$data1[$keysI[$i]] = implode("{{:}}", $data1[$keysI[$i]]);
				}
			}
			$data1 = implode("{{;}}", $data1);
			$data2 = $this->data2;
			$keysI = array_keys($data2);
			for($i = 0; $i < count($data2); $i++) {
				if(is_array($data2[$keysI[$i]])) {
					$keysII = array_keys($data2[$keysI[$i]]);
					for($ii = 0; $ii < count($data2[$keysI[$i]]); $ii++) {
						if(is_array($data2[$keysI[$i]][$keysII[$ii]])) {
							$keysIII = array_keys($data2[$keysI[$i]][$keysII[$ii]]);
							for($iii = 0; $iii < count($data2[$keysI[$i]][$keysII[$ii]]); $iii++) {
								if(is_array($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]])) {
									$keysIV = array_keys($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
									for($iv = 0; $iv < count($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]); $iv++) {
										if(is_array($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]])) {
											$data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]] = 
												implode("{{|}}", $data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]]);
										}
									}
									$data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]] = implode("{{.}}", $data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
								}
							}
							$data2[$keysI[$i]][$keysII[$ii]] = implode("{{,}}", $data2[$keysI[$i]][$keysII[$ii]]);
						}
					}
					$data2[$keysI[$i]] = implode("{{:}}", $data2[$keysI[$i]]);
				}
			}
			$data2 = implode("{{;}}", $data2);
			$data3_keys = array_keys($this->data3);
			$data3 = array();
			for($i = 0; $i < count($this->data3); $i++)
				array_push($data3, implode('{{:}}', $this->data3[$data3_keys[$i]]));
			$data3_new = '';
			for($i = 0; $i < count($data3); $i++) {
				if($data3[$i] != "{{:}}") {
					if($i != 0)
						$data3_new .= "{{;}}";
					$data3_new .= $data3[$i];
				}
			}
			$data3 = $data3_new;
			$sql = 
				"UPDATE `places` SET `data1` = '$data1', `data2` = '$data2', `data3` = '$data3' WHERE `id` = $this->id";
			mysql_query($sql);
		}
	}
	class Event {
		public $id;
		public $columns = array();
		public $data1 = array();
		public $data2 = array();
		public $data3 = array();
		function buildFromId($_id) {
			$this->id = $_id;
			$sql = "SELECT * FROM `events` WHERE `id` = $this->id";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$this->columns = $rows[0];
			$this->data1 = explode("{{;}}", $this->columns['data1']);
			$data1_keys = array();
			$data1_values = array();
			for($i = 0; $i < count($this->data1); $i++) {
				$o = explode("{{:}}", $this->data1[$i]);
				array_push($data1_keys, $o[0]);
				$data1_values[$i] = array();
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
										$data1_values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$data1_values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$data1_values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($data1_values[$i], $o[$ii]);
					}
				}
			}
			$this->data1 = array_combine($data1_keys, $data1_values);
			$this->data2 = explode("{{;}}", $this->columns['data2']);
			$data2_keys = array();
			$data2_values = array();
			for($i = 0; $i < count($this->data2); $i++) {
				$o = explode("{{:}}", $this->data2[$i]);
				array_push($data2_keys, $o[0]);
				$data2_values[$i] = array();
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
										$data2_values[$i][$oii[0]][$oiii[0]][$oiv[0]] = $oiv;
									} else
										$data2_values[$i][$oii[0]][$oiii[0]][$iv] = $oiii[$iv];
								}
							} else
								$data2_values[$i][$oii[0]][$iii] = $oii[$iii];
						}
					} else {
						array_push($data2_values[$i], $o[$ii]);
					}
				}
			}
			$this->data2 = array_combine($data2_keys, $data2_values);
			$data3_keys = array();
			$data3_values = array();
			for($i = 0; $i < count($this->data3); $i++) {
				$o = explode("{{:}}", $this->data3[$i]);
				array_push($data3_keys, $o[0]);
				$data3_values[$i] = array();
				for($ii = 0; $ii < count($o); $ii++)
					array_push($data3_values[$i], $o[$ii]);
			}
			$this->data3 = array_combine($data3_keys, $data3_values);
		}
		function updateDB() {
			$data1 = $this->data1;
			$keysI = array_keys($data1);
			for($i = 0; $i < count($data1); $i++) {
				if(is_array($data1[$keysI[$i]])) {
					$keysII = array_keys($data1[$keysI[$i]]);
					for($ii = 0; $ii < count($data1[$keysI[$i]]); $ii++) {
						if(is_array($data1[$keysI[$i]][$keysII[$ii]])) {
							$keysIII = array_keys($data1[$keysI[$i]][$keysII[$ii]]);
							for($iii = 0; $iii < count($data1[$keysI[$i]][$keysII[$ii]]); $iii++) {
								if(is_array($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]])) {
									$keysIV = array_keys($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
									for($iv = 0; $iv < count($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]); $iv++) {
										if(is_array($data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]])) {
											$data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]] = 
												implode("{{|}}", $data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]]);
										}
									}
									$data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]] = implode("{{.}}", $data1[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
								}
							}
							$data1[$keysI[$i]][$keysII[$ii]] = implode("{{,}}", $data1[$keysI[$i]][$keysII[$ii]]);
						}
					}
					$data1[$keysI[$i]] = implode("{{:}}", $data1[$keysI[$i]]);
				}
			}
			$data1 = implode("{{;}}", $data1);
			$data2 = $this->data2;
			$keysI = array_keys($data2);
			for($i = 0; $i < count($data2); $i++) {
				if(is_array($data2[$keysI[$i]])) {
					$keysII = array_keys($data2[$keysI[$i]]);
					for($ii = 0; $ii < count($data2[$keysI[$i]]); $ii++) {
						if(is_array($data2[$keysI[$i]][$keysII[$ii]])) {
							$keysIII = array_keys($data2[$keysI[$i]][$keysII[$ii]]);
							for($iii = 0; $iii < count($data2[$keysI[$i]][$keysII[$ii]]); $iii++) {
								if(is_array($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]])) {
									$keysIV = array_keys($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
									for($iv = 0; $iv < count($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]); $iv++) {
										if(is_array($data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]])) {
											$data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]] = 
												implode("{{|}}", $data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]][$keysIV[$iv]]);
										}
									}
									$data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]] = implode("{{.}}", $data2[$keysI[$i]][$keysII[$ii]][$keysIII[$iii]]);
								}
							}
							$data2[$keysI[$i]][$keysII[$ii]] = implode("{{,}}", $data2[$keysI[$i]][$keysII[$ii]]);
						}
					}
					$data2[$keysI[$i]] = implode("{{:}}", $data2[$keysI[$i]]);
				}
			}
			$data2 = implode("{{;}}", $data2);
			$data3_keys = array_keys($this->data3);
			$data3 = array();
			for($i = 0; $i < count($this->data3); $i++)
				array_push($data3, implode('{{:}}', $this->data3[$data3_keys[$i]]));
			$data3_new = '';
			for($i = 0; $i < count($data3); $i++) {
				if($data3[$i] != "{{:}}") {
					if($i != 0)
						$data3_new .= "{{;}}";
					$data3_new .= $data3[$i];
				}
			}
			$data3 = $data3_new;
			$sql = 
				"UPDATE `events` SET `data1` = '$data1', `data2` = '$data2', `data3` = '$data3' WHERE `id` = $this->id";
			mysql_query($sql);
		}
	}
	function saveEvent($name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $td_type, $td_d_from, $td_d_to, $td_t_from,
	$td_t_to, $td_text, $cb_oh, $oh_text, $td_notes, $a_type, $admission, $admission_text, $admission_notes, $category, $theme, $catthe_notes, $email, $phone,
	$website, $contact_notes, $update) {
		$owner = "owner{{:}}" . $_SESSION['account_id'];
		$active = "{{;}}active{{:}}0";
		$name = "name{{:}}" . $name;
		$description = ($description != "") ? ("{{;}}description{{:}}" . $description) : "";
		$_img = "{{;}}img";
		for($i = 0; $i < count($img); $i++) {
			for($ii = 0; $ii < count($img[$i]); $ii++) {
				if($ii == 0)
					$_img .= "{{:}}i".$i."{{,}}";
				if($ii > 0)
					$_img .= "{{,}}";
				$_img .= $img[$i][$ii];
			}
		} $img = $_img;
		$address = "";
		if($address_type == "address") {
			$address = "{{;}}address{{:}}type{{,}}a{{:}}country{{,}}" . $address_a['country'];
			$address .= ($address_a['city'] != "") ? ("{{:}}city{{,}}" . $address_a['city']) : "";
			$address .= ($address_a['street'] != "") ? ("{{:}}street{{,}}" . $address_a['street']) : "";
		} else if($address_type == "coordinates" && $address_c != "")
			$address = "{{;}}address{{:}}type{{,}}c{{:}}c{{,}}" . $address_c;
		if($address != "" && $address_notes != "")
			$address .= "{{:}}notes{{,}}" . $address_notes;
		else if($address_notes != "")
			$address = "{{;}}address{{:}}type{{,}}t{{:}}text{{,}}" . $address_notes;
		$td = ""; $oh = "";
		if($td_type == "tdnt") {
			$td = "{{;}}td{{:}}type{{,}}nt{{:}}df{{,}}".$td_d_from."{{:}}tf{{,}}".$td_t_from."{{:}}dt{{,}}".$td_d_to."{{:}}tt{{,}}".$td_t_to;
		} else if($td_text != "") {
			$td = "{{;}}td{{:}}type{{,}}t{{:}}text{{,}}" . $td_text;
		} if($cb_oh && $oh_text != "") {
			$oh = "{{;}}oh{{:}}" . $oh_text;
		} if($td_notes != "")
			$td_notes = "{{;}}td_notes{{:}}" . $td_notes;
		$_admission = "";
		if($a_type == "ant") {
			if(!(!$admission[0][0] && !$admission[1][0] && !$admission[2][0] && !$admission[3][0] && !$admission[4][0] && !$admission[5][0] 
			&& !$admission[6][0] && !$admission[7][0])) {
					$_admission = array();
					for($i = 0; $i < 8; $i++)
					if($admission[$i][0]) {
						$_price = "i" . $i . "{{,}}" . $admission[$i][1];
						if($admission[$i][2])
							$_price .= "{{,}}ages{{.}}" . $admission[$i][3] . "{{.}}" . $admission[$i][4];
						$_price .= "{{,}}" . $admission[$i][5] . "{{,}}" . $admission[$i][6];
						array_push($_admission, $_price);
					}
					$_admission = "{{;}}admission{{:}}type{{,}}nt{{:}}" . implode("{{:}}", $_admission);
			}
		} else if($admission_text != "") {
			$_admission = "{{;}}admission{{:}}type{{,}}t{{:}}text{{,}}" . $admission_text;
		} if($admission_notes != "") {
			if($_admission == "")
				$_admission = "{{;}}admission{{:}}type{{,}}t{{:}}text{{,}}";
			$_admission .= "{{:}}notes{{,}}" . $admission_notes;
		}  
		$admission = $_admission;
		$catthe = "{{;}}catthe{{:}}cat{{,}}" . $category . "{{:}}the{{,}}" . $theme;
		if($catthe_notes != "")
			$catthe .= "{{:}}notes{{,}}" . $catthe_notes;
		$email = "{{;}}email{{:}}" . $email;
		$phone = ($phone != "") ? ("{{;}}phone{{:}}" . $phone) : "";
		$web = ($website != "") ? ("{{;}}web{{:}}" . $website) : "";
		$contact_notes = ($contact_notes != "") ? ("{{;}}contact_notes{{:}}" . $contact_notes) : "";
		$data1 = 
			$owner.$active;
		$data2 = 
			$name.$description.$img.$address.$td.$oh.$td_notes.$admission.$catthe.$email.$phone.$web.$contact_notes;
		if(!$update)
			$sql = "INSERT INTO `events` (`data1`,`data2`)VALUES('$data1','$data2')";
		else
			$sql = "UPDATE `events` SET `data1` = '$data1', `data2` = '$data2' WHERE ID = ".$_POST['id'];
		mysql_query($sql);
		if(!$update)
			echo"<meta http-equiv='Refresh' content='0;index.php'>";
		else
			echo"<meta http-equiv='Refresh' content='0;index.php?a=veEvent&b=".$_POST['id']."&tab=0'>";
	}
	function savePlace($pltype, $name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $cb_seasons, $seasons, $oh_type, 
	$opening_hours, $opening_hours_text, $ohs_notes, $a_type, $admission, $admission_text, $admission_notes, $m_type, $menu_text, $menu_notes, $menu_pics,
	$category, $theme, $catthe_notes, $email, $phone, $website, $contact_notes, $update) {
		$owner = "owner{{:}}" . $_SESSION['account_id'];
		$active = "{{;}}active{{:}}0";
		$name = "name{{:}}" . $name;
		$_pltype = $pltype;
		$pltype = "{{;}}pltype{{:}}" . $pltype;
		$description = ($description != "") ? ("{{;}}description{{:}}" . $description) : "";
		$_img = "{{;}}img";
		for($i = 0; $i < count($img); $i++) {
			for($ii = 0; $ii < count($img[$i]); $ii++) {
				if($ii == 0)
					$_img .= "{{:}}i".$i."{{,}}";
				if($ii > 0)
					$_img .= "{{,}}";
				$_img .= $img[$i][$ii];
			}
		} $img = $_img;
		$address = "";
		if($address_type == "address") {
			$address = "{{;}}address{{:}}type{{,}}a{{:}}country{{,}}" . $address_a['country'];
			$address .= ($address_a['city'] != "") ? ("{{:}}city{{,}}" . $address_a['city']) : "";
			$address .= ($address_a['street'] != "") ? ("{{:}}street{{,}}" . $address_a['street']) : "";
		} else if($address_type == "coordinates" && $address_c != "")
			$address = "{{;}}address{{:}}type{{,}}c{{:}}c{{,}}" . $address_c;
		if($address != "" && $address_notes != "")
			$address .= "{{:}}notes{{,}}" . $address_notes;
		else if($address_notes != "")
			$address = "{{;}}address{{:}}type{{,}}t{{:}}text{{,}}" . $address_notes;
		$openingHours = "";
		if($oh_type == "ohnt") {
			if(!(!$opening_hours['mo'][0] && !$opening_hours['tu'][0] && !$opening_hours['we'][0] && !$opening_hours['th'][0] && !$opening_hours['fr'][0] &&
				!$opening_hours['sa'][0] && !$opening_hours['su'][0])) {
					$openingHours = array();
					$week = array("mo", "tu", "we", "th", "fr", "sa", "su");
					for($i = 0; $i < count($week); $i++)
					if($opening_hours[$week[$i]][0]) {
						array_push($openingHours, 
						($week[$i] . "{{,}}" . $opening_hours[$week[$i]][1] . "{{,}}" . $opening_hours[$week[$i]][2] . "{{,}}" . 
						$opening_hours[$week[$i]][3] . "{{,}}" . $opening_hours[$week[$i]][4]));
					}
					$openingHours = "{{;}}opening_hours{{:}}type{{,}}nt{{:}}" . implode("{{:}}", $openingHours);
			}
		} else if($opening_hours_text != "") {
			$openingHours = "{{;}}opening_hours{{:}}type{{,}}t{{:}}text{{,}}" . $opening_hours_text;
		} if($cb_seasons && $seasons != "") {
			$seasons = "{{;}}seasons{{:}}" . $seasons;
		} if($ohs_notes != "")
			$ohs_notes = "{{;}}ohs_notes{{:}}" . $ohs_notes;
		$_admission = "";
		$_menu = "";
		if($_pltype == "pldo") {
			if($a_type == "ant") {
				if(!(!$admission[0][0] && !$admission[1][0] && !$admission[2][0] && !$admission[3][0] && !$admission[4][0] && !$admission[5][0] 
				&& !$admission[6][0] && !$admission[7][0])) {
						$_admission = array();
						for($i = 0; $i < 8; $i++)
						if($admission[$i][0]) {
							$_price = "i" . $i . "{{,}}" . $admission[$i][1];
							if($admission[$i][2])
								$_price .= "{{,}}ages{{.}}" . $admission[$i][3] . "{{.}}" . $admission[$i][4];
							$_price .= "{{,}}" . $admission[$i][5] . "{{,}}" . $admission[$i][6];
							array_push($_admission, $_price);
						}
						$_admission = "{{;}}admission{{:}}type{{,}}nt{{:}}" . implode("{{:}}", $_admission);
				}
			} else if($admission_text != "") {
				$_admission = "{{;}}admission{{:}}type{{,}}t{{:}}text{{,}}" . $admission_text;
			} if($admission_notes != "") {
				if($_admission == "")
					$_admission = "{{;}}admission{{:}}type{{,}}t{{:}}text{{,}}";
				$_admission .= "{{:}}notes{{,}}" . $admission_notes;
			} 
		} else {
			if($m_type == "mt" && $menu_text != "") {
				$_menu = "{{;}}menu{{:}}type{{,}}t{{:}}" . $menu_text;
			} else if($m_type == "mp") {
				$_menu = "{{;}}menu{{:}}type{{,}}p{{:}}" . implode("{{:}}", $menu_pics);
			} if($menu_notes != "") {
				if($_menu == "")
						$_menu = "{{;}}menu{{:}}type{{,}}t{{:}}";
				$_menu .= "{{:}}notes{{,}}" . $menu_notes;
			}
		} 
		$admission = $_admission;
		$menu = $_menu;
		$catthe = "{{;}}catthe{{:}}cat{{,}}" . $category . "{{:}}the{{,}}" . $theme;
		if($catthe_notes != "")
			$catthe .= "{{:}}notes{{,}}" . $catthe_notes;
		$email = "{{;}}email{{:}}" . $email;
		$phone = ($phone != "") ? ("{{;}}phone{{:}}" . $phone) : "";
		$web = ($website != "") ? ("{{;}}web{{:}}" . $website) : "";
		$contact_notes = ($contact_notes != "") ? ("{{;}}contact_notes{{:}}" . $contact_notes) : "";
		$data1 = 
			$owner.$active;
		$data2 = 
			$name.$pltype.$description.$img.$address.$openingHours.$seasons.$ohs_notes.$admission.$menu.$catthe.$email.$phone.$web.$contact_notes;
		//echo '<textarea>'.$data2.'</textarea>';
		if(!$update)
			$sql = "INSERT INTO `places` (`data1`,`data2`)VALUES('$data1','$data2')";
		else
			$sql = "UPDATE `places` SET `data1` = '$data1', `data2` = '$data2' WHERE ID = ".$_POST['id'];
		mysql_query($sql);
		if(!$update)
			echo"<meta http-equiv='Refresh' content='0;index.php'>";
		else
			echo"<meta http-equiv='Refresh' content='0;index.php?a=vePlace&b=".$_POST['id']."&tab=0'>";
	}
	function createActivity($type) {
		if($type == "place") {
			$ready = false;
			$name = ""; $description = ""; $address_type = "address"; $address_a = array("street" => "", "city" => "", "country" => ""); $address_c = "";
			$seasons = ""; $cb_seasons = false; $oh_type = "ohnt"; $a_type = "ant"; $address_notes = ""; $ohs_notes = ""; $admission_notes = "";
			$opening_hours = array("mo" => array(true, "00", "00", "24", "00"), "tu" => array(true, "00", "00", "24", "00"), "mo" => 
			array(true, "00", "00", "24", "00"), "we" => array(true, "00", "00", "24", "00"), "th" => array(true, "00", "00", "24", "00"), 
			"fr" => array(true, "00", "00", "24", "00"), "sa" => array(true, "00", "00", "24", "00"), "su" => array(true, "00", "00", "24", "00"));
			$admission = array(array(true, "adults", true, "18", "", "", "\$"), array(true, "kids", true, "0", "6", "", "\$"), 
			array(true, "students", false, "", "", "", "\$"), array(true, "monthly ticket", false, "", "", "", "\$"), 
			array(true, "annual ticket", false, "", "", "", "\$"), array(false, "", true, "", "", "", ""), array(false, "", true, "", "", "", ""), 
			array(false, "", true, "", "", "", "")); $email = ""; $phone = ""; $website = ""; $contact_notes = ""; $opening_hours_text = ""; 
			$admission_text = ""; $pltype = "pldo"; $m_type = "mt"; $menu_text = ""; $menu_notes = ""; $menu_pics = array("", "", "", "");
			$img = array(array("", ""), array("", ""), array("", "")); $catthe_notes = ""; $category = ""; $theme = "";
			if(isset($_POST['a']) && $_POST['a'] == "createActSubmission") {
				$error_strs = array("Please enter a name.", "Please enter an email address. ");
				$errors = array();
				$pltype = $_POST['pltype'];
				$name = $_POST['name'];
				$description = $_POST['description'];
				$img[0][0] = $_POST['img1'];
				$img[0][1] = $_POST['img1title'];
				$img[1][0] = $_POST['img2'];
				$img[1][1] = $_POST['img2title'];
				$img[2][0] = $_POST['img3'];
				$img[2][1] = $_POST['img3title'];
				$address_type = $_POST['address_type'];
				if($address_type == "address") {
					$address_a['street'] = $_POST['address_street'];
					$address_a['city'] = $_POST['address_city'];
					$address_a['country'] = $_POST['address_country'];
				} else
					$address_c = $_POST['address_coordinates'];
				$address_notes = $_POST['address_notes'];
				if(isset($_POST['cb_seasons'])) {
					$cb_seasons = $_POST['cb_seasons'];
					$seasons = $_POST['seasons'];
				}
				$oh_type = $_POST['oh_type'];
				if($oh_type == "ohnt") {
					$week = array("mo", "tu", "we", "th", "fr", "sa", "su");
					for($i = 0; $i < count($week); $i++) {
						if(isset($_POST['open_'.$week[$i]])) {
							$opening_hours[$week[$i]][0] = true;
							$opening_hours[$week[$i]][1] = $_POST['opening_hours_'.$week[$i].'_hr_from'];
							$opening_hours[$week[$i]][2] = $_POST['opening_hours_'.$week[$i].'_min_from'];
							$opening_hours[$week[$i]][3] = $_POST['opening_hours_'.$week[$i].'_hr_to'];
							$opening_hours[$week[$i]][4] = $_POST['opening_hours_'.$week[$i].'_min_to'];
						}
						else
							$opening_hours[$week[$i]] = array(false, "00", "00", "24", "00");
					}
				} else
					$opening_hours_text = $_POST['oh_text'];
				$ohs_notes = $_POST['ohs_notes'];
				$a_type = $_POST['a_type'];
				if($a_type == "ant") {
					for($i = 1; $i < 9; $i++) {
						if(isset($_POST['admission_'.$i])) {
							$admission[$i-1][0] = true;
							$admission[$i-1][1] = $_POST['admission_'.$i.'_name'];
							if(isset($_POST['admission_'.$i.'_ages'])) {
								$admission[$i-1][2] = true;
								$admission[$i-1][3] = $_POST['admission_'.$i.'_from'];
								$admission[$i-1][4] = $_POST['admission_'.$i.'_to'];
							} else {
								$admission[$i-1][2] = false;
								$admission[$i-1][3] = "";
								$admission[$i-1][4] = "";
							}
							$admission[$i-1][5] = $_POST['admission_'.$i.'_price'];
							$admission[$i-1][6] = $_POST['admission_'.$i.'_money'];
						} else
							$admission[$i-1] = array(false, "title", true, "age from", "age to", "price", "\$/&euro;/&pound;/&yen...");
					}
				} else
					$admission_text = $_POST['admission_text'];
				$admission_notes = $_POST['admission_notes'];
				$m_type = $_POST['m_type'];
				if($m_type == "mt")
					$menu_text = $_POST['menu_text'];
				else {
					$menu_pics[0] = $_POST['mpic1'];
					$menu_pics[1] = $_POST['mpic2'];
					$menu_pics[2] = $_POST['mpic3'];
					$menu_pics[3] = $_POST['mpic4'];
				}
				$menu_notes = $_POST['menu_notes'];
				$category = $_POST['category'];
				$theme = $_POST['theme'];
				$catthe_notes = $_POST['catthe_notes'];
				$email = $_POST['contact_email'];
				$phone = $_POST['contact_phone'];
				$website = $_POST['contact_website'];
				$contact_notes = $_POST['contact_notes'];
				if($name == "")
					array_push($errors, 0);
				if($email == "")
					array_push($errors, 1);
				if(empty($errors) || (isset($_POST['b']) && $_POST['b'] == "createActSubmitNTL")) {
					$ready = true;
					$owner = "";
					savePlace($pltype, $name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $cb_seasons, $seasons, $oh_type, 
					$opening_hours, $opening_hours_text, $ohs_notes, $a_type, $admission, $admission_text, $admission_notes, $m_type, $menu_text, $menu_notes,
					$menu_pics, $category, $theme, $catthe_notes, $email, $phone, $website, $contact_notes, false);
				}
			}
			if(!$ready) {
				include'act_fts/create_act_stuff.php';
				include "act_fts/create_place.php";
			}					
		} else if($type == "event") {
			$ready = false;
			$name = ""; $description = ""; $address_type = "address"; $address_a = array("street" => "", "city" => "", "country" => ""); $address_c = "";
			$a_type = "ant"; $address_notes = ""; $admission_notes = ""; $email = ""; $phone = ""; $website = "";
			$contact_notes = ""; $admission_text = ""; $img = array(array("", "Poster"), array("", ""), array("", ""));
			$admission = array(array(true, "adults", true, "18", "", "", "\$"), array(true, "kids", true, "0", "6", "", "\$"), 
			array(true, "students", false, "", "", "", "\$"), array(false, "", true, "", "", "", ""), array(false, "", true, "", "", "", ""), 
			array(false, "", true, "", "", "", "")); $catthe_notes = ""; $category = ""; $theme = ""; $td_type = "tdnt"; $td_d_from = ""; $td_d_to = "";
			$td_t_from = ""; $td_t_to = ""; $td_text = ""; $cb_oh = false; $oh_text = ""; $td_notes = "";
			if(isset($_POST['a']) && $_POST['a'] == "createActSubmission") {
				$error_strs = array("Please enter a name.", "Please enter an email address. ");
				$errors = array();
				$name = $_POST['name'];
				$description = $_POST['description'];
				$img[0][0] = $_POST['img1'];
				$img[0][1] = $_POST['img1title'];
				$img[1][0] = $_POST['img2'];
				$img[1][1] = $_POST['img2title'];
				$img[2][0] = $_POST['img3'];
				$img[2][1] = $_POST['img3title'];
				$address_type = $_POST['address_type'];
				if($address_type == "address") {
					$address_a['street'] = $_POST['address_street'];
					$address_a['city'] = $_POST['address_city'];
					$address_a['country'] = $_POST['address_country'];
				} else
					$address_c = $_POST['address_coordinates'];
				$address_notes = $_POST['address_notes'];
				$td_type = $_POST['td_type'];
				if($td_type == "tdnt") {
					$td_d_from = $_POST['td_d_from'];
					$td_d_to = $_POST['td_d_to'];
					$td_t_from = $_POST['td_t_from'];
					$td_t_to = $_POST['td_t_to'];
				} else
					$td_text = $_POST['td_text'];
				if(isset($_POST['cb_oh'])) {
					$cb_oh = $_POST['cb_oh'];
					$oh_text = $_POST['oh_text'];
				}
				$td_notes = $_POST['td_notes'];
				$a_type = $_POST['a_type'];
				if($a_type == "ant") {
					for($i = 1; $i < 7; $i++) {
						if(isset($_POST['admission_'.$i])) {
							$admission[$i-1][0] = true;
							$admission[$i-1][1] = $_POST['admission_'.$i.'_name'];
							if(isset($_POST['admission_'.$i.'_ages'])) {
								$admission[$i-1][2] = true;
								$admission[$i-1][3] = $_POST['admission_'.$i.'_from'];
								$admission[$i-1][4] = $_POST['admission_'.$i.'_to'];
							} else {
								$admission[$i-1][2] = false;
								$admission[$i-1][3] = "";
								$admission[$i-1][4] = "";
							}
							$admission[$i-1][5] = $_POST['admission_'.$i.'_price'];
							$admission[$i-1][6] = $_POST['admission_'.$i.'_money'];
						} else
							$admission[$i-1] = array(false, "title", true, "age from", "age to", "price", "\$/&euro;/&pound;/&yen...");
					}
				} else
					$admission_text = $_POST['admission_text'];
				$admission_notes = $_POST['admission_notes'];
				$category = $_POST['category'];
				$theme = $_POST['theme'];
				$catthe_notes = $_POST['catthe_notes'];
				$email = $_POST['contact_email'];
				$phone = $_POST['contact_phone'];
				$website = $_POST['contact_website'];
				$contact_notes = $_POST['contact_notes'];
				if($name == "")
					array_push($errors, 0);
				if($email == "")
					array_push($errors, 1);
				if(empty($errors) || (isset($_POST['b']) && $_POST['b'] == "createActSubmitNTL")) {
					$ready = true;
					$owner = "";
					saveEvent($name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $td_type, $td_d_from, $td_d_to, $td_t_from,
					$td_t_to, $td_text, $cb_oh, $oh_text, $td_notes, $a_type, $admission, $admission_text, $admission_notes, $category, $theme, $catthe_notes,
					$email, $phone, $website, $contact_notes, false);
				}
			}
			if(!$ready) {
				include'act_fts/create_act_stuff.php';
				include "act_fts/create_event.php";
			}
		} else {
			include 'act_fts/create_act.php';
		}
	}
	
	function editActivity($type) {
		if($type == "place") {
			$ready = false;
			$name = ""; $description = ""; $address_type = "address"; $address_a = array("street" => "", "city" => "", "country" => ""); $address_c = "";
			$seasons = ""; $cb_seasons = false; $oh_type = "ohnt"; $a_type = "ant"; $address_notes = ""; $ohs_notes = ""; $admission_notes = "";
			$opening_hours = array("mo" => array(true, "00", "00", "24", "00"), "tu" => array(true, "00", "00", "24", "00"), "mo" => 
			array(true, "00", "00", "24", "00"), "we" => array(true, "00", "00", "24", "00"), "th" => array(true, "00", "00", "24", "00"), 
			"fr" => array(true, "00", "00", "24", "00"), "sa" => array(true, "00", "00", "24", "00"), "su" => array(true, "00", "00", "24", "00"));
			$admission = array(array(true, "adults", true, "18", "", "", "\$"), array(true, "kids", true, "0", "6", "", "\$"), 
			array(true, "students", false, "", "", "", "\$"), array(true, "monthly ticket", false, "", "", "", "\$"), 
			array(true, "annual ticket", false, "", "", "", "\$"), array(false, "", true, "", "", "", ""), array(false, "", true, "", "", "", ""), 
			array(false, "", true, "", "", "", "")); $email = ""; $phone = ""; $website = ""; $contact_notes = ""; $opening_hours_text = ""; 
			$admission_text = ""; $pltype = "pldo"; $m_type = "mt"; $menu_text = ""; $menu_notes = ""; $menu_pics = array("", "", "", "");
			$img = array(array("", ""), array("", ""), array("", "")); $catthe_notes = ""; $category = ""; $theme = "";
			$error_strs = array("Please enter a name.", "Please enter an email address. ");
			$errors = array();
			$pltype = $_POST['pltype'];
			$name = $_POST['name'];
			$description = $_POST['description'];
			$img[0][0] = $_POST['img1'];
			$img[0][1] = $_POST['img1title'];
			$img[1][0] = $_POST['img2'];
			$img[1][1] = $_POST['img2title'];
			$img[2][0] = $_POST['img3'];
			$img[2][1] = $_POST['img3title'];
			$address_type = $_POST['address_type'];
			if($address_type == "address") {
				$address_a['street'] = $_POST['address_street'];
				$address_a['city'] = $_POST['address_city'];
				$address_a['country'] = $_POST['address_country'];
			} else
				$address_c = $_POST['address_coordinates'];
			$address_notes = $_POST['address_notes'];
			if(isset($_POST['cb_seasons'])) {
				$cb_seasons = $_POST['cb_seasons'];
				$seasons = $_POST['seasons'];
			}
			$oh_type = $_POST['oh_type'];
			if($oh_type == "ohnt") {
				$week = array("mo", "tu", "we", "th", "fr", "sa", "su");
				for($i = 0; $i < count($week); $i++) {
					if(isset($_POST['open_'.$week[$i]])) {
						$opening_hours[$week[$i]][0] = true;
						$opening_hours[$week[$i]][1] = $_POST['opening_hours_'.$week[$i].'_hr_from'];
						$opening_hours[$week[$i]][2] = $_POST['opening_hours_'.$week[$i].'_min_from'];
						$opening_hours[$week[$i]][3] = $_POST['opening_hours_'.$week[$i].'_hr_to'];
						$opening_hours[$week[$i]][4] = $_POST['opening_hours_'.$week[$i].'_min_to'];
					}
					else
						$opening_hours[$week[$i]] = array(false, "00", "00", "24", "00");
				}
			} else
				$opening_hours_text = $_POST['oh_text'];
			$ohs_notes = $_POST['ohs_notes'];
			$a_type = $_POST['a_type'];
			if($a_type == "ant") {
				for($i = 1; $i < 9; $i++) {
					if(isset($_POST['admission_'.$i])) {
						$admission[$i-1][0] = true;
						$admission[$i-1][1] = $_POST['admission_'.$i.'_name'];
						if(isset($_POST['admission_'.$i.'_ages'])) {
							$admission[$i-1][2] = true;
							$admission[$i-1][3] = $_POST['admission_'.$i.'_from'];
							$admission[$i-1][4] = $_POST['admission_'.$i.'_to'];
						} else {
							$admission[$i-1][2] = false;
							$admission[$i-1][3] = "";
							$admission[$i-1][4] = "";
						}
						$admission[$i-1][5] = $_POST['admission_'.$i.'_price'];
						$admission[$i-1][6] = $_POST['admission_'.$i.'_money'];
					} else
						$admission[$i-1] = array(false, "title", true, "age from", "age to", "price", "\$/&euro;/&pound;/&yen...");
				}
			} else
				$admission_text = $_POST['admission_text'];
			$admission_notes = $_POST['admission_notes'];
			$m_type = $_POST['m_type'];
			if($m_type == "mt")
				$menu_text = $_POST['menu_text'];
			else {
				$menu_pics[0] = $_POST['mpic1'];
				$menu_pics[1] = $_POST['mpic2'];
				$menu_pics[2] = $_POST['mpic3'];
				$menu_pics[3] = $_POST['mpic4'];
			}
			$menu_notes = $_POST['menu_notes'];
			$category = $_POST['category'];
			$theme = $_POST['theme'];
			$catthe_notes = $_POST['catthe_notes'];
			$email = $_POST['contact_email'];
			$phone = $_POST['contact_phone'];
			$website = $_POST['contact_website'];
			$contact_notes = $_POST['contact_notes'];
			if($name == "") {
				$pl = new Place();
				$pl->buildFromId($_POST['id']);
				$name = $pl->data2['name'][1];
			}
			if($email == "") {
				$pl = new Place();
				$pl->buildFromId($_POST['id']);
				$name = $pl->data2['name'][1];
			}
			savePlace($pltype, $name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $cb_seasons, $seasons, $oh_type, 
			$opening_hours, $opening_hours_text, $ohs_notes, $a_type, $admission, $admission_text, $admission_notes, $m_type, $menu_text, $menu_notes,
			$menu_pics, $category, $theme, $catthe_notes, $email, $phone, $website, $contact_notes, true);
		} else if($type == "event") {
			$ready = false;
			$name = ""; $description = ""; $address_type = "address"; $address_a = array("street" => "", "city" => "", "country" => ""); $address_c = "";
			$a_type = "ant"; $address_notes = ""; $admission_notes = ""; $email = ""; $phone = ""; $website = "";
			$contact_notes = ""; $admission_text = ""; $img = array(array("", "Poster"), array("", ""), array("", ""));
			$admission = array(array(true, "adults", true, "18", "", "", "\$"), array(true, "kids", true, "0", "6", "", "\$"), 
			array(true, "students", false, "", "", "", "\$"), array(false, "", true, "", "", "", ""), array(false, "", true, "", "", "", ""), 
			array(false, "", true, "", "", "", "")); $catthe_notes = ""; $category = ""; $theme = ""; $td_type = "tdnt"; $td_d_from = ""; $td_d_to = "";
			$td_t_from = ""; $td_t_to = ""; $td_text = ""; $cb_oh = false; $oh_text = ""; $td_notes = "";
			$error_strs = array("Please enter a name.", "Please enter an email address. ");
			$errors = array();
			$name = $_POST['name'];
			$description = $_POST['description'];
			$img[0][0] = $_POST['img1'];
			$img[0][1] = $_POST['img1title'];
			$img[1][0] = $_POST['img2'];
			$img[1][1] = $_POST['img2title'];
			$img[2][0] = $_POST['img3'];
			$img[2][1] = $_POST['img3title'];
			$address_type = $_POST['address_type'];
			if($address_type == "address") {
				$address_a['street'] = $_POST['address_street'];
				$address_a['city'] = $_POST['address_city'];
				$address_a['country'] = $_POST['address_country'];
			} else
				$address_c = $_POST['address_coordinates'];
			$address_notes = $_POST['address_notes'];
			$td_type = $_POST['td_type'];
			if($td_type == "tdnt") {
				$td_d_from = $_POST['td_d_from'];
				$td_d_to = $_POST['td_d_to'];
				$td_t_from = $_POST['td_t_from'];
				$td_t_to = $_POST['td_t_to'];
			} else
				$td_text = $_POST['td_text'];
			if(isset($_POST['cb_oh'])) {
				$cb_oh = $_POST['cb_oh'];
				$oh_text = $_POST['oh_text'];
			}
			$td_notes = $_POST['td_notes'];
			$a_type = $_POST['a_type'];
			if($a_type == "ant") {
				for($i = 1; $i < 7; $i++) {
					if(isset($_POST['admission_'.$i])) {
						$admission[$i-1][0] = true;
						$admission[$i-1][1] = $_POST['admission_'.$i.'_name'];
						if(isset($_POST['admission_'.$i.'_ages'])) {
							$admission[$i-1][2] = true;
							$admission[$i-1][3] = $_POST['admission_'.$i.'_from'];
							$admission[$i-1][4] = $_POST['admission_'.$i.'_to'];
						} else {
							$admission[$i-1][2] = false;
							$admission[$i-1][3] = "";
							$admission[$i-1][4] = "";
						}
						$admission[$i-1][5] = $_POST['admission_'.$i.'_price'];
						$admission[$i-1][6] = $_POST['admission_'.$i.'_money'];
					} else
						$admission[$i-1] = array(false, "title", true, "age from", "age to", "price", "\$/&euro;/&pound;/&yen...");
				}
			} else
				$admission_text = $_POST['admission_text'];
			$admission_notes = $_POST['admission_notes'];
			$category = $_POST['category'];
			$theme = $_POST['theme'];
			$catthe_notes = $_POST['catthe_notes'];
			$email = $_POST['contact_email'];
			$phone = $_POST['contact_phone'];
			$website = $_POST['contact_website'];
			$contact_notes = $_POST['contact_notes'];
			if($name == "") {
				$pl = new Event();
				$pl->buildFromId($_POST['id']);
				$name = $pl->data2['name'][1];
			}
			if($email == "") {
				$pl = new Event();
				$pl->buildFromId($_POST['id']);
				$name = $pl->data2['name'][1];
			}
			saveEvent($name, $description, $img, $address_type, $address_a, $address_c, $address_notes, $td_type, $td_d_from, $td_d_to, $td_t_from,
			$td_t_to, $td_text, $cb_oh, $oh_text, $td_notes, $a_type, $admission, $admission_text, $admission_notes, $category, $theme, $catthe_notes,
			$email, $phone, $website, $contact_notes, true);
		}
	}
	
	function vePlace($actId) {
		$act = new Place();
		$act->buildFromId($actId);
		if($act->data1['owner'][1] == $_SESSION['account_id']) {
			include'act_fts/create_act_stuff.php';
			include'act_fts/vePlace.php';
		} else
			echo"<meta http-equiv='Refresh' content='0;index.php'>";
	}
	function veEvent($actId) {
		$act = new Event();
		$act->buildFromId($actId);
		if($act->data1['owner'][1] == $_SESSION['account_id']) {
			include'act_fts/create_act_stuff.php';
			include'act_fts/veEvent.php';
		} else
			echo"<meta http-equiv='Refresh' content='0;index.php'>";
	}
	
	function catthe_suggest_screen() {
		include'act_fts/catthe_suggest_scr.php';
	}
	
	function activatePlace($id) {
		$act = new Place();
		$act->buildFromId($id);
		$act->data1['active'][1] = 1;
		$act->updateDB();
		$u = new User();
		$u->buildFromId($act->data1['owner'][1]);
		$headers  = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
		$headers .= 'To: <'.$u->email.'>' . "\r\n";
		$headers .= 'From: actio <postmaster@localhost>' . "\r\n";
		mail($u->email, "actio password recovery", "
		<html>
			<head>
				<title>actio place activation</title>
			</head>
			You activated your place. Please transmit an amout of 19,90€ to me. If the money isn't there by the next month, your place will be
			deactivated for one month. If the money isn't there for the month after that, it will be deleted!
		</html>"
		, $headers);
		echo"<meta http-equiv='Refresh' content='0;index.php?a=vePlace&b=$id'>";
	}
	
	function activateEvent($id) {
		$act = new Event();
		$act->buildFromId($id);
		$act->data1['active'][1] = 1;
		$act->updateDB();
		$u = new User();
		$u->buildFromId($act->data1['owner'][1]);
		$headers  = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
		$headers .= 'To: <'.$u->email.'>' . "\r\n";
		$headers .= 'From: actio <postmaster@localhost>' . "\r\n";
		mail($u->email, "actio password recovery", "
		<html>
			<head>
				<title>actio place activation</title>
			</head>
			You activated your event. Please transmit an amout of 2.99€ * [days of activity] to me.
		</html>"
		, $headers);
		echo"<meta http-equiv='Refresh' content='0;index.php?a=veEvent&b=$id'>";
	}
	
?>