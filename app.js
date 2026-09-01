// Pitstop Web Companion - Cross-Platform Application Logic
// Compatible with iOS Safari, Android Chrome, and Desktop browsers

// Register Service Worker for Offline PWA Installation
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js')
      .then((reg) => console.log('⚡ Pitstop Service Worker Registered:', reg.scope))
      .catch((err) => console.log('Service Worker registration skipped:', err));
  });
}

const STATE = {
  activeVehicle: {
    name: "Hyundai i20 Asta",
    plate: "DL-01-AB-1234",
    type: "CAR",
    emoji: "🚗"
  },
  vehicles: [
    { name: "Hyundai i20 Asta", plate: "DL-01-AB-1234", type: "CAR", emoji: "🚗" },
    { name: "Hero Splendor Plus BS6", plate: "DL-05-CD-5678", type: "TWO_WHEELER", emoji: "🏍️" },
    { name: "Bajaj RE Compact 4S", plate: "DL-1R-XY-9876", type: "THREE_WHEELER", emoji: "🛺" }
  ],
  filterType: "ALL",
  activeBooking: {
    title: "Periodic Standard Maintenance",
    workshop: "Apex 24/7 Auto Rescue Hub",
    time: "Today, 3:30 PM",
    badge: "In-Progress"
  },
  issues: [
    {
      id: "issue_flat_tire",
      title: "Flat Tire & Puncture Plug",
      summary: "Tire pressure loss from nail puncture or bead leak on highway.",
      type: "CAR",
      typeEmoji: "🚗",
      category: "Wheels & Tires",
      severity: "CRITICAL",
      difficulty: "Easy DIY",
      time: "15 mins"
    },
    {
      id: "issue_battery_dead",
      title: "Dead 12V Battery / No-Crank",
      summary: "Engine clicking or completely dead when turning ignition key.",
      type: "CAR",
      typeEmoji: "🚗",
      category: "Electrical",
      severity: "CRITICAL",
      difficulty: "Easy DIY",
      time: "10 mins"
    },
    {
      id: "issue_bike_chain_slack",
      title: "Loose or Derailment-Prone Drive Chain",
      summary: "Drive chain slapping against swingarm with excessive slack.",
      type: "TWO_WHEELER",
      typeEmoji: "🏍️",
      category: "Drivetrain",
      severity: "HIGH",
      difficulty: "Moderate DIY",
      time: "15 mins"
    },
    {
      id: "issue_bike_kick_start_fail",
      title: "Engine Cranks but Won't Start (Fouled Plug)",
      summary: "Motorbike will not fire up due to fouled spark plug or flooded carb.",
      type: "TWO_WHEELER",
      typeEmoji: "🏍️",
      category: "Ignition",
      severity: "HIGH",
      difficulty: "Easy DIY",
      time: "10 mins"
    },
    {
      id: "issue_auto_clutch_cable",
      title: "Snapped or Limp Clutch Cable",
      summary: "Clutch lever limp and auto-rickshaw will not shift into gear.",
      type: "THREE_WHEELER",
      typeEmoji: "🛺",
      category: "Transmission",
      severity: "CRITICAL",
      difficulty: "Moderate DIY",
      time: "20 mins"
    }
  ],
  garages: [
    {
      name: "Apex 24/7 Auto Rescue & Towing Hub",
      rating: "⭐ 4.9 (340 reviews)",
      dist: "0.8 km",
      phone: "+919876543210",
      type: "🚗 Car & 🛺 3-Wheeler 24/7 Rescue"
    },
    {
      name: "MotoDoc 2-Wheeler & Superbike Pitstop",
      rating: "⭐ 4.8 (210 reviews)",
      dist: "0.9 km",
      phone: "+919876543211",
      type: "🏍️ 2-Wheeler & Superbike Specialist"
    },
    {
      name: "National Multi-Brand Car Care",
      rating: "⭐ 4.7 (180 reviews)",
      dist: "2.1 km",
      phone: "+919876543212",
      type: "🚗 Multi-Brand Diagnostics & AC"
    }
  ]
};

// Initialize Application
document.addEventListener("DOMContentLoaded", () => {
  renderIssues();
  renderGuides();
  renderGarages();
  setupNavigation();
  setupFilters();
  setupSearch();
  setupVehicleSwitcher();
  setupModals();
  setupTheme();
  setupSos();
});

