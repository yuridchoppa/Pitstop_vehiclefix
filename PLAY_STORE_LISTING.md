# 📱 Google Play Store Listing & Submission Kit

Use this document to copy and paste your store listing metadata directly into [Google Play Console](https://play.google.com/console).

---

## 1. App Store Metadata

### **App Name** *(Max 30 characters)*
```
Pitstop: Vehicle AI & SOS
```

### **Short Description** *(Max 80 characters)*
```
AI car diagnosis, OBD2 code reader, roadside SOS beacon & verified local garages
```

### **Full Description** *(Formatted for Google Play Store)*
```markdown
⚡ Pitstop is the all-in-one vehicle companion for cars, motorcycles, and 3-wheelers. Diagnose engine faults, decode OBD-II dashboard warning lights with AI, broadcast emergency roadside breakdown beacons, and book certified mechanics in seconds.

Whether you are stranded on the highway with a flat tire, diagnosing engine knocks, or planning periodic maintenance, Pitstop gives you instant mechanical intelligence right in your pocket.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🌟 KEY FEATURES & CAPABILITIES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✦ 📷 AI Visual & Acoustic Diagnostics
• Visual Defect Classifier: Snap a photo of mechanical leaks, belt wear, or dashboard warning lights for instant troubleshooting.
• Acoustic Sound Analyzer: Listen to engine knocks, brake squeals, or exhaust rattles with pattern detection.

✦ 🔌 Instant OBD-II Trouble Code (DTC) Decoder
• Decode thousands of standard powertrain, chassis, body, and network DTC error codes (e.g. P0300, P0562, P0217).
• Learn root causes, recommended replacement parts with cost estimates, and DIY fix feasibility.

✦ 🚨 One-Tap Roadside Emergency SOS & Safety Beacon
• Broadcast your exact GPS milestone location via WhatsApp/SMS to emergency contacts in 1 tap.
• Instant speed-dial to 24/7 National Highway Rescue (1033) and Emergency Services (112).
• Built-in Amber Hazard strobe screen and flashlight for nighttime safety.

✦ 📖 Interactive Step-by-Step DIY Repair Guides
• 100% offline-accessible repair walkthroughs for cars, bikes, and autos.
• Interactive tool & parts checklist with safety precaution warnings and built-in cooldown timers.

✦ 📍 24/7 Verified Garage & Towing Directory
• Search nearby certified multi-brand workshops, puncture repair centers, EV battery specialists, and flatbed tow trucks.
• Direct calling and integrated map directions.

✦ 🚘 Multi-Vehicle Garage Fleet Manager
• Manage specs, odometer tracking, service logs, and recommended tire pressures (PSI) across your entire household fleet.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔒 OFFLINE-FIRST & PRIVACY FOCUSED
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Essential repair manuals and diagnostic databases work completely offline without cellular network.
• No annoying full-screen ads or intrusive background location tracking.
```

---

## 2. Google Play Console Setup Parameters

| Field | Value |
|---|---|
| **Default Language** | English (United States) / English (India) |
| **App Category** | `Auto & Vehicles` |
| **Tags** | Auto diagnostics, Car repair, Roadside assistance, OBD2 scanner, Mechanic guides |
| **Content Rating** | Everyone (PEGI 3 / ESRB Everyone) |
| **Target Audience** | Ages 18 and older (Licensed drivers & motorists) |
| **Contains Ads** | No |
| **Privacy Policy URL** | Host `web/privacy_policy.html` on GitHub Pages or your web domain |

---

## 3. Data Safety Form Responses (Play Console)

- **Location**:
  - *Collected?* Yes (Approximate & Precise)
  - *Shared?* No (Only transmitted via user-initiated SMS/WhatsApp SOS broadcast)
  - *Purpose?* App functionality & emergency roadside rescue
  - *Ephemeral?* Yes (Not retained on external servers)
- **Photos / Videos**:
  - *Collected?* No (Photos are processed on-device for AI visual diagnosis)
- **Personal Info (Name, Phone)**:
  - *Collected?* Stored locally on-device for motorist profile and rescue dispatches
- **Security Practices**:
  - Data encrypted in transit (HTTPS)
  - User can reset or delete local app data anytime

---

## 4. Keystore & Signing Credentials Reference

- **Keystore File**: `pitstop-release.jks`
- **Keystore Alias**: `pitstop`
- **Keystore Password**: `pitstoprelease2026`
- **Key Password**: `pitstoprelease2026`
- **Validity**: 10,000 Days (~27 Years)
