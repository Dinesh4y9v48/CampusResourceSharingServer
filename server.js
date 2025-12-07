const express = require("express");
const path = require("path");
const app = express();

// Render / Cloud port
const PORT = process.env.PORT || 8080;

// Static files serve (CSS, JS, images if any)
app.use(express.static(__dirname));

// Serve your HTML file
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "index.html"));
});

app.listen(PORT, () => {
  console.log(`🚀 Server running on port ${PORT}`);
});
