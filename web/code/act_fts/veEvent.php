<?php
	heade('View &amp; Edit');
	$act = new Event();
	$act->buildFromId($actId);
	$_data2 = $act->data2;
	$_data2['admission']['i0'] = (!isset($_data2['admission']['i0'])) ? array("0", "") : $_data2['admission']['i0'];
	$_data2['admission']['i1'] = (!isset($_data2['admission']['i1'])) ? array("0", "") : $_data2['admission']['i1'];
	$_data2['admission']['i2'] = (!isset($_data2['admission']['i2'])) ? array("0", "") : $_data2['admission']['i2'];
	$_data2['admission']['i3'] = (!isset($_data2['admission']['i3'])) ? array("0", "") : $_data2['admission']['i3'];
	$_data2['admission']['i4'] = (!isset($_data2['admission']['i4'])) ? array("0", "") : $_data2['admission']['i4'];
	$_data2['admission']['i5'] = (!isset($_data2['admission']['i5'])) ? array("0", "") : $_data2['admission']['i5'];
	$_data2['admission']['i0']['ages'] = (!isset($_data2['admission']['i0']['ages'])) ? array("0", "", "") : $_data2['admission']['i0']['ages'];
	$_data2['admission']['i1']['ages'] = (!isset($_data2['admission']['i1']['ages'])) ? array("0", "", "") : $_data2['admission']['i1']['ages'];
	$_data2['admission']['i2']['ages'] = (!isset($_data2['admission']['i2']['ages'])) ? array("0", "", "") : $_data2['admission']['i2']['ages'];
	$_data2['admission']['i3']['ages'] = (!isset($_data2['admission']['i3']['ages'])) ? array("0", "", "") : $_data2['admission']['i3']['ages'];
	$_data2['admission']['i4']['ages'] = (!isset($_data2['admission']['i4']['ages'])) ? array("0", "", "") : $_data2['admission']['i4']['ages'];
	$_data2['admission']['i5']['ages'] = (!isset($_data2['admission']['i5']['ages'])) ? array("0", "", "") : $_data2['admission']['i5']['ages'];
	if($_data2['admission']['type'][1] == "nt") {
	$_data2['admission']['i0']['price'] = (isset($_data2['admission']['i0'][2])) ? 
	array("price", $_data2['admission']['i0'][2], $_data2['admission']['i0'][3]) : array("price", $_data2['admission']['i0'][3], $_data2['admission']['i0'][4]);
	$_data2['admission']['i1']['price'] = (isset($_data2['admission']['i1'][2])) ? 
	array("price", $_data2['admission']['i1'][2], $_data2['admission']['i1'][3]) : array("price", $_data2['admission']['i1'][3], $_data2['admission']['i1'][4]);
	$_data2['admission']['i2']['price'] = (isset($_data2['admission']['i2'][2])) ? 
	array("price", $_data2['admission']['i2'][2], $_data2['admission']['i2'][3]) : array("price", $_data2['admission']['i2'][3], $_data2['admission']['i2'][4]);
	$_data2['admission']['i3']['price'] = (isset($_data2['admission']['i3'][2])) ? 
	array("price", $_data2['admission']['i3'][2], $_data2['admission']['i3'][3]) : array("price", $_data2['admission']['i3'][3], $_data2['admission']['i3'][4]);
	$_data2['admission']['i4']['price'] = (isset($_data2['admission']['i4'][2])) ? 
	array("price", $_data2['admission']['i4'][2], $_data2['admission']['i4'][3]) : array("price", $_data2['admission']['i4'][3], $_data2['admission']['i4'][4]);
	$_data2['admission']['i5']['price'] = (isset($_data2['admission']['i5'][2])) ? 
	array("price", $_data2['admission']['i5'][2], $_data2['admission']['i5'][3]) : array("price", $_data2['admission']['i5'][3], $_data2['admission']['i5'][4]);}
	else {
	$_data2['admission']['i0']['price'] = array("price", "", "");
	$_data2['admission']['i1']['price'] = array("price", "", "");
	$_data2['admission']['i2']['price'] = array("price", "", "");
	$_data2['admission']['i3']['price'] = array("price", "", "");
	$_data2['admission']['i4']['price'] = array("price", "", "");
	$_data2['admission']['i5']['price'] = array("price", "", "");}
	//echo'<pre style="color:#fff">';print_r($_data2);echo'</pre>';
	if(!$act->data1['active'][1] && !isset($_GET['tab'])) {
		$_GET['tab'] = 4;
	}
