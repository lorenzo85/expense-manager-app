$(function () {
    if(location.hash) {
        var a = $('[href=' + location.hash + ']');
        a && a.tab('show');
    }
});

$('.nav a').on('shown.bs.tab', function (e) {
    window.location = "#top";
});