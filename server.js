const express = require("express");
const path = require("path");
const app = express();

// Render / cloud port
const PORT = process.env.PORT || 8080;

// Static files (index.html, etc.)
app.use(express.static(__dirname));

// Main page: send index.html
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "index.html"));
});

app.listen(PORT, () => {
  console.log(`🚀 Server running on port ${PORT}`);
});
