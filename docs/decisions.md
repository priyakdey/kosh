# Kosh — Product Decision Log

All product and UX decisions are recorded here for reference.

---

## D001 — Logo Direction
**Date:** 08 Mar 2026
**Decision:** Modern Vault (V5A variant) — rounded square with vault dial, 4 cardinal ticks, center dot. No needle, thicker strokes, reduced corner radius.
**Rationale:** Financial SaaS identity. Vault communicates security/trust. Dial adds distinctiveness. Works as favicon down to 16px.
**Logo file:** `designs/shared/logo.png`

## D002 — Design Personality
**Date:** 08 Mar 2026
**Decision:** Hybrid — premium dark chrome + clean data layouts
**Rationale:** CRED-like premium feel for the shell (sidebar, header, branding) but with Stripe/Linear-level clarity for data-heavy sections. Finance data must be scannable, never sacrificed for aesthetics.

## D003 — Color System
**Date:** 08 Mar 2026
**Decision:** Gold (#C9973A) as primary brand, teal (#4EAAA0) as accent. Warm palette rooted in the treasury/kosh meaning. Final refinement at SPO's discretion.
**Open:** CEO may share existing HTML files to further inspire the theme.

## D004 — MVP0 Scope
**Date:** 08 Mar 2026
**Decision:** Five modules for first sprint:
1. Dashboard (net worth overview)
2. Income & Expenses
3. Investments — MF, Stocks, FD, RD
4. Liabilities — Loans, Credit Cards
5. Protection — Term Insurance, Health Insurance

Scope may expand if CEO deems fit during design phase.

## D005 — Tech Stack
**Date:** 08 Mar 2026
**Decision:**
- Backend: Spring Boot + PostgreSQL
- Frontend: React 18+, TypeScript, Vite, TailwindCSS 4, shadcn/ui, TanStack Router, TanStack Query, React Hook Form + Zod, Zustand (financial state), React Context (profile/theme), Recharts, Framer Motion

## D006 — Design Workflow
**Date:** 08 Mar 2026
**Decision:** Design phase uses pure HTML/CSS/JS (Tailwind CDN) as Figma replacement. Duplication across pages is acceptable and intentional. Each page must be self-contained and openable in a browser. Engineer phase extracts reusable React components from the designs.

## D007 — User Modes
**Date:** 08 Mar 2026
**Decision:** Three view modes — "My View" (individual), "Our View" (couple/household aggregate), and per-member view. Household mode is the primary design target.

## D008 — Family Model
**Date:** 08 Mar 2026
**Decision:** One person signs up and manages their personal finances. They can invite an existing or new user to form a "Family." This enables a second portfolio — the Family portfolio — with the same views and pages, but a new tab appears at the top (e.g., Personal / Family) to toggle between portfolios. Both portfolios share the same page structure and components.
**Implication:** Every data page needs a Personal/Family tab switcher. Family view aggregates data from all members. The tab is only visible once a family has been formed.

## D009 — Data Entry Model
**Date:** 08 Mar 2026
**Decision:** 100% manual entry for MVP0. Kosh is not a certified FIU. Auto-sync (bank APIs, MF CAS import) may come in future versions.
**Implication:** Every data type needs an "Add" flow. Forms are critical UX — they must be fast, smart (defaults, dropdowns for known instruments), and forgiving (easy edit/delete). Bulk entry should be considered for things like MF transactions.

## D010 — Dashboard Approach
**Date:** 08 Mar 2026
**Decision:** CEO has a rough idea, SPO to brainstorm together with CEO before designing.

## D011 — Design Language Calibration from Existing Work
**Date:** 08 Mar 2026
**Decision:** CEO shared 6 existing HTML design files (landing page, income, income-family, expense, mutual funds, mutual-funds-family). These establish the canonical Kosh design language. CLAUDE.md updated to match:
- Fonts: Fraunces (display) + Plus Jakarta Sans (body) — NOT Bricolage Grotesque / DM Sans
- Gold: #C9A84C — NOT #C9973A
- Dark bg: #0F0F0F / cards: #161616 / borders: #2A2A2A — NOT the bluer tones
- Module-themed gradient banners with stat pills
- Custom dropdown (.kd), date picker (.kdp), modal patterns already established
- Hand-drawn SVG charts with interactive tooltips
- Personal/Family pill-toggle with member avatars
All new pages MUST match these existing patterns exactly.
