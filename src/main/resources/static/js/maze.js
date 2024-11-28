const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");
const errorContainer = document.getElementById("errorContainer");
const infoUserGame = document.getElementById("infoGame");
const borderWidth = 40;
const squareSize = canvas.width - 2 * borderWidth;
const x = borderWidth;
const y = borderWidth;
const coinPositions = [];

function obteinInfo() {
    if (jsonInfo) {
        let roomInfo = JSON.parse(jsonInfo)
        console.log(roomInfo)
        drawRoom(roomInfo)
    } else {
        console.log("currentRoom no está definido.");
    }}
function drawWall() {
    ctx.lineWidth = borderWidth;
    ctx.strokeStyle = 'black';
    ctx.strokeRect(x, y, squareSize, squareSize);
}
function drawUserInfo(roomInfo) {
    infoUserGame.innerHTML = `
    <div>Habitación: ${roomInfo.userRoom}</div>
    <div>Llaves: ${roomInfo.userKeys}</div>
    <div>Monedas: ${roomInfo.userCoins}</div>`
}
const doorPositions = []; // Guardará las coordenadas y estados de las puertas
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
    const doorColor = state === 0 ? "red" : "white"; // 0: cerrado, 1: abierto
    ctx.fillStyle = doorColor;
    ctx.lineWidth = borderWidth;
    const doorWidth = 40;
    const doorHeight = 40;
    let x, y;
    switch (direction) {
        case "north":
            x = squareSize / 2 + borderWidth / 2;
            y = 20;
            break;
        case "south":
            x = squareSize / 2 + borderWidth / 2;
            y = squareSize + 20;
            break;
        case "east":
            x = squareSize + borderWidth / 2;
            y = squareSize / 2 + 20;
            break;
        case "west":
            x = 20;
            y = squareSize / 2 + 20;
            break;
    }
    ctx.fillRect(x, y, doorWidth, doorHeight);
    pushDoorToDoorPositions(state,x,y,doorWidth, doorHeight, direction)

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
        console.log ("no hay puerta")
        return
    }
}

function drawAllCoins(coinImage, roomInfo, coinsPerRow, coinSize, padding, wallPadding) {
    coinPositions.length = 0;
    for (let i = 0; i < roomInfo.coins; i++) {
        // Cálculo de la posición de cada moneda
        const row = Math.floor(i / coinsPerRow); // Índice de fila
        const col = i % coinsPerRow; // Índice de columna
        const xPos = x + wallPadding + col * (coinSize + padding);
        const yPos = y + wallPadding + row * (coinSize + padding);
        // Verifica que las monedas no se salgan del área dibujable
        if (yPos + coinSize <= squareSize + borderWidth) {
            ctx.drawImage(coinImage, xPos, yPos, coinSize, coinSize);
            coinPositions.push({ x: xPos, y: yPos, size: coinSize });
        } else { break }
    }
}

function drawCoins(roomInfo) {
    const coinImagePath = "./img/coin.png";
    const coinImage = new Image();
    coinImage.src = coinImagePath;
    const coinSize = 60;
    const padding = 10; // Espacio entre monedas
    const wallPadding = 20; // Espacio entre las monedas y las paredes del cuadro
    const coinsPerRow = Math.floor((squareSize - 2 * wallPadding) / (coinSize + padding));
    coinImage.onload = function () {
        drawAllCoins(coinImage, roomInfo, coinsPerRow, coinSize, padding, wallPadding);
    };
}
let keyPosition = null;

function drawKey(roomInfo) {
    const keyImagePath = "./img/key.png";
    const keyImage = new Image();
    keyImage.src = keyImagePath;
    const keySize = 70;
    const wallPadding = 50; // Espacio entre la llave y las paredes del cuadro

    keyImage.onload = function () {
        const xPos = x + (squareSize / 2) - (keySize / 2);
        const yPos = y + wallPadding;
        ctx.drawImage(keyImage, xPos, yPos, keySize, keySize);
        keyPosition = { x: xPos, y: yPos, size: keySize };
    };
}
let winImagePosition = null;
function drawFinal() {
    const img = new Image();
    img.src = './win.jpg';
    img.onload = function () {
        const xPos = borderWidth;
        const yPos = borderWidth;
        const width = squareSize;
        const height = squareSize;
        winImagePosition = { x: xPos, y: yPos, width: width, height: height };
        ctx.drawImage(img, xPos, yPos, width, height);
    };

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
    if (roomInfo.finalRoom > 0){drawFinal()}
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
    const rect = img.getBoundingClientRect();  // Obtenemos el tamaño y la posición de la imagen
    const x = event.clientX - rect.left;  // Coordenada X relativa a la imagen
    const y = event.clientY - rect.top;   // Coordenada Y relativa a la imagen
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
        window.location.href = `/nav?dir=${direction}`;
    } else {
        console.log("Clic fuera de las flechas");
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
        window.location.href = '/scores';
    }
}
obteinInfo()
canvas.addEventListener("click", detectKeyClick);
canvas.addEventListener("click", detectCoinClick);
canvas.addEventListener("click", detectCloseDoorClick);

