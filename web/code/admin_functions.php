<?php
	class Admin {
		public $columns = array();
		public $data1 = array();
		public $data2 = array();
		function __construct() {
			$sql = "SELECT * FROM `accounts` WHERE `id` = 0";
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
										$data1_values[$i][$oii[0]][$oiii[0].$oiii[1]][$oiv[0]] = $oiv;
									} else
										$data1_values[$i][$oii[0]][$oiii[0].$oiii[1]][$iv] = $oiii[$iv];
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
										$data2_values[$i][$oii[0]][$oiii[0].$oiii[1]][$oiv[0]] = $oiv;
									} else
										$data2_values[$i][$oii[0]][$oiii[0].$oiii[1]][$iv] = $oiii[$iv];
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
			echo"<p style='text-align:left;word-break:break-all;'>";print_r($data2);echo"</p>";
			$sql = 
				"UPDATE `accounts` SET `data1` = '$data1', `data2` = '$data2' WHERE `id` = 0";
			mysql_query($sql);
		}
		function routineAddArray($month, $array) {
			if(!isset($this->data2['r'.$month]['acts']) || !is_array($this->data2['r'.$month]['acts'])) {
				$this->data2['r'.$month]['acts'] = array();
				$this->data2['r'.$month]['acts'][0] = "acts";
			}
			array_push($this->data2['r'.$month]['acts'], $array);
			$this->updateDB();
		}
		function newRoutine($month, $l_statement) {
			$this->data2['r'.$month] = array();
			$this->data2['r'.$month][0] = 'r'.$month;
			$this->data2['r'.$month]['status'] = array('status', 'running');
			$this->data2['r'.$month]['l_statement'] = array('l_statement', $l_statement);
			$this->updateDB();
		}
	}
	function showAdminHome() {
		include 'admin_fts/home.php';
	}
	function admin_do($command) {
		global $me;
		if(strpos($command, "routine") !== false || $command == "r" || (substr($command, 0, 1) == "r" && strlen($command) == 5)) {
			if($command == "routine" || $command == "r")
				$month = date('ym');
			else if(strpos($command, "routine"))
				$month = substr($command, 8);
			else
				$month = substr($command, 1);
			admin_home_pg_start("admin!!! :: routine");
			echo'
				<header>';
					if(!isset($_GET['s']) && !isset($me->data2['r'.$month]))
						echo'<h2>Do your routine!&emsp;(r'.$month.')</h2>';
					else if(!isset($_GET['s']) && isset($me->data2['r'.$month]) && $me->data2['r'.$month]['status'][1] == "finito")
						echo'<h2>Already done!&emsp;(r'.$month.')</h2>';
					else if(!isset($_GET['s']) && isset($me->data2['r'.$month]) && $me->data2['r'.$month]['status'][1] == "running")
						echo'<h2>Again from the beginning!&emsp;(r'.$month.')</h2>';
					else if($_GET['s'] != 3)
						echo'<h2>Step '.$_GET['s'].'</h2>';
					else if(isset($_GET['id']) && !isset($_GET['money']))
						echo'<h2>Step 3 - 2&emsp;id: '.$_GET['acttype'].$_GET['id'].'</h2>';
					else if(isset($_GET['id']) && isset($_GET['money']))
						echo'<h2>Step 3 - 3&emsp;id: '.$_GET['acttype'].$_GET['id'].'</h2>';
					else if(!isset($_GET['id']) && $_GET['s'] == 3)
						echo'<h2>Step 3 - 1</h2>';
					echo'
				</header>
				<div>
					<!--<article style="background:rgba(255, 255, 255, .5);">-->';
					if((!isset($_GET['s']) && !isset($me->data2['r'.$month])) || 
					(!isset($_GET['s']) && isset($me->data2['r'.$month]) && $me->data2['r'.$month]['status'][1] == "running")) {
						echo'
							<form method="get">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<div class="row" style="margin-top:90px;">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="s" value="1" class="form button">Start!</button>
										<a href="index.php" class="form button">Cancel</a>
									</div>
								</div>
							</form>';
					} else if(!isset($_GET['s']) && isset($me->data2['r'.$month]) && $me->data2['r'.$month]['status'][1] == "finito"){
						if($month == date('ym')) {
							echo'
								<p>Next routine: 14.'.((date('m') == 12) ? '01' : (date('m')+1)).'. - 28.'.((date('m') == 12) ? '01' : (date('m')+1)).'.</p>
							';
						}
						echo'<pre style="text-align:left;margin-left:150px;">';print_r($me->data2['r'.$month]);echo'</pre>';
						echo'
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<div class="row" style="margin-top:90px;">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="s" value="1" class="form button">Again</button>
										<a href="index.php" class="form button">Back</a>
									</div>
								</div>
							</form>
						';
					} else if($_GET['s'] == 1) {
						echo'
							<p>Last statement previous month: '.((substr($month, 2) == "01") ? ($me->data2['r'.(substr($month, 0, 2) - 1).'12']['l_statement'][1])
							 : ($me->data2['r'.(substr($month, 0, 2)).(substr($month, 2) - 1)]['l_statement'][1])).'</p>
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<div class="row" style="display:inline">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;display:inline">
										Last statement this month:&emsp;
										<input type="text" class="text" name="l_statement" 
										autocomplete="off" style="text-align:center;display:inline;width:20%;" required/>
									</div>
								</div>
								<div class="row" style="margin-top:15px;">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="s" value="2" class="form button">OK</button>
										<a href="index.php" ';if($month < date('ym') || date('d') > 28)echo'onclick="alert(\'Och junge!\');" ';echo'
										class="form button">Cancel</a>
									</div>
								</div>
							</form>
						 ';
					} else if($_GET['s'] == 2) {
						$plcdueids = array();
						$sql = "SELECT id FROM `places`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 0; $i < count($rows); $i++) {
							$plc = new Place();
							$plc->buildFromId($rows[$i][0]);
							if($plc->data1['next_routine'][1] == $month)
								array_push($plcdueids, $plc->id);
						}
						$evdueids = array();
						$sql = "SELECT id FROM `events`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 0; $i < count($rows); $i++) {
							/*$ev = new Event();
							$ev->buildFromId($rows[$i][0]);
							if($ev->data1['due_routine'][1] == $month)
								array_push($evdueids, $ev->id);*/
						}
						$acdueids = array();
						$sql = "SELECT id FROM `accounts`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 1; $i < count($rows); $i++) {
							$u = new User();
							$u->buildFromId($rows[$i][0]);
							if(isset($u->data1['pay_routine']) && $u->data1['pay_routine'][1] == $month)
								array_push($acdueids, $u->id);
						}
						echo'
							<p>There '.((count($plcdueids) != 1) ? 'are' : 'is').' '.
							count($plcdueids).' place'.((count($plcdueids) != 1) ? 's' : '').' due this routine!<br/>
							There '.((count($evdueids) != 1) ? 'are' : 'is').' '.
							count($evdueids).' event'.((count($evdueids) != 1) ? 's' : '').' due this routine!<br/>
							There '.((count($acdueids) != 1) ? 'are' : 'is').' '.
							count($acdueids).' account'.((count($acdueids) != 1) ? 's' : '').' due this routine!</p>
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<input type="hidden" name="l_statement" value="'.$_GET['l_statement'].'" />
								<div class="row" style="margin-top:50px;">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										';if(count($plcdueids) > 0)echo'
										<button type="submit" name="s" value="nr2" class="form button">Then go!</button>
										<span onclick="alert(\'Na Los!\');" class="form button">Cancel</span>';
										else echo'
										<a href="index.php" class="form button">Hm.. son scheiß</a>';echo'
									</div>
								</div>
							</form>
						 ';
					} else if($_GET['s'] == "nr2") {
						$me->newRoutine($month, $_GET['l_statement']);
						echo"<meta http-equiv='Refresh' content='0;index.php?a=admin&b=".$_GET['b']."&s=3'>";
					} else if($_GET['s'] == 3 && !isset($_GET['id'])) {
						echo'
							<h1>Please Enter an id</h1>
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<div class="row" style="width:80%;margin:0 auto;margin-top:50px;">
									<div class="12u" style="width:100%;margin:0;padding:0;display:inline">
										<select name="acttype" class="text" style="width:10%;display:inline">
											<option value="PL" title="place">PL</option>
											<option value="EV" title="event">EV</option>
											<option value="AC" title="account (also nothing)">AC</option>
										</select>
										<select name="pay" class="text" style="width:30%;display:inline">
											<option value="PAYMENT" title="annual payment or event payment">PAYMENT</option>
											<!--<option value="activate" title="one time for activation">ACTIVATE</option>-->
											<option value="EXTRA" title="one time for an extra">EXTRA</option>
											<option value="?">?</option>
										</select>
										<input type="text" class="text" name="id" autocomplete="off" style="text-align:center;display:inline;width:55%;
										padding-top:14px;padding-bottom:15px;" required/>
									</div>
								</div>
								<div class="row">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="s" value="3" class="form button">OK</button>
										<a href="index.php?a=admin&b='.$_GET['b'].'&s=4" class="form button">That\'s it!</a>
									</div>
								</div>
							</form>
						 ';
					} else if($_GET['s'] == 3 && isset($_GET['id']) && !isset($_GET['money'])) {
						$apdue = false;
						$exdue = false;
						$ap = "-";
						$ep = "-";
						if($_GET['acttype'] == "PL") {
							$act = new Place();
							$act->buildFromId($_GET['id']);
							if($act->data1['next_routine'][1] == $month)
								$due = true;
							$ap = $act->data1['annual_price'][1].' &'.$act->data1['annual_price'][2].';';
							$ep = ((isset($act->data1['extra_price'][1])) ? ($act->data1['extra_price'][1].' &'.$act->data1['extra_price'][2].';') : '-');
							$name = $act->data2['name'][1];
							$u = new User();
							$u->buildFromId($act->data1['owner'][1]);
							$email = $u->email;
						} else if($_GET['acttype'] == "EV"){
							$act = new Place();
							$act->buildFromId($_GET['id']);
							if($act->data1['next_routine'][1] == $month)
								$due = true;
							$ap = $act->data1['price'][1].'&'.$act->data1['price'][2].';';
							$ep = ((isset($act->data1['extra_price'][1])) ? ($act->data1['extra_price'][1].' &'.$act->data1['extra_price'][2].';') : '-');
							$name = $act->data2['name'][1];
							$u = new User();
							$email = $u->email;
							$u->buildFromId($act->data1['owner'][1]);
						} else if($_GET['acttype'] == "AC"){
							$u = new User();
							$u->buildFromId($_GET['id']);
							$ap = ((isset($u->data1['annual_price'][1])) ? ($u->data1['annual_price'][1].' &'.$u->data1['annual_price'][2].';') : '-');
							$ep = ((isset($u->data1['extra_price'][1])) ? ($u->data1['extra_price'][1].' &'.$u->data1['extra_price'][2].';') : '-');
							$name = ((isset($u->data1['name'][1])) ? $u->data1['name'][1] : $u->email);
							$email = $u->email;
						}
						$ap = strtolower($ap);
						$ep = strtolower($ep);
						$apdue = ($ap != "-");
						$exdue = ($ep != "-");
						$apsel = "";
						$epsel = "";
						$opsel = "";
						if($_GET['pay'] == "PAYMENT") {
							$apsel = "selected";
						} else if($_GET['pay'] == "EXTRA") {
							$epsel = "selected";
						} else {
							$opsel = "selected";
						}
						echo'
							<h1>'.($exdue ? 'Exdue' : '').(($exdue && $apdue) ? ', ' : '')
							.($apdue ? 'Apdue' : '').'&ensp;<a href="mailto:'.$email.'">Email</a>&ensp;(AP: '.
							$ap.' | EP: '.$ep.')</h1>
							<p>'.$name.'</p>
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<input type="hidden" name="id" value="'.$_GET['id'].'" />
								<input type="hidden" name="acttype" value="'.$_GET['acttype'].'" />
								<input type="hidden" name="pay" value="'.$_GET['pay'].'" />
								<input type="hidden" name="money" value="true" />
								<div class="row" style="width:80%;margin:0 auto;margin-top:0px;">
									<div class="12u" style="width:100%;margin:0;padding:0;">
										<select name="pay2" class="text" style="width:10%;display:inline">
											<option value="AP" title="annual payment or event payment" '.$apsel.'>AP</option>
											<option value="EP" title="one time for an extra" '.$epsel.'>EP</option>
											<option value="?" '.$opsel.'>?</option>
										</select>
										<input type="text" class="text" name="amount" autocomplete="off" style="text-align:center;width:40%;display:inline;
										padding-top:14px;padding-bottom:15px;" required/>
										<input type="text" class="text" name="currency" value="EURO" style="text-align:center;width:45%;display:inline;
										padding-top:14px;padding-bottom:15px;" required/>
									</div>
								</div>
								<div class="row">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="s" value="3" class="form button">OK</button>
										<a href="index.php?a=admin&b='.$_GET['b'].'&s=3" class="form button">Wait! Back!</a>
									</div>
								</div>
							</form>
						 ';
					} else if($_GET['s'] == 3 && isset($_GET['id']) && isset($_GET['money'])) {
						$array = array($_GET['acttype'],  $_GET['id'], 'pay' => array("pay", $_GET['pay'], $_GET['pay2']), 
						'money' => array("money", 'amount' => $_GET['amount'], 'currency' => $_GET['currency']));
						$me->routineAddArray($month, $array);
						echo"<meta http-equiv='Refresh' content='0;index.php?a=admin&b=".$_GET['b']."&s=3'>";
					} else if($_GET['s'] == 4) {
						echo'<h1>Okay, let\'s see</h1>';
						$plcdueids = array();
						$sql = "SELECT id FROM `places`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 0; $i < count($rows); $i++) {
							$plc = new Place();
							$plc->buildFromId($rows[$i][0]);
							if($plc->data1['next_routine'][1] == $month)
								array_push($plcdueids, $plc->id);
						}
						$evdueids = array();
						$sql = "SELECT id FROM `events`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 0; $i < count($rows); $i++) {
							/*$ev = new Event();
							$ev->buildFromId($rows[$i][0]);
							if($ev->data1['due_routine'][1] == $month)
								array_push($evdueids, $ev->id);*/
						}
						$acdueids = array();
						$sql = "SELECT id FROM `accounts`";
						$result = mysql_query($sql);
						$rows = array();
						while($row = mysql_fetch_array($result))
							array_push($rows, $row);
						for($i = 1; $i < count($rows); $i++) {
							$u = new User();
							$u->buildFromId($rows[$i][0]);
							if(isset($u->data1['pay_routine']) && $u->data1['pay_routine'][1] == $month)
								array_push($acdueids, $u->id);
						}
						$keys = array_keys($me->data2['r'.$month]['acts']);
						$paidPlaces = array();
						for($i = 1; $i < count($me->data2['r'.$month]['acts']); $i++) {
							if($me->data2['r'.$month]['acts'][$keys[$i]][0] == 'PL') {
								$plc = new Place();
								$plc->buildFromId($me->data2['r'.$month]['acts'][$keys[$i]][1]);
								if(in_array($me->data2['r'.$month]['acts'][$keys[$i]][1], $plcdueids) && 
								$me->data2['r'.$month]['acts'][$keys[$i]]['money'][1] >= $plc->data1['annual_price'][1] && 
								$me->data2['r'.$month]['acts'][$keys[$i]]['money'][2] == $plc->data1['annual_price'][2]) {
									array_push($paidPlaces, $plc->id);
								}
							}
						}
						$paidEvents = array();
						for($i = 1; $i < count($me->data2['r'.$month]['acts']); $i++) {
							if($me->data2['r'.$month]['acts'][$keys[$i]][0] == 'EV') {
								/*$pl = new Place();
								$pl->buildFromId($me->data2['r'.$month]['acts'][$keys[$i]][1]);
								if($pl->data1['next_routine'][1] == $month) {
									array_push($paidPlaces, $pl->id);
								}*/
							}
						}
						$paidAccountExtras = array();
						for($i = 1; $i < count($me->data2['r'.$month]['acts']); $i++) {
							if($me->data2['r'.$month]['acts'][$keys[$i]][0] == 'AC') {
								$u = new User();
								$u->buildFromId($me->data2['r'.$month]['acts'][$keys[$i]][1]);
								if(in_array($me->data2['r'.$month]['acts'][$keys[$i]][1], $acdueids) && 
								$me->data2['r'.$month]['acts'][$keys[$i]]['money'][1] >= $u->data1['annual_price'][1] && 
								$me->data2['r'.$month]['acts'][$keys[$i]]['money'][2] == $u->data1['annual_price'][2]) {
									array_push($paidAccountExtras, $u->id);
								}
								/*$pl = new Place();
								$pl->buildFromId($me->data2['r'.$month]['acts'][$keys[$i]][1]);
								if($pl->data1['next_routine'][1] == $month) {
									array_push($paidPlaces, $pl->id);
								}*/
							}
						}
						$profit = array();
						for($i = 1; $i < count($me->data2['r'.$month]['acts']); $i++) {
							if(!array_key_exists($me->data2['r'.$month]['acts'][$keys[$i]]['money'][2], $profit))
								$profit[$me->data2['r'.$month]['acts'][$keys[$i]]['money'][2]] = array();
							array_push($profit[$me->data2['r'.$month]['acts'][$keys[$i]]['money'][2]], $me->data2['r'.$month]['acts'][$keys[$i]]['money'][1]);
						}
						$_profit = "";
						$keys = array_keys($profit);
						for($i = 0; $i < count($profit); $i++) {
							$_p = 0;
							for($ii = 0; $ii < count($profit[$keys[$i]]); $ii++) {
								$_p += $profit[$keys[$i]][$ii];
							}
							$_p .= ' &'.strtolower($keys[$i]).';';
							$_profit .= ($i > 0) ? ', '.$_p : $_p;
						}
						$profit = ($_profit != "") ? $_profit : "-";
						echo'
							<p>Paid: '.count($paidPlaces).'/'.count($plcdueids).' PL | '.count($paidEvents).'/'.count($evdueids).' EV | '
							.count($paidAccountExtras).'/'.count($acdueids).' AC<br />
							Profit: <b>'.$profit.'</b></p>
							<form action="index.php">
								<input type="hidden" name="a" value="admin" />
								<input type="hidden" name="b" value="'.$_GET['b'].'" />
								<input type="hidden" name="s" value="5" />
								<div class="row">
									<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
										<button type="submit" name="c" value="write_emails" class="form button">Write Emails</button>
										<button type="submit" name="c" value="end_routine" class="form button">End Routine</button>
									</div>
								</div>
							</form>
						';
					} else if($_GET['s'] == 5 && $_GET['c'] == "write_emails") {
						$emails_1stt_pl = array();
						$emails_2ndt_pl = array();
						$emails_next_pl = array();
//						$emails_
					} else if($_GET['s'] == 5 && $_GET['c'] == "end_routine") {
						$me->data2['r'.$month]['status'][1] = "finito";
						$me->updateDB();
						echo"<meta http-equiv='Refresh' content='0;index.php'>";
					}
					echo'
					<!--</article>-->
				</div>
			';
			admin_home_pg_ende();
		} else if($command == "testarea") {
			include'admin_fts/testarea.php';
		} else if($command == "was steht an") {
		} else if($command == "geld") {
		} else if($command == "info") {
			admin_home_pg_start("admin!!! :: info");
			echo'
				<header>
					<p>Lastly Online: '.substr($me->data1['lastly_online'][1], 6, 2) . "." 
					. substr($me->data1['lastly_online'][1], 4, 2) . "." . substr($me->data1['lastly_online'][1], 0, 4) . ", "
					. substr($me->data1['lastly_online'][1], 8, 2) . ":" . substr($me->data1['lastly_online'][1], 10, 2) . '</p>
				</header>
				<div>
					<!--<article style="background:rgba(255, 255, 255, .5);">-->
						<!--<form>
							<div class="row" style="width:80%;margin:0 auto;margin-top:50px;">
								<div class="12u" style="width:100%;margin:0;padding:0;">
									<input type="text" class="text" name="b" style="text-align:center" />
								</div>
							</div>
							<div class="row">
								<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
									<button type="submit" name="a" value="admin" class="form button">OK</button>
								</div>
							</div>
						</form>-->
					<!--</article>-->
				</div>
			';
			admin_home_pg_ende();
		} else 
			echo"<meta http-equiv='Refresh' content='0;index.php'>";
	}
	function admin_home_pg_start($msg) {
		heade($msg);echo'
		<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;min-height:400px;">
			<div style="text-align:center;width:100%">';
	}
	function admin_home_pg_ende() {
		echo'</div></article>';footer();
	}
?>