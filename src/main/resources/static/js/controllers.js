// controller to handle user registration
demoApp.controller('RegisterController', function($scope, $http, userHash) {
    $scope.registerUser = function() {
      if(typeof $scope.credentials.username === 'undefined' || $scope.credentials.username === "") {
        alert("Username can not be empty!");
        return;
      }
      if(typeof $scope.credentials.password === 'undefined' || $scope.credentials.password === "") {
        alert("Password can not be empty!");
        return;
      }
      // encrypt credentials for sending
      var aesObj = securityUtil.encryptCredentials($scope.credentials.username, $scope.credentials.password);
      $scope.encrypted = aesObj;
        $http({
          method: 'POST',
          url: "/register/entity",
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          transformRequest: function(obj) {
              var str = [];
              for(var p in obj)
              str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
              return str.join("&");
          },
          data: aesObj
        }).success(function (data, status) {
          $scope.decrypted = data;
          userHash.setHash($scope.decrypted.hash);
          $scope.userRegistered = true;
        }).error(function(data, status, headers, config) {
          alert("An error has occured with status: " + status + " and error message: " + data);
      });
    }
});

// controller to handle file upload - non HTML5 browsers are not supported
demoApp.controller('UploadFileController', function($scope, $http, userHash) {
    $scope.uploadFile = function() {
      function doMultiPartPost(file, fileName) {
         if(typeof userHash.hash === 'undefined' || userHash.hash === "") {
          alert("User hash is empty! Please ensure that you have entered your credentials in Step 1.");
          return;
         }
          var fd = new FormData();
          fd.append('file', file);
          fd.append('filename', file.name);
          // shared secret expected by the server
          fd.append('secret', 'kGQvUzSp#fLt+k+kUPk2x_-3F_GwUCB!r&+^H*Ka5kMJ9J#tWq&4ZByHetsk$QfA');
          fd.append('hash', userHash.hash);
          $http.post("upload/file", fd, {
              transformRequest: angular.identity,
              headers: {'Content-Type': undefined}
          }).success(function (data, status) {
              $scope.response = data;
              $scope.hasUpload = true;
          }).error(function(data, status, headers, config) {
              alert("An error has occured with status: " + status + " and error message: " + data);
          });
      }
      // check if browser supports HTML5 FromData
      if (typeof(window.FormData) === 'undefined') {
        $scope.noFormDataSupport = true;
        return;
      }
      var f = document.getElementById("upload-file").files[0];
      doMultiPartPost(f, f.name);
    }
});