<?php
	heade('Account Settings');
?>

<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;min-height:400px;">
	<div style="text-align:center;width:100%">
       <!-- <div id="tabs">
            <table><tr><td style="width:20%;">
                <table style="position:absolute;width:20%;"><tr><td>
                    <a href="#tabs-general" style="color:#fff;border:none;width:100%;text-align:center;display:block;">General</a></td></tr><tr><td>
                    <a href="#tabs-more" style="color:#fff;border:none;width:100%;text-align:center;display:block;">More</a>
                </td></tr></table>
                </td><td>
                <div id="tabs_container" >
                    <div id="tabs-general" style="width:100%;">-->
                        <article style="background:rgba(255, 255, 255, .5);" id="general">
                            <form style="width:80%;margin:0 auto;padding:30px 0;" method="post" action="#general">  
                            	<p style="text-align:left;margin-left:-5px;margin-bottom:0px;">Name:</p>
                           		<input type="text" class="text" name="name" placeholder="Name" value='<?php global $me;
								if(isset($me->data1['name']))echo $me->data1['name'][1]; ?>'/>
                                <button class="button form" type='submit' name='a' value='saveAccGen' style="margin-top:20px;">Save</button>
                                <button class="button form" type='reset' style="margin-top:20px;">Cancel</button>
                            </form>
                        </article>
                    <!--</div>
                    <div id="tabs-more" style="width:100%;">-->
                        <article style="background:rgba(255, 255, 255, .5);padding:30px 0;" id="cp">
                        	<div style="text-align:center;width:100%">
                                <header>
                                    <h1 style="color:#fff;">Change Password</h1>
                                    <?php global $cperrors, $cperror_strs; if(isset($cperrors)){echo'<p style="margin-bottom:-30px;">'; 
									for($i = 0; $i < count($cperrors); $i++) echo $cperror_strs[$cperrors[$i]]; echo'</p>';} ?>
                                </header>
                                <form method="post" style="text-align:center;width:100%" action="#cp">
                                <div class="row" style="width:60%;margin:0 auto">
                                    <div class="6u" style="width:100%;margin:0;padding:0;">
                                        <input type="password" class="text" name="cur_password" style="text-align:center" placeholder="Current Password" />
                                        <input type="password" class="text" name="password" style="text-align:center;margin-top:20px;"
                                        placeholder="New Password" />
                                        <input type="password" class="text" name="password2" style="text-align:center;margin-top:5px;"
                                        placeholder="Repeat New Password" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="6u" style="width:100%;margin:0;text-align:center;">
                                        <button type="submit" name="a" value="change_password" class="form button">Submit</button>
                                        <button class="button form" type='reset'>Cancel</button>
                                    </div>
                                </div></form>
                            </div>
                        </article>
                        <article style="background:rgba(255, 255, 255, .5);padding:30px 0;" id="da">
                        	<div style="text-align:center;width:100%">
                                <header>
                                    <h1 style="color:#fff;">Delete Account</h1>
                                    <?php global $daerrors, $daerror_strs; if(isset($daerrors)){echo'<p style="margin-bottom:-30px;">'; 
									for($i = 0; $i < count($daerrors); $i++){ if($i > 0) echo'<br />'; echo $daerror_strs[$daerrors[$i]]; } echo'</p>';} ?>
                                </header>
                                <form method="post" style="text-align:center;width:100%" action="#da">
                                <div class="row" style="width:60%;margin:0 auto">
                                    <div class="6u" style="width:100%;margin:0;padding:0;">
                                        <input type="password" class="text" name="pw" style="text-align:center" id="acdelpw"
                                        placeholder="Password" onkeyup="acdelpwcng();" onchange="acdelpwcng();"/>
                                        <p id="acdelnotice" style="display:none;margin-top:10px">ATTENTION! THIS CANNOT BE UNDONE!</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="6u" style="width:100%;margin:0;text-align:center;">
                                        <button type="submit" name="a" value="delete_account" class="form button">Delete</button>
                                        <button class="button form" type='reset' onclick="document.getElementById('acdelnotice').style.display = 'none';">
                                        Cancel</button>
                                    </div>
                                </div></form>
                            </div>
                        </article><!--
                    </div>
                </div>
            </td></tr></table>
        </div>-->
    </div>
</article>

<?php
	footer();
?>