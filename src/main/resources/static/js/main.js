/* API locations */
var REGISTER_ROUTE = "register/entity";

/* System variables */
var userName, password;

$(document).ready(function() {

    /* Set language labels */
    $("#userName").attr('placeholder', USER_NAME_LABEL);
    $("#password").attr('placeholder', PASSWORD_LABEL);
    $("#submitUserPassword").html(SUBMIT_LABEL);

    $("#submitUserPassword").click(function(){
        alert("submitUserPassword");
        userName = $("#username").val();
        password = $("#password").val();
        registerUser(userName, password);
    });

});

function showModalError(message){
    $("#errorMessage").remove();
    $("#error").append("<p id='errorMessage' style='text-align:center;font-size: 20px;'>"+message+"</p>");
    var options = {
        "backdrop" : "static"
    }
    $('#modalError').modal(options);
}

function registerUser(userName, password) {
    if(userName === "") {
        alert(USERNAME_EMPTY_LABEL);
        return;
    }

    if(password === "") {
        alert(PASSWORD_EMPTY_LABEL);
        return;
    }

    var aesObj = securityUtil.encryptCredentials(userName, password);

     $.ajax({
        url:REGISTER_ROUTE,
        data: aesObj,
        type:"POST",
        dataType:"html",
        success: function (data){
            alert(data);
        },
        error: function(){
            showModalError(ERROR_MESSAGE_LABEL);
        }
    } )

} 


