const express = require("express");
const app = express();

const PORT = process.env.PORT || 8000;

app.get("/", (req, res) => {
  res.send("✅ Campus Resource Sharing Server live hai (fixed URL)!");
});

app.get("/api/status", (req, res) => {
  res.json({
    status: "ok",
    message: "Server sahi se chal raha hai Render pe."
  });
});

app.listen(PORT, () => {
  console.log(`🚀 Server running on port ${PORT}`);
});
