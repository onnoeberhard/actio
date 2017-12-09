<?php
	heade('Category Edit');
?>

<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;min-height:400px;">
	<div style="text-align:center;width:100%">
        <header>
        	<h2>Enter: super-level, sub-level, Name</h2>
            <?php global $errors, $error_strs; if(isset($errors)){echo'<p style="margin-bottom:-50px;">'; 
				for($i = 0; $i < count($errors); $i++) echo $error_strs[$errors[$i]]; echo'</p>';} ?>
        </header>
        <div>
			<form method="post" action="index.php">
				<div class="row" style="width:80%;margin:0 auto;margin-top:50px;">
					<div class="12u" style="width:100%;margin:0;padding:0">
						<input type="text" class="text" name="suplvl" autocomplete="off" placeholder="sup" style="text-align:center;width:12%;display:inline" />
						<input type="text" class="text" name="sublvl" autocomplete="off" placeholder="sub" style="text-align:center;width:12%;display:inline" />
						<input type="text" class="text" name="name" autocomplete="off" placeholder="name" style="text-align:center;width:67%;display:inline" />
					</div>
				</div>
				<div class="row">
					<div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
						<button type="submit" name="a" value="cateditsubmit" class="form button">OK</button>
					</div>
				</div>
			</form>
        </div>
    </div>
</article>

<?php
	footer();
?>
