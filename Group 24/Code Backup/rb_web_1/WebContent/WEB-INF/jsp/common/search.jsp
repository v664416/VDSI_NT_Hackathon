<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <script src= "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
</head>
<body>

<div class="container"  ng-app="myApp"  ng-controller="customersCtrl">
  <h2>Rongu-Bombu Inc Online library</h2>
  <form role="form">
    <div class="form-group">
      <input type="text" class="form-control" name="q" ng-model="serach" id="q" placeholder="Search Library">
    </div>
   <a href="#" id="srh" class="btn btn-info btn-lg" ng-click="doSearch()"><span class="glyphicon glyphicon-search"></span> Search</a>
      <div class="table-responsive"></div>
      <div class="container-fluid"  ng-repeat="x in names">
		  <h4>{{ x.book_name }}</h4>
		  <p>{{ x.auth_name }}</p>  
		  <p>{{ x.publisher }}</p>    
		  <p>{{ x.category }}</p>        
	  </div>
      <ul class="pagination">
		  <li><a href="#">&laquo;</a></li>
		  <li class="active"><a href="#">1</a></li>
		  <li class="disabled"><a href="#">2</a></li>
		  <li><a href="#">3</a></li>
		  <li><a href="#">4</a></li>
		  <li><a href="#">5</a></li>
		  <li><a href="#">&raquo;</a></li>
	  </ul>
  </form>
</div>


 <script type="text/javascript">
  	/* $("#srh").click(function(){
  		var qval = $("#q").val();
  		if(qval == ""){
  			return false;
  		}
  		$('.table-responsive').html("Searching.....");
  	    $.get("search/query?q="+qval, function(data, status){
  	      $('.table-responsive').html(data);
  	    });
  	    return false;
  	}); */
  	var app = angular.module('myApp', []);
  	app.controller('customersCtrl', function($scope, $http) {
  		$scope.doSearch=function() {
  			var qval = $scope.serach;
  	  		 if(qval == ""){
  	  			return false;
  	  		}
  	  		/* $('.table-responsive').html("Searching.....");
  	  	    $.get("search/query?q="+qval, function(data, status){
  	  	      $('.table-responsive').html(data);
  	  	    }); */
  			$http.get("search/query?q="+qval)
  	  	    .success(function (response) {$scope.names = response});
  	    };
  	   /*  $http.get("http://www.w3schools.com/angular/customers.php")
  	    .success(function (response) {$scope.names = response.records;}); */
  	});
  	</script>

</body>
</html>
