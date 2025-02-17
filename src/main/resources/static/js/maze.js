const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");
const errorContainer = document.getElementById("errorContainer");
const infoUserGame = document.getElementById("infoGame");
const borderWidth = 40;
const squareSize = canvas.width - 2 * borderWidth;
const x = borderWidth;
const y = borderWidth;
const coinPositions = [];
const windowFormScore= document.querySelector('.windowFinalScore');
const formScore = document.querySelector('.containerFormScore form');
let roomInfo;
const loadedCharacterImage = new Image();
loadedCharacterImage.src =  "./img/front.png";
const loadedKeyImage = new Image();
loadedKeyImage.src =  "./img/key.png";
const loadedCoinImage = new Image();
loadedCoinImage.src = "./img/coin.png";
const bufferCanvas = document.createElement('canvas');
const bufferCtx = bufferCanvas.getContext('2d');
bufferCanvas.width = canvas.width;
bufferCanvas.height = canvas.height;
////////////////////////////////////////////////////////////////////////////////////
formScore.addEventListener('submit', () => {
    windowFormScore.style.display = "none"; // Esconde la ventana modal
});
/////////////////////////////////////////////////////////////
function loadAllAssets(callback) {
    let loadedCount = 0;
    const totalAssets = 3; // Cambia según el número de imágenes que tienes

    function onAssetLoad() {
        loadedCount++;
        if (loadedCount === totalAssets) {
            callback();
        }
    }

    loadedCharacterImage.onload = onAssetLoad;
    loadedKeyImage.onload = onAssetLoad;
    loadedCoinImage.onload = onAssetLoad;
}

loadAllAssets(() => {
   obteinInfo()
});
function obteinInfo() {
    if (jsonInfo) {
        roomInfo = JSON.parse(jsonInfo)
        loadRoom(roomInfo)
    }
}
function drawWall() {
    ctx.lineWidth = borderWidth;
    ctx.strokeStyle = '#858483';
    ctx.strokeRect(x, y, squareSize, squareSize);
}

function drawUserInfo(roomInfo) {
    console.log(roomInfo.mapName)
    infoUserGame.innerHTML = `
    <div>Mapa: ${roomInfo.mapName}</div>
    <div>Habitación: ${roomInfo.userRoom}</div>
    <div>Llaves: ${roomInfo.userKeys}</div>
    <div>Monedas: ${roomInfo.userCoins}</div>`
}

const doorPositions = [];
function pushDoorToDoorPositions(state, x,y,doorWidth, doorHeight, direction){
    if (state === 0) {
        doorPositions.push({
            x,
            y,
            width: doorWidth,
            height: doorHeight,
            direction,
        });
    }
}

function drawDoor(state, direction) {
    const doorColor = state === 0 ? "red" : "black"; // 0: cerrado, 1: abierto
    ctx.fillStyle = doorColor;
    const centerX = x + squareSize / 2;
    const centerY = y + squareSize / 2;
    let doorX, doorY;

    switch (direction) {
        case "north":
            doorX = centerX - 70 / 2;
            doorY = y - 40 / 2;
            ctx.fillRect(doorX, doorY, 70, 40);
            pushDoorToDoorPositions(state, doorX, doorY, 70, 40, direction);
            break;
        case "south":
            doorX = centerX - 70 / 2;
            doorY = y + squareSize - 40 / 2;
            ctx.fillRect(doorX, doorY, 70, 40);
            pushDoorToDoorPositions(state, doorX, doorY, 70, 40, direction);
            break;
        case "east":
            doorX = x + squareSize - 40 / 2;
            doorY = centerY - 70 / 2;
            ctx.fillRect(doorX, doorY, 40, 70);
            pushDoorToDoorPositions(state, doorX, doorY, 40, 70, direction);
            break;
        case "west":
            doorX = x - 40 / 2;
            doorY = centerY - 70 / 2;
            ctx.fillRect(doorX, doorY, 40, 70);
            pushDoorToDoorPositions(state, doorX, doorY, 40, 70, direction);
            break;
    }
}
function searchDoors(room) {
    let doors = room.doors;
    if (doors) {
        if (room.north !== undefined) {
            let northDoor = doors.find(door => door.doorId === room.north);
            if (northDoor) drawDoor(northDoor.state, "north");
        }

        if (room.south !== undefined) {
            let southDoor = doors.find(door => door.doorId === room.south);
            if (southDoor) drawDoor(southDoor.state, "south");
        }

        if (room.east !== undefined) {
            let eastDoor = doors.find(door => door.doorId === room.east);
            if (eastDoor) drawDoor(eastDoor.state, "east");
        }

        if (room.west !== undefined) {
            let westDoor = doors.find(door => door.doorId === room.west);
            if (westDoor) drawDoor(westDoor.state, "west");
        }
    }else {
        return
    }
}

