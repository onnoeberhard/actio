$(document).ready(function(){
	$('input[type=radio][name=address_type]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).prop('disabled',false);
		$('input[type=radio][name=address_type]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).prop('disabled',true);
		});
	});
	$('input[type=radio][name=oh_type]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).css('display', 'block');
		$('input[type=radio][name=oh_type]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).css('display', 'none');
		});
	});
	$('input[type=radio][name=a_type]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).css('display', 'block');
		$('input[type=radio][name=a_type]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).css('display', 'none');
		});
	});
	$('input[type=radio][name=m_type]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).css('display', 'block');
		$('input[type=radio][name=m_type]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).css('display', 'none');
		});
	});
	$('input[type=radio][name=pltype]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).css('display', 'block');
		$('input[type=radio][name=pltype]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).css('display', 'none');
		});
	});
	$('input[type=radio][name=td_type]').click(function(){
		var related_class=$(this).val();
		$('.'+related_class).css('display', 'block');
		$('input[type=radio][name=td_type]').not(':checked').each(function(){
			var other_class=$(this).val();
			$('.'+other_class).css('display', 'none');
		});
	});
	$('input[type=checkbox]').click(function(){
		var related_class=$(this).attr('name');
		$('.'+related_class).prop('disabled',!$(this).prop('checked'));
	});
    $('#tabs').tabulous({
    	effect: 'scale'
    });
});
//Das Objekt, das gerade bewegt wird.
var dragobjekt = null;

// Position, an der das Objekt angeklickt wurde.
var dragx = 0;
var dragy = 0;

// Mausposition
var posx = 0;
var posy = 0;


function draginit() {
 // Initialisierung der Überwachung der Events

  document.onmousemove = drag;
  document.onmouseup = dragstop;
}


function dragstart(element) {
   //Wird aufgerufen, wenn ein Objekt bewegt werden soll.

  dragobjekt = element;
  dragx = posx - dragobjekt.offsetLeft;
  dragy = posy - dragobjekt.offsetTop;
}


function dragstop() {
  //Wird aufgerufen, wenn ein Objekt nicht mehr bewegt werden soll.

  dragobjekt=null;
}


function drag(ereignis) {
  //Wird aufgerufen, wenn die Maus bewegt wird und bewegt bei Bedarf das Objekt.

  posx = document.all ? window.event.clientX : ereignis.pageX;
  posy = document.all ? window.event.clientY : ereignis.pageY;
  if(dragobjekt != null) {
	dragobjekt.style.left = (posx - dragx) + "px";
	dragobjekt.style.top = (posy - dragy) + "px";
  }
}
function popup (url, name, width, height, resizable) {
 fenster = window.open(url, name, "width="+width+",height="+height+",resizable="+(resizable ? "yes" : "no"));
 fenster.focus();
 return false;
}

function sel_val(sel, val) {
	return $('#'+sel+' > option[value="'+val+'"]').attr("selected", "selected");
}

function acdelpwcng() {
	if(document.getElementById("acdelpw").value != "") {
		document.getElementById("acdelnotice").style.display = "block";
	} else
		document.getElementById("acdelnotice").style.display = "none";
}