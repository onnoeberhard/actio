<article class="container box style3 noline" style="margin-top:40px;background:#0ab246">
	<div>
        <header>
            <h1 style="color:#fff;">Category / Theme Suggestion</h1>
        </header>
        <div style="text-align:center;">
        	<form method="post" action="index.php">
                <input type="hidden" name="b" value="ACTIO CATTHE <?php echo $_GET['b']; ?> SUGGESTION FROM <?php echo $_SESSION['account_id']; ?>"/>
                <input type="hidden" name="close" value="true"/>
                <div class="12u">
                    <textarea name="c" style="min-height:0;" rows="5" placeholder="Tell us your idea (title, use)"></textarea>
                </div>
                <button class="form button" type="submit" style="margin-top:20px" name="a" value="mailme">Send</button>
				<span class="form button" onclick="window.close();">Cancel</span>
			</form>
        </div>
    </div>
</article>