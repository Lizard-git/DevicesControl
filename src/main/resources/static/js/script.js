//navbar-toggle - true это открытое меню, а false - закрытое
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