function drawAllCoins(coinImage, roomInfo, coinsPerRow, coinSize, padding, wallPadding) {
    coinPositions.length = 0;
    for (let i = 0; i < roomInfo.coins; i++) {
        const row = Math.floor(i / coinsPerRow);
        const col = i % coinsPerRow;
        const xPos = x + wallPadding + col * (coinSize + padding);
        const yPos = y + wallPadding + row * (coinSize + padding);
        if (yPos + coinSize <= squareSize + borderWidth) {
            ctx.drawImage(coinImage, xPos, yPos, coinSize, coinSize);
            coinPositions.push({ x: xPos, y: yPos, size: coinSize });
        } else { break }
    }
}


function drawCoins(roomInfo) {
    const coinSize = 50;
    const padding = 10;
    const wallPadding = 20;
    const coinsPerRow = Math.floor((squareSize - 2 * wallPadding) / (coinSize + padding));
    drawAllCoins(loadedCoinImage, roomInfo, coinsPerRow, coinSize, padding, wallPadding);
}

let keyPosition = null;

function drawKey(roomInfo) {
    const keySize = 70;
    const wallPadding = 50;
    const xPos = x + 320;
    const yPos = y + 320;
    ctx.drawImage(loadedKeyImage, xPos, yPos, keySize, keySize);
    keyPosition = { x: xPos, y: yPos, size: keySize };
}

let winImagePosition = null;
function drawFinal() {
    const img = new Image();
    img.src = './img/win.jpg';
    img.onload = function () {
        const xPos = borderWidth;
        const yPos = borderWidth;
        const width = squareSize;
        const height = squareSize;
        winImagePosition = { x: xPos, y: yPos, width: width, height: height };
        ctx.drawImage(img, xPos, yPos, width, height);
    };

}

function drawCharacter() {
    const scaleFactor = 0.35;
    const newWidth = loadedCharacterImage.width * scaleFactor;
    const newHeight = loadedCharacterImage.height * scaleFactor;
    const centerX = x + squareSize / 2 - newWidth / 2;
    const centerY = y + squareSize / 2 - newHeight / 2;
    ctx.drawImage(loadedCharacterImage, centerX, centerY, newWidth, newHeight);
}
function drawStaticElements(roomInfo) {
    bufferCtx.clearRect(0, 0, bufferCanvas.width, bufferCanvas.height);  // Limpiar el buffer
    if (roomInfo.coins > 0) { drawCoins(roomInfo); }
    if (roomInfo.keys > 0) { drawKey(roomInfo); }
    if (roomInfo.finalRoom > 0){drawFinal()}
    drawWall();
    drawUserInfo(roomInfo);
    searchDoors(roomInfo);
    if (roomInfo.errorMessage != "") {
        errorContainer.innerText = roomInfo.errorMessage;
    }
}

function loadRoom(roomInfo) {
    if (roomInfo.finalRoom == 0 || roomInfo.finalRoom == null){
        drawCharacter();  // Dibujamos al personaje estático por primera vez
    }
    drawStaticElements(roomInfo);  // Dibujamos los elementos estáticos (fondo)
    ctx.drawImage(bufferCanvas, 0, 0);  // Copiar el contenido del buffer al canvas principal
}
function drawRoom(roomInfo) {
    drawWall()
    drawUserInfo(roomInfo)
    searchDoors(roomInfo)
    if (roomInfo.errorMessage != ""){
        errorContainer.innerText = roomInfo.errorMessage;
    }
    if (roomInfo.coins > 0){drawCoins(roomInfo)}
    if (roomInfo.keys > 0){drawKey(roomInfo)}
    if (roomInfo.finalRoom > 0){drawFinal()
    }
}

function getMousePosition(event, canvas) {
    const rect = canvas.getBoundingClientRect();
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top,
    };
}

document.getElementById('flechasImg').addEventListener('click', function(event) {
    const img = event.target;
    const rect = img.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const width = rect.width;
    const height = rect.height;
    let direction = "";
    if (x >= width * 0.3 && x <= width * 0.7 && y >= height * 0.1 && y <= height * 0.5) {
        direction = "north";
    } else if (x >= width * 0.3 && x <= width * 0.7 && y >= height * 0.6 && y <= height * 0.9) {
        direction = "south";
    }else if (x >= width * 0.6 && x <= width * 0.9 && y >= height * 0.3 && y <= height * 0.7) {
        direction = "east";
    }else if (x >= width * 0.1 && x <= width * 0.4 && y >= height * 0.3 && y <= height * 0.7) {
        direction = "west";
    }
    if (direction) {
        animateMovement(direction, () => {
            window.location.href = `/nav?dir=${direction}`;
        });
    }
});

document.addEventListener('keydown', function(event) {
    let direction = "";
    switch(event.code) {
        case "ArrowUp":
            direction = "north";
            break;
        case "ArrowDown":
            direction = "south";
            break;
        case "ArrowRight":
            direction = "east";
            break;
        case "ArrowLeft":
            direction = "west";
            break;
    }

    if (direction) {
        animateMovement(direction, () => {
            window.location.href = `/nav?dir=${direction}`;
        });
    }
});


