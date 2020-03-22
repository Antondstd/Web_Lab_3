window.onload = function () {
    clockUpdate();
};

function clockUpdate() {
    let now = new Date();
    let time = [now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
    let date = [now.getDate(), now.getMonth() + 1, now.getFullYear()].join('.');
    document.getElementById('clock').innerHTML = date + " | " + time;
    setTimeout(clockUpdate, 5000);
}