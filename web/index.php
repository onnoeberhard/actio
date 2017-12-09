<?php	include './code/init.php'	?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Actio</title>
<link rel="shortcut icon" type="image/x-icon" href="actioic.png">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,300italic" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/skel-noscript.css" />
<link rel="stylesheet" href="css/style.css" />
<!--<link rel="stylesheet" href="css/tabulous.css" />-->
<link rel="stylesheet" href="css/messi.min.css" />
<!--<link rel="stylesheet" href="css/apprise-v2.css" type="text/css">-->
<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
<script src="js/jquery.min.js"></script>
<!--<script type="text/javascript" src="js/apprise-v2.js"></script>-->
<script src="js/messi.min.js"></script>
<!--<script src="js/tabulous.js"></script>-->
<script src="js/jquery.poptrox.min.js"></script>
<script src="js/skel.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<script src="js/init.js"></script>
<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
</head>

<body onload="draginit()">
	<?php
		if(isLoggedInNormal()) {
			$me = new User();
			$me->buildFromId($_SESSION['account_id']);
			$_SESSION['me'] = $me;
			//echo'<pre style="color:#fff">';print_r($me);echo'</pre>';
			if(userActiveState($me->email) == 2) {
				session_destroy();
				echo"<meta http-equiv='Refresh' content='0;index.php'>";
				exit();
			}
		} else if(isLoggedIn()) {
			include './code/admin_functions.php';
			$me = $_SESSION['me'] = new Admin();
		}
		if(!empty($_POST) || !empty($_GET)) {
			if(isset($_POST['a']) && $_POST['a'] == "login") {
				$l_error_strs = array("Email empty!", "Password empty!", "User doesn't exist!", "Account isn't active!", "Wrong Password / Username!", 
				"Please enter your password, master");
				$l_errors = array();
				if(!isset($_SESSION['admin_wt_login']) && $_POST['email'] == "log me in as admin" && 
				(md5($_POST['password']) == "6050ce63e4bce6764cb34cac51fb44d1" || md5($_POST['password']) == "ffe181130430aff6386df37df36347f8")) {
					$_SESSION['admin_wt_login'] = true;
					array_push($l_errors, 5);
				} else if(!isset($_SESSION['admin_wt_login'])){
					$email = $_POST['email'];
					$password = $_POST['password'];
					if(empty($email) || empty($password)) {
						if(empty($email))
							array_push($l_errors, 0);
						if(empty($password))
							array_push($l_errors, 1);
					} else if(!userExists($email))
						array_push($l_errors, 2);
					else if(userActiveState($email) == 2)
						array_push($l_errors, 3);
					else {
						$login_result = loginResult($email, $password);
						if($login_result == -1)
							array_push($l_errors, 4);
						else if($login_result != 0) {
							if(userActiveState($email) == 1) {
								emailActivationScreen($email);
							} else {
								$u = new User();
								$u->buildFromEmail($email);
								$_SESSION['account_id'] = $u->id;
								echo"<meta http-equiv='Refresh' content='0;index.php'>";
								exit();
							}
						}
					}
				} else {
					if(md5($_POST['password']) == "1b207465eac83b5d4b12e335faa0b53a") {
						$_SESSION['account_id'] = 0;
						echo"<meta http-equiv='Refresh' content='0;index.php'>";
						exit();
					} else {
						session_destroy();
						echo"<meta http-equiv='Refresh' content='0;index.php'>";
						exit();
					}
				} if(!empty($l_errors)) {
					include './code/welcome.php';
					echo"<script>window.scrollTo(0, ($(\"#l\").offset().top)-10);</script>";
				}
			} else if(isset($_GET['a']) && $_GET['a'] == "logout" && isLoggedIn()) {
				session_destroy();
				echo"<meta http-equiv='Refresh' content='0;index.php'>";
				exit();
			} else if(isset($_POST['a']) && $_POST['a'] == "register") {
				$r_error_strs = array("Email empty!", "Password empty!", "Passwords don't match!", "Email already registered!", "Enter a valid email adress!");
				$r_errors = array();
				$email = $_POST['email'];
				$password = $_POST['password'];
				$password2 = $_POST['password2'];
				if(empty($email) || empty($password)) {
					if(empty($email))
						array_push($r_errors, 0);
					if(empty($password))
						array_push($r_errors, 1);
				} if($password != $password2)
					array_push($r_errors, 2);
				if(userExists($email))
					array_push($r_errors, 3);
				if(!filter_var($email, FILTER_VALIDATE_EMAIL) && !in_array(0, $r_errors))
					array_push($r_errors, 4);
				if(count($r_errors) == 0) {
					registerUser($email, $password);
					//$me = new User();
					//$me->buildFromEmail($email);
					//$_SESSION['account_id'] = $me->id;
					//echo"<meta http-equiv='Refresh' content='0;index.php'>";
				} else {
					include './code/welcome.php';
					echo"<script>window.scrollTo(0, ($(\"#r\").offset().top)-10);</script>";
				}
			} else if(isset($_POST['a']) && $_POST['a'] == "change_password") {
				$cperror_strs = array("Please fill out all fileds!", "Wrong current password!", "The new passwords don't match!");
				$cperrors = array();
				if($_POST['password'] == "" || $_POST['password2'] == "" || $_POST['cur_password'] == "")
					array_push($cperrors, 0);
				else if(md5($_POST['cur_password']) != $me->columns['password'])
					array_push($cperrors, 1);
				else if($_POST['password'] != $_POST['password2'])
					array_push($cperrors, 2);
				else {
					$me->changePassword($_POST['password']);
					echo"<meta http-equiv='Refresh' content='0;index.php?a=accountSettings#cp'>";
				}
				accountSettings();
			} else if(isset($_POST['a']) && $_POST['a'] == "emailActivation") {
				$result = activateEmail($_POST['b'], $_POST['c']);
				if($result == 0) {
					$_SESSION['account_id'] = $_POST['b'];
					echo"<meta http-equiv='Refresh' content='0;index.php'>";
					exit();
				} else if($result == 1 || $result == 2) {
					$u = new User();
					$u->buildFromId($_POST['b']);
					emailActivationScreen($u->email);
				}
			} else if(isset($_GET['a']) && $_GET['a'] == "emailActivation2") {
				$result = activateEmail($_GET['b'], $_GET['c']);
				if($result == 0) {
					$_SESSION['account_id'] = $_GET['b'];
					echo"<meta http-equiv='Refresh' content='0;index.php'>";
					exit();
				} else if($result == 1 || $result == 2) {
					$u = new User();
					$u->buildFromId($_GET['b']);
					emailActivationScreen($u->email);
				}
			} else if(isset($_POST['a']) && $_POST['a'] == "sendActivationEmail") {
				sendActivationEmail($_POST['b'], $_POST['eac']);
				$u = new User();
				$u->buildFromId($_POST['b']);
				emailActivationScreen($u->email);
			} else if(isset($_POST['a']) && $_POST['a'] == "delete_account") {
				$daerror_strs = array("Wrong Password!", "You first have to delete all your activities!");
				$daerrors = array();
				if(isset($_POST['b'])) {
					$id = $_POST['b'];
					$u = new User();
					$u->buildFromId($id);
				} else {
					$u = $_SESSION['me'];
					$id = $_SESSION['account_id'];
				}
				if(md5($_POST['pw']) != $u->password && $_POST['pw'] != $u->password) 
					array_push($daerrors, 0);
				if($u->hasActivities())
					array_push($daerrors, 1);
				if(empty($daerrors)) {
					$sql = "DELETE FROM `accounts` WHERE `id` = $id";
					mysql_query($sql);
					session_destroy();
					echo $id;
					echo"<meta http-equiv='Refresh' content='0;index.php'>";
					exit();
				}
				accountSettings();
			} else if(isset($_POST['a']) && $_POST['a'] == "saveAccGen") {
				if(isset($me->data1['name']))
					$me->data1['name'][1] = $_POST['name'];
				else
					array_push($me->data1, array("name", $_POST['name']));
				$me->updateDB();
				echo"<meta http-equiv='Refresh' content='0;index.php?a=accountSettings#general'>";
			} else if(isset($_POST['a']) && $_POST['a'] == "recoverPassword") {
				if(isset($_POST['email'])) {
					if(userExists($_POST['email'])) {
						if(!isset($_POST['new_pw'])) {
							$length = 10;
							$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
							$new_pw = '';
							for ($i = 0; $i < $length; $i++)
								$new_pw .= $characters[rand(0, strlen($characters) - 1)];
							sendPasswordEmail($_POST['email'], $new_pw);
							$u = new User();
							$u->buildFromEmail($_POST['email']);
							if(!isset($u->data1['new_pw'][1]))
								array_push($u->data1, array("new_pw", md5($new_pw)));
							else
								$u->data1['new_pw'][1] = md5($new_pw);
							$u->updateDB();
						} else {
							sendPasswordEmail($_POST['email'], $_POST['new_pw']);
						}
					} 
				}
				$l_error_strs = array("Email empty!", "User doesn't exist!", "A new password has been sent to your email address");
				$l_errors = array();
				if(empty($_POST['email']))
					array_push($l_errors, 0);
				else if(!userExists($_POST['email']))
					array_push($l_errors, 1);
				else
					array_push($l_errors, 2);
				include './code/welcome.php';
				echo"<script>window.scrollTo(0, ($(\"#l\").offset().top)-10);</script>";
			} else if(isset($_GET['a']) && $_GET['a'] == "createActivity" && isLoggedIn()) {
				createActivity("");
			} else if(isset($_GET['a']) && $_GET['a'] == "createPlace" && (!isset($_POST['a']) || $_POST['a'] != "cancel") && isLoggedIn()) {
				createActivity("place");
			} else if(isset($_GET['a']) && $_GET['a'] == "createEvent" && (!isset($_POST['a']) || $_POST['a'] != "cancel") && isLoggedIn()) {
				createActivity("event");
			} else if(isset($_GET['a']) && $_GET['a'] == "catthe_suggest_screen" && isLoggedIn()) {
				catthe_suggest_screen();
			} else if(isset($_POST['a']) && $_POST['a'] == "mailme") {
				mail("OnnoEberhard@googlemail.com", $_POST['b'], $_POST['c']);
				if(isset($_POST['close']))
					echo "<script>window.close();</script>";
			} else if(isset($_POST['a']) && $_POST['a'] == "placeActivateOK" && isLoggedIn()) {
				activatePlace($_POST['b']);
			} else if(isset($_POST['a']) && $_POST['a'] == "eventActivateOK" && isLoggedIn()) {
				activateEvent($_POST['b']);
			} else if(isset($_POST['a']) && $_POST['a'] == "savePlaceEdit" && isLoggedIn()) {
				editActivity("place");
			} else if(isset($_GET['a']) && $_GET['a'] == "vePlace" && isset($_GET['b']) && isLoggedIn()) {
				if(isset($_POST['a']) && $_POST['a'] == "deactivatePlace") {
					$daerror_strs = array("Wrong Password!");
					$daerrors = array();
					if(md5($_POST['pw']) == $me->password) {
						$pl = new Place();
						$pl->buildFromId($_POST['id']);
						$pl->data1['active'][1] = 2;
						$pl->updateDB();
						echo"<meta http-equiv='Refresh' content='0;index.php?a=vePlace&b=".$_POST['id']."&tab=3'>";
					} else 
						array_push($daerrors, 0);
				} else if(isset($_POST['a']) && $_POST['a'] == "reactivatePlace") {
					$aaerror_strs = array("Wrong Password!");
					$aaerrors = array();
					//if(md5($_POST['pw']) == $me->password) {
						$pl = new Place();
						$pl->buildFromId($_POST['id']);
						$pl->data1['active'][1] = 1;
						$pl->updateDB();
						echo"<meta http-equiv='Refresh' content='0;index.php?a=vePlace&b=".$_POST['id']."&tab=3'>";
					//} else 
					//	array_push($aaerrors, 0);
				} if(isset($_POST['a']) && $_POST['a'] == "deletePlace") {
					$daerror_strs = array("Wrong Password!");
					$daerrors = array();
					if(md5($_POST['pw']) == $me->password) {
						$sql = "DELETE FROM `places` WHERE `id`=".$_POST['id'];
						mysql_query($sql);
						echo"<meta http-equiv='Refresh' content='0;index.php'>";
					} else 
						array_push($daerrors, 0);
				} 
				vePlace($_GET['b']);
			} else if(isset($_POST['a']) && $_POST['a'] == "saveEventEdit" && isLoggedIn()) {
				editActivity("event");
			} else if(isset($_GET['a']) && $_GET['a'] == "veEvent" && isset($_GET['b']) && isLoggedIn()) {
				if(isset($_POST['a']) && $_POST['a'] == "deactivateEvent") {
					$daerror_strs = array("Wrong Password!");
					$daerrors = array();
					if(md5($_POST['pw']) == $me->password) {
						$ev = new Event();
						$ev->buildFromId($_POST['id']);
						$ev->data1['active'][1] = 2;
						$ev->updateDB();
						echo"<meta http-equiv='Refresh' content='0;index.php?a=veEvent&b=".$_POST['id']."&tab=3'>";
					} else 
						array_push($daerrors, 0);
				} else if(isset($_POST['a']) && $_POST['a'] == "reactivateEvent") {
					$aaerror_strs = array("Wrong Password!");
					$aaerrors = array();
					//if(md5($_POST['pw']) == $me->password) {
						$ev = new Event();
						$ev->buildFromId($_POST['id']);
						$ev->data1['active'][1] = 1;
						$ev->updateDB();
						echo"<meta http-equiv='Refresh' content='0;index.php?a=veEvent&b=".$_POST['id']."&tab=3'>";
					//} else 
					//	array_push($aaerrors, 0);
				} if(isset($_POST['a']) && $_POST['a'] == "deleteEvent") {
					$daerror_strs = array("Wrong Password!");
					$daerrors = array();
					if(md5($_POST['pw']) == $me->password) {
						$sql = "DELETE FROM `events` WHERE `id`=".$_POST['id'];
						mysql_query($sql);
						echo"<meta http-equiv='Refresh' content='0;index.php'>";
					} else 
						array_push($daerrors, 0);
				} 
				veEvent($_GET['b']);
			} else if(isset($_GET['a']) && $_GET['a'] == "accountSettings" && isLoggedIn()) {
				accountSettings();
			} else if(isset($_GET['a']) && $_GET['a'] == "admin" && $_SESSION['account_id'] == 0) {
				admin_do($_GET['b']);
			} else if(isset($_GET['a']) && $_GET['a'] == "contact") {
				include'./code/contact.php';
			} else if(isset($_GET['a']) && $_GET['a'] == "privacy") {
				include'./code/privacy.php';
			} else if(isset($_GET['a']) && $_GET['a'] == "catedit") {
				include'./code/catedit.php';
			} else if(isset($_POST['a']) && $_POST['a'] == "cateditsubmit") {
				$error_strs = array("No Super-Level defined", "Doesn't exist");
				$errors = array();
				$sup = $_POST['suplvl'];
				$sub = $_POST['sublvl'];
				$name = $_POST['name'];
				if($sub == "")
					$sub = 0;
				if($sup == "") 
					array_push($errors, 0);
				$sql = "SELECT * FROM `categories`";
				$result = mysql_query($sql);
				$rows = array();
				while($row = mysql_fetch_array($result))
					array_push($rows, $row);
				$found = -1;
				for($i = 0; $i < count($rows); $i++)
					if($rows[$i]["level"] == ($sup.".".$sub))
						$found = $i;
				if($found == -1 && $name == "" && empty($errors))
					array_push($errors, 1);
				if(empty($errors)) {
					if($name == "") {
						$sql = "DELETE FROM `categories` WHERE `level` = '".$sup.".".$sub."'";
						mysql_query($sql);
					} else {
						if($found == -1) {
							$sql = "INSERT INTO `categories` (`level`, `name`)VALUES('".$sup.".".$sub."', '".$name."')";
							mysql_query($sql);
						} else {
							$sql = "UPDATE `categories` SET `name` = '".$name."' WHERE `level` = '".$sup.".".$sub."'";
							mysql_query($sql);
						}
					}
				}
				include'./code/catedit.php';
			} else if(isset($_POST['error']) && $_POST['error'] == "404") {
				error_404();
			} else echo"<meta http-equiv='Refresh' content='0;index.php'>";
		} else if(!isLoggedIn()) {
			include'./code/welcome.php';
		} else if(isLoggedInNormal()) {
			include "./code/home.php";
		} else {
			showAdminHome();
		}
	?>
</body>
</html>