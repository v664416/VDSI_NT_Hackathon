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
    <div class="checkbox">
	  <label><input type="checkbox" value="" ng-model="pageshow">PageResult</label>
	</div>
    <div class="form-group" ng-show="pageshow">
    	<input type="text" value="1" class="form-control" name="page" ng-model="page" id="page" placeholder="Enter Page Number">
    </div>
    <a href="#" id="srh" class="btn btn-info btn-lg" ng-click="doSearch()"><span class="glyphicon glyphicon-search"></span> Search</a>
   <br/><span ng-show="shwtxt" style="color:green;font-weight:bold;">ResponseTime: <span ng-bind="restime"></span> MilliSeconds, ResultsCount: <span ng-bind="rescont"></span></span>
      <div class="table-responsive"></div>
      <div class="container-fluid"  ng-repeat="x in names">
		  <h4><a herf="#">{{ x.book_name }}</a></h4>
		  <p>Author : {{ x.auth_name }}</p>  
		  <p>Publisher : {{ x.publisher }}</p>    
		  <p>Category : {{ x.category }}</p>
		  <hr/>        
	  </div>
      <!-- ul class="pagination">
		  <li><a href="#">&laquo;</a></li>
		  <li class="active"><a href="#">1</a></li>
		  <li class="disabled"><a href="#">2</a></li>
		  <li><a href="#">3</a></li>
		  <li><a href="#">4</a></li>
		  <li><a href="#">5</a></li>
		  <li><a href="#">&raquo;</a></li>
	  </ul-->
  </form>
</div>


 <script type="text/javascript">
  	var app = angular.module('myApp', []);
  	app.controller('customersCtrl', function($scope, $http) {
  		$scope.doSearch=function() {
  			var qval = $scope.serach;
  	  		if(qval == null || qval == "" || qval == "undefined" || qval == undefined){
  	  			$("#q").focus();
  	  			return false;
  	  		}
  	  	   $("body, #q, #srh").css("cursor", "progress");
  	  		var pageVal = "1";
  	  	    if($scope.pageshow){
  	  	    	pageVal = $scope.page;
  	  	    }
  			$http.get("search/query?q="+qval+"&page="+pageVal, false)
  	  	    .success(function (response) {
  	  	    	$scope.names = response.result; 
  	  	    	$scope.restime = response.ResponseTime;
  	  			$scope.rescont = response.Count;
  	  			$scope.shwtxt=true;
  	  		    $("body, #q, #srh").css("cursor", "default");
  	  			$("#srh").css("cursor", "pointer");
  	  			$("#q").css("cursor", "text");
  	  	    });
  	    };
  	  $scope.shwtxt=false;
  	});
  	</script>

</body>
</html>
