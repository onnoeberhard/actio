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
                <h1 style="color:#fff">Create Event</h1>
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
                <h2>Date &amp; Time</h2>
            </header>
            <div class="row">
                <div class="6u" style="width:50%;text-align:center;">
                	Date &amp; Time<br />
                    <input id="td_nt" type='radio' name='td_type' value="tdnt" <?php if($td_type == "tdnt") echo"checked"; ?> />
                	<label for="td_nt" style="display:inline">Non-Text</label>&emsp;
                    <input id="td_t" type='radio' name='td_type' value="tdt" <?php if($td_type == "tdt") echo"checked"; ?> />
                	<label for="td_t" style="display:inline">Text</label>
                    <div style="text-align:left;margin-top:20px; <?php if($td_type != "tdnt") echo"display:none"; ?>" class="tdnt">
                    	<table cellspacing="10"><tr><td><p>From</p></td></tr><tr><td>
                        <input type='text' placeholder="YYYY-MM-DD" style="text-align:center;"
                        name='td_d_from' class='text' value='<?php echo $td_d_from ?>' /></td><td>
                        <input type='text' placeholder="hh:mm" style="text-align:center;" 
                        name='td_t_from' class='text' value='<?php echo $td_t_from ?>' /></td></tr><tr><td>
                        <p>To</p></td></tr><tr><td>
                        <input type='text' placeholder="YYYY-MM-DD" style="text-align:center;" 
                        name='td_d_to' class='text' value='<?php echo $td_d_to ?>' /></td><td>
                        <input type='text' placeholder="hh:mm" style="text-align:center;" 
                        name='td_t_to' class='text' value='<?php echo $td_t_to ?>' /></td></tr></table>
                    </div>
                    <div style="text-align:left;margin-top:15px; <?php if($td_type != "tdt") echo"display:none"; ?>" class="tdt">
                    	<div class="row half">
                            <div class="12u">
                                <textarea name="td_text" rows="7" placeholder="Date &amp; Time" class="text"><?php echo $td_text; ?></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="6u" style="width:50%;text-align:center;">
                	<input id="cb_oh" type='checkbox' name='cb_oh' <?php if($cb_oh) echo"checked"; ?> />
                	<label for="cb_oh" style="display:inline">Opening Hours</label>
                    <div style="text-align:left;margin-top:15px;">
                        <div class="row half">
                            <div class="12u">
                                <textarea name="oh_text" rows="8" placeholder="Opening Hours" class="cb_oh text"
                                 <?php if(!$cb_oh) echo"disabled"; ?>><?php echo $oh_text; ?></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row half">
                <div class="12u">
                    <textarea name="td_notes" style="min-height:0;" rows="2" placeholder="Notes"><?php echo $td_notes; ?></textarea>
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
                    </td></tr></table>
            	</div>
                <div style="text-align:left;margin-top:15px;width:100%;padding:0; <?php if($a_type != "at") echo"display:none"; ?>" class="at">
                    <div class="row" style="width:100%;margin:0 auto;">
                        <div class="12u" style="width:100%;margin:0 auto;">
                            <textarea name="admission_text" rows="8" style="width:100%;margin:0 auto;" placeholder="Admission" 
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
                                <?php category_event_select(); ?>
                        </select> <?php echo"<script>sel_val('category_select', '".$category."');</script>"; ?> </td></tr><tr><td>
                        <p style="display:inline;">Theme:</p></td><td>
                        <select name='theme' id="theme_select" class='text' style="line-height:1.1em;color:inherit;">
                                <?php theme_event_select(); ?>
                        </select> <?php echo"<script>sel_val('theme_select', '".$theme."');</script>"; ?> </td></tr></table>
                        <a class="form button" target="_blank" href="index.php?a=catthe_suggest_screen&b=EV" 
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
	            <p style="font-size:18px;margin-bottom:10px;">Everything here can be changed later.</p>
            	<p style="font-size:18px;margin-bottom:10px;">After submitting, you must activate your activity</p>
                <button class="button form" type='submit' name='a' value='createActSubmission'>Submit</button>
				<button class="button form" type='submit' name='a' value='cancel'>Cancel</button>
            </header>
        </div>
    </article>
</form>
<?php footer(); ?>