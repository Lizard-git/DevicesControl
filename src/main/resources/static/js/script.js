
$(document).ready(function () {
    if (window.innerWidth < 992.99) {
        localStorage.setItem("navbar-toggle", "true");
    }
    let stateNavbar = localStorage.getItem('navbar-toggle');
    if (stateNavbar === "true") {
        if (!$("#navbar-toggle").hasClass("navbarMobile")) {
            $("#navbar-toggle").addClass("navbarMobile");
            $("#content-toggle").addClass("contentMobile");
        }
    } else {
        if ($("#navbar-toggle").hasClass("navbarMobile")) {
            $("#navbar-toggle").removeClass("navbarMobile");
            $("#content-toggle").removeClass("contentMobile");
        }
    }
});

$(window).resize(() => {
    if (window.innerWidth < 992.99) {
        localStorage.setItem("navbar-toggle", "true");
        if (!$("#navbar-toggle").hasClass("navbarMobile")) {
            $("#navbar-toggle").addClass("navbarMobile");
            $("#content-toggle").addClass("contentMobile");
        }
    } else {
        if (window.localStorage.getItem("navbar-toggle") === "false")
        if ($("#navbar-toggle").hasClass("navbarMobile")) {
            $("#navbar-toggle").removeClass("navbarMobile");
            $("#content-toggle").removeClass("contentMobile");
        }
    }
})

function toggleNavbarScript() {
    $("#navbar-toggle").toggleClass("navbarMobile");
    $("#content-toggle").toggleClass("contentMobile");
    localStorage.setItem("navbar-toggle", $("#navbar-toggle").hasClass("navbarMobile"));
}