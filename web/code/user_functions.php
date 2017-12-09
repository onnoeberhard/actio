<?php
	class User {
		public $id;
		public $columns = array();
		public $active;
		public $data1 = array();
		public $data2 = array();
		public $password;
		public $email;
		function buildFromId($_id) {
			$this->id = $_id;
			$sql = "SELECT * FROM `accounts` WHERE `id` = $this->id";
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
				for($ii = 0; $ii < count($o); $ii++)
					array_push($data1_values[$i], $o[$ii]);
			}
			$this->data1 = array_combine($data1_keys, $data1_values);
			$this->active = $this->data1['active'][1];
			$this->data2 = explode("{{;}}", $this->columns['data2']);
			$this->email = $this->columns['email'];
			$this->password = $this->columns['password'];
		}
		function buildFromEmail($email) {
			$sql = "SELECT `id` FROM `accounts` WHERE `email` = '$email'";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			$this->buildFromId($rows[0][0]);
		}
		function updateDB() {
			$data1_keys = array_keys($this->data1);
			$data1 = array();
			for($i = 0; $i < count($this->data1); $i++)
				array_push($data1, implode('{{:}}', $this->data1[$data1_keys[$i]]));
			$data1_new = '';
			for($i = 0; $i < count($data1); $i++) {
				if($data1[$i] != "{{:}}") {
					if($i != 0)
						$data1_new .= "{{;}}";
					$data1_new .= $data1[$i];
				}
			}
			$data1 = $data1_new;
			$data2 = implode("{{;}}", $this->data2);
			$sql = 
				"UPDATE `accounts` SET `email` = '$this->email', `password` = '$this->password', `data1` = '$data1', `data2` = '$data2' WHERE `id` = $this->id";
			mysql_query($sql);
		}
		function changePassword($pw) {
			$this->password = md5($pw);
			$this->updateDB();
		}
		function hasActivities() {
			$sql = "SELECT * FROM `places`";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			for($i = 0; $i < count($rows); $i++) {
				$data = $rows[$i]['data1'];
				$data_I = explode("{{;}}", $data);
				for($ii = 0; $ii < count($data_I); $ii++) {
					$data_II = explode("{{:}}", $data_I[$ii]);
					if($data_II[0] == "owner")
						if($data_II[1] == $this->id)
							return true;
				}
			} 
			$sql = "SELECT * FROM `events`";
			$result = mysql_query($sql);
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			for($i = 0; $i < count($rows); $i++) {
				$data = $rows[$i]['data1'];
				$data_I = explode("{{;}}", $data);
				for($ii = 0; $ii < count($data_I); $ii++) {
					$data_II = explode("{{:}}", $data_I[$ii]);
					if($data_II[0] == "owner")
						if($data_II[1] == $this->id)
							return true;
				}
			}
			return false;
		}
		function allPlaces() {
			$sql = "SELECT * FROM `places`";
			$result = mysql_query($sql);
			$acts = array();
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			for($i = 0; $i < count($rows); $i++) {
				$data = $rows[$i]['data1'];
				$data_I = explode("{{;}}", $data);
				for($ii = 0; $ii < count($data_I); $ii++) {
					$data_II = explode("{{:}}", $data_I[$ii]);
					if($data_II[0] == "owner")
						if($data_II[1] == $this->id)
							array_push($acts, $rows[$i]);
				}
			} return $acts;
		}
		function allEvents() {
			$sql = "SELECT * FROM `events`";
			$result = mysql_query($sql);
			$acts = array();
			$rows = array();
			while($row = mysql_fetch_array($result))
				array_push($rows, $row);
			for($i = 0; $i < count($rows); $i++) {
				$data = $rows[$i]['data1'];
				$data_I = explode("{{;}}", $data);
				for($ii = 0; $ii < count($data_I); $ii++) {
					$data_II = explode("{{:}}", $data_I[$ii]);
					if($data_II[0] == "owner")
						if($data_II[1] == $this->id)
							array_push($acts, $rows[$i]);
				}
			} return $acts;
		}
	}
	function userExists($email) {
		$email = mysql_real_escape_string($email);
		$sql = "SELECT COUNT(`id`) FROM `accounts` WHERE `email` = '$email'";
		$result = mysql_query($sql);
		return (mysql_result($result, 0) == 1);
	}
	function userActiveState($email) {
		$user = new User();
		$user->buildFromEmail($email);
		if($user->active == 1)
			return 0;
		if($user->active == 0 && isset($user->data1['email_activation_code']['1']))
			return 1;
		if($user->active == 0)
			return 2;
	}
	function registerUser($email, $password) {
		$pw = md5($password);
		$now = date("Ymd");
		$length = 5;
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$eac = '';
		for ($i = 0; $i < $length; $i++)
			$eac .= $characters[rand(0, strlen($characters) - 1)];
		$sql = "INSERT INTO `accounts` (`email`, `password`, `data1`)VALUES
				('$email', '$pw', 'active{{:}}0{{;}}email_activation_code{{:}}$eac{{;}}created_on{{:}}$now')";
		mysql_query($sql);
		$u = new User();
		$u->buildFromEmail($email);
		$uid = $u->id;
		sendActivationEmail($uid, $eac);
		emailActivationScreen($email);
	}
	function loginResult($email, $password) {
		$result = 0;
		$u = new User();
		$u->buildFromEmail($email);
		if(isset($u->data1['new_pw'][1]) && md5($password) == $u->data1['new_pw'][1]) {
			$u->password = md5($password);
			$u->data1['new_pw'][0] = "";
			$u->data1['new_pw'][1] = "";
			$u->updateDB();
			$result = 2;
		} else if(md5($password) != $u->password)
			$result = -1;
		else if(isset($u->data1['new_pw'][1])) {
			$u->data1['new_pw'][0] = "";
			$u->data1['new_pw'][1] = "";
			$u->updateDB();
			$result = 1;
		} else if(md5($password) == $u->password)
			$result = 1;
		return $result;
	}
	function isLoggedInNormal() {
		return (isset($_SESSION['account_id']) && $_SESSION['account_id'] != 0);
	}
	function isLoggedIn() {
		return isset($_SESSION['account_id']);
	}
	function emailActivationScreen($email) {
		$u = new User();
		$u->buildFromEmail($email);
		$eac = $u->data1['email_activation_code'][1];
		include 'acc_fts/emailActivationScreen.php';
	}
	function sendActivationEmail($id, $eac) {
		$u = new User();
		$u->buildFromId($id);
		$email = $u->email;
		$headers  = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
		$headers .= 'To: <'.$email.'>' . "\r\n";
		$headers .= 'From: actio <postmaster@localhost>' . "\r\n";
		mail($email, "Actio Registration Verification", 
		"<html>
			<head>
				<title>Actio Registration Verification</title>
			</head>
			<p>Thank you for joining Actio!</p>
			<p>To verify your email address and thus activating your Actio account, please click <a 
			href='http://actio.bplaced.net/index.php?a=emailActivation2&b=".$id."&c=".$eac."'>here</a> or enter this code: ".$eac." (on the activation page)
		</html>"
		, $headers);
	}
	function sendPasswordEmail($email, $new_pw) {
		$u = new User();
		$u->buildFromEmail($email);
		$headers  = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
		$headers .= 'To: <'.$email.'>' . "\r\n";
		$headers .= 'From: actio <postmaster@localhost>' . "\r\n";
		mail($email, "Actio Password Recovery", 
		"<html>
			<head>
				<title>Actio Password Recovery</title>
			</head>
			Your new password: ".$new_pw."
		</html>"
		, $headers);
	}
	function activateEmail($id, $code) {
		$result = 1;
		$u = new User();
		$u->buildFromId($id);
		if(isset($u->data1['email_activation_code'][1]) && $u->data1['email_activation_code'][1] == $code) {
			$u->data1['active'][1] = 1;
			$u->data1['email_activation_code'][0] = "";
			$u->data1['email_activation_code'][1] = "";
			$u->updateDB();
			$result = 0;
		} else if(!isset($u->data1['email_activation_code'][1]))
			$result = 2;
		return $result;
	}
	function accountSettings() {
		include 'acc_fts/accountSettings.php';
	}
?>