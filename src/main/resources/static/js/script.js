//navbar-toggle = true - это открытое меню, а false - закрытое
$(document).ready(function () {
    if (window.innerWidth < 992.99) {
        localStorage.setItem("navbar-toggle", "false");
    }
    let stateNavbar = localStorage.getItem('navbar-toggle');
    let navbarToggle = $("#navbar-toggle");
    let contentContainer = $("#content-main");

    if (stateNavbar === "true") {
        if (navbarToggle.hasClass("navbarMobile")) {
            navbarToggle.removeClass("navbarMobile");
            contentContainer.removeClass("closeNavbar");
        }
    } else {
        if (!navbarToggle.hasClass("navbarMobile")) {
            navbarToggle.addClass("navbarMobile");
            contentContainer.addClass("closeNavbar");
        }
    }

    let a = document.getElementsByClassName("list-status-type-device");

    for (let i = 0; i < a.length; i++) {
        getCountByStatus(a[i]);
    }
    if ($('#device-status-change').val() ==='6') $("#disposal-date-input").removeClass("d-none");

    $('#device-status-change').change(function() {
        var selectedValue = $(this).val();
        if (selectedValue === '6') {
            $("#disposal-date-input").removeClass("d-none");
        } else {
            $("#disposal-date-input").addClass("d-none");
        }
    });
});

$(window).resize(() => {
    let navbarToggle = $("#navbar-toggle");
    let contentContainer = $("#content-main");
    if (window.localStorage.getItem("navbar-toggle") === "true"){
        if (window.innerWidth < 992.99) {
            if (!navbarToggle.hasClass("navbarMobile") && !contentContainer.hasClass("closeNavbar")) {
                navbarToggle.addClass("navbarMobile");
                contentContainer.addClass("closeNavbar");
            }
            localStorage.setItem("navbar-toggle", "false");
        } else {
            if (navbarToggle.hasClass("navbarMobile") && contentContainer.hasClass("closeNavbar")) {
                navbarToggle.removeClass("navbarMobile");
                contentContainer.removeClass("closeNavbar");
            }
            localStorage.setItem("navbar-toggle", "true");
        }
    } else {

        if (!navbarToggle.hasClass("navbarMobile")) {
            navbarToggle.addClass("navbarMobile");
        }

        localStorage.setItem("navbar-toggle", "false");
    }
})

function toggleNavbarScript() {
    let navbarToggle = $("#navbar-toggle");
    let contentContainer = $("#content-main");

    if (window.innerWidth < 992.99) {
        navbarToggle.toggleClass("navbarMobile");
        if (!contentContainer.hasClass("closeNavbar")) { contentContainer.addClass("closeNavbar"); }
    } else {
        navbarToggle.toggleClass("navbarMobile");
        contentContainer.toggleClass("closeNavbar");
        localStorage.setItem("navbar-toggle", !navbarToggle.hasClass("navbarMobile"));
    }
}

function getSurnameAndMiddleNameAndName(targetObject) {
    let surname = document.getElementById("user-surname-create");
    let name = document.getElementById("user-name-create");
    let middleName = document.getElementById("user-middle-name-create");
    $.ajax({
        type:'post',
        url:'/api/v1/users/get/' + targetObject.value,
        success:function(result){
            surname.value = result.name.split(" ")[1];
            name.value = result.name.split(" ")[2];
            middleName.value = result.name.split(" ")[3];
        }

    })
}

function getUserByDepartment(targetObject) {
    $.ajax({
        type:'post',
        url:'/api/v1/users/get/department/' + targetObject.value,
        success:function(result){
            $("#device-user-using-change").empty();
            result.forEach(user => {
                $("#device-user-using-change").append('<option value="'+user.id+'">'+user.name + ' ('+ user.domain +')' +'</option>');
                return true;
            })
            if (result.length === 0) $("#device-user-using-change").append('<option value="0">Выбирите отдел для отображения пользователей...</option>')
        }

    })
}

async function getCountByStatus(ul) {
    let id = ul.id;
    $.ajax({
        type:'post',
        url:'/api/v1/devices/get/count/by/status/' + id,
        success: await function(result){
            $('#'+id).empty();
            let map = new Map();
            let keys = Object.keys(result);
            for (const key of keys) {
                map.set(key, result[key]);
            }
            map.forEach((value, key) => {
                $('#'+id).append('<li> <p>' + key + '</p> <p>' + value + '</p> </li>')
            })
        }

    })
}