<?php
	function footer() {
		echo'
			</div>
			<div id="footer" style="background:rgba(10, 178, 70, .3);"><!--background:rgba(53, 184, 143, .3);--><!--<p class="fa"></p>-->&ensp;
				<a style="margin:0;float:left;margin-left:20px;border:none;cursor:pointer;" href="index.php?a=contact" onclick="'./*
				"new Messi('Onno Eberhard\\nIm Riehewinkel 10', {title: 'Contact', modal: true, titleClass: 'info', buttons: [{id: 0, label: 'Close', 
				val: 'X'}]});".*/'" title="'.txt("Contact").'">&copy; 2014, Onno Eberhard</a>
				<a style="margin:0;float:right;margin-right:20px;border:none;cursor:pointer;" href="index.php?a=privacy">'.txt("Privacy").'</a>
			</div>
		';
	}
	function heade($title) {
		echo'
	<div id="page-wrap">
		<div style="width:100%;text-align:center;">
			<header style="color:#fff;">';
				if($title == "") {
					echo'<h2>Hello, ';
					if(isset($_SESSION['me']->data1['name'][1])) echo $_SESSION['me']->data1['name'][1]; else echo $_SESSION['me']->email;echo'!</h2>';
				} else if(!(strpos($title, "!!!") !== false)) {
					echo '<h2>'.$title.'</h2>';
				} else {
					if($title == "admin!!!") {
						echo '<h2>Greetings, Master</h2>';
					} else if(strpos($title, "admin!!!") !== false) {
						$_title = $title;
						$_title = str_replace("admin!!!", "admin", $_title);
						echo '<h2>'.$_title.'</h2>';
					}
				} echo'
				
			</header>';if(isLoggedIn()){ echo'
			<div class="headerMenu" style="position:absolute;background:rgba(0, 0, 0, .3);right:0;color:white;top:0px;height:38px;width:38px;text-align:center;">
			   <ul style="height:100%">
					<li style="height:100%;display:table"><span style="display:table-cell;vertical-align:middle">&#9660;</span>
						<ul>
							<li><a href="index.php" style="border:none;"><span>Home</span></a></li>';if(!(strpos($title, "admin!!!") !== false)) {echo'
							<li><a href="index.php?a=accountSettings" style="border:none;"><span>Settings</span></a></li>';}echo'
							<li><a href="index.php?a=logout" style="border:none;"><span style="white-space:nowrap;">Log out</span></a></li>
						</ul>
					</li>
				</ul>
			</div>';} else { echo'
			<div class="headerMenu" style="position:absolute;background:rgba(0, 0, 0, .3);right:0;color:white;top:0px;height:38px;width:38px;text-align:center;">
					<a style="border:none;font-size:16px;" href="index.php"><span class="fa fa-home" style="color:#fff;"></span></a>
			</div>';} echo'
		</div>';
	}
?>