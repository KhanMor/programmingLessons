/**
 * Created by Mordr on 08.03.2017.
 */

function clearCourseDetails(course_id, course_name, course_duration) {
    course_id.val("");
    course_name.val("");
    course_duration.val("");
}

function viewSelectedCourseDetails(course, course_id, course_name, course_duration) {
    var id_value = course.data().id;
    var name_value = course.find(".course-name").get(0).innerText;
    var duration_value =  course.find(".course-duration").get(0).innerText;

    course_id.val(id_value);
    course_name.val(name_value);
    course_duration.val(duration_value);
}

function getCourseData(course_id, course_name, course_duration) {
    return "id=" + course_id.val() +
        "&name=" + course_name.val() +
        "&duration=" + course_duration.val();
}

function deleteSelectedCourse(course, course_id, courseDeleteModal) {
    var id_value = course_id.val();
    $.ajax({
        url: "/courses/delete/" + id_value,
        type: "DELETE",
        beforeSend: function () {

        },
        complete: function () {
            courseDeleteModal.modal("hide");
        },
        success: function (data) {
            course.remove();
        }
    });
}

function updateSelectedCourse(course, course_id, course_name, course_duration, courseModal) {
    $.ajax({
        url : "/courses/update",
        type : "POST",
        data : getCourseData(course_id, course_name, course_duration),
        beforeSend: function() {

        },
        complete: function() {
            courseModal.modal("hide");
        },
        success: function(data) {
            course.find(".course-name").get(0).innerText = course_name.val();
            course.find(".course-duration").get(0).innerText = course_duration.val();
        }
    });
}

var course_global;

$(function(){
    var courseModal = $("#courseModal");
    var course_id = $("#course-id-input");
    var course_name = $("#course-name-input");
    var course_duration = $("#course-duration-input");
    var courseDeleteModal = $("#courseDeleteModal");

    $(document).off("click", ".editCourseBtn");
    $(document).on("click", ".editCourseBtn", function() {
        var course = $(this).closest("li");
        course_global = course;
        viewSelectedCourseDetails(course, course_id, course_name, course_duration);
        courseModal.data().mode = "update";
        courseModal.modal("show");
    });

    $(document).off("click", ".deleteCourseBtn");
    $(document).on("click", ".deleteCourseBtn", function() {
        var course = $(this).closest("li");
        course_global = course;
        var id_value = course.data().id;
        course_id.val(id_value);
        $("#deletingCourseName").html(course.find(".course-name").get(0).innerText);
        courseDeleteModal.modal("show");
    });

    $("#removeCourseBtn").click(function() {
        deleteSelectedCourse(course_global, course_id, courseDeleteModal);
    });

    $("#saveCourseBtn").click(function() {
        updateSelectedCourse(course_global, course_id, course_name, course_duration, courseModal);
    });
})