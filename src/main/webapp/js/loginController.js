var app = angular.module('myApp', []);


        
app.controller( 'loginController',  function($scope, $http, $window, $filter) {


		$scope.email = '';
		$scope.password = '';
		
		
		$scope.login = function(user, password) {
		console.log('user '+user);
			var url = 'ws/rest/bidderService/login/user/'+user+'/password/'+password+'/tenantId/test';
			console.log('url '+url);
			$http.post(url, null).
			    success(function(data) {
			    if(data == null || data ==''){
			    //do nothing. think later
			    alert('Authentication failed. Try again!');
			    $window.localStorage.setItem("loggedIn", 'no');   
			    }
			    else{
			    $scope.user = data;
			    $window.localStorage.setItem("loggedIn", 'yes');  
				    if($scope.user.userType == 'Admin'){
				    $window.localStorage.setItem("adminEmail", $scope.user.userEmail); 
				    $window.localStorage.setItem("adminFirstName", $scope.user.firstName); 
				    $window.localStorage.setItem("adminLastName", $scope.user.lastName); 
					$window.location.href = 'AdminShortCuts.html';
				    }
				    else if($scope.user.userType == 'Vendor'){
				    $window.localStorage.setItem("vendor", $scope.user);  
				    $window.localStorage.setItem("vendorEmail", $scope.user.userEmail);  
					$window.location.href = 'VendorLandingPage.html';
				    }
			    }
			
			});
		};
		
		
	
		
	    
 });
 
 
 
           
        