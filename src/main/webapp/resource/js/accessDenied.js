var seconds = 5;
var interval;

var goBack = function () {
    clearInterval(interval);
    window.location.replace("/BTA");
}

function updateSeconds() {
    document.getElementById("seconds").innerHTML = seconds;
    seconds--;
    if (seconds < 0) {
        goBack();
    }
}

function countdownTimer() {
    interval = setInterval(function () {
        updateSeconds()
    }, 1000);
}


function easterEgg() {
    window.location.href = "https://www.youtube.com/watch?v=KvpVJMDZVF8&t=15s";
}