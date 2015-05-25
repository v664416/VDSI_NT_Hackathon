<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ADMIN PAGE</title>
  <link rel="stylesheet" href="../sample.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="pageWrapper">
<div class="content">
<div class="buttonDiv">
<button class=btn id="esUpdate">Upload All Data to ES</button>
</div>
<div class="clear"></div>
<div class="buttonDiv">
<button class=btn id="startTimer">Start ES Timer</button>
</div>
<div class="clear"></div>
<div class="buttonDiv">
<button class=btn id="stopTimer">Stop ES Timer</button>
</div>
<div class="clear"></div>
<div class="buttonDiv">
<button class=btn id="dbupdate">Update DB with new Catalog</button>
</div>
<div class="clear"></div>
<div class="Response">

</div>
</div>

</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.btn').click( function()
	        {
	          
	          var path=$( this ).attr("id");
	          $.get("admin/"+path, function(data, status){
	        	  $('.Response').html("Request Processed...!!!");
	      	    });
	      	    return false;
	        }
	     );
});

</script>
</body>
</html>