?>

<article class="container box style3 noline homeArticle" style="margin-top:50px;background:#0ab246;min-height:400px;">
	<div style="text-align:center;width:100%">
        <header>
            <?php global $errors, $error_strs; if(isset($errors)){echo'<p>'; for($i = 0; $i < count($errors); $i++) echo $error_strs[$errors[$i]]; echo'</p>';} ?>
            <h2><?php echo $_data2['name'][1]; ?></h2>
        </header>
        <div id="tabs" style="padding-bottom:40px;">
        	<table><tr><?php if(!$act->data1['active'][1]) {
			echo'<td style="width:25%;">
            <a style="color:#fff;border:none;width:100%;text-align:center;display:block;"
            ';if($_GET['tab'] == 4) echo'class="tab_active"'; 
			echo'href="index.php?a=veEvent&b='.$_GET['b'].'&tab=4">Activation</a></td>';}?><td style="width:25%;">
            <a style="color:#fff;border:none;width:100%;text-align:center;display:block;"
            <?php if(!isset($_GET['tab']) || $_GET['tab'] == 0) echo'class="tab_active"'; 
			echo'href="index.php?a=veEvent&b='.$_GET['b'].'&tab=0"';?>>Edit</a></td><td style="width:25%;">
            <a style="color:#fff;border:none;width:100%;text-align:center;display:block;"
            <?php if(isset($_GET['tab']) && $_GET['tab'] == 2) echo'class="tab_active"'; 
			echo'href="index.php?a=veEvent&b='.$_GET['b'].'&tab=2"';?>>Ratings</a></td><td style="width:25%;">
            <a style="color:#fff;border:none;width:100%;text-align:center;display:block;"
            <?php if(isset($_GET['tab']) && $_GET['tab'] == 3) echo'class="tab_active"'; 
			echo'href="index.php?a=veEvent&b='.$_GET['b'].'&tab=3"';?>>Options</a></td></tr></table>
                <div id="tabs_container" >
                    <div id="tabs-edit" style="width:100%;<?php if(!(!isset($_GET['tab']) || $_GET['tab'] == 0)) echo'display:none;'; ?>">
                        <article style="background:rgba(255, 255, 255, .5);">
                            <form style="width:80%;margin:0 auto;padding:30px 0;" method="post">
                            	<div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
	                            <h1 style="color:#fff">General</h1>
                            	<p style="text-align:left;margin-left:-5px;margin-bottom:0px;">Name:</p>
                           		<input type="text" class="text" name="name" placeholder="Name *" value='<?php
								if(isset($_data2['name']))echo $_data2['name'][1]; ?>' />
                                
                                <p style="text-align:left;margin-left:-5px;margin-bottom:0px;">Description:</p>
                           		<textarea name="description" placeholder="Description"><?php
                                if(isset($_data2['description']))echo $_data2['description'][1]; ?></textarea>
                                
                                <p style="text-align:left;margin-left:-5px;margin-bottom:0px;">Images:</p>
                                <input type="text" class="text" name="img1" placeholder="Image 1 - Link" value='<?php
                                if(isset($_data2['img']['i0']))echo $_data2['img']['i0'][1]; ?>' />
                                <input type="text" class="text" name="img1title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                                value='<?php if(isset($_data2['img']['i0']))echo $_data2['img']['i0'][2]; ?>' />
                                <input type="text" class="text" name="img2" placeholder="Image 2 - Link"  value='<?php
                                if(isset($_data2['img']['i1']))echo $_data2['img']['i1'][1]; ?>' />
                                <input type="text" class="text" name="img2title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                                value='<?php if(isset($_data2['img']['i1']))echo $_data2['img']['i1'][2]; ?>' />
                                <input type="text" class="text" name="img3" placeholder="Image 3 - Link" value='<?php
                                if(isset($_data2['img']['i2']))echo $_data2['img']['i2'][1]; ?>' />
                                <input type="text" class="text" name="img3title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                                value='<?php if(isset($_data2['img']['i2']))echo $_data2['img']['i2'][2]; ?>' />
                                <br /><br />
                                </div>
                                <br />
                                
                                <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">                               
	                            <h1 style="color:#fff;">Address</h1>
                                <div style="float:left;text-align:left;margin-top:15px;width:48%;">
                                    <input type='radio' name='address_type' value='address' id='address_radio_address' 
                                    <?php if($_data2['address']['type'][1] == "a") echo"checked"; ?> />
                                    <label for='address_radio_address' style="display:inline;">Address</label><br />
                                    <input type='text' class="text address" name='address_street' value='<?php 
									if($_data2['address']['type'][1] == "a" && isset($_data2['address']['street'][1])) echo $_data2['address']['street'][1] ?>'
                                    placeholder="Street &amp; House Number" <?php if($_data2['address']['type'][1] != "a") echo"disabled" ?> 
                                    style="margin-top:10px;"/>
                                    <input type='text' class="text address" name='address_city' value='<?php 
									if($_data2['address']['type'][1] == "a" && isset($_data2['address']['city'][1])) echo $_data2['address']['city'][1] ?>'
                                    placeholder="ZIP-Code &amp; City" <?php if($_data2['address']['type'][1] != "a") echo"disabled" ?> 
                                    style="margin-top:10px;"/>
                                    <select name='address_country' id="country_select" class='address text'
                                    <?php if($_data2['address']['type'][1] != "a")echo"disabled" ?> style="margin-top:10px;line-height:1.1em;color:inherit;">
                                        <?php create_act_countries(); ?>
                                    </select> 
                                    <?php 
                                        if(isset($_data2['address']['country'][1]) && $_data2['address']['country'][1] != "")
                                            echo"<script>sel_val('country_select', '".$_data2['address']['country'][1]."');</script>";
                                        else
                                            echo"<script>sel_val('country_select', '".strtoupper(substr(getenv('HTTP_ACCEPT_LANGUAGE'), 0, 2))."');</script>";
                                    ?> 
                                </div>
                                <div style="float:right;text-align:left;margin-top:15px;width:48%;">
                                    <input type='radio' name='address_type' value='coordinates' id='address_radio_coordinates'
                                    <?php if($_data2['address']['type'][1] == "c") echo"checked"; ?> />
                                    <label for='address_radio_coordinates' style="display:inline;">Coordinates</label><br/>
                                    <input type='text' class="text coordinates" name='address_coordinates' value='<?php 
									if($_data2['address']['type'][1] == "c" && isset($_data2['address']['c'][1])) echo $_data2['address']['c'][1]; ?>'
                                    placeholder="Coordinates" <?php if($_data2['address']['type'][1] != "c") echo"disabled"; ?> style="margin-top:10px;"/>
                                    <p style="opacity:.5;text-align:center	">Just copy from Google Maps</p>
                                </div><br /><br /><br /><br /><br /><br /><br />
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Notes:</p>
                                	<textarea name="address_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php 
									if(isset($_data2['address']['notes'][1])) echo $_data2['address']['notes'][1]; ?></textarea>
                                </div>
                                <br />
                                
                                <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
	                            <h1 style="color:#fff;">Date &amp; Time</h1>
                                <input id="td_nt" type='radio' name='td_type' value="tdnt" <?php if($_data2['td']['type'][1] == "nt") echo"checked"; ?> />
                                <label for="td_nt" style="display:inline">Non-Text</label>&emsp;
                                <input id="td_t" type='radio' name='td_type' value="tdt" <?php if($_data2['td']['type'][1] == "t") echo"checked"; ?> />
                                <label for="td_t" style="display:inline">Text</label>
                                <div style="text-align:left; <?php if($_data2['td']['type'][1] != "nt") echo"display:none"; ?>" class="tdnt">
                                    <table cellspacing="10"><tr><td><p>From</p></td></tr><tr><td>
                                    <input type='text' placeholder="YYYY-MM-DD" style="text-align:center;"
                                    name='td_d_from' class='text' value='<?php if(isset($_data2['td']['df'][1])) echo $_data2['td']['df'][1] ?>' /></td><td>
                                    <input type='text' placeholder="hh:mm" style="text-align:center;" 
                                    name='td_t_from' class='text' value='<?php if(isset($_data2['td']['tf'][1])) echo $_data2['td']['tf'][1] ?>' />
                                    </td></tr><tr><td><p>To</p></td></tr><tr><td>
                                    <input type='text' placeholder="YYYY-MM-DD" style="text-align:center;" 
                                    name='td_d_to' class='text' value='<?php if(isset($_data2['td']['dt'][1])) echo $_data2['td']['dt'][1] ?>' /></td><td>
                                    <input type='text' placeholder="hh:mm" style="text-align:center;" 
                                    name='td_t_to' class='text' value='<?php if(isset($_data2['td']['tt'][1])) echo $_data2['td']['tt'][1] ?>' />
                                    </td></tr></table>
                                </div>
                                <div style="text-align:left;margin-top:15px; <?php if($_data2['td']['type'][1] != "t") echo"display:none"; ?>" class="tdt">
                                    <textarea name="td_text" rows="7" placeholder="Date &amp; Time" class="text"><?php 
									if(isset($_data2['td']['text'][1])) echo $_data2['td']['text'][1]; ?></textarea>
                                </div>
                                <div class="6u" style="width:100%;text-align:center;margin-top:20px;position:relative;">
                                    <input id="cb_oh" type='checkbox' name='cb_oh' <?php if(isset($_data2['oh'][1])) echo"checked"; ?> />
                                    <label for="cb_oh" style="display:inline">Opening Hours</label>
                                    <div style="text-align:left;margin-top:15px;">
                                        <div class="row half">
                                            <div class="12u">
                                                <textarea name="oh_text" rows="8" placeholder="Opening Hours" class="cb_oh text"
                                                 <?php if(!isset($_data2['oh'][1])) echo"disabled"; ?>><?php 
												 if(isset($_data2['oh'][1])) echo $_data2['oh'][1]; ?></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Notes:</p>
                                <textarea name="td_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php 
                                if(isset($_data2['td_notes'][1])) echo $_data2['td_notes'][1]; ?></textarea>
                                </div>
                                <br />
                                
                                <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
	                            <h1 style="color:#fff;">Admission</h1>
                                <input id="a_nt" type='radio' name='a_type' value="ant" <?php if($_data2['admission']['type'][1] == "nt") echo"checked"; ?> />
                                <label for="a_nt" style="display:inline">Non-Text</label>&emsp;
                                <input id="a_t" type='radio' name='a_type' value="at" <?php if($_data2['admission']['type'][1] == "t") echo"checked"; ?> />
                                <label for="a_t" style="display:inline">Text</label>
                                <div style="width:100%; <?php if($_data2['admission']['type'][1] != "nt") echo"display:none"; ?>" class="ant">
                                    <table hspace="5px"><tr><td>
                                    <input type='checkbox' name='admission_1' <?php if($_data2['admission']['i0'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_1_name' class='admission_1 text' 
                                    value='<?php echo $_data2['admission']['i0'][1] ?>'
                                    <?php if(!$_data2['admission']['i0'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_1_ages' class='admission_1'
                                    <?php if($_data2['admission']['i0']['ages'][0]) echo" checked"; if(!$_data2['admission']['i0'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_1 admission_1_ages text' 
                                    name='admission_1_from' value='<?php echo $_data2['admission']['i0']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i0'][0] || !$_data2['admission']['i0']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_1 admission_1_ages text' 
                                    name='admission_1_to' value='<?php echo $_data2['admission']['i0']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i0'][0] || !$_data2['admission']['i0']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_1 text' name='admission_1_price' 
                                    value='<?php echo $_data2['admission']['i0']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i0'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_1 text' 
                                    name='admission_1_money' value='<?php echo $_data2['admission']['i0']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i0'][0]) echo"disabled"; ?> />
                                    </td></tr><tr><td>
                                    <input type='checkbox' name='admission_2' <?php if($_data2['admission']['i1'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_2_name' class='admission_2 text' 
                                    value='<?php echo $_data2['admission']['i1'][1] ?>' 
                                    <?php if(!$_data2['admission']['i1'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_2_ages' class='admission_2'
                                    <?php if($_data2['admission']['i1']['ages'][0]) echo" checked"; if(!$_data2['admission']['i1'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_2 admission_2_ages text' 
                                    name='admission_2_from' value='<?php echo $_data2['admission']['i1']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i1'][0] || !$_data2['admission']['i1']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_2 admission_2_ages text' 
                                    name='admission_2_to' value='<?php echo $_data2['admission']['i1']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i1'][0] || !$_data2['admission']['i1']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_2 text' name='admission_2_price' 
                                    value='<?php echo $_data2['admission']['i1']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i1'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_2 text' 
                                    name='admission_2_money' value='<?php echo $_data2['admission']['i1']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i1'][0]) echo"disabled"; ?> />
                                    </td></tr><tr><td>
                                    <input type='checkbox' name='admission_3' <?php if($_data2['admission']['i2'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_3_name' class='admission_3 text' 
                                    value='<?php echo $_data2['admission']['i2'][1] ?>' 
                                    <?php if(!$_data2['admission']['i2'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_3_ages' class='admission_3'
                                    <?php if($_data2['admission']['i2']['ages'][0]) echo" checked"; if(!$_data2['admission']['i2'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_3 admission_3_ages text' 
                                    name='admission_3_from' value='<?php echo $_data2['admission']['i2']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i2'][0] || !$_data2['admission']['i2']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_3 admission_3_ages text' 
                                    name='admission_3_to' value='<?php echo $_data2['admission']['i2']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i2'][0] || !$_data2['admission']['i2']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_3 text' name='admission_3_price'
                                     value='<?php echo $_data2['admission']['i2']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i2'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_3 text' 
                                    name='admission_3_money' value='<?php echo $_data2['admission']['i2']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i2'][0]) echo"disabled"; ?> />
                                    </td></tr><tr><td>
                                    <input type='checkbox' name='admission_4' <?php if($_data2['admission']['i3'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_4_name' class='admission_4 text' 
                                    value='<?php echo $_data2['admission']['i3'][1] ?>' 
                                    <?php if(!$_data2['admission']['i3'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_4_ages' class='admission_4'
                                    <?php if($_data2['admission']['i3']['ages'][0]) echo" checked"; if(!$_data2['admission']['i3'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_4 admission_4_ages text' 
                                    name='admission_4_from' value='<?php echo $_data2['admission']['i3']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i3'][0] || !$_data2['admission']['i3']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_4 admission_4_ages text' 
                                    name='admission_4_to' value='<?php echo $_data2['admission']['i3']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i3'][0] || !$_data2['admission']['i3']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_4 text' name='admission_4_price' 
                                    value='<?php echo $_data2['admission']['i3']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i3'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_4 text' 
                                    name='admission_4_money' value='<?php echo $_data2['admission']['i3']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i3'][0]) echo"disabled"; ?> />
                                    </td></tr><tr><td>
                                    <input type='checkbox' name='admission_5' <?php if($_data2['admission']['i4'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_5_name' class='admission_5 text' 
                                    value='<?php echo $_data2['admission']['i4'][1] ?>' 
                                    <?php if(!$_data2['admission']['i4'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_5_ages' class='admission_5'
                                    <?php if($_data2['admission']['i4']['ages'][0]) echo" checked"; if(!$_data2['admission']['i4'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_5 admission_5_ages text' 
                                    name='admission_5_from' value='<?php echo $_data2['admission']['i4']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i4'][0] || !$_data2['admission']['i4']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_5 admission_5_ages text' 
                                    name='admission_5_to' value='<?php echo $_data2['admission']['i4']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i4'][0] || !$_data2['admission']['i4']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_5 text' name='admission_5_price' 
                                    value='<?php echo $_data2['admission']['i4']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i4'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_5 text' 
                                    name='admission_5_money' value='<?php echo $_data2['admission']['i4']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i4'][0]) echo"disabled"; ?> />
                                    </td></tr><tr><td>
                                    <input type='checkbox' name='admission_6' <?php if($_data2['admission']['i5'][0]) echo"checked"; ?> /></td><td>
                                    <input type='text' placeholder="Label" name='admission_6_name' class='admission_6 text' 
                                    value='<?php echo $_data2['admission']['i5'][1] ?>' 
                                    <?php if(!$_data2['admission']['i5'][0]) echo"disabled"; ?> /></td><td>: </td><td>
                                    <input type='checkbox' name='admission_6_ages' class='admission_6'
                                    <?php if($_data2['admission']['i5']['ages'][0]) echo" checked"; if(!$_data2['admission']['i5'][0]) echo" disabled"; ?> />
                                    </td><td>
                                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_6 admission_6_ages text' 
                                    name='admission_6_from' value='<?php echo $_data2['admission']['i5']['ages'][1] ?>' <?php 
									if(!$_data2['admission']['i5'][0] || !$_data2['admission']['i5']['ages'][0]) echo"disabled"; ?> />
                                    </td><td> - </td><td>
                                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_6 admission_6_ages text' 
                                    name='admission_6_to' value='<?php echo $_data2['admission']['i5']['ages'][2] ?>' <?php 
									if(!$_data2['admission']['i5'][0] || !$_data2['admission']['i5']['ages'][0]) echo"disabled"; ?> />
                                    </td><td>: </td><td>
                                    <input type='text' placeholder="Price" size='3' class='admission_6 text' name='admission_6_price' 
                                    value='<?php echo $_data2['admission']['i5']['price'][1] ?>'
                                    <?php if(!$_data2['admission']['i5'][0]) echo"disabled"; ?> /></td><td>
                                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_6 text' 
                                    name='admission_6_money' value='<?php echo $_data2['admission']['i5']['price'][2] ?>' <?php 
									if(!$_data2['admission']['i5'][0]) echo"disabled"; ?> />
                                    </td></tr></table>
                                </div>
                                <div style="text-align:left;margin-top:15px;width:100%; <?php if($_data2['admission']['type'][1] != "t")
								 echo"display:none"; ?>" class="at">
                                    <textarea name="admission_text" rows="10" style="width:100%;margin:0 auto;" placeholder="Admission" 
                                    class="text"><?php if(isset($_data2['admission']['text'][1])) echo $_data2['admission']['text'][1]; ?></textarea>
                                </div>
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Notes:</p>
                                	<textarea name="admission_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php 
									if(isset($_data2['admission']['notes'][1])) echo $_data2['admission']['notes'][1]; ?></textarea>
                                </div>
                                <br />
                                
                                <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
	                            <h1 style="color:#fff;">Category &amp; Theme</h1>
                                <p style="text-align:left;margin-top:10px;margin-bottom:0px;">Category:</p>
                                <select name='category' id="category_select" class='text' style="line-height:1.1em;color:inherit;">
                                        <?php category_event_select(); ?>
                                </select> <?php echo"<script>sel_val('category_select', '".$_data2['catthe']['cat'][1]."');</script>"; ?>
                                <p style="text-align:left;margin-top:10px;margin-bottom:0px;">Theme:</p>
                                <select name='theme' id="theme_select" class='text' style="line-height:1.1em;color:inherit;">
                                        <?php theme_event_select(); ?>
                                </select> <?php echo"<script>sel_val('theme_select', '".$_data2['catthe']['the'][1]."');</script>"; ?>
                                <a class="form button" target="_blank" href="index.php?a=catthe_suggest_screen&b=PL" style="margin-top:15px;"
                                onclick="return popup(this.href, 'Suggestion', 700, 500, false);">Suggest new Category/Theme</a>
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Notes:</p>
                                <textarea name="catthe_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php 
								if(isset($_data2['catthe']['notes'][1])) echo $_data2['catthe']['notes'][1]; ?></textarea>
                                </div>
                                <br />
                                
                                <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
	                            <h1 style="color:#fff;">Contact</h1>
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Email:</p>
                                <input type="text" class="text" name="contact_email" placeholder="Email *" 
                                value='<?php if(isset($_data2['email'][1])) echo $_data2['email'][1]; ?>' />
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Phone:</p>
                                <input type="text" class="text" name="contact_phone" placeholder="Phone" 
                                value='<?php if(isset($_data2['phone'][1])) echo $_data2['phone'][1]; ?>' />
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Website:</p>
                                <input type="text" class="text" name="contact_website" placeholder="Website" 
                                value='<?php if(isset($_data2['web'][1])) echo $_data2['web'][1]; ?>' />
                                <p style="text-align:left;margin-top:15px;margin-bottom:0px;">Notes:</p>
                                <textarea name="contact_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php 
								if(isset($_data2['contact_notes'][1]))echo $_data2['contact_notes'][1]; ?></textarea>
                                </div>
                                
                                <input type="hidden" name="id" value="<?php echo $_GET['b'] ?>" />
                                
                                <button class="button form" type='submit' name='a' value='saveEventEdit' style="margin-top:20px;">Save</button>
                                <button class="button form" type='reset' style="margin-top:20px;">Cancel</button>
                            </form>
                        </article>
                    </div>
                    <div id="tabs-ratings" style="width:100%;<?php if(!(isset($_GET['tab']) && $_GET['tab'] == 2)) echo'display:none;'; ?>">
                        <article style="background:rgba(255, 255, 255, .5);">
                            <header>
                                <h1>Ratings</h1>
                                <?php
									echo'<p>No content!</p>'; 		
                                ?>
                            </header>
                        </article>
                    </div>
                    <div id="tabs-options" style="width:100%;<?php if(!(isset($_GET['tab']) && $_GET['tab'] == 3)) echo'display:none;'; ?>">
                        <article style="background:rgba(255, 255, 255, .5);">
                            <header>
                                <div style="width:80%;margin:0 auto;padding:30px 0;">
                            <?php if($act->data1['active'][1] == 1) { echo'
                            <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
                            <form method="post">
                            <h1 style="color:#fff;">Activity deactivation</h1>';global $daerrors, $daerror_strs; if(isset($daerrors)){
							echo'<p style="margin-bottom:-30px;">'; 
							for($i = 0; $i < count($daerrors); $i++){if($i > 0) echo'<br/>'; echo $daerror_strs[$daerrors[$i]];} echo'</p>';}echo'
                            <input type="password" class="text" name="pw" placeholder="Account Password" style="text-align:center;"/>
							<input type="hidden" name="id" value="'.$_GET['b'].'" />
                            <button class="button form" type="submit" name="a" value="deactivateEvent" style="margin-top:20px;">Deactivate</button>
                            </form>
                            </div>'; } else if($act->data1['active'][1] == 2) { echo'
                            <div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
                            <form method="post">
                            <h1 style="color:#fff;">Activity reacvation</h1>';global $aaerrors, $aaerror_strs; if(isset($aaerrors)){
							echo'<p style="margin-bottom:-30px;">'; 
							for($i = 0; $i < count($aaerrors); $i++){if($i > 0) echo'<br/>'; echo $aaerror_strs[$aaerrors[$i]];} echo'</p>';}echo'
							<input type="hidden" name="id" value="'.$_GET['b'].'" />
                            <button class="button form" type="submit" name="a" value="reactivateEvent" style="margin-top:20px;">Activate</button>
                            </form>
                            </div><br />'; } if($act->data1['active'][1] == 2 || $act->data1['active'][1] == 0) { echo'
							<div style="background:rgba(255, 255, 255, .3);padding:15px 50px;">
                            <form method="post">
                            <h1 style="color:#fff;">Delete activity</h1>';global $daerrors, $daerror_strs; if(isset($daerrors)){
							echo'<p style="margin-bottom:-30px;">'; 
							for($i = 0; $i < count($daerrors); $i++){if($i > 0) echo'<br/>'; echo $daerror_strs[$daerrors[$i]];} echo'</p>';}echo'
                            <input type="password" class="text" name="pw" placeholder="Account Password" style="text-align:center;"/>
							<input type="hidden" name="id" value="'.$_GET['b'].'" />
                            <button class="button form" type="submit" name="a" value="deleteEvent" style="margin-top:20px;">Delete</button>
                            </form>
                            </div>'; } /*else {
								echo'No Content!';
							}*/?>
                            </div>
                            </header>
                        </article>
                    </div>
                    <div id="tabs-activation" style="width:100%;<?php if(!(isset($_GET['tab']) && $_GET['tab'] == 4)) echo'display:none;'; ?>">
                        <article style="background:rgba(255, 255, 255, .5);">
                            <header>
                                <p style="padding-top:10px;"><?php echo txt("event-activation-notice"); ?></p>
                                <div style="height:400px;overflow:auto;border:2px solid rgba(255, 255, 255, .4);margin:10px 50px;margin-top:-10px;">
                                <p><?php echo txt("event-activation-agreement"); ?></p></div>
                                <form method="post" action="index.php">
                                	<input type="checkbox" name="paacb" id="paacb" />
                                    <label for="paacb" style="display:inline">I agree!</label><br />
                                    <input type="hidden" name="b" value="<?php echo $_GET['b'] ?>" />
                                    <button class="button form paacb" type='submit' name='a' value='eventActivateOK' style="margin:20px 0;" 
                                    disabled>Activate</button>
                                </form>
                            </header>
                        </article>
                    </div>
                </div>
        </div>
    </div>
</article>

<?php
	footer();
?>