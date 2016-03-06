var app = angular.module('myApp', []);

        
app.controller( 'vendorBidController',  function($scope, $http, $window, $filter) {

	 var loggedIn = $window.localStorage.getItem("loggedIn");
		console.log('loggedIn is '+loggedIn);
			if(loggedIn == 'yes'){
				console.log('user logged '+loggedIn);
			}
			else{
				$window.location.href = 'login.html';	
			}	
	
	var user = $window.localStorage.getItem("vendorEmail");
	user = window.encodeURIComponent(user );
	if(user == '' || user == null){
		alert('Something wrong. User exists but vendor doesnt. Contact Admin');
		$window.location.href = 'login.html';
	}
	
	
	var url = 'ws/rest/bidderService/vendor/email/'+user;
	console.log(url);
	
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.vendor = data;
		    var ven = JSON.stringify($scope.vendor);
		    console.log(' vendor got is '+ven);
			    if(data == '' || data ==null){
				alert('Something wrong. User exists but vendor doesnt. Contact Admin');
					$window.location.href = 'login.html';
			    }
		    
			    var vName =  window.encodeURIComponent($scope.vendor.name );
				url = 'ws/rest/bidderService/listTendersVendorsCanBidFor/vendorName/'+vName+'/token/test'; 
				console.log(' url to fetch tenders '+url);
					$http.get(url).
				    success(function(data) {
						console.log('here ');
					    $scope.tenders = data;
					    
					    
					   
				    });
			
	    });
	   
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
		
		$scope.goToBidForTenderPage = function(vendorName, tender) {
			console.log('vendor name is '+vendorName);
			$window.localStorage.setItem("vendorName", vendorName);
			$window.localStorage.setItem("tenderUniqueNo", tender.tenderUniqueId);
			$window.location.href = 'VendorBidPage.html';
		};
		
		
	
		
	    
 });
 
 
app.controller( 'bidController',  function($scope, $http, $window, $filter) {
	
 var loggedIn = $window.localStorage.getItem("loggedIn");
	console.log('loggedIn is '+loggedIn);
		if(loggedIn == 'yes'){
			console.log('user logged '+loggedIn);
		}
		else{
			$window.location.href = 'login.html';	
		}	
	
	var vendorName = window.localStorage.getItem("vendorName");
	var tenderNo = window.localStorage.getItem("tenderUniqueNo");
	
	var url = 'ws/rest/bidderService/getVendorBid/vendorName/'+vendorName+'/tenderNo/'+tenderNo;
	console.log(url);
	
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.vendorBid = data;
		    $scope.bidItems = $scope.vendorBid.bidItems;
		   console.log('bid items '+JSON.stringify($scope.bidItems));
	    });
	    
		$scope.updateBidItem = function(bidItem) {
			var url = 'ws/rest/bidderService/updateBidItemVendorBid/token/test';
				$http.post(url, bidItem).
			    success(function(data) {
			    	$window.location.href = 'VendorLandingPage.html';
				   
			    });
		
		
			
		};
		
		$scope.logOff = function() { 

			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'login.html';

			  };
		
		//calculateTotalAmount
		$scope.calculateTotalAmount = function(bidItem) {
			
			bidItem.amount = bidItem.rate * bidItem.totalQuantity;
		
			
		};
 });
           
           
        