# 🚀 One-Click Free Deployment Guide (Vercel & GitHub Pages)

Your project has been fully configured with automated configuration files, PWA Service Worker offline caching, and automated GitHub Actions workflows for **100% Free** one-click hosting.

---

## ⚡ Option A: One-Click Vercel Deployment (Fastest & Custom URL)

### How to Deploy:
1. Go to [vercel.com/new](https://vercel.com/new) and log in with your GitHub account (free).
2. Select your `vehiclefix` repository and click **Import**.
3. Vercel will automatically detect `vercel.json` (already pre-configured to deploy `web/` with clean URLs and security headers).
4. Click **Deploy**.
5. Within 20 seconds, your web app is live with free global HTTPS at:
   ```
   https://vehiclefix.vercel.app  (or your custom name)
   ```

---

## 🐙 Option B: One-Click GitHub Pages Deployment (Automatic on Git Push)

We created a GitHub Actions workflow in [`.github/workflows/deploy-pages.yml`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/.github/workflows/deploy-pages.yml).

### How to Activate:
1. In your GitHub repository, click **Settings** (tab at the top).
2. On the left sidebar, click **Pages**.
3. Under **Build and deployment → Source**, change the dropdown from *Deploy from a branch* to **GitHub Actions**.
4. That's it! Every time you push code (`git push origin main`), GitHub will automatically build and publish your web app to:
   ```
   https://<your-github-username>.github.io/vehiclefix/
   ```

---

## 📱 How Users Install It on Their Phones

Because we added a **Service Worker (`sw.js`)** and **Web App Manifest (`manifest.json`)**:

### On Android (Chrome / Brave / Samsung Internet):
1. Open your Vercel or GitHub Pages URL.
2. A prompt **"Add Pitstop to Home Screen"** or **"Install App"** will appear at the bottom.
3. Tap **Install** — it adds a native-like app icon with offline vehicle diagnostic lookup!

### On iPhone (Safari):
1. Open the URL in Safari.
2. Tap the **Share** button (box with upward arrow).
3. Scroll down and tap **"Add to Home Screen"**.

---

## 📂 Configuration Files Summary

| File | Purpose |
|---|---|
| [`vercel.json`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/vercel.json) | Directs Vercel to serve `/web` with HTTPS headers & PWA caching |
| [`web/vercel.json`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/web/vercel.json) | Subfolder Vercel config if importing `web/` directly |
| [`.github/workflows/deploy-pages.yml`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/.github/workflows/deploy-pages.yml) | Automated GitHub Actions CI/CD for GitHub Pages |
| [`web/sw.js`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/web/sw.js) | Service worker for offline caching and fast load times |
| [`web/.nojekyll`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/web/.nojekyll) | Ensures GitHub Pages serves all assets and scripts raw |
| [`web/privacy_policy.html`](file:///c:/Users/ANKIT%20MAJHI/AndroidStudioProjects/vehiclefix/web/privacy_policy.html) | Live Privacy Policy page linked inside the web app |