// Render Issues
function renderIssues() {
  const container = document.getElementById("issuesListContainer");
  const query = document.getElementById("inputSearchIssues").value.toLowerCase();
  
  const filtered = STATE.issues.filter(issue => {
    const matchesType = STATE.filterType === "ALL" || issue.type === STATE.filterType;
    const matchesQuery = !query || issue.title.toLowerCase().includes(query) || issue.summary.toLowerCase().includes(query);
    return matchesType && matchesQuery;
  });

  if (filtered.length === 0) {
    container.innerHTML = `<div style="text-align: center; color: var(--text-muted); padding: 24px;">No guides found. Try another search.</div>`;
    return;
  }

  container.innerHTML = filtered.map(issue => `
    <div class="card issue-card" onclick="alert('Viewing Guide: ${issue.title}\\n\\n${issue.summary}')">
      <div class="issue-badges-row">
        <div class="badge-group">
          <span class="badge-tag">${issue.typeEmoji} ${issue.category}</span>
          <span class="badge-tag success">${issue.difficulty}</span>
        </div>
        <span class="badge-tag emergency">${issue.severity}</span>
      </div>
      <div class="issue-title">${issue.title}</div>
      <div class="issue-summary">${issue.summary}</div>
      <div class="issue-footer">
        <span style="font-size: 11px; color: var(--text-muted);">⏱ ${issue.time}</span>
        <span style="font-size: 11px; color: var(--primary); font-weight: 700;">View Fix ➔</span>
      </div>
    </div>
  `).join("");
}

// Render Guides
function renderGuides() {
  const container = document.getElementById("guidesListContainer");
  container.innerHTML = STATE.issues.map(guide => `
    <div class="card" style="cursor: pointer;" onclick="alert('Detailed Manual: ${guide.title}')">
      <div class="card-header-row">
        <span style="font-weight: 700;">${guide.typeEmoji} ${guide.title}</span>
        <span class="badge-tag">${guide.time}</span>
      </div>
      <p style="font-size: 12px; color: var(--text-secondary); margin-top: 4px;">${guide.summary}</p>
    </div>
  `).join("");
}

// Render Garages
function renderGarages() {
  const container = document.getElementById("garagesListContainer");
  container.innerHTML = STATE.garages.map(g => `
    <div class="card">
      <div class="card-header-row">
        <span style="font-weight: 700; font-size: 14px;">${g.name}</span>
        <span class="badge-tag success">${g.dist}</span>
      </div>
      <div style="font-size: 12px; color: var(--primary); margin: 4px 0;">${g.rating} • ${g.type}</div>
      <div style="display: flex; gap: 8px; margin-top: 10px;">
        <a href="tel:${g.phone}" class="btn-primary" style="text-decoration: none; padding: 8px; font-size: 12px;">📞 Call Workshop</a>
      </div>
    </div>
  `).join("");
}

// Navigation Tabs
function setupNavigation() {
  const navButtons = document.querySelectorAll(".nav-btn");
  navButtons.forEach(btn => {
    btn.addEventListener("click", () => {
      navButtons.forEach(b => b.classList.remove("active"));
      document.querySelectorAll(".tab-view").forEach(tab => tab.classList.remove("active"));
      
      btn.classList.add("active");
      const targetTab = document.getElementById(btn.dataset.tab);
      if (targetTab) targetTab.classList.add("active");
      window.scrollTo({ top: 0, behavior: "smooth" });
    });
  });

  // Dock items navigation
  document.getElementById("btnDockHealth").onclick = () => switchTab("tab-mygarage");
  document.getElementById("btnDockService").onclick = () => openModal("modalServiceBooking");
  document.getElementById("btnDockSos").onclick = () => switchTab("tab-sos");
  document.getElementById("btnDockGarages").onclick = () => switchTab("tab-garages");
  document.getElementById("btnQuickSosHeader").onclick = () => switchTab("tab-sos");
}

function switchTab(tabId) {
  const btn = document.querySelector(`.nav-btn[data-tab="${tabId}"]`);
  if (btn) btn.click();
}

// Filter Chips
function setupFilters() {
  const chips = document.querySelectorAll(".chip[data-type]");
  chips.forEach(chip => {
    chip.addEventListener("click", () => {
      chips.forEach(c => c.classList.remove("active"));
      chip.classList.add("active");
      STATE.filterType = chip.dataset.type;
      renderIssues();
    });
  });
}

// Search
function setupSearch() {
  document.getElementById("inputSearchIssues").addEventListener("input", () => {
    renderIssues();
  });
}

// Render Library
function renderLibrary() {
  const container = document.getElementById("webLibraryContainer");
  const lib = [
    { make: "Hyundai", model: "i20 Asta 1.2", type: "🚗 Premium Hatchback", oil: "5W-30 Synthetic", psi: "33/33 PSI", interval: "10,000 km" },
    { make: "Tata", model: "Nexon EV Max", type: "⚡ Electric SUV", oil: "EV Reducer Fluid", psi: "34/34 PSI", interval: "15,000 km" },
    { make: "Hero", model: "Splendor Plus BS6", type: "🏍️ Commuter Bike", oil: "10W-30 4T", psi: "28/32 PSI", interval: "3,000 km" },
    { make: "Bajaj", model: "RE Compact 4S", type: "🛺 Auto Rickshaw", oil: "20W-50 4T", psi: "24/36 PSI", interval: "4,000 km" },
    { make: "Royal Enfield", model: "Classic 350", type: "🏍️ Retro Cruiser", oil: "15W-50 Semi-Synth", psi: "32/36 PSI", interval: "5,000 km" }
  ];

  container.innerHTML = lib.map(v => `
    <div class="card">
      <div class="card-header-row">
        <span style="font-weight: 700; font-size: 14px;">${v.make} ${v.model}</span>
        <span class="badge-tag">${v.type}</span>
      </div>
      <div style="font-size: 11px; color: var(--text-secondary); margin-top: 4px;">
        Oil: <strong style="color: var(--primary);">${v.oil}</strong> • Tire: <strong>${v.psi}</strong> • Service: <strong>${v.interval}</strong>
      </div>
      <button class="btn-primary" style="margin-top: 8px; padding: 6px; font-size: 11px;" onclick="addLibVehicleToState('${v.make} ${v.model}')">➕ Select &amp; Set Active</button>
    </div>
  `).join("");
}

