<?php 
	heade('Create Activity');
	if(!empty($errors)) {
		echo'
		<article class="container box style2 noline" style="margin-top:20px;padding-bottom:10px;background:#0ab246">
            <div>
                <header style="background:#0ab246">
                    <!--<h1 style="color:#fff">Errors &amp; Warnings</h2>-->
					';for($i = 0; $i < count($errors); $i++) {
						$error = $error_strs[$errors[$i]];
						if(!(isset($_POST['b']) && $_POST['b'] == "createActSubmitNTL"))
						echo $error . "<br/>";
					}
					if(!in_array(0, $errors) && !in_array(1, $errors) && !(empty($errors) || (isset($_POST['b']) && $_POST['b'] == "createActSubmitNTL"))) {
						$post_keys = array_keys($_POST);
						echo '<br/>
						<form method="post">';
							for($i = 0; $i < count($_POST); $i++)
								echo '<input type="hidden" name="'.$post_keys[$i].'" value="'.$_POST[$post_keys[$i]].'"/>';
							echo'
							<button type="submit" class="button form" name="b" value="createActSubmitNTL">Submit None the Less</button>
						</form>';
					}echo'
				</header>
            </div>
        </article>
        ';
	}
?>
<form method="post">
    <article class="container box style2 <?php if(empty($errors)) echo'noline" style="margin-top:20px;'; ?>">
        <div>
            <header style="background:#0ab246">
                <h1 style="color:#fff">Create Place</h1>
                <div style="margin-top:15px;">
                Which word describes what one does at your place better?
                <table><tr><td>
                <input id="pldo" type='radio' name='pltype' value="pldo" <?php if($pltype == "pldo") echo"checked"; ?> /></td><td>
                <label for="pldo" style="display:inline"> ACTION </label><br /></td></tr><tr><td>
                <input id="pleat" type='radio' name='pltype' value="pleat" <?php if($pltype == "pleat") echo"checked"; ?> /></td><td>
                <label for="pleat" style="display:inline"> EAT/DRINK </label></td></tr></table>
                </div>
            </header>
            <div>
            </div>
        </div>
    </article>
    <article class="container box style3">
        <div>
            <header>
                <h2>General</h2>
            </header>
            <div class="row">
                <div class="6u" style="width:100%;margin:0;"><input type="text" class="text" name="name" placeholder="Name *" 
                value='<?php echo $name; ?>' /></div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="description" placeholder="Description"><?php echo $description; ?></textarea>
                </div>
            </div>
            <div class="row">
                <div class="6u" style="width:100%;margin:0;margin-top:-20px;">
                	<input type="text" class="text" name="img1" placeholder="Image 1 - Link" value='<?php echo $img[0][0]; ?>' style="margin-top:10px;"/>
                    <input type="text" class="text" name="img1title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                    value='<?php echo $img[0][1]; ?>' />
                    <input type="text" class="text" name="img2" placeholder="Image 2 - Link"  value='<?php echo $img[1][0]; ?>' />
                    <input type="text" class="text" name="img2title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                    value='<?php echo $img[1][1]; ?>' />
                    <input type="text" class="text" name="img3" placeholder="Image 3 - Link" value='<?php echo $img[2][0]; ?>' />
                    <input type="text" class="text" name="img3title" placeholder="&rarr; Title" style="width:85%;margin:10px 0;float:right;" 
                    value='<?php echo $img[2][1]; ?>' />
                </div>
            </div>
        </div>
        <p style="font-size:14px;margin-top:15px;margin-bottom:-55px">* These fields have to be filled out.</p>
    </article>
    <article class="container box style3">
        <div>
            <header>
                <h2>Address</h2>
            </header>
            <div class="row half">
                <div class="6u" style="width:50%;text-align:center;">
                	<input type='radio' name='address_type' value='address' id='address_radio_address' <?php if($address_type == "address") echo"checked"; ?> />
                    <label for='address_radio_address' style="display:inline;">Address</label><br />
                    <div style="text-align:left;margin-top:15px;">
                        <input type='text' class="text address" name='address_street' value='<?php echo $address_a['street'] ?>'
						placeholder="Street &amp; House Number" <?php if($address_type != "address") echo"disabled" ?> style="margin-top:10px;"/>
                        <input type='text' class="text address" name='address_city' value='<?php echo $address_a['city'] ?>'
						placeholder="ZIP-Code &amp; City" <?php if($address_type != "address") echo"disabled" ?> style="margin-top:10px;"/>
                        <select name='address_country' id="country_select" class='address text'
						<?php if($address_type != "address")echo"disabled" ?> style="margin-top:10px;line-height:1.1em;color:inherit;">
							<?php create_act_countries(); ?>
                        </select> 
						<?php 
							if($address_a['country'] != "")
								echo"<script>sel_val('country_select', '".$address_a['country']."');</script>";
							else
								echo"<script>sel_val('country_select', '".strtoupper(substr(getenv('HTTP_ACCEPT_LANGUAGE'), 0, 2))."');</script>"; ?> 
                    </div>
                </div>
                <div class="6u" style="width:50%;text-align:center;">
                    <input type='radio' name='address_type' value='coordinates' id='address_radio_coordinates'
                    <?php if($address_type == "coordinates") echo"checked"; ?> />
                    <label for='address_radio_coordinates' style="display:inline;">Coordinates</label><br/>
                    <div style="text-align:left;margin-top:15px;">
                        <input type='text' class="text coordinates" name='address_coordinates' value='<?php echo $address_c; ?>'
						placeholder="Coordinates" <?php if($address_type != "coordinates") echo"disabled"; ?> style="margin-top:10px;"/>
                        <p style="opacity:.5;text-align:center	">Just copy from Google Maps</p>
                    </div>
                </div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="address_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $address_notes; ?></textarea>
                </div>
            </div>
        </div>
    </article>
    <article class="container box style3">
        <div>
            <header>
                <h2>Opening Hours &amp; Seasons</h2>
            </header>
            <div class="row">
                <div class="6u" style="width:50%;text-align:center;">
                	<input id="cb_seasons" type='checkbox' name='cb_seasons' <?php if($cb_seasons) echo"checked"; ?> />
                	<label for="cb_seasons" style="display:inline">Seasons</label>
                    <div style="text-align:left;margin-top:15px;">
                        <div class="row half">
                            <div class="12u">
                                <textarea name="seasons" rows="12" placeholder="Seasons" class="cb_seasons text"
                                 <?php if(!$cb_seasons) echo"disabled"; ?>><?php echo $seasons; ?></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="6u" style="width:50%;text-align:center;">
                	Opening Hours<br />
                    <input id="oh_nt" type='radio' name='oh_type' value="ohnt" <?php if($oh_type == "ohnt") echo"checked"; ?> />
                	<label for="oh_nt" style="display:inline">Non-Text</label>&emsp;
                    <input id="oh_t" type='radio' name='oh_type' value="oht" <?php if($oh_type == "oht") echo"checked"; ?> />
                	<label for="oh_t" style="display:inline">Text</label>
                    <div style="text-align:left;margin-top:15px; <?php if($oh_type != "ohnt") echo"display:none"; ?>" class="ohnt">
                    	<table><tr><td>
                            <input id="cb_opn_mo" type='checkbox' name='open_mo' <?php if($opening_hours['mo'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_mo">Mo:</label>
                            </td><td><input type='text' maxlength='2' size='2' class='open_mo text' name='opening_hours_mo_hr_from' 
                            value='<?php echo $opening_hours['mo'][1]; ?>' <?php if(!$opening_hours['mo'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_mo text' name='opening_hours_mo_min_from' 
                            value='<?php echo $opening_hours['mo'][2]; ?>' <?php if(!$opening_hours['mo'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_mo text' name='opening_hours_mo_hr_to' 
                            value='<?php echo $opening_hours['mo'][3] ?>' <?php if(!$opening_hours['mo'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_mo text' name='opening_hours_mo_min_to' 
                            value='<?php echo $opening_hours['mo'][4] ?>' <?php if(!$opening_hours['mo'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_tu" type='checkbox' name='open_tu' <?php if($opening_hours['tu'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_tu">Tu:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_tu text' name='opening_hours_tu_hr_from' 
                            value='<?php echo $opening_hours['tu'][1]; ?>' <?php if(!$opening_hours['tu'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_tu text' name='opening_hours_tu_min_from' 
                            value='<?php echo $opening_hours['tu'][2]; ?>' <?php if(!$opening_hours['tu'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_tu text' name='opening_hours_tu_hr_to' 
                            value='<?php echo $opening_hours['tu'][3] ?>' <?php if(!$opening_hours['tu'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_tu text' name='opening_hours_tu_min_to' 
                            value='<?php echo $opening_hours['tu'][4] ?>' <?php if(!$opening_hours['tu'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_we" type='checkbox' name='open_we' <?php if($opening_hours['we'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_we">We:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_we text' name='opening_hours_we_hr_from' 
                            value='<?php echo $opening_hours['we'][1]; ?>' <?php if(!$opening_hours['we'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_we text' name='opening_hours_we_min_from' 
                            value='<?php echo $opening_hours['we'][2]; ?>' <?php if(!$opening_hours['we'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_we text' name='opening_hours_we_hr_to' 
                            value='<?php echo $opening_hours['we'][3] ?>' <?php if(!$opening_hours['we'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_we text' name='opening_hours_we_min_to' 
                            value='<?php echo $opening_hours['we'][4] ?>' <?php if(!$opening_hours['we'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_th" type='checkbox' name='open_th' <?php if($opening_hours['th'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_th">Th:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_th text' name='opening_hours_th_hr_from' 
                            value='<?php echo $opening_hours['th'][1]; ?>' <?php if(!$opening_hours['th'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_th text' name='opening_hours_th_min_from' 
                            value='<?php echo $opening_hours['th'][2]; ?>' <?php if(!$opening_hours['th'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_th text' name='opening_hours_th_hr_to' 
                            value='<?php echo $opening_hours['th'][3] ?>' <?php if(!$opening_hours['th'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_th text' name='opening_hours_th_min_to' 
                            value='<?php echo $opening_hours['th'][4] ?>' <?php if(!$opening_hours['th'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_fr" type='checkbox' name='open_fr' <?php if($opening_hours['fr'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_fr">Fr:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_fr text' name='opening_hours_fr_hr_from' 
                            value='<?php echo $opening_hours['fr'][1]; ?>' <?php if(!$opening_hours['fr'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_fr text' name='opening_hours_fr_min_from' 
                            value='<?php echo $opening_hours['fr'][2]; ?>' <?php if(!$opening_hours['fr'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_fr text' name='opening_hours_fr_hr_to' 
                            value='<?php echo $opening_hours['fr'][3] ?>' <?php if(!$opening_hours['fr'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_fr text' name='opening_hours_fr_min_to' 
                            value='<?php echo $opening_hours['fr'][4] ?>' <?php if(!$opening_hours['fr'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_sa" type='checkbox' name='open_sa' <?php if($opening_hours['sa'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_sa">Sa:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_sa text' name='opening_hours_sa_hr_from' 
                            value='<?php echo $opening_hours['sa'][1]; ?>' <?php if(!$opening_hours['sa'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_sa text' name='opening_hours_sa_min_from' 
                            value='<?php echo $opening_hours['sa'][2]; ?>' <?php if(!$opening_hours['sa'][0]) echo"disabled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_sa text' name='opening_hours_sa_hr_to' 
                            value='<?php echo $opening_hours['sa'][3] ?>' <?php if(!$opening_hours['sa'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_sa text' name='opening_hours_sa_min_to' 
                            value='<?php echo $opening_hours['sa'][4] ?>' <?php if(!$opening_hours['sa'][0]) echo"disabled"; ?>
                            style="text-align:center;"/></td></tr><tr><td>
                            <input id="cb_opn_su" type='checkbox' name='open_su' <?php if($opening_hours['su'][0]) echo"checked"; ?> /></td><td>
                            <label for="cb_opn_su">Su:</label> 
                            </td><td><input type='text' maxlength='2' size='2' class='open_su text' name='opening_hours_su_hr_from' 
                            value='<?php echo $opening_hours['su'][1]; ?>' <?php if(!$opening_hours['su'][0]) echo"disubled"; ?> 
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_su text' name='opening_hours_su_min_from' 
                            value='<?php echo $opening_hours['su'][2]; ?>' <?php if(!$opening_hours['su'][0]) echo"disubled"; ?> 
                            style="text-align:center;"/></td><td> - </td><td>
                            <input type='text' maxlength='2' size='2' class='open_su text' name='opening_hours_su_hr_to' 
                            value='<?php echo $opening_hours['su'][3] ?>' <?php if(!$opening_hours['su'][0]) echo"disubled"; ?>
                            style="text-align:center;"/></td><td> : </td><td>
                            <input type='text' maxlength='2' size='2' class='open_su text' name='opening_hours_su_min_to' 
                            value='<?php echo $opening_hours['su'][4] ?>' <?php if(!$opening_hours['su'][0]) echo"disubled"; ?>
                            style="text-align:center;"/></td></tr>
                            </table>
                    </div>
                    <div style="text-align:left;margin-top:15px; <?php if($oh_type != "oht") echo"display:none"; ?>" class="oht">
                    	<div class="row half">
                            <div class="12u">
                                <textarea name="oh_text" rows="11" placeholder="Opening Hours" class="text"><?php echo $opening_hours_text; ?></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="ohs_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $ohs_notes; ?></textarea>
                </div>
            </div>
        </div>
    </article>
    <article class="container box style3 pldo" style=" <?php if($pltype != "pldo") echo"display:none"; ?> ">
        <div>
            <header>
                <h2>Admission</h2>
                <input id="a_nt" type='radio' name='a_type' value="ant" <?php if($a_type == "ant") echo"checked"; ?> />
                <label for="a_nt" style="display:inline">Non-Text</label>&emsp;
                <input id="a_t" type='radio' name='a_type' value="at" <?php if($a_type == "at") echo"checked"; ?> />
                <label for="a_t" style="display:inline">Text</label>
            </header>
            <div class="row">
            	<div style="width:100%; <?php if($a_type != "ant") echo"display:none"; ?>" class="ant">
                    <table hspace="5px"><tr><td>
                    <input type='checkbox' name='admission_1' <?php if($admission[0][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_1_name' class='admission_1 text' value='<?php echo $admission[0][1] ?>' 
                    <?php if(!$admission[0][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_1_ages' class='admission_1'
                    <?php if($admission[0][2]) echo" checked"; if(!$admission[0][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_1 admission_1_ages text' 
                    name='admission_1_from' value='<?php echo $admission[0][3] ?>' <?php if(!$admission[0][0] || !$admission[0][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_1 admission_1_ages text' 
                    name='admission_1_to' value='<?php echo $admission[0][4] ?>' <?php if(!$admission[0][0] || !$admission[0][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_1 text' name='admission_1_price' value='<?php echo $admission[0][5] ?>'
                    <?php if(!$admission[0][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_1 text' 
                    name='admission_1_money' value='<?php echo $admission[0][6] ?>' <?php if(!$admission[0][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_2' <?php if($admission[1][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_2_name' class='admission_2 text' value='<?php echo $admission[1][1] ?>' 
                    <?php if(!$admission[1][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_2_ages' class='admission_2'
                    <?php if($admission[1][2]) echo" checked"; if(!$admission[1][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_2 admission_2_ages text' 
                    name='admission_2_from' value='<?php echo $admission[1][3] ?>' <?php if(!$admission[1][0] || !$admission[1][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_2 admission_2_ages text' 
                    name='admission_2_to' value='<?php echo $admission[1][4] ?>' <?php if(!$admission[1][0] || !$admission[1][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_2 text' name='admission_2_price' value='<?php echo $admission[1][5] ?>'
                    <?php if(!$admission[1][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_2 text' 
                    name='admission_2_money' value='<?php echo $admission[1][6] ?>' <?php if(!$admission[1][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_3' <?php if($admission[2][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_3_name' class='admission_3 text' value='<?php echo $admission[2][1] ?>' 
                    <?php if(!$admission[2][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_3_ages' class='admission_3'
                    <?php if($admission[2][2]) echo" checked"; if(!$admission[2][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_3 admission_3_ages text' 
                    name='admission_3_from' value='<?php echo $admission[2][3] ?>' <?php if(!$admission[2][0] || !$admission[2][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_3 admission_3_ages text' 
                    name='admission_3_to' value='<?php echo $admission[2][4] ?>' <?php if(!$admission[2][0] || !$admission[2][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_3 text' name='admission_3_price' value='<?php echo $admission[2][5] ?>'
                    <?php if(!$admission[2][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_3 text' 
                    name='admission_3_money' value='<?php echo $admission[2][6] ?>' <?php if(!$admission[2][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_4' <?php if($admission[3][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_4_name' class='admission_4 text' value='<?php echo $admission[3][1] ?>' 
                    <?php if(!$admission[3][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_4_ages' class='admission_4'
                    <?php if($admission[3][2]) echo" checked"; if(!$admission[3][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_4 admission_4_ages text' 
                    name='admission_4_from' value='<?php echo $admission[3][3] ?>' <?php if(!$admission[3][0] || !$admission[3][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_4 admission_4_ages text' 
                    name='admission_4_to' value='<?php echo $admission[3][4] ?>' <?php if(!$admission[3][0] || !$admission[3][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_4 text' name='admission_4_price' value='<?php echo $admission[3][5] ?>'
                    <?php if(!$admission[3][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_4 text' 
                    name='admission_4_money' value='<?php echo $admission[3][6] ?>' <?php if(!$admission[3][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_5' <?php if($admission[4][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_5_name' class='admission_5 text' value='<?php echo $admission[4][1] ?>' 
                    <?php if(!$admission[4][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_5_ages' class='admission_5'
                    <?php if($admission[4][2]) echo" checked"; if(!$admission[4][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_5 admission_5_ages text' 
                    name='admission_5_from' value='<?php echo $admission[4][3] ?>' <?php if(!$admission[4][0] || !$admission[4][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_5 admission_5_ages text' 
                    name='admission_5_to' value='<?php echo $admission[4][4] ?>' <?php if(!$admission[4][0] || !$admission[4][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_5 text' name='admission_5_price' value='<?php echo $admission[4][5] ?>'
                    <?php if(!$admission[4][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_5 text' 
                    name='admission_5_money' value='<?php echo $admission[4][6] ?>' <?php if(!$admission[4][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_6' <?php if($admission[5][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_6_name' class='admission_6 text' value='<?php echo $admission[5][1] ?>' 
                    <?php if(!$admission[5][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_6_ages' class='admission_6'
                    <?php if($admission[5][2]) echo" checked"; if(!$admission[5][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_6 admission_6_ages text' 
                    name='admission_6_from' value='<?php echo $admission[5][3] ?>' <?php if(!$admission[5][0] || !$admission[5][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_6 admission_6_ages text' 
                    name='admission_6_to' value='<?php echo $admission[5][4] ?>' <?php if(!$admission[5][0] || !$admission[5][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_6 text' name='admission_6_price' value='<?php echo $admission[5][5] ?>'
                    <?php if(!$admission[5][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_6 text' 
                    name='admission_6_money' value='<?php echo $admission[5][6] ?>' <?php if(!$admission[5][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_7' <?php if($admission[6][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_7_name' class='admission_7 text' value='<?php echo $admission[6][1] ?>' 
                    <?php if(!$admission[6][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_7_ages' class='admission_7'
                    <?php if($admission[6][2]) echo" checked"; if(!$admission[6][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_7 admission_7_ages text' 
                    name='admission_7_from' value='<?php echo $admission[6][3] ?>' <?php if(!$admission[6][0] || !$admission[6][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_7 admission_7_ages text' 
                    name='admission_7_to' value='<?php echo $admission[6][4] ?>' <?php if(!$admission[6][0] || !$admission[6][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_7 text' name='admission_7_price' value='<?php echo $admission[6][5] ?>'
                    <?php if(!$admission[6][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_7 text' 
                    name='admission_7_money' value='<?php echo $admission[6][6] ?>' <?php if(!$admission[6][0]) echo"disabled"; ?> />
                    </td></tr><tr><td>
                    <input type='checkbox' name='admission_8' <?php if($admission[7][0]) echo"checked"; ?> /></td><td>
                    <input type='text' placeholder="Label" name='admission_8_name' class='admission_8 text' value='<?php echo $admission[7][1] ?>' 
                    <?php if(!$admission[7][0]) echo"disabled"; ?> /></td><td>: </td><td>
                    <input type='checkbox' name='admission_8_ages' class='admission_8'
                    <?php if($admission[7][2]) echo" checked"; if(!$admission[7][0]) echo" disabled"; ?> /></td><td>
                    <input type='text' placeholder="Age from" maxlength='2' size='4' class='admission_8 admission_8_ages text' 
                    name='admission_8_from' value='<?php echo $admission[7][3] ?>' <?php if(!$admission[7][0] || !$admission[7][2]) echo"disabled"; ?> />
                    </td><td> - </td><td>
                    <input type='text' placeholder="Age to" maxlength='2' size='4' class='admission_8 admission_8_ages text' 
                    name='admission_8_to' value='<?php echo $admission[7][4] ?>' <?php if(!$admission[7][0] || !$admission[7][2]) echo"disabled"; ?> />
                    </td><td>: </td><td>
                    <input type='text' placeholder="Price" size='3' class='admission_8 text' name='admission_8_price' value='<?php echo $admission[7][5] ?>'
                    <?php if(!$admission[7][0]) echo"disabled"; ?> /></td><td>
                    <input type='text' placeholder="$/&euro;/&yen;.." maxlength='1' size='3' class='admission_8 text' 
                    name='admission_8_money' value='<?php echo $admission[7][6] ?>' <?php if(!$admission[7][0]) echo"disabled"; ?> />
                    </td></tr></table>
            	</div>
                <div style="text-align:left;margin-top:15px;width:100%;padding:0; <?php if($a_type != "at") echo"display:none"; ?>" class="at">
                    <div class="row" style="width:100%;margin:0 auto;">
                        <div class="12u" style="width:100%;margin:0 auto;">
                            <textarea name="admission_text" rows="10" style="width:100%;margin:0 auto;" placeholder="Admission" 
                            class="text"><?php echo $admission_text; ?></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="admission_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $admission_notes; ?></textarea>
                </div>
            </div>
        </div>
    </article>
    <article class="container box style3 pleat" style=" <?php if($pltype != "pleat") echo"display:none"; ?> ">
        <div>
            <header>
                <h2>Menu</h2>
                <input id="m_t" type='radio' name='m_type' value="mt" <?php if($m_type == "mt") echo"checked"; ?> />
                <label for="m_t" style="display:inline">Text</label>&emsp;
                <input id="m_p" type='radio' name='m_type' value="mp" <?php if($m_type == "mp") echo"checked"; ?> />
                <label for="m_p" style="display:inline">Picture</label>
            </header>
            <div class="row">
            	<div style="text-align:left;margin-top:15px;width:100%;padding:0; <?php if($m_type != "mt") echo"display:none"; ?>" class="mt">
	                <div class="row" style="width:100%;margin:0 auto;">
                        <div class="12u" style="width:100%;margin:0 auto;">
                            <textarea name="menu_text" rows="10" style="width:100%;margin:0 auto;" placeholder="Menu" 
                            class="text"><?php echo $menu_text; ?></textarea>
                        </div>

                    </div>
            	</div>
                <div style="text-align:left;margin-top:15px;width:100%; <?php if($m_type != "mp") echo"display:none"; ?>" class="mp">
                    <input type="text" class="text" name="mpic1" placeholder="Menu-Image 1 - Link" value='<?php echo $menu_pics[0]; ?>' style="margin-top:10px;"/>
                    <input type="text" class="text" name="mpic2" placeholder="Menu-Image 2 - Link" value='<?php echo $menu_pics[1]; ?>' style="margin-top:10px;"/>
                    <input type="text" class="text" name="mpic3" placeholder="Menu-Image 3 - Link" value='<?php echo $menu_pics[2]; ?>' style="margin-top:10px;"/>
                    <input type="text" class="text" name="mpic4" placeholder="Menu-Image 4 - Link" value='<?php echo $menu_pics[3]; ?>' style="margin-top:10px;"/>
                </div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="menu_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $menu_notes; ?></textarea>
                </div>
            </div>
        </div>
    </article>
    <article class="container box style3">
        <div>
            <header>
                <h2>Category &amp; Theme</h2>
            </header>
            <div class="row">
            	<div class="12u" style="text-align:center">
                	<div>
                    	<table cellspacing="15px"><tr><td width="20%">
                        <p style="display:inline;">Category:</p></td><td width="80%">
                        <select name='category' id="category_select" class='text' style="line-height:1.1em;color:inherit;">
                                <?php category_place_select(); ?>
                        </select> <?php echo"<script>sel_val('category_select', '".$category."');</script>"; ?> </td></tr><tr><td>
                        <p style="display:inline;">Theme:</p></td><td>
                        <select name='theme' id="theme_select" class='text' style="line-height:1.1em;color:inherit;">
                                <?php theme_place_select(); ?>
                        </select> <?php echo"<script>sel_val('theme_select', '".$theme."');</script>"; ?> </td></tr></table>
                        <a class="form button" target="_blank" href="index.php?a=catthe_suggest_screen&b=PL" 
                        onclick="return popup(this.href, 'Suggestion', 700, 500, false);">Suggest new Category/Theme</a>
                    </div>
				</div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="catthe_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $catthe_notes; ?></textarea>
                </div>
            </div>
        </div>
    </article>
    <article class="container box style3">
        <div>
            <header>
                <h2>Contact</h2>
            </header>
            <div class="row">
                <div class="6u" style="width:100%;margin-top:15px;"><input type="text" class="text" name="contact_email" placeholder="Email *" 
                value='<?php echo $email; ?>' /></div>
                <div class="6u" style="width:100%;margin-top:15px;"><input type="text" class="text" name="contact_phone" placeholder="Phone" 
                value='<?php echo $phone; ?>' /></div>
                <div class="6u" style="width:100%;margin-top:15px;"><input type="text" class="text" name="contact_website" placeholder="Website" 
                value='<?php echo $website; ?>' /></div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="contact_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $contact_notes; ?></textarea>
                </div>
            </div>
            <p style="font-size:14px;margin-top:15px;margin-bottom:-55px">* These fields have to be filled out.</p>
        </div>
    </article>
    <article class="container box style2">
         <div>
			<!--<div onmousedown="dragstart(this)" style="z-index:1000;cursor:move;position:absolute;top:0px;left:0px;height:100px;width:100px;background:#f00">
            </div>-->
            <header style="background:#0ab246;">
	            <p style="font-size:18px;margin-bottom:10px;">Everything here except the type (ACTION / EAT/DRINK) can be changed later.</p>
            	<p style="font-size:18px;margin-bottom:10px;">After submitting, you must activate your activity</p>
                <button class="button form" type='submit' name='a' value='createActSubmission'>Submit</button>
				<button class="button form" type='submit' name='a' value='cancel'>Cancel</button>
            </header>
        </div>
    </article>
</form>
<?php footer(); ?>