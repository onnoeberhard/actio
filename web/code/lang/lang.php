<?php
	include'EN.php';
	if(substr(getenv('HTTP_ACCEPT_LANGUAGE'), 0, 2) == "de"){
	  include'DE.php';
	}
	function txt($_txt) {
	  global $txt, $txt_EN;
	  if(array_key_exists(strtolower($_txt), $txt)) {
		  return $txt[strtolower($_txt)];
	  } else if(array_key_exists(strtolower($_txt), $txt_EN)) {
		  return $txt_EN[strtolower($_txt)];
	  } else {
		  return $_txt;
	  }
	}
?>