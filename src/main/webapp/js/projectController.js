var app = angular.module('myApp', []);


        
app.controller( 'projectsController',  function($scope, $http, $window, $filter) {
	
	

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


	
	var url = 'ws/rest/bidderService/projects/token/temp';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.projects = data;
		   
	    });
	
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	
		$scope.editProject = function(project) {
			console.log('in edit project');
			$window.localStorage.setItem("project", JSON.stringify(project) ); 
			$window.location.href = 'projectForm.html';	
		};
		
		
		$scope.deleteProject = function(project) {
		
			var name = project.projectName;
			name = window.encodeURIComponent(name);
			console.log('project name '+name);
			
			var url = 'ws/rest/bidderService/deleteProject/projectName/'+name+'/token/{token}';
			console.log('url '+url);
				$http.post(url, null).
			    success(function(data) {
			    $window.location.reload();
				 $window.localStorage.setItem("project", '');   
			    });
		};
		
		$scope.addProject = function() {
			
			 $window.localStorage.setItem("project", '');   
			$window.location.href = 'projectForm.html';	
			
		};
	
		
	    
 });
 
 
 app.controller( 'projectController',  function($scope, $http, $window, $filter) {
	var project = window.localStorage.getItem("project");
		if(project != '' ){
			console.log('project for update');
			$scope.project = jQuery.parseJSON(project);
			$scope.disableIt = true;
		}
		else{
		console.log('project for create');
		$scope.disableIt = false;
		}
		
	
	
	 console.log('here in project form '+ $scope.project );
		$scope.saveProject = function(ven) {
			var url = 'ws/rest/bidderService/createUpdateProject/token/temp';
			$http.post(url, ven).
		    success(function(data) {
			    $scope.projectResponse = data;
			   
			$window.location.href = 'projects.html';
			   
			});
	
		};
		
		$scope.logOff = function() { 

			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'login.html';

			  };
	    
		$scope.goBack = function() {
			
			
		
			$window.location.href = 'projects.html';
		};
 });
           
        