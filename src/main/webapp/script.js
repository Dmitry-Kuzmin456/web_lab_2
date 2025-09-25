const canvas = document.getElementById('graphCanvas');
const ctx = canvas.getContext('2d');
const size = 400;
const padding = 40;
const scale = (size - 2 * padding) / 14;

let selectedX = null;
let selectedR = null;

// Обработка кнопок X
const xButtons = document.querySelectorAll('input[name="x"]');
xButtons.forEach(button => {
    button.addEventListener('click', () => {
        xButtons.forEach(b => b.classList.remove('active'));
        button.classList.add('active');
        selectedX = Number(button.value);
        document.getElementById("xInput").value = selectedX;
    });
});

// Обработка радиуса R
const rRadios = document.querySelectorAll('input[name="r"]');
rRadios.forEach(radio => {
    radio.addEventListener('change', () => {
        selectedR = Number(radio.value);
    });
});


function drawGraphWithZones() {
    ctx.clearRect(0, 0, size, size);
    ctx.fillStyle = '#fff';
    ctx.fillRect(0, 0, size, size);

    ctx.strokeStyle = '#000';
    ctx.lineWidth = 2;
    const axisExtend = 20;

    ctx.beginPath();
    ctx.moveTo(padding - axisExtend, size / 2);
    ctx.lineTo(size - padding + axisExtend, size / 2);
    ctx.moveTo(size / 2, padding - axisExtend);
    ctx.lineTo(size / 2, size - padding + axisExtend);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(size - padding + axisExtend, size / 2);
    ctx.lineTo(size - padding + axisExtend - 10, size / 2 - 5);
    ctx.lineTo(size - padding + axisExtend - 10, size / 2 + 5);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(size / 2, padding - axisExtend);
    ctx.lineTo(size / 2 - 5, padding - axisExtend + 10);
    ctx.lineTo(size / 2 + 5, padding - axisExtend + 10);
    ctx.closePath();
    ctx.fill();

    ctx.font = '14px Arial';
    ctx.fillText('x', size - padding + axisExtend + 10, size / 2 - 10);
    ctx.fillText('y', size / 2 + 10, padding - axisExtend - 5);

    const marks = [-7, -3.5, 3.5, 7];
    marks.forEach(val => {
        const x = size / 2 + val * scale;
        ctx.beginPath();
        ctx.moveTo(x, size / 2 - 5);
        ctx.lineTo(x, size / 2 + 5);
        ctx.stroke();
        ctx.fillText(val.toString(), x - 12, size / 2 + 20);
    });
    marks.forEach(val => {
        const y = size / 2 - val * scale;
        ctx.beginPath();
        ctx.moveTo(size / 2 - 5, y);
        ctx.lineTo(size / 2 + 5, y);
        ctx.stroke();
        ctx.fillText(val.toString(), size / 2 - 35, y + 5);
    });

    const step = 0.01;
    function transformX(x) { return size / 2 + x * scale; }
    function transformY(y) { return size / 2 - y * scale; }

    function upperY(x) {
        const absX = Math.abs(x);
        if (absX < 0.5) return 2.25;
        if (0.5 <= absX && absX < 0.75) return 3 * absX + 0.75;
        if (0.75 <= absX && absX < 1) return 9 - 8 * absX;
        if (1 <= absX && absX <= 3) return 1.5 - 0.5 * absX - (6 * Math.sqrt(10) / 14) * (Math.sqrt(3 - x * x + 2 * absX) - 2);
        if (3 < absX && absX <= 7) return 3 * Math.sqrt(1 - (x / 7) ** 2);
        return 0;
    }

    function lowerY(x) {
        const absX = Math.abs(x);
        if (4 < absX && absX <= 7) return -3 * Math.sqrt(1 - (x / 7) ** 2);
        if (absX <= 4) return Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x * x - 3 + Math.sqrt(1 - (Math.abs(absX - 2) - 1) ** 2);
        return 0;
    }

    ctx.beginPath();
    for (let x = -7; x <= 7; x += step) {
        ctx.lineTo(transformX(x), transformY(upperY(x)));
    }
    for (let x = 7; x >= -7; x -= step) {
        ctx.lineTo(transformX(x), transformY(lowerY(x)));
    }
    ctx.closePath();
    ctx.fillStyle = 'black';
    ctx.fill();
}


document.getElementById("pointForm").addEventListener("submit", (e) => {
    e.preventDefault();

    const errorX = document.getElementById("errorX");
    const errorY = document.getElementById("errorY");
    const errorR = document.getElementById("errorR");
    const serverError = document.getElementById("serverError");

    [errorX, errorY, errorR].forEach(el => {
        el.textContent = "";
        el.style.display = "none";
    });
    serverError.textContent = "";
    serverError.classList.remove("visible");

    const formData = new FormData(e.target);
    const yRaw = formData.get("y");
    const y = yRaw ? yRaw.trim() : "";
    const r = formData.get("r");

    let hasError = false;

    if (selectedX === null || selectedX === undefined) {
        errorX.textContent = "Выберите X";
        errorX.style.display = "inline-block";
        hasError = true;
    }

    if (!y || !/^[-+]?[0-4]+(\.\d+)?$/.test(y)) {
        errorY.textContent = "Введите корректное число Y (-5...5)";
        errorY.style.display = "inline-block";
        hasError = true;
    }

    if (!r) {
        errorR.textContent = "Выберите R";
        errorR.style.display = "inline-block";
        hasError = true;
    }

    if (hasError) return;

    e.target.submit();
});


canvas.addEventListener('click', (e) => {
    const selectedR = document.querySelector('input[name="r"]:checked')?.value;
    if (!selectedR) {
        alert("Невозможно определить координаты точки, выберите R");
        return;
    }

    const rect = canvas.getBoundingClientRect();
    const canvasX = e.clientX - rect.left;
    const canvasY = e.clientY - rect.top;

    const graphX = (canvasX - size/2) / scale;
    const graphY = (size/2 - canvasY) / scale;

    const url = `${contextPath}/request?action=click&x=${graphX}&y=${graphY}&r=${selectedR}`;




    fetch(url)
        .then(res => res.json())
        .then(data => {
            drawGraphWithZones();
            drawPoint(data.x, data.y, data.hit);
        })
        .catch(err => console.error(err));
});


function drawPoint(x, y, hit) {
    ctx.fillStyle = hit ? 'green' : 'red';
    const px = size/2 + x*scale;
    const py = size/2 - y*scale;
    ctx.beginPath();
    ctx.arc(px, py, 4, 0, 2*Math.PI);
    ctx.fill();
}


window.addEventListener('load', () => {
    drawGraphWithZones();
});
