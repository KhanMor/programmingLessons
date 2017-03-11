/**
 * Created by Mordr on 11.03.2017.
 */
function deleteSelectedLesson(lesson, lessonDeleteModal) {
    var id_value = lesson.data().id;
    $.ajax({
        url: "/lessons/delete/" + id_value,
        type: "DELETE",
        beforeSend: function () {

        },
        complete: function () {
            lessonDeleteModal.modal("hide");
        },
        success: function (data) {
            lesson.remove();
        }
    });
}

var lesson_global;

$(function(){
    var lessonDeleteModal = $("#lessonDeleteModal");

    $(document).off("click", ".deleteLessonBtn");
    $(document).on("click", ".deleteLessonBtn", function() {
        var lesson = $(this).closest("li");
        lesson_global = lesson;
        $("#deletingLessonName").html(lesson.find(".lesson-name").get(0).innerText);
        lessonDeleteModal.modal("show");
    });

    $("#removeLessonBtn").click(function() {
        deleteSelectedLesson(lesson_global, lessonDeleteModal);
    });
})