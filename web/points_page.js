var canvas = document.getElementById("canvas"),
    context = canvas.getContext("2d");
document.getElementById("PointForm:variableR").value = "";
var hiddenTable = document.getElementById("bottom");
hiddenTable.style.display = "none";

var unhidden = function(){
    hiddenTable.style.display = "block";
};
var getR = function () {
    let R = document.getElementById("PointForm:variableR").value;
    if (R == "" || isNaN(R) || R > 5 || R < 2){
        R = "R";
    }
    return R;
};
var hiddenMessageR = document.getElementById("hiddenMessageR");
hiddenMessageR.innerHTML= "<b style='color:red'>Нужно ввести R в диапозоне [2;5]</b>";
var cleanCanvas = function() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    let R = getR();
    let R2;
    if (R != "R"){
        R2 = R/2;
    }else{
        R2 = "R/2"
    }

//прямоугольник
    context.beginPath();
    context.rect(200, 100, 100, 50);
    context.closePath();
    context.strokeStyle = "#4899FF";
    context.fillStyle = "#4899FF";
    context.fill();
    context.stroke();

// сектор
    context.beginPath();
    context.moveTo(200, 150);
    context.arc(200, 150, 50, Math.PI, Math.PI/2, true);
    context.closePath();
    context.strokeStyle = "#4899FF";
    context.fillStyle = "#4899FF";
    context.fill();
    context.stroke();

   //через две точки
    context.beginPath();
    context.moveTo(200, 150);
    context.lineTo(200, 200);
    context.lineTo(300, 150);
    context.lineTo(200, 150);
    context.closePath();
    context.strokeStyle = "#4899FF";
    context.fillStyle = "#4899FF";
    context.fill();
    context.stroke();

//отрисовка осей
    context.beginPath();
    context.strokeStyle = "black";
    context.font = "14px Comic Sans MS";
    context.moveTo(200, 0);
    context.lineTo(200, 300);
    context.fillStyle = "black";
    context.stroke();
    context.moveTo(195, 200);
    context.lineTo(205, 200);
    context.stroke();
    context.fillText("-"+R2, 206, 196);
    context.moveTo(195, 250);
    context.lineTo(205, 250);
    context.stroke();
    context.fillText("-"+R, 206, 246);

    context.moveTo(195, 50);
    context.lineTo(205, 50);
    context.stroke();
    context.fillText(R, 206, 46);

    context.moveTo(195, 100);
    context.lineTo(205, 100);
    context.stroke();
    context.fillText(R2, 206, 96); //X = 0

    context.moveTo(100, 147);
    context.lineTo(100, 153);
    context.stroke();
    context.fillText("-"+R, 102, 147);

// context.moveTo(150, 147); context.lineTo(150, 153);
// context.stroke();
// context.fillText("R", 152, 147);

    context.moveTo(150, 147);
    context.lineTo(150, 153);
    context.stroke();
    context.fillText("-"+R2, 152, 147);

    context.moveTo(300, 147);
    context.lineTo(300, 153);
    context.stroke();
    context.fillText(R, 302, 147);

    context.fillText("Y", 205, 10);
    context.moveTo(0, 150);
    context.lineTo(400, 150);
    context.stroke();

    context.fillText("X", 390, 140);


    context.closePath();
    context.stroke();
}

document.getElementById("PointForm:variableR").onkeyup = function(e){

    let R = getR();
    cleanCanvas();
    if (R != "R") {
        hiddenMessageR.innerHTML = "";
        // setR([{name:'Radius', value: R}]);
        needList([{name:'Radius', value: R}]);
    }
    else{
        hiddenMessageR.innerHTML= "<b style='color:red'>Нужно ввести R в диапозоне [2;5]</b>";
    }
};
cleanCanvas();
var mouseX;
var mouseY;
var mouseXshow;
var mouseYshow;
canvas.addEventListener('click', function(e) {
    let R = getR();
    if (R != "R"){
        var shout = sendClick([{name:'PointX', value: mouseXshow},{name:'PointY', value: mouseYshow},{name:'PointR', value: getR()}]);
        }
}, false);
var getPointColor =  function (itsShot){
    let color;
        if (itsShot == 1) {
            color = "green";
        } else if (itsShot == 2) {
            color = "red";
        } else {
            color = "grey";
        }
        return color;
};
var drawPoint = function (x,y,r,shout){
    let xRes = parseFloat(x) * 100 / parseFloat(r) + 200;
    let yRes = parseFloat(y) * -1 * 100 / parseFloat(r) + 150;
    console.log("x: " + x + " y: " + y + " r: " + r + " shout: " + shout + " xRes: " + xRes + " yRes: " + yRes);
    shout = parseInt(shout);
    if (shout != 4) {
        context.beginPath();
        context.arc(xRes, yRes, 2, 0, 2 * Math.PI, false);
        context.closePath();
        let color = getPointColor(shout);
            context.strokeStyle = color;
            context.fillStyle = color;
        context.fill();
        context.stroke();
    }
};
var drawAlotOfPoints = function(ListPoints) {
    var jsonResponsePoints = JSON.parse(ListPoints);
    jsonResponsePoints.forEach(function (value, index, array) {
        let xRes = parseFloat(value.x) * 100 / R + 200;
        let yRes = parseFloat(value.y) * -1 * 100 / R + 150;
        context.beginPath();
        let shout1 = value.result;
        context.arc(xRes, yRes, 2, 0, 2 * Math.PI, false);
        context.closePath();
        let color = getPointColor(shout1);
            context.strokeStyle = color;
            context.fillStyle = color;
        context.fill();
        context.stroke();
        console.log("xres= "+xRes + "yres = " +yRes + " result = " +value.result);
    });
};
function getCursorPosition(e) {
    R = getR();
        mouseX = e.offsetX;
        mouseY = e.offsetY;
    //console.log(mouseX);
    mouseXshow = (mouseX - 200) / 100 * R;
    mouseYshow = (mouseY - 150) / 100 * R * -1;
    if (R != "R") {
        document.getElementById("movelog").innerHTML = "X: " + mouseXshow + " / Y: " + mouseYshow + "/ R:" + R;
    }
    else{
        document.getElementById("movelog").innerHTML = "X: */ Y: */ R: *";
    }
}
canvas.addEventListener('mousemove', getCursorPosition, false);

