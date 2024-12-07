document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".game-time").forEach(cell => {
        let seconds = parseInt(cell.textContent, 10);
        let hours = Math.floor(seconds / 3600);
        let minutes = Math.floor((seconds % 3600) / 60);
        let secs = seconds % 60;
        cell.textContent = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    });
});