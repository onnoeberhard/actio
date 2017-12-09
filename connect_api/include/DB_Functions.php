<?php

class DB_Functions {

    private $db;

    function __construct() {
        require_once 'DB_Connect.php';
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    function __destruct() {}

    public function storeUser($name, $email, $password) {
		$pw = md5($password);
		$now = date("Ymd");
		$sql = "INSERT INTO `accounts` (`email`, `password`, `data1`)VALUES
				('$email', '$pw', 'active{{:}}1{{;}}created_on{{:}}$now')";
		mysql_query($sql);
        /*$hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"];
        $salt = $hash["salt"];
		$now = date("Ymd");
        $result = mysql_query("INSERT INTO accounts(email, password, data1, data2) VALUES
		('$email', '".$encrypted_password.$salt."', 'name{{:}}$name{{;}}created_on{{:}}$now', '')");*/
        if ($result) {
            $id = mysql_insert_id();
            $result = mysql_query("SELECT * FROM accounts WHERE id = $id");
            return mysql_fetch_array($result);
        } else
            return false;
    }

    public function getUserByEmailAndPassword($email, $password) {
        $result = mysql_query("SELECT * FROM accounts WHERE email = '$email'") or die(mysql_error());
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
			$md5pw = $result['password'];
            if (md5($password) == $md5pw)
                return $result;
        } else
            return false;
    }
	
	public function storePlace($name, $address, $describtion, $opening_hours, $prices, $contact) {
		$now = date("Ymd");
		if(is_array($address))
			$addresss = implode("{{,}}", $address);
		else
			$adresss = $adress;
		/*if(is_array($opening_hours))
			$opening_hourss = implode("{{,}}", $opening_hours);
		else
			$opening_hourss = $opening_hours;
		if(is_array($prices)) {
			$pricess = "adults{{.}}" . $prices[0] . $prices[1] . $prices[2] . "{{,}}pupils{{.}}" . $prices[3] . $prices[4] . $prices[5] . 
						"{{,}}students{{.}}" . $prices[6] . $prices[7] . $prices[8] . "{{,}}kids{{.}}" . $prices[9] . $prices[10] . $prices[11] .
					 	"{{,}}card_month{{.}}" . $prices[12] . $prices[13] . $prices[14] . "{{,}}card_year{{.}}" . $prices[15] . $prices[16] . $prices[17];
			if(count($prices) > 18) {
				$pricess .= "{{,}}";
				for($i = 0; $i < count($prices)-18; $i++) {
					$pricess .= $prices[$i+18];
					if (($i % 3) == 0)
						$pricess .= "{{,}}";
					else
						$pricess .= "{{.}}";
				}
			}
		}
		else
			$pricess = $prices;
		if(is_array($contact))
			$contacts = implode("{{,}}", $contact);
		else
			$contacts = $contact;
        $result = mysql_query("INSERT INTO places(data1, data2) VALUES
		('name{{:}}$name{{;}}address{{:}}$addresss{{;}}description{{:}}$description{{;}}
		opening_hours{{:}}$opening_hourss{{;}}prices{{:}}$pricess{{;}}contact{{:}}$contacts{{;}}created_on{{:}}$now', '')");*/
		$result = mysql_query("INSERT INTO places(data1, data2) VALUES
		('name{{:}}$name{{;}}address{{:}}$addresss', '')");
        if ($result) {
            $id = mysql_insert_id();
            $result = mysql_query("SELECT * FROM places WHERE id = $id");
            return mysql_fetch_array($result);
        } else
            return false;
    }

    public function isUserExisted($email) {
        $result = mysql_query("SELECT email from accounts WHERE email = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0)
            return true;
        else
            return false;
    }

    /*public function hashSSHA($password) {
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    public function checkhashSSHA($salt, $password) {
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
        return $hash;
    }*/

}

?>