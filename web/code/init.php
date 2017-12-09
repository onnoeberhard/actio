<?php
	$alpha = false;
	$alpha = true;
	if($alpha) {
		if(!isset($_GET['dev']))
			die("Access Denied");
		else if(count($_GET) == 1)
			$_GET = array();
	}
	//session_start();
	error_reporting(0);
	mysql_connect('db4.cwsurf.de', 'actio', 'cwcitY22321');
	mysql_select_db('actio');
	include 'lang/lang.php';
	include 'user_functions.php';
	include 'activity_functions.php';
	include 'code.php';
?>