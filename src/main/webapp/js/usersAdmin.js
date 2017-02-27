function getUserData(trUpdate, ID_ONLY) {
    var id=null;
    if(trUpdate != undefined && trUpdate != null) {
        id = $(trUpdate).data().id;
    }
    var data;
    if(ID_ONLY) {
        data = "id=" + id;
    }else {
        data = "id=" + id +
            "&email=" + $("#email-input").val() +
            "&password=" + $("#password-input").val() +
            "&firstname=" + $("#firstname-input").val() +
            "&surname=" + $("#surname-input").val() +
            "&patronymic=" + $("#patronymic-input").val() +
            "&birthday=" + $("#birthday-input").val() +
            "&sex=" + $("#sex-input").val() +
            "&changePassword=" + !$("#password-input").prop("disabled")+
            "&role=" + $("#currentUserRole").data().roleName;
    }

    return data;
}
function userToTd(id) {
    var trHtml = "<tr data-id=" + id + ">" +
                        "<td>" + $("#email-input").val() + "</td>" +
                        "<td>" + $("#firstname-input").val() + "</td>" +
                        "<td>" + $("#surname-input").val() + "</td>" +
                        "<td>" + $("#patronymic-input").val()+ "</td>" +
                        "<td>" + $("#birthday-input").val() + "</td>" +
                        "<td>" + $("#sex-input").val()+ "</td>" +
                        "<td data-roleName='" + $("#currentUserRole").data().roleName + "'>" +
                            $("#currentUserRole").html()+
                        "</td>" +
                    "</tr>";
    return trHtml;
}

function addUser() {
    $.ajax({
        url: "/usersAdmin/add",
        type: "POST",
        data: getUserData(trUpdate, false),
        success: function(user_id){
            if(isNumber(user_id)) {
                $("#usersTable").append(userToTd(user_id));
                $("#UserModal").modal("hide");
            } else {
                $("#error-response").show("fast");
                $("#error-response").html("<div class='label-danger' >Возникла ошибка! Проверьте правильность введенных данных!</div>");
            }
        },
        beforeSend: function() {

        },
        complete: function() {

        },
        error: function() {

        }
    })
}
var trUpdate;
function updateUser(tr) {
    $.ajax({
        url: "/usersAdmin/update",
        type: "POST",
        data: getUserData(trUpdate, false),
        success: function(user){
            console.log(user);
            $(tr).html( $(userToTd(user)).html() );
        },
        beforeSend: function() {

        },
        complete: function() {
            $("#UserModal").modal("hide");
        },
        error: function() {
            $("#UserModal").modal("hide");
        }
    })
}
function deleteUser(tr) {
    $.ajax({
        url: "/usersAdmin/delete",
        type: "POST",
        data: getUserData(trUpdate, true),
        success: function(){
            $(tr).remove();
        },
        beforeSend: function() {

        },
        complete: function() {
            $("#UserModal").modal("hide");
        },
        error: function() {
            $("#UserModal").modal("hide");
        }
    })
}
$(function() {
    $(document).off("click","#usersTable td");
    $(document).on("click","#usersTable td", function() {
        $("#usersTable tr").removeClass("selected");
        var tr=$(this).closest("tr");
        tr.addClass("selected");
    });
    $("#addUserBtn").click(function() {
        clearUserForm();
        $("#password-change-flag-container").hide();

        $("#password-input").prop("disabled", false);
        $("#password-change-flag").prop("checked", true);

        $("#error-response").hide();
        $("#UserModal").modal("show");
        $("#UserModal").data().mode="add";
    });
    $("#editUserBtn").click(function() {
        var tr=$("#usersTable tr.selected");
        trUpdate = tr;
        var tds=tr.children("td");
        console.log(tds);
        $("#email-input").val(tds.get(0).innerText);

        $("#password-input").val("*****");
        $("#password-input").prop("disabled", true);

        $("#firstname-input").val(tds.get(1).innerText);
        $("#surname-input").val(tds.get(2).innerText);
        $("#patronymic-input").val(tds.get(3).innerText);
        $("#birthday-input").val(tds.get(4).innerText);
        $("#sex-input").val(tds.get(5).innerText);

        $("#password-change-flag-container").show();
        $("#password-change-flag").prop("checked", false);
        $("#currentUserRole").data().roleName=$(tds.get(6)).data().roleName;
        $("#currentUserRole").html($(tds.get(6)).html());

        $("#error-response").hide();
        $("#UserModal").modal("show");
        $("#UserModal").data().mode="update";
    });
    $("#password-change-flag").change(function() {
        console.log($(this).prop("checked"));
        if($(this).prop("checked")) {
            $("#password-input").val("");
            $("#password-input").prop("disabled", false);
        }else {
            $("#password-input").val("*****");
            $("#password-input").prop("disabled", true);
        }
    });
    $("#saveUserBtn").click(function() {
        var modalMode=$("#UserModal").data().mode;
        switch (modalMode) {
            case "add":
                addUser();
                break;
            case "update":
                updateUser(trUpdate);
                break;
        }
    });

    $("#deleteUserBtn").click(function() {
        console.log("clicked");
        var tr=$("#usersTable tr.selected");
        trUpdate = tr;
        deleteUser(trUpdate);
    });

    $("#setAdminBtn").click(function() {
        console.log("clicked");
        $("#currentUserRole").data().roleName="admin";
        $("#currentUserRole").html("<li class='list-group-item list-group-item-success'>admin</li>");
    });
    $("#setAuthorBtn").click(function() {
        $("#currentUserRole").data().roleName="author";
        $("#currentUserRole").html("<li class='list-group-item list-group-item-info'>author</li>");
    });
    $("#setStudentBtn").click(function() {
        $("#currentUserRole").data().roleName="student";
        $("#currentUserRole").html("<li class='list-group-item list-group-item-info'>student</li>");
    });
    $("#setBlockBtn").click(function() {
        $("#currentUserRole").data().roleName="blocked";
        $("#currentUserRole").html("<li class='list-group-item list-group-item-danger'>blocked</li>");
    });
})