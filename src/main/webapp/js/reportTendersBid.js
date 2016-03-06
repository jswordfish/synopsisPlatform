var app = angular.module('myApp', []);


        
app.controller( 'reportTenderController',  function($scope, $http, $window, $filter) {

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
		
		$scope.seeAllBids = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorsBidDetail.html';
		};
		
		$scope.seeAllBidsInternalBudget = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorBidDetail_Internal.html';
		};
		
		$scope.seeAllBidsCompleteCompAnalysis = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorsBidDetail_Complete.html';
		};
	
		$scope.seeAllBidsRateCompAnalysis = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorsBid_Rate.html';
		};
		
		$scope.seeAllBidsAmountCompAnalysis = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorsBid_Amount.html';
		};
		
		$scope.seeAllBidsRateAndAmountCompAnalysis = function(tenderUniqueNo) {
			console.log('tenderUniqueNo  is '+tenderUniqueNo);
			$window.localStorage.setItem("tenderUniqueNo", tenderUniqueNo);
			$window.location.href = 'reportVendorBid_RateAndAmount.html';
		};
	    
 });

app.controller( 'reportTenderVendorsBidController',  function($scope, $http, $window, $filter) {

	var tenderNo = $window.localStorage.getItem('tenderUniqueNo');
	tenderNo = window.encodeURIComponent(tenderNo );
	$scope.tenderUniqueNo = $window.localStorage.getItem('tenderUniqueNo');
	var url = 'ws/rest/bidderService/listVendorBidsForTender/tenderUniqueNo/'+tenderNo+'/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		    $scope.allVenderBids = data;
		    console.log(' bids'+JSON.stringify(data));
		    if($scope.allVenderBids == '' || $scope.allVenderBids == null || $scope.allVenderBids.length ==0){
		     console.log(' 1111');
			$scope.noBidsMsg = 'No Bid/Quote has been submitted by any vendor for this tender '+$window.localStorage.getItem('tenderUniqueNo');
		    
		    }
		    else{
		    console.log(' 222');
		    $scope.noBidsMsg = '';
		    }
		    
		    
		   
	    });
		
		
		
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
	
		
	    
 });
 
 
