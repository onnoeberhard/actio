<?php
	heade('');
?>

<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;">
	<div style="text-align:center;width:100%">
        <?php global $errors, $error_strs; if(isset($errors)){echo'<header>
            <p>'; for($i = 0; $i < count($errors); $i++) echo $error_strs[$errors[$i]]; 
        echo'</p></header>';} ?>
        <div>
            <article style="background:rgba(255, 255, 255, .5);">
            <?php
                if(!$me->hasActivities())
					echo'
					<header>
						<h1>You don\'t have any activities!</h1>
						<p>Click the button to create an activity</p>
					</header>
					<form method="get" style="padding-bottom:20px;margin-top:-10px;">
						<button class="button form" type="submit" name="a" value="createActivity">Create Activity</button>
					</form>';
                    
				else {
					$pls = $me->allPlaces();
					$pls_n = array();
					for($i = 0; $i < count($pls); $i++) {
						$pl = array("id"=>$pls[$i]['id'], "name"=>"");
						$data = $pls[$i]['data2'];
						$data_I = explode("{{;}}", $data);
						for($ii = 0; $ii < count($data_I); $ii++) {
							$data_II = explode("{{:}}", $data_I[$ii]);
							if($data_II[0] == "name")
								$pl['name'] = $data_II[1];
						}
						array_push($pls_n, $pl);
					}
					$evs = $me->allEvents();
					$evs_n = array();
					for($i = 0; $i < count($evs); $i++) {
						$ev = array("id"=>$evs[$i]['id'], "name"=>"");
						$data = $evs[$i]['data2'];
						$data_I = explode("{{;}}", $data);
						for($ii = 0; $ii < count($data_I); $ii++) {
							$data_II = explode("{{:}}", $data_I[$ii]);
							if($data_II[0] == "name")
								$ev['name'] = $data_II[1];
						}
						array_push($evs_n, $ev);
					}
					echo'
					<header>
						<h1>My Activities</h1>
					</header>
					<div style="background:#0ccc4f;width:70%;margin:auto;">';
					if(count($pls) > 0 && count($evs) > 0) echo'<h1>Places</h1><div style="background:rgba(255, 255, 255, .5);height:1px;width:100%;"></div>';
					for($i = 0; $i < count($pls); $i++) {
						echo'
						<div>';
							if($i != 0)
								echo'<div style="background:rgba(255, 255, 255, .5);height:1px;width:100%;"></div>';
							echo'
							<a href="index.php?a=vePlace&b='.$pls_n[$i]["id"].'" style="color:inherit;" title="View &amp; Edit">'.$pls_n[$i]["name"].'</a>';
						echo'</div>
						';
					}
					if(count($pls) > 0 && count($evs) > 0) echo'<div style="background:rgba(255, 255, 255, .5);height:1px;width:100%;"></div><h1>Events</h1>
					<div style="background:rgba(255, 255, 255, .5);height:1px;width:100%;"></div>';
					for($i = 0; $i < count($evs); $i++) {
						echo'
						<div>';
							if($i != 0)
								echo'<div style="background:rgba(255, 255, 255, .5);height:1px;width:100%;"></div>';
							echo'
							<a href="index.php?a=veEvent&b='.$evs_n[$i]["id"].'" style="color:inherit;" title="View &amp; Edit">'.$evs_n[$i]["name"].'</a>';
						echo'</div>
						';
					}
					echo'</div>
					<form method="get" style="padding-bottom:20px;margin-top:20px;">
						<button class="button form" type="submit" name="a" value="createActivity">Create Activity</button>
					</form>';
				}
            ?>
            </article>
        </div>
    </div>
</article>

<?php
	footer();
?>
