import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class CampusResourceSharingServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RootHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Campus Resource Sharing Platform running at http://localhost:" + port);
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String html = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                      <meta charset="UTF-8" />
                      <title>Campus Resource Sharing Platform</title>
                      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                      <style>
                        * {
                          box-sizing: border-box;
                          margin: 0;
                          padding: 0;
                          font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
                        }

                        body {
                          min-height: 100vh;
                          background: linear-gradient(135deg, #3b82f6, #22c55e, #f97316);
                          background-size: 200% 200%;
                          animation: gradientMove 15s ease infinite;
                          color: #0f172a;
                        }

                        @keyframes gradientMove {
                          0% { background-position: 0% 50%; }
                          50% { background-position: 100% 50%; }
                          100% { background-position: 0% 50%; }
                        }

                        .app-container {
                          max-width: 1200px;
                          margin: 20px auto;
                          padding: 16px;
                        }

                        .card {
                          background: rgba(255, 255, 255, 0.95);
                          border-radius: 16px;
                          padding: 20px;
                          box-shadow: 0 10px 30px rgba(15, 23, 42, 0.25);
                          backdrop-filter: blur(10px);
                        }

                        .header {
                          display: flex;
                          justify-content: space-between;
                          align-items: center;
                          gap: 16px;
                          margin-bottom: 20px;
                          color: #0f172a;
                        }

                        .logo-text {
                          font-size: 1.8rem;
                          font-weight: 800;
                          letter-spacing: 0.03em;
                        }

                        .tagline {
                          font-size: 0.95rem;
                          opacity: 0.9;
                        }

                        .badge {
                          display: inline-block;
                          padding: 4px 10px;
                          border-radius: 999px;
                          font-size: 0.75rem;
                          font-weight: 600;
                          background: linear-gradient(135deg, #22c55e, #eab308);
                          color: #052e16;
                        }

                        .layout {
                          display: grid;
                          grid-template-columns: 280px 1fr;
                          gap: 18px;
                          margin-top: 12px;
                        }

                        @media (max-width: 900px) {
                          .layout {
                            grid-template-columns: 1fr;
                          }
                        }

                        .section-title {
                          font-size: 1.1rem;
                          font-weight: 700;
                          margin-bottom: 8px;
                          display: flex;
                          align-items: center;
                          gap: 8px;
                        }

                        .section-title span.icon {
                          width: 22px;
                          height: 22px;
                          display: inline-flex;
                          align-items: center;
                          justify-content: center;
                          border-radius: 999px;
                          font-size: 12px;
                          color: white;
                          background: linear-gradient(135deg, #3b82f6, #8b5cf6);
                        }

                        .text-muted {
                          font-size: 0.86rem;
                          color: #64748b;
                          line-height: 1.4;
                        }

                        .card.secondary {
                          background: rgba(15, 23, 42, 0.9);
                          color: #e5e7eb;
                        }

                        .card.secondary .section-title {
                          color: #e5e7eb;
                        }

                        .nav-btn {
                          border: none;
                          width: 100%;
                          text-align: left;
                          padding: 10px 12px;
                          border-radius: 999px;
                          font-size: 0.9rem;
                          font-weight: 600;
                          margin-bottom: 6px;
                          cursor: pointer;
                          display: flex;
                          align-items: center;
                          justify-content: space-between;
                          background: transparent;
                          color: #e5e7eb;
                          transition: all 0.18s ease;
                        }

                        .nav-btn span.icon {
                          font-size: 1rem;
                          margin-right: 6px;
                        }

                        .nav-btn.active {
                          background: linear-gradient(135deg, #22c55e, #3b82f6);
                          color: #0f172a;
                          box-shadow: 0 6px 16px rgba(34, 197, 94, 0.35);
                        }

                        .nav-btn:not(.active):hover {
                          background: rgba(148, 163, 184, 0.25);
                        }

                        .nav-caption {
                          font-size: 0.75rem;
                          color: #9ca3af;
                          margin-top: 6px;
                        }

                        .input-group {
                          margin-bottom: 10px;
                        }

                        label {
                          display: block;
                          font-size: 0.85rem;
                          font-weight: 600;
                          margin-bottom: 4px;
                          color: #0f172a;
                        }

                        input[type="text"],
                        input[type="email"],
                        input[type="number"],
                        select,
                        textarea {
                          width: 100%;
                          padding: 7px 9px;
                          border-radius: 10px;
                          border: 1px solid #cbd5f5;
                          font-size: 0.9rem;
                          outline: none;
                          transition: box-shadow 0.15s ease, border-color 0.15s ease;
                          background: #f9fafb;
                        }

                        textarea {
                          resize: vertical;
                          min-height: 50px;
                        }

                        input:focus,
                        select:focus,
                        textarea:focus {
                          border-color: #3b82f6;
                          box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.25);
                          background: #ffffff;
                        }

                        .btn {
                          display: inline-flex;
                          align-items: center;
                          justify-content: center;
                          gap: 6px;
                          padding: 8px 16px;
                          border-radius: 999px;
                          border: none;
                          cursor: pointer;
                          font-size: 0.9rem;
                          font-weight: 600;
                          transition: transform 0.1s ease, box-shadow 0.1s ease, background 0.1s ease;
                        }

                        .btn.primary {
                          background: linear-gradient(135deg, #22c55e, #3b82f6);
                          color: #0f172a;
                          box-shadow: 0 6px 16px rgba(34, 197, 94, 0.35);
                        }

                        .btn.primary:hover {
                          transform: translateY(-1px);
                          box-shadow: 0 8px 20px rgba(34, 197, 94, 0.45);
                        }

                        .btn.secondary {
                          background: #0f172a;
                          color: #e5e7eb;
                        }

                        .btn.sm {
                          padding: 5px 10px;
                          font-size: 0.8rem;
                          border-radius: 999px;
                        }

                        .btn:disabled {
                          opacity: 0.5;
                          cursor: not-allowed;
                          transform: none;
                          box-shadow: none;
                        }

                        .status-pill {
                          font-size: 0.75rem;
                          padding: 3px 8px;
                          border-radius: 999px;
                          background: #eff6ff;
                          color: #1d4ed8;
                          display: inline-block;
                        }

                        .status-pill.sold {
                          background: #fee2e2;
                          color: #b91c1c;
                        }

                        .status-pill.completed {
                          background: #dcfce7;
                          color: #15803d;
                        }

                        .section {
                          display: none;
                        }

                        .section.active {
                          display: block;
                        }

                        .section-header {
                          display: flex;
                          justify-content: space-between;
                          align-items: flex-start;
                          margin-bottom: 10px;
                          gap: 10px;
                        }

                        .section-header h2 {
                          font-size: 1.1rem;
                          font-weight: 700;
                        }

                        .section-header p {
                          font-size: 0.85rem;
                          color: #64748b;
                        }

                        .grid {
                          display: grid;
                          grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
                          gap: 12px;
                        }

                        .mini-card {
                          background: #f9fafb;
                          border-radius: 12px;
                          padding: 10px;
                          border: 1px solid #e5e7eb;
                        }

                        .mini-card h3 {
                          font-size: 0.98rem;
                          margin-bottom: 4px;
                        }

                        .mini-card p {
                          font-size: 0.8rem;
                          color: #6b7280;
                          margin-bottom: 3px;
                        }

                        .mini-card-footer {
                          display: flex;
                          justify-content: space-between;
                          align-items: center;
                          margin-top: 6px;
                          gap: 6px;
                        }

                        .empty-state {
                          text-align: center;
                          padding: 20px 10px;
                          font-size: 0.86rem;
                          color: #6b7280;
                        }

                        .badge-small {
                          font-size: 0.75rem;
                          padding: 3px 8px;
                          border-radius: 999px;
                          background: #ecfeff;
                          color: #0891b2;
                        }

                        .toast {
                          position: fixed;
                          right: 16px;
                          bottom: 16px;
                          padding: 10px 14px;
                          background: #16a34a;
                          color: white;
                          border-radius: 999px;
                          font-size: 0.85rem;
                          box-shadow: 0 8px 18px rgba(22, 163, 74, 0.55);
                          opacity: 0;
                          transform: translateY(10px);
                          pointer-events: none;
                          transition: opacity 0.2s ease, transform 0.2s ease;
                          z-index: 50;
                        }

                        .toast.show {
                          opacity: 1;
                          transform: translateY(0);
                          pointer-events: auto;
                        }

                        .highlight-box {
                          padding: 6px 10px;
                          border-radius: 12px;
                          background: #eef2ff;
                          font-size: 0.78rem;
                          color: #4f46e5;
                          margin-top: 8px;
                        }

                        .kpi-row {
                          display: flex;
                          flex-wrap: wrap;
                          gap: 8px;
                          margin-top: 8px;
                        }

                        .kpi-card {
                          flex: 1;
                          min-width: 140px;
                          background: #0f172a;
                          color: #e5e7eb;
                          border-radius: 10px;
                          padding: 7px 10px;
                          font-size: 0.8rem;
                        }

                        .kpi-card span {
                          display: block;
                        }

                        .kpi-label {
                          opacity: 0.7;
                        }

                        .kpi-value {
                          font-weight: 700;
                          font-size: 1rem;
                          margin-top: 2px;
                        }

                        .welcome-user {
                          font-size: 0.9rem;
                          font-weight: 500;
                        }

                        .role-pill {
                          font-size: 0.8rem;
                          padding: 3px 8px;
                          border-radius: 999px;
                          background: #e0f2fe;
                          color: #0369a1;
                          margin-left: 6px;
                        }

                        .reply-ref {
                          font-size: 0.75rem;
                          padding: 4px 6px;
                          border-left: 3px solid #a5b4fc;
                          background: #eef2ff;
                          border-radius: 6px;
                          margin-bottom: 4px;
                          color: #4338ca;
                        }

                        .reply-preview {
                          display: none;
                          margin-bottom: 8px;
                          padding: 6px 8px;
                          border-radius: 8px;
                          background: #fef3c7;
                          font-size: 0.8rem;
                          color: #92400e;
                        }

                        .reply-preview button {
                          border: none;
                          background: transparent;
                          color: #b91c1c;
                          font-size: 0.75rem;
                          margin-left: 8px;
                          cursor: pointer;
                          text-decoration: underline;
                        }

                        .chat-message {
                          padding: 4px 2px;
                          cursor: pointer;
                        }
                      </style>
                    </head>
                    <body>
                      <div class="app-container">
                        <div class="card">
                          <div class="header">
                            <div>
                              <div class="logo-text">
                                Campus Resource Sharing
                              </div>
                              <div class="tagline">
                                Connect juniors & seniors • Share books • Cut costs • Reduce waste
                              </div>
                            </div>
                            <div>
                              <div class="badge">Peer-to-Peer Book & Notes Exchange</div>
                            </div>
                          </div>

                          <div class="layout">
                            <div class="card secondary">
                              <div class="section-title">
                                <span class="icon">🎓</span> Join the Campus Platform
                              </div>
                              <p class="text-muted">
                                Register as a <b>Senior (Seller)</b> or <b>Junior (Buyer)</b> and start sharing academic resources inside your campus.
                              </p>

                              <form id="loginForm" style="margin-top: 12px;">
                                <div class="input-group">
                                  <label for="name">Name</label>
                                  <input id="name" type="text" placeholder="e.g., Rohan Sharma" required />
                                </div>
                                <div class="input-group">
                                  <label for="email">Email</label>
                                  <input id="email" type="email" placeholder="e.g., rohan@college.edu" required />
                                </div>
                                <div class="input-group">
                                  <label for="role">Role</label>
                                  <select id="role" required>
                                    <option value="">Select role</option>
                                    <option value="senior">Senior (Seller / Donor)</option>
                                    <option value="junior">Junior (Buyer)</option>
                                  </select>
                                </div>
                                <button type="submit" class="btn primary" style="width: 100%; margin-top: 4px;">
                                  Enter Platform
                                </button>
                                <p class="nav-caption">
                                  (Prototype: Java serves this page, data is stored in browser memory using JavaScript.)
                                </p>
                              </form>

                              <div id="navArea" style="margin-top: 18px; display: none;">
                                <div class="section-title">
                                  <span class="icon">🧭</span> Platform Flow
                                </div>
                                <button class="nav-btn active" data-section="uploadSection">
                                  <span><span class="icon">📤</span> Upload Books / Notes</span>
                                  <span>Senior</span>
                                </button>
                                <button class="nav-btn" data-section="searchSection">
                                  <span><span class="icon">📚</span> Search & Request Books</span>
                                  <span>Junior</span>
                                </button>
                                <button class="nav-btn" data-section="requestsSection">
                                  <span><span class="icon">🔁</span> Requests & Transactions</span>
                                  <span>Both</span>
                                </button>
                                <button class="nav-btn" data-section="chatSection">
                                  <span><span class="icon">💬</span> Campus Chat Room</span>
                                  <span>Both</span>
                                </button>
                                <button class="nav-btn" data-section="feedbackSection">
                                  <span><span class="icon">⭐</span> Feedback & Evaluation</span>
                                  <span>Both</span>
                                </button>

                                <p class="nav-caption">
                                  Flowchart: Upload → Search → Request → Purchase → Chat → Feedback → Evaluate.
                                </p>
                              </div>
                            </div>

                            <div>
                              <div class="section active" id="welcomeSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Why this platform?</h2>
                                    <p>
                                      Juniors struggle with the <b>high cost of books</b>, while seniors keep unused textbooks and notes.
                                      This campus-only, peer-to-peer website connects both sides for affordable and sustainable resource reuse.
                                    </p>
                                    <div class="highlight-box">
                                      “From high-cost textbooks to a colourful, student-friendly sharing hub — entirely inside one campus.”
                                    </div>
                                  </div>
                                </div>
                                <div class="kpi-row">
                                  <div class="kpi-card">
                                    <span class="kpi-label">Problem Highlight</span>
                                    <span class="kpi-value">High Book Costs</span>
                                  </div>
                                  <div class="kpi-card">
                                    <span class="kpi-label">Proposed Solution</span>
                                    <span class="kpi-value">Senior ↔ Junior Exchange</span>
                                  </div>
                                  <div class="kpi-card">
                                    <span class="kpi-label">Platform Focus</span>
                                    <span class="kpi-value">Campus-Only, Safe & Simple</span>
                                  </div>
                                </div>
                              </div>

                              <div class="section" id="uploadSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Upload Book / Study Material</h2>
                                    <p>
                                      Seniors can list textbooks, reference books, notes, lab manuals or equipment. Juniors will see them in the search area.
                                    </p>
                                  </div>
                                  <div class="welcome-user" id="welcomeUser"></div>
                                </div>

                                <form id="uploadForm">
                                  <div class="grid">
                                    <div>
                                      <div class="input-group">
                                        <label for="bookTitle">Title</label>
                                        <input id="bookTitle" type="text" placeholder="e.g., Data Structures with Java" required />
                                      </div>
                                      <div class="input-group">
                                        <label for="bookAuthor">Author</label>
                                        <input id="bookAuthor" type="text" placeholder="e.g., E. Horowitz" />
                                      </div>
                                      <div class="input-group">
                                        <label for="subject">Subject</label>
                                        <input id="subject" type="text" placeholder="e.g., Data Structures" required />
                                      </div>
                                    </div>
                                    <div>
                                      <div class="input-group">
                                        <label for="semester">Semester</label>
                                        <input id="semester" type="text" placeholder="e.g., 3rd Sem" />
                                      </div>
                                      <div class="input-group">
                                        <label for="price">Price (₹) or 0 for Donation</label>
                                        <input id="price" type="number" min="0" value="0" required />
                                      </div>
                                      <div class="input-group">
                                        <label for="condition">Condition</label>
                                        <select id="condition">
                                          <option value="Like New">Like New</option>
                                          <option value="Good">Good</option>
                                          <option value="Used">Used</option>
                                        </select>
                                      </div>
                                    </div>
                                  </div>

                                  <div class="input-group">
                                    <label for="description">Short Description</label>
                                    <textarea id="description" placeholder="Include edition, publication year, or extra notes..."></textarea>
                                  </div>

                                  <button type="submit" class="btn primary">
                                    📤 List this book / material
                                  </button>
                                </form>

                                <p class="text-muted" style="margin-top: 10px;">
                                  Once submitted, this item will appear in the <b>Search & Request</b> section for juniors.
                                </p>
                              </div>

                              <div class="section" id="searchSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Search & Request Books</h2>
                                    <p>
                                      Juniors can filter by subject, semester and price. Requesting a book sends a message (inside this page) to the senior who uploaded it.
                                    </p>
                                  </div>
                                  <div class="badge-small">Step: Search → Select → Send Request</div>
                                </div>

                                <div class="mini-card" style="margin-bottom: 10px;">
                                  <div class="grid">
                                    <div>
                                      <div class="input-group">
                                        <label for="searchQuery">Title / Subject</label>
                                        <input id="searchQuery" type="text" placeholder="e.g., Java, Algorithms..." />
                                      </div>
                                    </div>
                                    <div>
                                      <div class="input-group">
                                        <label for="searchSemester">Semester</label>
                                        <input id="searchSemester" type="text" placeholder="e.g., 3rd Sem" />
                                      </div>
                                    </div>
                                    <div>
                                      <div class="input-group">
                                        <label for="maxPrice">Max Price (₹)</label>
                                        <input id="maxPrice" type="number" min="0" placeholder="e.g., 500" />
                                      </div>
                                    </div>
                                  </div>
                                  <button id="searchBtn" class="btn secondary" style="margin-top: 5px;">
                                    🔍 Search Available Materials
                                  </button>
                                </div>

                                <div id="searchResults"></div>
                              </div>

                              <div class="section" id="requestsSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Requests & Transactions</h2>
                                    <p>
                                      This area shows the <b>flow of requests</b>. Seniors see incoming requests, juniors see their outgoing requests and status.
                                    </p>
                                  </div>
                                  <div class="badge-small">Status: Pending → Completed</div>
                                </div>

                                <div class="grid">
                                  <div class="mini-card">
                                    <h3>Incoming Requests (for Seniors)</h3>
                                    <p>View all requests juniors have made on your uploaded books and mark transactions as completed.</p>
                                    <div id="incomingRequests"></div>
                                  </div>
                                  <div class="mini-card">
                                    <h3>My Requests (for Juniors)</h3>
                                    <p>Track which books you have requested, see status, and give feedback after completion.</p>
                                    <div id="myRequests"></div>
                                  </div>
                                </div>
                              </div>

                              <div class="section" id="chatSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Campus Chat Room</h2>
                                    <p>
                                      A simple chat space where seniors and juniors can talk, clarify doubts, and coordinate exchanges.
                                      Swipe a message to reply. Long press (2 sec) your own message to delete it.
                                    </p>
                                  </div>
                                  <div class="badge-small">Normal conversation / interactions</div>
                                </div>

                                <div class="mini-card" id="chatMessages" style="max-height: 260px; overflow-y: auto;">
                                  <!-- chat messages will appear here -->
                                </div>

                                <form id="chatForm" class="mini-card" style="margin-top: 8px;">
                                  <div id="replyPreview" class="reply-preview"></div>
                                  <div class="input-group">
                                    <label for="chatInput">Message</label>
                                    <textarea id="chatInput" placeholder="Type your message to everyone..." required></textarea>
                                  </div>
                                  <button type="submit" class="btn primary sm">💬 Send Message</button>
                                  <p class="text-muted" style="margin-top:6px;">
                                    (Prototype chat: messages are stored in this browser. In a full system they sync across all devices via server.)
                                  </p>
                                </form>
                              </div>

                              <div class="section" id="feedbackSection">
                                <div class="section-header">
                                  <div>
                                    <h2>Feedback & Evaluation</h2>
                                    <p>
                                      After transactions are done, students can share quick feedback and rating. This helps evaluate how well the system supports affordable resource sharing.
                                    </p>
                                  </div>
                                  <div class="badge-small">Supports your “Evaluate Results” step</div>
                                </div>

                                <div id="feedbackList" class="mini-card" style="margin-bottom: 10px;">
                                </div>

                                <div class="mini-card">
                                  <h3>Platform Summary (Auto Calculated)</h3>
                                  <p class="text-muted">
                                    These simple metrics help in research evaluation: how many resources listed, how many requests, and
                                    how many were completed successfully.
                                  </p>
                                  <div class="kpi-row">
                                    <div class="kpi-card">
                                      <span class="kpi-label">Total Books Listed</span>
                                      <span class="kpi-value" id="kpiBooks">0</span>
                                    </div>
                                    <div class="kpi-card">
                                      <span class="kpi-label">Total Requests</span>
                                      <span class="kpi-value" id="kpiRequests">0</span>
                                    </div>
                                    <div class="kpi-card">
                                      <span class="kpi-label">Completed Transactions</span>
                                      <span class="kpi-value" id="kpiCompleted">0</span>
                                    </div>
                                  </div>
                                </div>
                              </div>

                            </div>
                          </div>
                        </div>

                        <div id="toast" class="toast"></div>
                      </div>

                      <script>
                        const state = {
                          currentUser: null,
                          books: [],
                          requests: [],
                          feedbacks: [],
                          chatMessages: [],
                          replyTo: null
                        };

                        const loginForm = document.getElementById("loginForm");
                        const navArea = document.getElementById("navArea");
                        const toastEl = document.getElementById("toast");
                        const welcomeUser = document.getElementById("welcomeUser");
                        const replyPreviewEl = document.getElementById("replyPreview");
                        const chatInput = document.getElementById("chatInput");

                        const sections = {
                          welcome: document.getElementById("welcomeSection"),
                          upload: document.getElementById("uploadSection"),
                          search: document.getElementById("searchSection"),
                          requests: document.getElementById("requestsSection"),
                          chat: document.getElementById("chatSection"),
                          feedback: document.getElementById("feedbackSection")
                        };

                        function showToast(message) {
                          toastEl.textContent = message;
                          toastEl.classList.add("show");
                          setTimeout(() => toastEl.classList.remove("show"), 2200);
                        }

                        function switchSection(id) {
                          Object.values(sections).forEach(sec => sec.classList.remove("active"));
                          if (sections[id]) {
                            sections[id].classList.add("active");
                          }
                        }

                        function updateKPIs() {
                          document.getElementById("kpiBooks").textContent = state.books.length;
                          document.getElementById("kpiRequests").textContent = state.requests.length;
                          const completedCount = state.requests.filter(r => r.status === "Completed").length;
                          document.getElementById("kpiCompleted").textContent = completedCount;
                        }

                        function renderBooksForSearch(filteredBooks) {
                          const container = document.getElementById("searchResults");
                          container.innerHTML = "";

                          if (!filteredBooks.length) {
                            container.innerHTML = '<div class="empty-state">No matching materials found yet. Ask seniors to upload or try different filters.</div>';
                            return;
                          }

                          const grid = document.createElement("div");
                          grid.className = "grid";

                          filteredBooks.forEach(book => {
                            const div = document.createElement("div");
                            div.className = "mini-card";

                            div.innerHTML = `
                              <h3>${book.title}</h3>
                              <p><b>Subject:</b> ${book.subject || "-"} | <b>Semester:</b> ${book.semester || "-"}</p>
                              <p><b>Seller:</b> ${book.sellerName}</p>
                              <p><b>Price:</b> ₹${book.price} ${book.price === 0 ? '<span class="pill pill-green">Donation</span>' : ""}</p>
                              <p style="margin-top:4px;">${book.description || ""}</p>
                              <div class="mini-card-footer">
                                <span class="status-pill ${book.status === "Sold" ? "sold" : ""}">
                                  ${book.status === "Sold" ? "Sold / Completed" : "Available"}
                                </span>
                                <button class="btn sm primary" ${book.status === "Sold" ? "disabled" : ""} data-book-id="${book.id}">
                                  Request Book
                                </button>
                              </div>
                            `;
                            grid.appendChild(div);
                          });

                          container.appendChild(grid);

                          container.querySelectorAll("button[data-book-id]").forEach(btn => {
                            btn.addEventListener("click", () => {
                              const bookId = parseInt(btn.getAttribute("data-book-id"), 10);
                              handleBookRequest(bookId);
                            });
                          });
                        }

                        function renderRequests() {
                          const incoming = document.getElementById("incomingRequests");
                          const myReq = document.getElementById("myRequests");

                          incoming.innerHTML = "";
                          myReq.innerHTML = "";

                          const user = state.currentUser;
                          if (!user) {
                            incoming.innerHTML = '<div class="empty-state">Login to view requests.</div>';
                            myReq.innerHTML = '<div class="empty-state">Login to view your requests.</div>';
                            return;
                          }

                          const userIncoming = state.requests.filter(r => {
                            const book = state.books.find(b => b.id === r.bookId);
                            return book && book.sellerEmail === user.email;
                          });

                          const userOutgoing = state.requests.filter(r => r.buyerEmail === user.email);

                          if (!userIncoming.length) {
                            incoming.innerHTML = '<div class="empty-state">No incoming requests yet.</div>';
                          } else {
                            userIncoming.forEach(req => {
                              const book = state.books.find(b => b.id === req.bookId);
                              const div = document.createElement("div");
                              div.className = "mini-card";
                              div.style.marginTop = "6px";

                              div.innerHTML = `
                                <p><b>Book:</b> ${book ? book.title : "-"}</p>
                                <p><b>Buyer:</b> ${req.buyerName} (${req.buyerEmail})</p>
                                <p><b>Message:</b> ${req.message}</p>
                                <p style="margin-top:3px;">
                                  <span class="status-pill ${req.status === "Completed" ? "completed" : ""}">
                                    ${req.status}
                                  </span>
                                </p>
                              `;

                              if (req.status === "Pending") {
                                const btn = document.createElement("button");
                                btn.className = "btn sm primary";
                                btn.textContent = "Mark Transaction Completed";
                                btn.style.marginTop = "6px";
                                btn.addEventListener("click", () => {
                                  req.status = "Completed";
                                  if (book) {
                                    book.status = "Sold";
                                  }
                                  showToast("Transaction marked as completed!");
                                  renderRequests();
                                  updateKPIs();
                                  renderBooksForSearch(state.books);
                                });
                                div.appendChild(btn);
                              }

                              incoming.appendChild(div);
                            });
                          }

                          if (!userOutgoing.length) {
                            myReq.innerHTML = '<div class="empty-state">You have not requested any books yet.</div>';
                          } else {
                            userOutgoing.forEach(req => {
                              const book = state.books.find(b => b.id === req.bookId);
                              const div = document.createElement("div");
                              div.className = "mini-card";
                              div.style.marginTop = "6px";

                              div.innerHTML = `
                                <p><b>Book:</b> ${book ? book.title : "-"}</p>
                                <p><b>Seller:</b> ${book ? book.sellerName + " (" + book.sellerEmail + ")" : "-"}</p>
                                <p><b>Your message:</b> ${req.message}</p>
                                <p style="margin-top:3px;">
                                  <span class="status-pill ${req.status === "Completed" ? "completed" : ""}">
                                    ${req.status}
                                  </span>
                                </p>
                              `;

                              if (req.status === "Completed") {
                                const alreadyFeedback = state.feedbacks.some(f => f.bookId === req.bookId && f.from === user.email);
                                if (!alreadyFeedback) {
                                  const btn = document.createElement("button");
                                  btn.className = "btn sm secondary";
                                  btn.textContent = "Give Feedback";
                                  btn.style.marginTop = "6px";
                                  btn.addEventListener("click", () => {
                                    giveFeedback(book, req);
                                  });
                                  div.appendChild(btn);
                                }
                              }

                              myReq.appendChild(div);
                            });
                          }
                        }

                        function renderFeedback() {
                          const container = document.getElementById("feedbackList");
                          container.innerHTML = "";

                          if (!state.feedbacks.length) {
                            container.innerHTML = '<div class="empty-state">No feedback yet. Once transactions complete, students can share comments here.</div>';
                            return;
                          }

                          const header = document.createElement("h3");
                          header.textContent = "Student Feedback";
                          container.appendChild(header);

                          state.feedbacks.forEach(fb => {
                            const book = state.books.find(b => b.id === fb.bookId);
                            const div = document.createElement("div");
                            div.className = "mini-card";
                            div.style.marginTop = "6px";

                            div.innerHTML = `
                              <p><b>Book:</b> ${book ? book.title : "-"}</p>
                              <p><b>From:</b> ${fb.from}</p>
                              <p><b>Rating:</b> <span class="pill pill-green">${fb.rating}/5</span></p>
                              <p><b>Comment:</b> ${fb.comment}</p>
                            `;
                            container.appendChild(div);
                          });
                        }

                        function updateReplyPreview() {
                          if (!state.replyTo) {
                            replyPreviewEl.style.display = "none";
                            replyPreviewEl.innerHTML = "";
                            return;
                          }

                          const text = state.replyTo.text || "";
                          const short = text.length > 60 ? text.slice(0, 57) + "..." : text;

                          replyPreviewEl.style.display = "block";
                          replyPreviewEl.innerHTML = `
                            Replying to <b>${state.replyTo.name}</b>: “${short}”
                            <button type="button" id="cancelReplyBtn">cancel</button>
                          `;

                          const cancelBtn = document.getElementById("cancelReplyBtn");
                          cancelBtn.addEventListener("click", () => {
                            state.replyTo = null;
                            updateReplyPreview();
                          });
                        }

                        function handleSwipeReply(message) {
                          state.replyTo = message;
                          updateReplyPreview();
                          switchSection("chat");
                          chatInput.focus();
                        }

                        function handleLongPressDelete(message) {
                          const user = state.currentUser;
                          if (!user) {
                            showToast("Login first to manage chat messages.");
                            return;
                          }
                          if (user.email !== message.email) {
                            showToast("You can delete only your own messages.");
                            return;
                          }
                          const ok = confirm("Delete this message?");
                          if (!ok) return;
                          state.chatMessages = state.chatMessages.filter(m => m.id !== message.id);
                          renderChat();
                        }

                        function attachSwipeAndLongPress(div, message) {
                          let startX = null;
                          let startY = null;
                          let isTouch = false;
                          let longPressTimeout = null;
                          let moved = false;

                          function startPress(x, y) {
                            startX = x;
                            startY = y;
                            moved = false;
                            longPressTimeout = setTimeout(() => {
                              longPressTimeout = null;
                              handleLongPressDelete(message);
                            }, 2000); // 2 sec
                          }

                          function move(x, y) {
                            if (startX === null) return;
                            const dx = x - startX;
                            const dy = y - startY;
                            if (Math.abs(dx) > 5 || Math.abs(dy) > 5) {
                              moved = true;
                            }
                          }

                          function endPress(x, y) {
                            if (longPressTimeout) {
                              clearTimeout(longPressTimeout);
                              longPressTimeout = null;
                            }
                            if (startX === null) return;

                            const dx = x - startX;
                            if (Math.abs(dx) > 60) {
                              // treat as swipe → reply
                              handleSwipeReply(message);
                            }

                            startX = null;
                            startY = null;
                          }

                          // Touch events
                          div.addEventListener("touchstart", (e) => {
                            isTouch = true;
                            const t = e.touches[0];
                            startPress(t.clientX, t.clientY);
                          });

                          div.addEventListener("touchmove", (e) => {
                            const t = e.touches[0];
                            move(t.clientX, t.clientY);
                          });

                          div.addEventListener("touchend", (e) => {
                            const t = e.changedTouches[0];
                            endPress(t.clientX, t.clientY);
                          });

                          // Mouse events (for desktop)
                          div.addEventListener("mousedown", (e) => {
                            if (isTouch) return; // ignore if touch in use
                            if (e.button !== 0) return;
                            startPress(e.clientX, e.clientY);

                            function mouseMove(ev) {
                              move(ev.clientX, ev.clientY);
                            }

                            function mouseUp(ev) {
                              document.removeEventListener("mousemove", mouseMove);
                              document.removeEventListener("mouseup", mouseUp);
                              endPress(ev.clientX, ev.clientY);
                            }

                            document.addEventListener("mousemove", mouseMove);
                            document.addEventListener("mouseup", mouseUp);
                          });
                        }

                        function renderChat() {
                          const container = document.getElementById("chatMessages");
                          container.innerHTML = "";

                          if (!state.chatMessages.length) {
                            container.innerHTML = '<div class="empty-state">No messages yet. Start a conversation with your campus mates!</div>';
                            return;
                          }

                          state.chatMessages.forEach(msg => {
                            const div = document.createElement("div");
                            div.className = "chat-message";

                            let replyPart = "";
                            if (msg.replyToId) {
                              const prevText = msg.replyToText || "";
                              const short = prevText.length > 60 ? prevText.slice(0, 57) + "..." : prevText;
                              replyPart = `
                                <div class="reply-ref">
                                  Replying to <b>${msg.replyToName || "Someone"}</b>:
                                  “${short}”
                                </div>
                              `;
                            }

                            div.innerHTML = `
                              <p><b>${msg.name}</b> <span style="font-size:0.75rem;opacity:0.7;">(${msg.role}) • ${msg.time}</span></p>
                              ${replyPart}
                              <p style="font-size:0.85rem;">${msg.text}</p>
                              <hr style="border:none;border-top:1px solid #e5e7eb;margin:4px 0;">
                            `;
                            container.appendChild(div);

                            attachSwipeAndLongPress(div, msg);
                          });

                          container.scrollTop = container.scrollHeight;
                        }

                        function handleBookRequest(bookId) {
                          const user = state.currentUser;
                          if (!user) {
                            showToast("Please login as a junior to request a book.");
                            return;
                          }
                          if (user.role !== "junior") {
                            showToast("Only junior accounts can request books.");
                            return;
                          }
                          const book = state.books.find(b => b.id === bookId);
                          if (!book) return;
                          if (book.status === "Sold") {
                            showToast("This book is already sold / completed.");
                            return;
                          }

                          const message = prompt("Enter a short message to the seller:", "Hi, I am interested in this book. Is it still available?");
                          if (message === null || message.trim() === "") return;

                          const req = {
                            id: Date.now(),
                            bookId,
                            buyerName: user.name,
                            buyerEmail: user.email,
                            message: message.trim(),
                            status: "Pending"
                          };

                          state.requests.push(req);
                          showToast("Request sent to the seller!");
                          renderRequests();
                          updateKPIs();
                        }

                        function giveFeedback(book, req) {
                          const ratingStr = prompt("Rate your experience (1-5):", "5");
                          if (ratingStr === null) return;
                          const rating = parseInt(ratingStr, 10);
                          if (isNaN(rating) || rating < 1 || rating > 5) {
                            alert("Please enter a number between 1 and 5.");
                            return;
                          }
                          const comment = prompt("Write a short feedback comment:", "Very helpful, book was in good condition.");
                          if (comment === null || comment.trim() === "") return;

                          const fb = {
                            id: Date.now(),
                            bookId: book.id,
                            from: state.currentUser.email,
                            rating,
                            comment: comment.trim()
                          };
                          state.feedbacks.push(fb);
                          showToast("Thank you for your feedback!");
                          renderFeedback();
                          updateKPIs();
                        }

                        loginForm.addEventListener("submit", (e) => {
                          e.preventDefault();
                          const name = document.getElementById("name").value.trim();
                          const email = document.getElementById("email").value.trim();
                          const role = document.getElementById("role").value;

                          if (!name || !email || !role) {
                            showToast("Please fill all fields.");
                            return;
                          }

                          state.currentUser = { name, email, role };
                          navArea.style.display = "block";
                          sections.welcome.classList.add("active");
                          showToast(`Welcome, ${name.split(" ")[0]}!`);
                          welcomeUser.innerHTML = `
                            Logged in as <b>${name}</b>
                            <span class="role-pill">${role === "senior" ? "Senior (Seller)" : "Junior (Buyer)"}</span>
                          `;

                          if (role === "senior") {
                            switchSection("upload");
                          } else {
                            switchSection("search");
                          }
                        });

                        document.querySelectorAll(".nav-btn").forEach(btn => {
                          btn.addEventListener("click", () => {
                            document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("active"));
                            btn.classList.add("active");
                            const sectionId = btn.getAttribute("data-section");
                            if (sectionId === "uploadSection") switchSection("upload");
                            else if (sectionId === "searchSection") switchSection("search");
                            else if (sectionId === "requestsSection") { switchSection("requests"); renderRequests(); }
                            else if (sectionId === "chatSection") { switchSection("chat"); renderChat(); }
                            else if (sectionId === "feedbackSection") { switchSection("feedback"); renderFeedback(); updateKPIs(); }
                          });
                        });

                        const uploadForm = document.getElementById("uploadForm");
                        uploadForm.addEventListener("submit", (e) => {
                          e.preventDefault();
                          const user = state.currentUser;
                          if (!user || user.role !== "senior") {
                            showToast("Only seniors can upload materials.");
                            return;
                          }

                          const title = document.getElementById("bookTitle").value.trim();
                          const author = document.getElementById("bookAuthor").value.trim();
                          const subject = document.getElementById("subject").value.trim();
                          const semester = document.getElementById("semester").value.trim();
                          const price = parseInt(document.getElementById("price").value, 10) || 0;
                          const condition = document.getElementById("condition").value;
                          const description = document.getElementById("description").value.trim();

                          const book = {
                            id: Date.now(),
                            title,
                            author,
                            subject,
                            semester,
                            price,
                            condition,
                            description,
                            sellerName: user.name,
                            sellerEmail: user.email,
                            status: "Available"
                          };
                          state.books.push(book);
                          uploadForm.reset();
                          showToast("Book / material listed successfully!");
                          updateKPIs();
                        });

                        document.getElementById("searchBtn").addEventListener("click", () => {
                          const query = document.getElementById("searchQuery").value.trim().toLowerCase();
                          const sem = document.getElementById("searchSemester").value.trim().toLowerCase();
                          const maxPriceStr = document.getElementById("maxPrice").value.trim();
                          const maxPrice = maxPriceStr ? parseInt(maxPriceStr, 10) : null;

                          const filtered = state.books.filter(b => {
                            if (query && !(b.title.toLowerCase().includes(query) || b.subject.toLowerCase().includes(query))) return false;
                            if (sem && !(b.semester && b.semester.toLowerCase().includes(sem))) return false;
                            if (maxPrice !== null && b.price > maxPrice) return false;
                            return true;
                          });

                          renderBooksForSearch(filtered);
                        });

                        const chatForm = document.getElementById("chatForm");

                        chatForm.addEventListener("submit", (e) => {
                          e.preventDefault();
                          const user = state.currentUser;
                          if (!user) {
                            showToast("Login first to send chat messages.");
                            return;
                          }
                          const text = chatInput.value.trim();
                          if (!text) return;

                          const now = new Date();
                          const time = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

                          const msg = {
                            id: Date.now(),
                            name: user.name,
                            email: user.email,
                            role: user.role === "senior" ? "Senior" : "Junior",
                            text,
                            time
                          };

                          if (state.replyTo) {
                            msg.replyToId = state.replyTo.id;
                            msg.replyToName = state.replyTo.name;
                            msg.replyToText = state.replyTo.text;
                          }

                          state.chatMessages.push(msg);
                          chatInput.value = "";
                          state.replyTo = null;
                          updateReplyPreview();
                          renderChat();
                        });

                        renderBooksForSearch(state.books);
                        updateKPIs();
                        renderChat();
                        updateReplyPreview();
                      </script>
                    </body>
                    </html>
                    """;

            byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}
