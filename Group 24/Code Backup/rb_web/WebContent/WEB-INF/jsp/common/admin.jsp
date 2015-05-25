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
<div class="buttonDiv">
<a href="http://localhost:5601/#/" target="_blank">Cluster Dashboard</a>
</div>
<div class="buttonDiv">
<a href="http://localhost:9200/_plugin/marvel/" target="_blank">Cluster Status</a>
</div>
<div class="buttonDiv">
<a href="admin/getAddPage" target="_blank">AddNewBook</a>
</div>

<div class="buttonDiv">
<a href="admin/getAdminSearchPage" target="_blank">Add/Update Records</a>
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