function detectCoinClick(event) {
    const { x: mouseX, y: mouseY } = getMousePosition(event, canvas);
    for (let i = 0; i < coinPositions.length; i++) {
        const coin = coinPositions[i];
        if (
        mouseX >= coin.x &&
        mouseX <= coin.x + coin.size &&
        mouseY >= coin.y &&
        mouseY <= coin.y + coin.size
        ) {
            window.location.href = `/getcoin`;
            return;
        }
    }
}

function detectKeyClick(event) {
    if (!keyPosition) return;
    const { x: mouseX, y: mouseY } = getMousePosition(event, canvas);
    if ( mouseX >= keyPosition.x && mouseX <= keyPosition.x + keyPosition.size && mouseY >= keyPosition.y &&  mouseY <= keyPosition.y + keyPosition.size) {
        window.location.href = `/getkey`;
        return;
    }
}

function detectCloseDoorClick(event) {
    const { x: mouseX, y: mouseY } = getMousePosition(event, canvas);
    for (const door of doorPositions) {
        if (
        mouseX >= door.x &&
        mouseX <= door.x + door.width &&
        mouseY >= door.y &&
        mouseY <= door.y + door.height
        ) {
            window.location.href = `/open?dir=${door.direction}`;
            return;
        }
    }
}

function detectWinClick(event) {
    if (!winImagePosition) return;
    const { x: mouseX, y: mouseY } = getMousePosition(event, canvas);
    if (
    mouseX >= winImagePosition.x &&
    mouseX <= winImagePosition.x + winImagePosition.width &&
    mouseY >= winImagePosition.y &&
    mouseY <= winImagePosition.y + winImagePosition.height
    ) {
        windowFormScore.style.display = "block";
        overlay.style.display = 'block';
    }
}
////////////////////////////////////////////////////////////////////////


canvas.addEventListener("click", detectKeyClick);
canvas.addEventListener("click", detectCoinClick);
canvas.addEventListener("click", detectCloseDoorClick);
canvas.addEventListener("click", detectWinClick);
//////////////////////////////////////////////////////////////////////////

const directions = {
    north: ['./img/north1.png', './img/north2.png', './img/north3.png', './img/north4.png', './img/north5.png'],
    south: ['./img/south1.png', './img/south2.png', './img/south3.png', './img/south4.png', './img/south5.png'],
    east: ['./img/east1.png', './img/east2.png', './img/east3.png', './img/east4.png', './img/east5.png'],
    west: ['./img/west1.png', './img/west2.png', './img/west3.png', './img/west4.png', './img/west5.png'],
};

const loadedImages = {};
function loadImages() {
    for (const direction in directions) {
        loadedImages[direction] = directions[direction].map((src) => {
            const img = new Image();
            img.src = src;
            return img;
        });
    }
}
loadImages();
const steps = 80; // Cantidad de pasos para completar el movimiento
let currentFrame = 0;
let animationRequest = null;

function drawFrame(direction, currentFrame, x, y) {
    const img = loadedImages[direction][currentFrame];
    ctx.drawImage(img, x, y, 70, 70);
}

let frameTime = 0;
const frameInterval = 60;

function getMovementCoordinates(direction) {
    switch (direction) {
        case "north":
            return {
                startX: 225.725,
                startY: 259.725,
                endX: 225.725,
                endY: 20,
            };
        case "south":
            return {
                startX: 225.725,
                startY: 259.725,
                endX: 225.725,
                endY: 430,
            };
        case "west":
            return {
                startX: 259.725,
                startY: 225.725,
                endX: 20,
                endY: 225.725,
            };
        case "east":
            return {
                startX: 259.725,
                startY: 225.725,
                endX: 430,
                endY: 225.725,
            };
        default:
            throw new Error("Dirección no válida");
    }
}

function animateMovement(direction, callback) {
    if (animationRequest) cancelAnimationFrame(animationRequest);
    const images = loadedImages[direction];
    const { startX, startY, endX, endY } = getMovementCoordinates(direction); // Coordenadas iniciales y finales
    let stepCount = 0; // Contador de pasos

    function animate() {
        const now = performance.now();
        const elapsed = now - frameTime;

        if (stepCount > steps) {
            if (callback) callback();
            return;
        }

        ctx.clearRect(0, 0, canvas.width, canvas.height); // Limpiar el canvas
        drawRoom(roomInfo); // Redibujar todo el contenido (paredes, monedas, puertas, etc.)

        if (elapsed >= frameInterval) {
            currentFrame = (currentFrame + 1) % images.length;
            frameTime = now;
        }
        const t = stepCount / steps;
        const currentX = startX + t * (endX - startX);
        const currentY = startY + t * (endY - startY);

        drawFrame(direction, currentFrame, currentX, currentY);
        stepCount++;
        animationRequest = requestAnimationFrame(animate);
    }

    animate();
}

