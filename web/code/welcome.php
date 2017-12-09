<!-- Top Links -->
    <?php /* <div style="width:100%;text-align:right;">
        <a href="#l" class="scrolly grey" style="margin:10px;">Login</a>
    </div> */?>
<!-- Header -->
    <section id="header">
        <header>
            <img src="./actionew2.png" style="padding:30px 0;"/><!--<h1 style="font-family:Stencil, Arial, Helvetica, sans-serif; font-size:48px">actio</h1>-->
            <p>carpe diem</p>
        </header>
        <footer>
            <a href='#banner' class="button style2 scrolly">What is actio?</a>
        </footer>
    </section>

<!-- Banner -->
    <section id="banner">
        <header>
            <h2>This is actio</h2>
        </header>
        <p>Actio is an app for Smartphones which shows you activities you can do in your area and<br/>
        lets you explore other cities and plan what to do there if you go there.<br/>
        Additionally it lets you rate / comment activities and shows you what activities are most popular.<br/><br/>
        For businesses who offer leisure activities (like swimming pools or amusement parks),<br/>
        for one-time events like concerts and for other kinds of events (like a fair),
        actio is the key to more visitors.<br/><br/>
        For everyone actio is a great app for bringing more fun to their spare time.
        </p>
        <footer>
            <a href="#getit" class="button style2 scrolly">I want to improve my business!</a>
            &emsp;<a href="#getit" class="button style2 scrolly">I want to have a better time!</a>
        </footer>
    </section>
	
    <article id="getit" class="container box style2">
         <div>
			<!--<div onmousedown="dragstart(this)" style="z-index:1000;cursor:move;position:absolute;top:0px;left:0px;height:100px;width:100px;background:#f00">
            </div>-->
            <header style="background:#0ab246;">
                <!--<button class="button form" type='submit' name='a' value='createActSubmission'>Get On Google Play</button>-->
				<a href="https://play.google.com/store/apps/details?id=actio.app" target="_blank">
				  <img alt="Get it on Google Play"
					   src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
				</a>
            </header>
        </div>
    </article>

<?php /*
	<article id="p" class="container box style3" style="background:#35b88f url('img/banner.svg') bottom center no-repeat;">
        <div style="text-align:center;">
            <header>
                <h2 style="color:#fff;">What's right for me?</h2>
            </header>
            <table style="text-align:center;table-layout:fixed;width:80%;margin:30px auto;"><tr><td>
            	<p>Place</p></td><td>
                <p>Event</p></td></tr><tr><td>
                <a href="?a=example&b=st" class="linkborder">Example</a></td><td>
                <a href="?a=example&b=ev" class="linkborder">Example</a></td></tr><tr><td>
                <p>19,90&euro; / year</p></td><td>
                <p>3,99&euro; / day</p>
            </td></tr></table>
            <div class="row">
                <div class="12u">
                    <ul class="actions">
                        <li><a href="#rc" class="button form scrolly">Register</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </article>

<!-- Register / Create -->
    <article id="r" class="container box style3">
        <div class="">
            <header>
                <h2>Register</h2>
               	<?php if(!isset($r_errors)){echo"<p>Create an actio account to manage and create places / events (activities)</p>";} ?>
            </header>
            <?php if(isset($r_errors)){echo"<header><p>";for($i=0;$i<count($r_errors);$i++){echo $r_error_strs[$r_errors[$i]], "&ensp;";}echo"</p></header>";} ?>
            <form style="width:100%;" method="post">
                <div class="row half" style="width:100%;">
                    <div class="6u" style="width:100%;">
                        <input type="text" class="text" name="email" placeholder="Email" 
                        <?php if(isset($_POST['email']) && $_POST['a'] == "register") echo"value=\"".$_POST['email']."\""; ?> style="width:100%;"/>
                    </div>
                </div>
                <div class="row half" style="width:100%;">
                    <div class="6u" style="width:50%;">
                        <input type="password" class="text" name="password" placeholder="Password"  style="width:100%;"/>
                    </div>
                    <div class="6u" style="width:50%;">
                        <input type="password" class="text" name="password2" placeholder="Repeat Password"  style="width:100%;"/>
                    </div>
                </div>
                <div class="row">
                    <div class="12u">
                        <ul class="actions">
                            <li><button type="submit" name="a" value="register" class="button form">Register</button></li>
                        </ul>
                    </div>
                </div>
            </form>
            <div style="height:2px; width:100%; background-color:#EEE; margin:10px;"></div>
            <form method="get">
                <div class="row">
                    <div class="12u">
                        <ul class="actions">
                            <li><a href="#l" class="button form scrolly">Already Registered. Log in!</a></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
    </article>
-->
<!-- Login -->
    <article id="l" class="container box style3">
        <div class="">
            <header>
                <h2>Log in</h2>
                <?php if(!isset($l_errors)){echo"<p>Log in to your actio account to manage and create places / events (activities)</p>";} ?>
            </header>
            <?php if(isset($l_errors)){echo"<header><p>";for($i=0;$i<count($l_errors);$i++){echo $l_error_strs[$l_errors[$i]], "&ensp;";}echo"</p></header>";} ?>
            <form style="width:100%;" method="post">
                <div class="row half" style="width:100%;">
                    <div class="6u" style="width:100%;">
                        <input type="text" class="text" name="email" placeholder="Email" 
						<?php if(isset($_POST['email']) && $_POST['a'] == "login") echo"value=\"".$_POST['email']."\""; ?> style="width:100%;"/>
                    </div>
                </div>
                <div class="row half" style="width:100%;">
                    <div class="6u" style="width:100%;">
                        <input type="password" class="text" name="password" placeholder="Password"  style="width:100%;"/>
                    </div>
                </div>
                <div class="row">
                    <div class="12u">
                        <ul class="actions">
	                        <?php if(isset($_POST['a']) && $_POST['a'] == "recoverPassword" && isset($l_errors) && $l_errors[0] == 2) 
							echo"
								<input type='hidden' name='email' value='".$_POST['email']."'/>
								<input type='hidden' name='new_pw' value='".$new_pw."'/>
								<input type='hidden' name='a' value='recoverPassword'/>
								<li><button type='submit' name='b' value='sendPasswordEmailAgain' class='button form'>Send Email Again</button></li>"; ?>
                            <li><button type="submit" name="a" value="login" class="button form">Log in</button></li>
                        </ul>
                    </div>
                </div>
            	<div style="height:2px; width:100%; background-color:#EEE; margin:10px;"></div>
                <div class="row">
                    <div class="12u">
                        <ul class="actions">
                            <li><button type="submit" name="a" value="recoverPassword"  class="button form">Forgot Password</button></li>
                            <li><a href="#r" class="button form scrolly">No Account. Register!</a></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
    </article>
*/ ?>
<?php footer(); ?>