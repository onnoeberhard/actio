<article class="container box style3 noline" style="margin-top:50px;background:#0ab246">
	<div>
        <header>
            <h1 style="color:#fff;">Email Activation</h1>
            <?php 
				global $result;
				if(!isset($result))
					echo"<p>We sent you an Email with an Activation Code to the Email Address $email.</p>";
				else if($result == 1)
					echo"<p>Wrong Activation Code!</p>";
				else if($result == 2)
					echo"<p>Already done!</p>";
			?>
        </header>
        <?php if(!isset($result) || $result != 2)
		echo'<form method="post">
        <div class="row">
            <div class="6u" style="width:100%;margin:0;">
                <input type="text" class="text" name="c" style="text-align:center" placeholder="Please Enter the Activation Code here" />
            </div>
        </div>
        <div class="row">
            <div class="6u" style="width:100%;margin:0;text-align:center;">
                <input type="hidden" name="b" value="'.$u->id.'"/>
                <button type="submit" name="a" value="emailActivation" class="form button">Submit</button>
                <button type="submit" name="a" value="cancel" class="form button">Cancel</button>
            </div>
        </div>
        <div style="height:2px; width:100%; background-color:#0ccc4f; margin:20px 0px;"></div></form><form method="post">
        <div class="row">
            <div class="6u" style="width:100%;margin:0;text-align:center;">
                <input type="hidden" name="b" value="'.$u->id.'"/>
				<input type="hidden" name="pw" value="'.$u->password.'"/>
				<input type="hidden" name="eac" value="'.$eac.'"/>
                <button type="submit" name="a" value="sendActivationEmail" class="form button">Send Email Again</button>
                <button type="submit" name="a" value="delete_account" class="form button">Cancel and Delete Account</button>
            </div>
        </div></form>'; ?>
    </div>
</article>