<?php
	error_reporting(0);
	if($_POST['type'] == "img") {
		$base = $_POST["image"];
		if (isset($base)) {
			$image_name = $_POST['img_name'].$_POST['format'];/*date("YmdHis")."_".$_POST['actid']."_img.jpg"*/
			$binary = base64_decode($base);
			header("Content-Type: bitmap; charset=utf-8");
			$file = fopen("../upload/" . $image_name, "wb");
			fwrite($file, $binary);
			fclose($file);
			die($image_name);
		}
	} else
		die("Access Denied");
?>