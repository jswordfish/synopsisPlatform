var app = angular.module('myApp', []);


        
app.controller( 'reportCompleteAnalysisController',  function($scope, $http, $window, $filter) {

	var loggedIn = $window.localStorage.getItem("loggedIn");
	console.log('loggedIn is '+loggedIn);
		if(loggedIn == 'yes'){
			console.log('user logged '+loggedIn);
		}
		else{
			$window.location.href = 'login.html';	
		}
	
	
	var tenderNo = $window.localStorage.getItem('tenderUniqueNo');
	tenderNo = window.encodeURIComponent(tenderNo );
	var url = 'ws/rest/bidderService/listCompleteAnalysisForTender/tenderUniqueNo/'+tenderNo+'/token/temp';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.compAnalysisData = data;
		    computeTotal(tenderNo);
		   
	    });
	   
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
		
	   
	   var computeTotal = function(tenderNo){
		   var vendorNames = '';
		   for(var i=0;i<$scope.compAnalysisData[0].bidItems.length;i++){
			   if(i == $scope.compAnalysisData[0].bidItems.length -1){
				   vendorNames += $scope.compAnalysisData[0].bidItems[i].vendorName; 
			   }
			   else{
				   vendorNames += $scope.compAnalysisData[0].bidItems[i].vendorName+','; 
			   }
		   }
		   vendorNames = window.encodeURIComponent(vendorNames );
		   
		   var url = 'ws/rest/bidderService/getVendorBids/vendorNames/'+vendorNames+'/tenderNo/'+tenderNo;
			console.log(url);
			   $http.get(url).
			    success(function(data) {
					console.log('here ');
				    $scope.vendorAggregates = data;
				   
			    });
		}
		
	    
 });


 
