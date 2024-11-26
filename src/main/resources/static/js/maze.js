const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");
const errorContainer = document.getElementById("errorContainer");
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
function drawDoor(state, direction) {
    const doorColor = state === 0 ? "red" : "white"; // 0: cerrado, 1: abierto
    ctx.fillStyle = doorColor;
    ctx.lineWidth = borderWidth;
    switch (direction) {
        case "north":
            ctx.fillRect(squareSize / 2 + borderWidth / 2, 20, 40, 40);
            break;
        case "south":
            ctx.fillRect(squareSize / 2 + borderWidth / 2, squareSize+20, 40, 40);
            break;
        case "east":
            ctx.fillRect(squareSize + borderWidth / 2, squareSize / 2 + 20, 40, 40);
            break;
        case "west":
            ctx.fillRect(20, squareSize / 2 + 20, 40, 40);
            break;
    }
}
function searchDoors(room) {
    let doors = room.doors;
    console.log(doors)
    console.log(room)
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
    const coinSize = 60; // Tamaño de las monedas
    const padding = 10; // Espacio entre monedas
    const wallPadding = 20; // Espacio entre las monedas y las paredes del cuadro
    const coinsPerRow = Math.floor((squareSize - 2 * wallPadding) / (coinSize + padding));
    coinImage.onload = function () {
        drawAllCoins(coinImage, roomInfo, coinsPerRow, coinSize, padding, wallPadding);
    };
}
let keyPosition = null;

// Función para dibujar la llave
function drawKey(roomInfo) {
    const keyImagePath = "./img/key.png"; // Ruta de la imagen de la llave
    const keyImage = new Image();
    keyImage.src = keyImagePath;
    const keySize = 70; // Tamaño de la llave
    const wallPadding = 50; // Espacio entre la llave y las paredes del cuadro

    keyImage.onload = function () {
        const xPos = x + (squareSize / 2) - (keySize / 2);
        const yPos = y + wallPadding;
        ctx.drawImage(keyImage, xPos, yPos, keySize, keySize);
        keyPosition = { x: xPos, y: yPos, size: keySize };
    };
}


function drawRoom(roomInfo) {
    drawWall()
    searchDoors(roomInfo)
    if (roomInfo.errorMessage != ""){
        errorContainer.innerText = roomInfo.errorMessage;
    }
    if (roomInfo.coins > 0){drawCoins(roomInfo)}
    if (roomInfo.keys > 0){drawKey(roomInfo)}
}

obteinInfo()

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
    const rect = canvas.getBoundingClientRect(); // Coordenadas del canvas en la pantalla
    const mouseX = event.clientX - rect.left; // Coordenada X relativa al canvas
    const mouseY = event.clientY - rect.top; // Coordenada Y relativa al canvas

    // Busca si el clic está dentro de alguna moneda
    for (let i = 0; i < coinPositions.length; i++) {
        const coin = coinPositions[i];
        if (
        mouseX >= coin.x &&
        mouseX <= coin.x + coin.size &&
        mouseY >= coin.y &&
        mouseY <= coin.y + coin.size
        ) {
            console.log(`Hiciste clic en la moneda ${i + 1}`);
            window.location.href = `/getcoin`;
            return; // Sal del bucle al encontrar la moneda clicada
        }
    }

    console.log("No hiciste clic en ninguna moneda");
}
function detectKeyClick(event) {
    if (!keyPosition) return;
    const rect = canvas.getBoundingClientRect(); // Coordenadas del canvas en la pantalla
    const mouseX = event.clientX - rect.left; // Coordenada X relativa al canvas
    const mouseY = event.clientY - rect.top; // Coordenada Y relativa al canvas
    if (
    mouseX >= keyPosition.x &&
    mouseX <= keyPosition.x + keyPosition.size &&
    mouseY >= keyPosition.y &&
    mouseY <= keyPosition.y + keyPosition.size
    ) {
        window.location.href = `/getkey`;
        return; // Sal del bucle al encontrar la moneda clicad
    } else {
        console.log("Clic fuera de la llave");
    }
}


canvas.addEventListener("click", detectKeyClick);
canvas.addEventListener("click", detectCoinClick);
