<?php
	heade('admin!!!');
?>

<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;min-height:400px;">
	<div style="text-align:center;width:100%">
        <header>
        	<h2>I'm your bitch. GO!</h2>
        	<!--<h1>Lastly Online: <?php global $me; echo substr($me->data1['lastly_online'][1], 6, 2) . "." 
			. substr($me->data1['lastly_online'][1], 4, 2) . "." . substr($me->data1['lastly_online'][1], 0, 4) . ", "
			. substr($me->data1['lastly_online'][1], 8, 2) . ":" . substr($me->data1['lastly_online'][1], 10, 2); ?></h1>-->
            <?php global $errors, $error_strs; if(isset($errors)){echo'<p>'; for($i = 0; $i < count($errors); $i++) echo $error_strs[$errors[$i]]; echo'</p>';} ?>
        </header>
        <div>
            <!--<article style="background:rgba(255, 255, 255, .5);">-->
            	<form>
                    <div class="row" style="width:80%;margin:0 auto;margin-top:50px;">
                        <div class="12u" style="width:100%;margin:0;padding:0;">
                            <input type="text" class="text" name="b" autocomplete="off" style="text-align:center" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="6u" style="width:100%;margin:0;margin-top:-10px;text-align:center;">
                            <button type="submit" name="a" value="admin" class="form button">OK</button>
                        </div>
                    </div>
            	</form>
            <!--</article>-->
        </div>
    </div>
</article>

<?php
	footer();
?>
