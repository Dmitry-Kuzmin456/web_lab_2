const rButtons = document.querySelectorAll('input[name="r"]');
let selectedR = null;

rButtons.forEach(button => {
    button.addEventListener('click', () => {
        rButtons.forEach(b => b.classList.remove('active'));
        button.classList.add('active');
        selectedR = Number(button.value);

        // Записываем в скрытое поле
        document.getElementById("rInput").value = selectedR;

        // drawGraphWithZones(selectedR);
    });
});


function drawGraphWithZones() {
    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');
    const size = 400;
    const padding = 40;

    // масштаб для диапазона [-7, 7]
    const scale = (size - 2 * padding) / 14;

    ctx.clearRect(0, 0, size, size);
    ctx.fillStyle = '#fff';
    ctx.fillRect(0, 0, size, size);

    // === оси ===
    ctx.strokeStyle = '#000';
    ctx.lineWidth = 2;
    const axisExtend = 20;

    ctx.beginPath();
    ctx.moveTo(padding - axisExtend, size / 2);
    ctx.lineTo(size - padding + axisExtend, size / 2);
    ctx.moveTo(size / 2, padding - axisExtend);
    ctx.lineTo(size / 2, size - padding + axisExtend);
    ctx.stroke();

    // стрелки осей
    ctx.beginPath();
    ctx.moveTo(size - padding + axisExtend, size / 2);
    ctx.lineTo(size - padding + axisExtend - 10, size / 2 - 5);
    ctx.lineTo(size - padding + axisExtend - 10, size / 2 + 5);
    ctx.closePath(); ctx.fill();

    ctx.beginPath();
    ctx.moveTo(size / 2, padding - axisExtend);
    ctx.lineTo(size / 2 - 5, padding - axisExtend + 10);
    ctx.lineTo(size / 2 + 5, padding - axisExtend + 10);
    ctx.closePath(); ctx.fill();

    ctx.font = '14px Arial';
    ctx.fillText('x', size - padding + axisExtend + 10, size / 2 - 10);
    ctx.fillText('y', size / 2 + 10, padding - axisExtend - 5);

    // === метки -7, -3.5, 3.5, 7 ===
    const marks = [-7, -3.5, 3.5, 7];
    marks.forEach(val => {
        const x = size / 2 + val * scale;
        ctx.beginPath();
        ctx.moveTo(x, size / 2 - 5);
        ctx.lineTo(x, size / 2 + 5);
        ctx.stroke();
        ctx.fillText(val === -7 ? "-7" : val === -3.5 ? "-3.5" : val === 3.5 ? "3.5" : "7", x - 12, size / 2 + 20);
    });
    marks.forEach(val => {
        const y = size / 2 - val * scale;
        ctx.beginPath();
        ctx.moveTo(size / 2 - 5, y);
        ctx.lineTo(size / 2 + 5, y);
        ctx.stroke();
        ctx.fillText(val === -7 ? "-7" : val === -3.5 ? "-3.5" : val === 3.5 ? "3.5" : "7", size / 2 - 35, y + 5);
    });

    // === рисуем фигуру "Бэтмена" ===
    const step = 0.01;

    function transformX(x) { return size / 2 + x * scale; }
    function transformY(y) { return size / 2 - y * scale; }

    function upperY(x) {
        const absX = Math.abs(x);
        if (absX < 0.5) return 2.25;
        if (0.5 <= absX && absX < 0.75) return 3 * absX + 0.75;
        if (0.75 <= absX && absX < 1) return 9 - 8 * absX;
        if (1 <= absX && absX <= 3) return 1.5 - 0.5 * absX - (6 * Math.sqrt(10) / 14) * (Math.sqrt(3 - x * x + 2 * absX) - 2);
        if (3 < absX && absX <= 7) return 3 * Math.sqrt(1 - (x / 7) ** 2); // верхний «крыло»
        return 0;
    }

    function lowerY(x) {
        const absX = Math.abs(x);
        if (4 < absX && absX <= 7) return -3 * Math.sqrt(1 - (x / 7) ** 2); // нижний «крыло» снаружи
        // объединяем участок 0..4 в одну формулу:
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



rButtons.forEach(button => {
    button.addEventListener('click', () => {
        rButtons.forEach(b => b.classList.remove('active'));
        button.classList.add('active');
        selectedR = Number(button.value);
        // drawGraphWithZones(selectedR);
    });
});

document.getElementById("pointForm").addEventListener("submit", async (e) => {
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
    const xRaw = formData.get("x");
    const yRaw = formData.get("y");
    const x = xRaw ? xRaw.trim() : "";
    const yInput = yRaw ? yRaw.trim() : "";
    const r = selectedR;

    let hasError = false;

    if (!x) {
        errorX.textContent = "Выберите X";
        errorX.style.display = "inline-block";
        hasError = true;
    }

    if (!yInput || !/^[-+]?\d+(\.\d+)?$/.test(yInput) || Number(yInput) <= -5 || Number(yInput) >= 5) {
        errorY.textContent = "Введите корректное число Y (-5...5)";
        errorY.style.display = "inline-block";
        hasError = true;
    }

    if (r === null || r === undefined) {
        errorR.textContent = "Выберите R";
        errorR.style.display = "inline-block";
        hasError = true;
    }

    if (hasError) return;

    // Отправляем форму обычным GET
    e.target.submit();
});



window.addEventListener('load', async () => {
    drawGraphWithZones(2);

    // try {
    //     const params = new URLSearchParams({ action: "history" });
    //     const response = await fetch("/request?" + params.toString());
    //     const history = await response.json();
    //     updateTableAndGraph(history);
    // } catch (err) {
    //     alert("Ошибка при загрузке истории: " + err);
    // }
});
