var app = angular.module('myApp', []);

app.service('loginService', function() {   

    this.logOff = function() {
    	$window.localStorage.setItem("loggedIn", 'no');
    };

  });	
        
app.controller( 'usersController',  function($scope, $http, $window, $filter) {
//window.encodeURIComponent(input);
	
	var loggedIn = $window.localStorage.getItem("loggedIn");
	console.log('loggedIn is '+loggedIn);
		if(loggedIn == 'yes'){
			console.log('user logged '+loggedIn);
		}
		else{
			$window.location.href = 'login.html';	
		}
	
	var url = 'ws/rest/bidderService/users/token/temp';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.users = data;
		   
	    });
	
		
	
		$scope.editUser = function(user) {
			console.log('in edit User');
			$window.localStorage.setItem("user", JSON.stringify(user) ); 
			$window.location.href = 'userForm.html';	
		};
		
		
		$scope.deleteUser = function(user) {
		
			var email = user.userEmail;
			name = window.encodeURIComponent(email);
			console.log('email '+email);
			
			var url = 'ws/rest/bidderService/deleteUser/userName/'+email+'/token/{token}';
			console.log('url '+url);
				$http.post(url, null).
			    success(function(data) {
			    $window.location.reload();
				 $window.localStorage.setItem("user", '');   
			    });
		};
		
		$scope.addUser = function() {
			
			 $window.localStorage.setItem("user", '');   
			$window.location.href = 'userForm.html';	
			
		};
		
		$scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	
		
	    
 });
 
 
 app.controller( 'userController',  function($scope, $http, $window, $filter) {
	
	 var loggedIn = $window.localStorage.getItem("loggedIn");
		console.log('loggedIn is '+loggedIn);
			if(loggedIn == 'yes'){
				console.log('user logged '+loggedIn);
			}
			else{
				$window.location.href = 'login.html';	
			} 
	 
	 var user = window.localStorage.getItem("user");
		if(user != '' ){
			$scope.user = jQuery.parseJSON(user);
			$scope.disableIt = true;
		}
		else{
		$scope.disableIt = false;
		}
	
	
	 console.log('here in user form '+ $scope.user );
		$scope.saveUser = function(ven) {
			var url = 'ws/rest/bidderService/createUpdateUser/token/{token}';
			$http.post(url, $scope.user).
		    success(function(data) {
			$window.location.href = 'users.html';
			   
			});
	
		};
		
	$scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	    
		$scope.goBack = function() {
			
			
		
			$window.location.href = 'users.html';
		};
 });
           
        