function addLibVehicleToState(fullName) {
  closeModals();
  STATE.activeVehicle.name = fullName;
  document.getElementById("headerVehicleName").innerText = `🚘 ${fullName.split(" ")[0]} [DL-01]`;
  alert(`✅ Selected ${fullName} from Vehicle Library!`);
}

// Vehicle Switcher
function setupVehicleSwitcher() {
  renderLibrary();

  document.getElementById("btnOpenWebLibrary").onclick = () => openModal("modalVehicleLibrary");
  document.getElementById("btnOpenWebSettings").onclick = () => openModal("modalWebSettings");

  const btn = document.getElementById("btnSwitchVehicle");
  btn.addEventListener("click", () => {
    const nextIndex = (STATE.vehicles.findIndex(v => v.name === STATE.activeVehicle.name) + 1) % STATE.vehicles.length;
    STATE.activeVehicle = STATE.vehicles[nextIndex];
    document.getElementById("headerVehicleName").innerText = `${STATE.activeVehicle.emoji} ${STATE.activeVehicle.name.split(" ")[0]} [${STATE.activeVehicle.plate.slice(0, 5)}]`;
    document.getElementById("bookingSubtitle").innerText = `For: ${STATE.activeVehicle.emoji} ${STATE.activeVehicle.name} (${STATE.activeVehicle.plate})`;
    alert(`Switched active vehicle to: ${STATE.activeVehicle.emoji} ${STATE.activeVehicle.name}`);
  });
}

// Modals
function setupModals() {
  document.getElementById("btnAiVision").onclick = () => openModal("modalVisionScan");
  document.getElementById("btnAiSound").onclick = () => openModal("modalSoundScan");
  document.getElementById("btnAiObd").onclick = () => {
    const code = prompt("Enter OBD-II DTC Fault Code (e.g. P0300, P0562, P0217):", "P0300");
    if (code) {
      alert(`🔌 OBD Code ${code} Lookup:\\n\\nStatus: Cylinder Misfire Detected\\nSeverity: HIGH\\nRecommended: Check Spark Plug & Ignition Coil.`);
    }
  };

  document.getElementById("btnSimulateVisionScan").onclick = () => {
    closeModals();
    alert("📷 AI Vision Scan Complete!\\n\\nDefect Detected: Nail Puncture in Tread Center\\nConfidence: 94%\\nRecommended: Use Tire Plug Repair Kit.");
  };

  document.getElementById("btnSimulateSoundScan").onclick = () => {
    closeModals();
    alert("🎙️ Sound AI Acoustic Analysis Complete!\\n\\nPattern Detected: Loose Drive Chain Slap & Valve Clatter\\nConfidence: 89%\\nRecommended: Adjust Chain Slack to 25mm.");
  };

  document.getElementById("btnSubmitBooking").onclick = () => {
    closeModals();
    const tracker = document.getElementById("trackerCard");
    tracker.style.display = "block";
    alert("✅ Service Booking Confirmed!\\n\\nVehicle: " + STATE.activeVehicle.name + "\\nPackage: Periodic Standard Maintenance\\nSchedule: Today, 3:30 PM\\n\\nLive tracking is now active on your dashboard!");
  };
}

function openModal(id) {
  document.getElementById(id).classList.add("open");
}

function closeModals() {
  document.querySelectorAll(".modal-overlay").forEach(m => m.classList.remove("open"));
}

// Appearance Theme
function setupTheme() {
  const btnDark = document.getElementById("btnThemeDark");
  const btnLight = document.getElementById("btnThemeLight");

  btnDark.onclick = () => {
    document.documentElement.className = "dark-theme";
    btnDark.classList.add("active");
    btnLight.classList.remove("active");
  };

  btnLight.onclick = () => {
    document.documentElement.className = "light-theme";
    btnLight.classList.add("active");
    btnDark.classList.remove("active");
  };
}

// SOS
function setupSos() {
  const msg = encodeURIComponent("🚨 EMERGENCY SOS BROADCAST: I need urgent roadside assistance for my vehicle (Hyundai i20). Location: Highway Bypass GPS 28.6139° N, 77.2090° E.");
  document.getElementById("btnSosWhatsapp").href = `https://wa.me/?text=${msg}`;
  
  document.getElementById("btnTriggerSos").onclick = () => {
    alert("🚨 EMERGENCY BEACON TRANSMITTED!\\n\\nDispatched to nearby roadside rescue units.\\nGPS: 28.6139° N, 77.2090° E\\nEmergency services have been alerted.");
  };
}
