# Kosh - Product Decision Log

All product and UX decisions are recorded here for reference.

---

## D001 - Logo Direction
**Date:** 08 Mar 2026
**Decision:** Modern Vault (V5A variant) - rounded square with vault dial, 4 cardinal ticks, center dot. No needle, thicker strokes, reduced corner radius.
**Rationale:** Financial SaaS identity. Vault communicates security/trust. Dial adds distinctiveness. Works as favicon down to 16px.
**Logo file:** `designs/shared/logo.png`

## D002 - Design Personality
**Date:** 08 Mar 2026
**Decision:** Hybrid - premium dark chrome + clean data layouts
**Rationale:** CRED-like premium feel for the shell (sidebar, header, branding) but with Stripe/Linear-level clarity for data-heavy sections. Finance data must be scannable, never sacrificed for aesthetics.

## D003 - Color System
**Date:** 08 Mar 2026
**Decision:** Gold (#C9973A) as primary brand, teal (#4EAAA0) as accent. Warm palette rooted in the treasury/kosh meaning. Final refinement at SPO's discretion.
**Open:** CEO may share existing HTML files to further inspire the theme.

## D004 - MVP0 Scope
**Date:** 08 Mar 2026
**Decision:** Five modules for first sprint:
1. Dashboard (net worth overview)
2. Income & Expenses
3. Investments - MF, Stocks, FD, RD
4. Liabilities - Loans, Credit Cards
5. Protection - Term Insurance, Health Insurance

Scope may expand if CEO deems fit during design phase.

## D005 - Tech Stack
**Date:** 08 Mar 2026
**Decision:**
- Backend: Spring Boot + PostgreSQL
- Frontend: React 18+, TypeScript, Vite, TailwindCSS 4, shadcn/ui, TanStack Router, TanStack Query, React Hook Form + Zod, Zustand (financial state), React Context (profile/theme), Recharts, Framer Motion

## D006 - Design Workflow
**Date:** 08 Mar 2026
**Decision:** Design phase uses pure HTML/CSS/JS (Tailwind CDN) as Figma replacement. Duplication across pages is acceptable and intentional. Each page must be self-contained and openable in a browser. Engineer phase extracts reusable React components from the designs.

## D007 - User Modes
**Date:** 08 Mar 2026
**Decision:** Three view modes - "My View" (individual), "Our View" (couple/household aggregate), and per-member view. Household mode is the primary design target.

## D008 - Family Model
**Date:** 08 Mar 2026
**Decision:** One person signs up and manages their personal finances. They can invite an existing or new user to form a "Family." This enables a second portfolio - the Family portfolio - with the same views and pages, but a new tab appears at the top (e.g., Personal / Family) to toggle between portfolios. Both portfolios share the same page structure and components.
**Implication:** Every data page needs a Personal/Family tab switcher. Family view aggregates data from all members. The tab is only visible once a family has been formed.

## D009 - Data Entry Model
**Date:** 08 Mar 2026
**Decision:** 100% manual entry for MVP0. Kosh is not a certified FIU. Auto-sync (bank APIs, MF CAS import) may come in future versions.
**Implication:** Every data type needs an "Add" flow. Forms are critical UX - they must be fast, smart (defaults, dropdowns for known instruments), and forgiving (easy edit/delete). Bulk entry should be considered for things like MF transactions.

## D010 - Dashboard Approach
**Date:** 08 Mar 2026
**Decision:** CEO has a rough idea, SPO to brainstorm together with CEO before designing.

## D011 - Design Language Calibration from Existing Work
**Date:** 08 Mar 2026
**Decision:** CEO shared 6 existing HTML design files (landing page, income, income-family, expense, mutual funds, mutual-funds-family). These establish the canonical Kosh design language. CLAUDE.md updated to match:
- Fonts: Fraunces (display) + Plus Jakarta Sans (body) - NOT Bricolage Grotesque / DM Sans
- Gold: #C9A84C - NOT #C9973A
- Dark bg: #0F0F0F / cards: #161616 / borders: #2A2A2A - NOT the bluer tones
- Module-themed gradient banners with stat pills
- Custom dropdown (.kd), date picker (.kdp), modal patterns already established
- Hand-drawn SVG charts with interactive tooltips
- Personal/Family pill-toggle with member avatars
All new pages MUST match these existing patterns exactly.

## D012 - Mutual Funds: Data Onboarding
**Date:** 10 Mar 2026
**Decision:** CAS PDF import (primary) + manual Add Transaction (secondary). CAS is the main onboarding path; manual entry for ongoing tracking.
**Rationale:** CAS reports contain complete transaction history. Import gets users productive immediately.

## D013 - Mutual Funds: SIP is a Transaction Type, Not an Entity
**Date:** 10 Mar 2026
**Decision:** SIP is one of 3 transaction types (SIP, Lumpsum, Sell) - NOT a separate entity. No SIP commitment tracking. No SIP tab in the table. Single "Holdings" tab only.
**Rationale:** Kosh tracks what happened (transactions), not what's scheduled (mandates). SIP mandates live in broker apps. Simplifies data model significantly.

## D014 - Mutual Funds: XIRR Computed from Cashflows
**Date:** 10 Mar 2026
**Decision:** XIRR is computed from actual transaction cashflows, not approximated. Displayed at portfolio level (banner) and fund level (table + detail).
**Rationale:** XIRR is the standard for measuring MF returns in India. Every serious investor expects it.

## D015 - Mutual Funds: No Regular vs Direct Distinction
**Date:** 10 Mar 2026
**Decision:** Each scheme-folio combination is a separate holding. No UI distinction between Regular and Direct plans.
**Rationale:** From CAS perspective, they're different schemes with different ISINs. Users who hold both see them as separate rows naturally.

## D016 - Mutual Funds: Category Auto-Detection
**Date:** 10 Mar 2026
**Decision:** Categories (Large Cap, Mid Cap, Flexi Cap, etc.) auto-detected from scheme name patterns. No manual categorization needed.
**Rationale:** Scheme names reliably contain category keywords. Reduces data entry burden.

## D017 - Mutual Funds: Broker as User-Tagged Metadata
**Date:** 10 Mar 2026
**Decision:** Broker (Groww, Zerodha Coin, Kuvera, etc.) is user-tagged per folio - not auto-detected. CAS import preview includes broker tagging dropdowns.
**Rationale:** CAS doesn't contain broker info. Collecting it during import is the most efficient time.

## D018 - Mutual Funds: Stamp Duty Handling
**Date:** 10 Mar 2026
**Decision:** Stamp duty parsed from CAS, rolled into parent transaction cost, NOT displayed separately in UI.
**Rationale:** Stamp duty is a minor cost component. Showing it clutters the UI without adding value.

## D019 - Mutual Funds: Sector Exposure Chart Dropped
**Date:** 10 Mar 2026
**Decision:** No sector exposure chart in MF page. Replaced with Fund Weightage horizontal bars.
**Rationale:** No reliable free API for sector-level fund holdings data. Fund weightage is more actionable.

## D020 - Mutual Funds: Page Header Buttons
**Date:** 10 Mar 2026
**Decision:** Two buttons only: "Import CAS" (outlined) + "Add Transaction" (primary gold).
**Rationale:** These are the only two actions needed. Import CAS for bulk onboarding, Add Transaction for manual entry.

## D021 - Mutual Funds: Banner Stat Pills
**Date:** 10 Mar 2026
**Decision:** 4 stat pills: Funds | XIRR | Invested | Returns. No SIP/Month pill.
**Rationale:** Aligned with decision D013 - SIP is not tracked as a separate concept.

## D022 - Holdings Table: Units & NAV Columns Removed
**Date:** 10 Mar 2026
**Decision:** Units and NAV columns removed from the holdings table. Now 7 columns: Fund | Category | Invested | Current | Returns | XIRR | Broker.
**Rationale:** Units and NAV are implementation details, not decision-making data at portfolio level. They remain visible in the fund detail modal summary banner.

## D023 - Fund Detail as Modal
**Date:** 10 Mar 2026
**Decision:** Fund detail is shown in a wide modal (820px max-width) instead of replacing the portfolio page. Contains summary banner, SIP info, charts, and transaction history.
**Rationale:** Page replacement lost scroll position and context. Modal keeps the portfolio visible behind backdrop, consistent with Add Transaction and CAS Import modals.

## D024 - SIP as Recurring Transaction Template
**Date:** 10 Mar 2026
**Decision:** SIP transactions create a recurring template (fund + amount + day of month). Kosh auto-records monthly installments. Users manage SIPs (Pause/Edit/Delete) from fund detail modal. Holdings table shows SIP chip (emerald=active, amber=paused). Add Transaction modal shows "Recurring Day" dropdown (1-28) when type=SIP.
**Rationale:** Users create SIPs at brokers and want Kosh to track monthly installments automatically. Kosh mirrors the schedule for record-keeping. Past transactions from deleted SIPs are preserved. Supersedes D013 partially - SIP is still a transaction type but now has a recurring template aspect.

## D025 - Transaction Form: Zod Discriminated Union for Type-Based Validation
**Date:** 10 Mar 2026
**Decision:** Use `z.discriminatedUnion("type", [...])` for the Add Transaction form (React Hook Form + Zod). Each transaction type (SIP, Lumpsum, Sell) defines its own schema branch. SIP requires `recurringDay` (1-28), no date. Lumpsum/Sell require `date`, no recurring day. Edit SIP modal uses a separate `editSipSchema` (amount + recurringDay only).
**Rationale:** Discriminated unions let Zod validate different field sets per transaction type with zero custom conditional logic. The schema itself is the source of truth for which fields are required. Eliminates brittle `.refine()` chains and keeps form validation declarative.
**Schema reference:**
```ts
const transactionSchema = z.discriminatedUnion("type", [
  z.object({ type: z.literal("SIP"), fundName: z.string().min(1), amount: z.number().positive(), recurringDay: z.number().int().min(1).max(28), folio: z.string().optional(), broker: z.string().min(1), notes: z.string().max(200).optional() }),
  z.object({ type: z.literal("Lumpsum"), fundName: z.string().min(1), amount: z.number().positive(), date: z.date(), folio: z.string().optional(), broker: z.string().min(1), notes: z.string().max(200).optional() }),
  z.object({ type: z.literal("Sell"), fundName: z.string().min(1), amount: z.number().positive(), date: z.date(), folio: z.string().optional(), broker: z.string().min(1), notes: z.string().max(200).optional() }),
]);
const editSipSchema = z.object({ amount: z.number().positive(), recurringDay: z.number().int().min(1).max(28) });
```

## D026 - Personal/Family Toggle Always Visible
**Date:** 10 Mar 2026
**Decision:** The Personal/Family toggle is always visible on every data page (MF, Stocks, Income, etc.), even for solo users. Solo users see an empty state + invite CTA on the Family tab. Family-enabled users see aggregated data with member breakdown. Eliminates the need for separate `-family.html` design files - one file per page handles both states inline.
**Supersedes:** D008 implication ("tab is only visible once a family has been formed") - toggle is now always shown.
**Rationale:** Consistent UI surface, discoverable family feature for solo users, reduces design file count by ~50%.

## D027 - Notification Panel in Topbar
**Date:** 10 Mar 2026
**Decision:** The bell icon in the topbar opens a dropdown notification panel (380px, right-aligned). Features: unread count badge, per-item "Mark read", bulk "Mark all read", empty state ("All caught up!"). Notification types include SIP auto-recorded, NAV updated, milestones, SIP paused, CAS import complete. Panel is a topbar feature - self-contained in every design HTML page with identical CSS, JS, and mock data.
**Rationale:** Notifications are a core app-shell feature. Keeping them self-contained per page maintains the design-as-Figma-replacement philosophy where every page is fully functional in isolation.

## D028 - Family Invite Flow
**Date:** 10 Mar 2026
**Decision:** "Invite Family Member" button (in Family tab empty state) opens a modal with: Email (required), Relationship (required: Spouse/Partner, Parent, Sibling, Child, Other). No name field - picked from the invitee's profile on account creation. Confirmation step shows email sent + next steps. "Learn more" link points to `https://docs.kosh.app/family-portfolios` (external docs site, opens in new tab).
**Rationale:** Minimal friction invite flow. Name comes from the invitee's own profile, not the inviter's assumption. Relationship is mandatory for family structure clarity.
