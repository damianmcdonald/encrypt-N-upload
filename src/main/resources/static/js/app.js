var demoApp = angular.module('demoApp', ['ngAnimate']);

// service used to share user hash information betewwen controllers
demoApp.factory('userHash', function () {
  var userHashService = {};

  userHashService.hash = "";

  userHashService.setHash = function(hash){
    userHashService.hash = hash;
  };

  return userHashService;
});