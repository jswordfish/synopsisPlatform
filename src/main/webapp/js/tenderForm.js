var app = angular.module('myApp', []);

	

app.service('uploadsService', function($http) {   

    var code = '';
    var fileName = '';


    this.uploadFile = function(files, tenId, fileDesc) {
	console.log('in uploadFile fileName '+fileName);

        var fd = new FormData();

        //Take the first selected file
        fd.append("image", files[0]);
var url = 'ws/rest/bidderService/uploadFileForTender/tenderUniqueNo/'+tenId+'/fileDesc/'+fileDesc+'/tenantId/{tenantId}';
        var promise =  $http.post(url, fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity
            }).then(function(response) {

            code = response.data.code;
            fileName = response.data.fileName;

            return{
                code: function() {
                    return code;
                },
                fileName: function() {
                    return fileName;
                }
            }; 
        });
        return promise;
    };

  });
        
app.controller( 'tendersController',  function($scope, $http, $window, $filter) {

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
	
	var url = 'ws/rest/bidderService/tenders/token/temp';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.tenders = data;
		   
	    });
	
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	
	   $scope.addTender = function() {
			
			$window.localStorage.setItem("tender", '');   
			$window.location.href = 'tenderForm.html';	
			
		};
	
		$scope.editTender = function(tender) {
			console.log('in edit tender');
			$window.localStorage.setItem("tender", JSON.stringify(tender) ); 
			$window.location.href = 'tenderForm.html';	
		};
		
		
		$scope.deleteTender = function(tender) {
		
			var tenderUniqueId = tender.tenderUniqueId;
			tenderUniqueId = window.encodeURIComponent(tenderUniqueId);
			console.log('tender tenderUniqueId '+tenderUniqueId);
			
			var url = 'ws/rest/bidderService/deleteTender/tenderUniqueNo/'+tenderUniqueId+'/token/test';
			console.log('url '+url);
				$http.post(url, null).
			    success(function(data) {
			    $window.location.reload();
				$window.localStorage.setItem("tender", '');   
			    });
		};
		
		$scope.addTender = function() {
			
			 $window.localStorage.setItem("tender", '');   
			$window.location.href = 'tenderForm.html';	
			
		};
	
		
	    
 });
 
 
 app.controller( 'tenderController',  function($scope, $http, $window, $filter, uploadsService) {
	
 var loggedIn = $window.localStorage.getItem("loggedIn");
	console.log('loggedIn is '+loggedIn);
		if(loggedIn == 'yes'){
			console.log('user logged '+loggedIn);
		}
		else{
			$window.location.href = 'login.html';	
		}
	 
	 
	 
	 $scope.tender = '';	
	 $scope.tenderBidItem = '';
	 $scope.individualFileDescription = '';
	 
	 $scope.fileProperty = '';
	 
	var tender = window.localStorage.getItem("tender");
		if(tender != '' ){
			$scope.tender = jQuery.parseJSON(tender);
			console.log($scope.tender.tenderIssuedByCompany );
			if($scope.tender.tenderIssuedByCompany == '' || $scope.tender.tenderIssuedByCompany == null){
			console.log('no issued by'  );
				$scope.tender.tenderIssuedByCompany = $window.localStorage.getItem("adminFirstName"); +' '+$window.localStorage.getItem("adminLastName"); 
			}
			else{
			console.log($scope.tender.tenderIssuedByCompany );
			}
			
		}
		else{
		console.log(' new tender.........' );
		console.log(' first name '+$window.localStorage.getItem("adminFirstName") );
		console.log(' last name '+$window.localStorage.getItem("adminLastName") );
		$scope.tender = new Object();
			$scope.tender.tenderIssuedByCompany = $window.localStorage.getItem("adminFirstName") +' '+$window.localStorage.getItem("adminLastName"); 
		}
		
	var url = 'ws/rest/bidderService/listProjectNames/token/temp';
			console.log(url);
		   $http.get(url).
		    success(function(data) {
			
			    $scope.projectNames = data;
			    console.log('here ');
			    var names = JSON.stringify(data);
			    console.log(names);
			   
		    });
		    
	    
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	 
		$scope.saveBasicTenderDetails = function(file) {
			
			var url = 'ws/rest/bidderService/createUpdateTender/token/temp';
			$http.post(url, $scope.tender).
		    success(function(data) {
			$scope.tender.tenderUniqueId = data;
			  
			
			   
			});
	
		};
		
		$scope.listFileItems = function() {
		var tenId = window.encodeURIComponent($scope.tender.tenderUniqueId );
			var url = 'ws/rest/bidderService/listFileItems/tenderUniqueNo/'+tenId+'/token/test';
			console.log(url);
			$http.get(url).
			success(function(data) {
			
			    $scope.fileItems = data;
			    console.log('here ');
			    var res = JSON.stringify(data);
			    console.log(res);
			   $scope.image = '';
		    });

		};
		
		 $scope.uploadFile = function(files) {
		   var fileDesc = $scope.individualFileDescription;
			if(fileDesc == '' || fileDesc == null){
			fileDesc = 'NA';
			}
		   fileDesc = window.encodeURIComponent(fileDesc);
		   console.log('file desc is '+fileDesc);
		    var tenId = window.encodeURIComponent($scope.tender.tenderUniqueId );
		    console.log('in uploadFile '+tenId);
                    uploadsService.uploadFile(files, tenId, fileDesc).then(function(promise){

                        $scope.code = promise.code();
                        $scope.fileName = promise.fileName();
			
			$scope.individualFileDescription = '';
			$scope.fileLink = '';
			$scope.listFileItems();
			});
			
		$scope.image = '';
		 $("#fileButton").val('');
		//$scope.$apply();
		};
		
		$scope.addFileLinkForTender = function(){
		console.log('in addFileLinkForTender fun');
		//var tenId = window.encodeURIComponent($scope.tender.tenderUniqueId );
		var tenId =$scope.tender.tenderUniqueId;
		var fP = $scope.fileProperty;
		fP.tenderUniqueId = tenId;
		
		 var fileDesc = fP.description;
			if(fileDesc == '' || fileDesc == null){
			fP.description = 'NA';
			}
		//fileDesc = window.encodeURIComponent(fileDesc);
			if(fP.fileURL == ''){
			alert(' File Link/URL can not be empty');
			}
			else{
			var fileLink = fP.fileURL
			fP.fileURL = fileLink;
			var url = 'ws/rest/bidderService/addFileLinkForTender/tenantId/test';
			console.log('file link ws is '+url);
				$http.post(url, fP).
				success(function(data) {
				$scope.fileProperty = '';
				
				$scope.listFileItems();
				});
			}
		
		
		
		}
		 
		
		
		
		
		
		
		$scope.deleteFile = function(fileId) {
		var fileId = window.encodeURIComponent(fileId );
			var url = 'ws/rest/bidderService/deleteFileForTender/fileId/'+fileId;
			console.log(url);
			$http.post(url, null).
			success(function(data) {
			console.log('listing files');
			$scope.listFileItems();
			//$scope.$apply();
			$scope.image = '';   
			   
		    });

		};
		
		$scope.listTenderBidItems = function() {
		var tenId = window.encodeURIComponent($scope.tender.tenderUniqueId );
			var url = 'ws/rest/bidderService/listTenderBidItems/tenderUniqueNo/'+tenId+'/token/test';
			console.log(url);
			$http.get(url).
			success(function(data) {
			
			    $scope.tenderBidItems = data;
			    console.log('here fetched items ');
			 
		    });

		};
		
		$scope.addTenderBidItem = function() {
		    $scope.tenderBidItem.tenderUniqueNo = $scope.tender.tenderUniqueId;
		    var str = JSON.stringify($scope.tenderBidItem);
		    console.log(' item is '+str);
		    var url = 'ws/rest/bidderService/addOrUpdateTenderBidItemForTender/tenantId/test';
		   $http.post(url, $scope.tenderBidItem).
			success(function(data) {
			$scope.tenderBidItem = '';
			$scope.listTenderBidItems();
			  
			   
		    });
			
		
		};
		
		$scope.updateTenderItem = function(item){
		 console.log(' updateTenderItem');
		 console.log('item is '+JSON.stringify(item));
		$scope.tenderBidItem  = item;
			
		console.log('serial '+$scope.tenderBidItem.serial);	
		
		}
		
		$scope.deleteTenderBidItem = function(tenderBidItemId) {
		var tenderBidItemId = window.encodeURIComponent(tenderBidItemId );
			var url = 'ws/rest/bidderService/deleteTenderBidItemForTender/tenderBidItemId/'+tenderBidItemId;
			console.log(url);
			
			$http.post(url, null).
			success(function(data) {
			$scope.listTenderBidItems();
			
			   
		    });

		};
		
		
	    
	    $scope.listVendors = function() {
		var url = 'ws/rest/bidderService/vendors/token/temp';
		console.log(url);
		   $http.get(url).
		    success(function(data) {
			console.log('here ');
			    $scope.vendors = data;
			   
		    });

		};
	    
	    
		
		$scope.shareTender = function(vendors) {
		var tenId = window.encodeURIComponent($scope.tender.tenderUniqueId );
			var url = 'ws/rest/bidderService/inviteVendorsForTender/tenderNo/'+tenId+'/token/test';
			console.log(url);
			
			$http.post(url, vendors).
			success(function(data) {
			
			alert('Tender shared');
			$window.location.href = 'tenders.html';	
			   
		    });

		};
		
	    
		
 });
           
        