/**
 * Created by Mordr on 24.02.2017.
 */
function clearUserForm() {
    $("#firstname-input").val("");
    $("#surname-input").val("");
    $("#patronymic-input").val("");
    $("#email-input").val("");
    $("#password-input").val("");
    $("#birthday-input").val("");
    $("#sex-input-input");
}
function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}
$(function(){
    $(document).off("click","td");
    $(document).on("click","td", function() {
        $("tr").removeClass("selected");
        var tr=$(this).closest("tr");
        tr.addClass("selected");
    });
})