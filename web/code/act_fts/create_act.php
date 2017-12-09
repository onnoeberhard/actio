<?php 
	heade('Create Activity');
?>
<article class="container box style2 <?php if(empty($errors)) echo'noline" style="margin-top:20px;'; ?>">
    <div>
        <header style="background:#0ab246">
            <h1 style="color:#fff">What do you want to create?</h2>
            <!--<form method="post" style="margin-top:20px;" action="index.php">
            	<input type="hidden" name="a" value="cracttype" />
                <button class="button form" type='submit' name='b' value='place'>Place</button>
                <button class="button form" type='submit' name='b' value='event'>Event</button>
            </form>-->
            <form method="get" style="margin-top:20px;" action="index.php">
                <button class="button form" type='submit' name='a' value='createPlace'>Place</button>
                <button class="button form" type='submit' name='a' value='createEvent'>Event</button>
            </form>
        </header>
    </div>
</article>
<article id="p" class="container box style3" style="background:#0ab246 url('img/banner.svg') bottom center no-repeat;">
    <div style="text-align:center;">
        <header>
            <h2 style="color:#fff;">What is what?</h2>
        </header>
        <table style="text-align:center;table-layout:fixed;width:80%;margin:30px auto;"><tr><td>
            <p>Place</p></td><td>
            <p>Event</p></td></tr><tr><td>
            <a href="?a=example&b=st" class="linkborder">Example</a></td><td>
            <a href="?a=example&b=ev" class="linkborder">Example</a></td></tr><tr><td>
            <p>19,90&euro; / year</p></td><td>
            <p>3,99&euro; / day</p>
        </td></tr></table>
    </div>
</article>
<?php footer(); ?>