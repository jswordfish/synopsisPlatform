var app = angular.module('myApp', []);



app.service('VendorService', function() {
     var currentVendor = '';
    
    var setCurrentVendor = function(vendor) {
		currentVendor = vendor;
    };
     
    var resetCurrentVendor = function() { 
    	currentVendor = "";
    };
     
    var getCurrentVendor = function() { 
    	return currentVendor;
    };
     
    return {
	    setCurrentVendor: setCurrentVendor,
	    resetCurrentVendor: resetCurrentVendor,
	    getCurrentVendor: getCurrentVendor
	  };
  
});
        
app.controller( 'vendorsController',  function($scope, $http, $window, $filter,  VendorService) {

	app.filter('urlencode', function() {
	  return function(input) {
	    return window.encodeURIComponent(input);
	  }
	});

	 var loggedIn = $window.localStorage.getItem("loggedIn");
		console.log('loggedIn is '+loggedIn);
			if(loggedIn == 'yes'){
				console.log('user logged '+loggedIn);
			}
			else{
				$window.location.href = 'login.html';	
			}
	
	var url = 'ws/rest/bidderService/vendors/token/temp';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.vendors = data;
		   
	    });
	
		
	
		$scope.editVendor = function(vendor) {
			console.log('in edit vendor');
			$window.localStorage.setItem("vendor", JSON.stringify(vendor) ); 
			$window.location.href = 'vendorForm.html';	
		};
		
		
		$scope.deleteVendor = function(vendor) {
		
			var name = vendor.name;
			name = window.encodeURIComponent(name);
			console.log('vendor name '+name);
			
			var url = 'ws/rest/bidderService/deleteVendor/vendorName/'+name+'/token/{token}';
			console.log('url '+url);
				$http.post(url, null).
			    success(function(data) {
			    $window.location.reload();
				 $window.localStorage.setItem("vendor", '');   
			    });
		};
		
		$scope.addVendor = function() {
			
			 $window.localStorage.setItem("vendor", '');   
			$window.location.href = 'vendorForm.html';	
			
		};
	
		$scope.logOff = function() { 

			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'login.html';

			  };	
	    
 });
 
 
 app.controller( 'vendorController',  function($scope, $http, $window, $filter, VendorService) {
	 var loggedIn = $window.localStorage.getItem("loggedIn");
		console.log('loggedIn is '+loggedIn);
			if(loggedIn == 'yes'){
				console.log('user logged '+loggedIn);
			}
			else{
				$window.location.href = 'login.html';	
			}	
	 
	 var vendor = window.localStorage.getItem("vendor");
		if(vendor != '' ){
			$scope.vendor = jQuery.parseJSON(vendor);
			$scope.disableIt = true;
		}
		else{
		$scope.disableIt = false;
		}
	
	
	 console.log('here in vendor form '+ $scope.vendor );
		$scope.saveVendor = function(ven) {
			var url = 'ws/rest/bidderService/createUpdateVendor/token/temp';
			$http.post(url, ven).
		    success(function(data) {
			    $scope.vendorResponse = data;
			    VendorService.resetCurrentVendor();
			$window.location.href = 'vendors.html';
			   
			})
		     .error(function(data) {
			   alert('There seem to be some problem at the server. One of te reasons may be the email you may have used is already registered ');
			   
			});
	
		};
	    
		$scope.goBack = function() {
			
			VendorService.resetCurrentVendor();
		
			$window.location.href = 'vendors.html';
		};
		
		$scope.logOff = function() { 

			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'login.html';

			  };
 });
           
        