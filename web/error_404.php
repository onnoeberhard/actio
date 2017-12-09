<?php
	$_POST['error'] = "404";
	include'index.php';
	function error_404() {
		heade('Error');
		echo'
			<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;">
				<div style="text-align:center;width:100%">
					<p style="font-size:48px">Error 404: Page not found!</p>
				</div>
			</article>';
		footer();
	}
?>
