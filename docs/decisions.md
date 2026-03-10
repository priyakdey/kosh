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

## D029 — Income & Expense: Deferred with Coming Soon Pages
**Date:** 10 Mar 2026
**Decision:** Income & Expense modules deferred — the cash inflow/outflow accounting model needs more thought (SIP is not an expense, account-level tracking, daily data entry burden). Pages show a polished "Coming Soon" state. Nav links remain clickable with a "Soon" badge.
**Rationale:** Better to ship a deliberate "coming soon" than a half-baked design. The accounting model for income/expense in a wealth tracker (vs a budgeting app) is fundamentally different and deserves dedicated product thinking.

## D030 — Service Unavailable Error State
**Date:** 10 Mar 2026
**Decision:** Designed a reusable "Service Unavailable" error state page (`assets/mutual-funds-service-not-available.html`). Full app shell remains functional (sidebar, topbar, notifications). Main content area replaced with a centered error state. Final animation chosen: **Server Rack** — a server rack with 5 slots, each with independently flickering red status lights (staggered CSS keyframes), a broken cable on the left with kosh-gold sparks, a warning triangle, and floating signal-lost dots. Also includes: signal-lost badge (top-right, fading red bars + X), staggered entrance animations (fadeSlideUp), "Try Again" button with loading spinner, subtle error code (`ERR_SERVICE_UNAVAILABLE · 503`), and "Back to Dashboard" fallback link. Tone is calm and reassuring — "your data is safe."
**Explored alternatives:** Rotating vault clock, pulse flatline (ECG), broken chain, scattered coins, cloud disconnect, shattered shield, hourglass glitch. Server rack selected for its clear visual metaphor and premium feel.
**Rationale:** In a wealth tracker, inability to see financial data triggers anxiety. The error state must reassure users their data is intact while providing a clear recovery path. The server rack metaphor is universally understood and the flickering lights convey "something is wrong but the system is still alive."

**Engineer notes:**
- Extract as a reusable `<ServiceError />` component (e.g., `src/components/shared/ServiceError.tsx`)
- Props: `moduleName: string` (displayed as subtitle above heading), `onRetry: () => void` (wired to TanStack Query's `refetch`), `backTo?: { label: string; href: string }` (defaults to Dashboard)
- Trigger this component when any API call returns a 5xx status code. Use TanStack Query's `isError` + `error.status >= 500` to conditionally render `<ServiceError />` in place of the page's main content area

---

## D031 — All Unbuilt Pages: Coming Soon Treatment
**Date:** 10 Mar 2026
**Decision:** Extended the Coming Soon pattern (D029) to all 9 unbuilt pages: Dashboard, Stocks, Fixed Deposits, Recurring Deposits, Credit Cards, Loans, Term Insurance, Health Insurance, Settings/Profile. Each gets a polished placeholder with module-specific icon, description, and feature preview. Nav sidebar on ALL existing pages updated with "Soon" badges for unbuilt modules.
**Rationale:** Consistent UX — no dead links anywhere in the app. Every nav link leads to a real page. Users see the full product vision even during the design phase.
- The app shell (sidebar, topbar, notifications) remains fully functional — only the `<main>` content swaps
- All animations are CSS-only (keyframes defined in component styles or Tailwind config) — no Framer Motion needed for this component
- The "Try Again" button should call `onRetry()` which triggers `queryClient.refetchQueries()` or the specific query's `refetch()`. Show the spinner state while refetching (use query's `isFetching` state)
- Dark/light theme support is built into the design — uses Tailwind dark: classes throughout

---

## D032 — Fixed Deposits: Single "Add FD" Button
**Date:** 10 Mar 2026
**Decision:** No import flow for FDs. Single "Add FD" button in page header. All FDs are manually entered.
**Rationale:** Users typically have 3-10 FDs. No standardized import format exists for FDs (unlike CAS for MFs). Manual entry is acceptable for this volume.

## D033 — Fixed Deposits: Quarterly Compounding Standard
**Date:** 10 Mar 2026
**Decision:** All FD maturity calculations use quarterly compounding: `P × (1 + r/400)^(4t)` where P=principal, r=annual rate, t=years.
**Rationale:** Matches RBI guidelines for Indian bank term deposits. Most banks compound quarterly. This is the industry standard for FD maturity computation.

## D034 — Fixed Deposits: Only Active FDs Shown
**Date:** 10 Mar 2026
**Decision:** The FD table only shows active FDs. Matured and broken FDs are removed from the view entirely. "Maturing Soon" badge (amber) shown on active FDs with ≤90 days to maturity. No Status column or filter needed.
**Rationale:** Kosh is a wealth tracker — matured/broken FDs no longer represent held wealth. Showing them adds clutter without value. The "Maturing Soon" badge provides actionable urgency on the maturity date cell.

## D035 — Fixed Deposits: Mark FD as Broken (Tracking Action)
**Date:** 10 Mar 2026
**Decision:** "Mark as Broken" is a Kosh tracking action — it removes the FD from the active portfolio view. It does NOT break the FD at the bank. The modal clearly states: "This only updates your Kosh records. To actually break this FD, visit your bank's net banking portal or branch." An estimated penalty display is shown (reduced rate = original − 1%, estimated amount received, interest lost) with a note that actual penalty terms may vary by bank. Tax-saving FDs cannot be marked as broken before 5-year lock-in — action is disabled with a warning. Confirm button reads "Remove from Portfolio."
**Rationale:** Kosh is a tracker, not a transactional platform. Users must visit their bank to actually break an FD. The estimated penalty display provides useful context, but the disclaimer prevents users from relying on it as exact figures.

## D036 — Fixed Deposits: No Interest Payout Tracking
**Date:** 10 Mar 2026
**Decision:** For non-cumulative FDs, Kosh does NOT track actual interest credits as transactions. However, the FD Detail modal shows a projected payout schedule table (dates + amounts). Actual income tracking is deferred to the Income module.
**Rationale:** Tracking individual interest payouts as transactions adds data entry burden without meaningful insight at the FD module level. The projected schedule provides visibility; actual tracking belongs in Income.

## D037 — Fixed Deposits: Banner Gradient Cyan
**Date:** 10 Mar 2026
**Decision:** Dark: `linear-gradient(135deg, #0f2a2e 0%, #0a1f24 50%, #0d1a1f 100%)`. Light: `linear-gradient(135deg, #e0f2f1 0%, #b2dfdb 50%, #e0f7fa 100%)`.
**Rationale:** Cyan differentiates FD from MF (indigo) and other modules. Consistent with the nav dot color (bg-cyan-500) for Fixed Deposits.

## D038 — Fixed Deposits: 4 Stat Pills
**Date:** 10 Mar 2026
**Decision:** FDs (count) | Avg Rate (weighted average) | Invested (total principal) | Interest (total accrued). No XIRR pill.
**Rationale:** FDs have fixed known returns — XIRR is unnecessary. Weighted avg rate is the most meaningful metric for comparing FD portfolio yield.

## D039 — Fixed Deposits: 3 Charts
**Date:** 10 Mar 2026
**Decision:** Maturity Timeline stacked bar chart (3 cols, principal + interest segments with hover tooltips) + Bank-wise Allocation donut (2 cols) + Highlights card (full width below). No Interest Rate Distribution chart.
**Rationale:** Maturity timeline is the primary decision-making visual for FDs (when do I get my money back?). Stacked bars show principal vs interest split at a glance. Hover tooltips show per-FD details (bank, dates, amounts). Bank-wise allocation shows concentration risk. Rate info is already in the table — a separate chart would be redundant. Three charts is the right density.

## D040 — Fixed Deposits: Table 7 Columns
**Date:** 10 Mar 2026
**Decision:** Bank | FD Type | Principal | Rate | Tenure | Maturity Date | Maturity Amt. Interest payout type shown as small tag below bank name. No Status column (only active FDs shown; "Maturing Soon" amber badge on Maturity Date cell).
**Rationale:** These 7 columns capture all decision-relevant FD information. Status would be redundant since only active FDs are displayed.

## D041 — Fixed Deposits: FD Detail as 720px Modal
**Date:** 10 Mar 2026
**Decision:** FD Detail shown in a 720px max-width modal (wider than Add FD's 540px but narrower than MF's 820px). Contains: compact cyan summary banner, interest breakdown table (quarterly compounding schedule for cumulative; projected payout schedule for non-cumulative), tenure progress bar, and action buttons (Edit, Mark as Broken, Delete).
**Rationale:** 720px accommodates the interest breakdown table comfortably. Wider than Add FD because the detail view has more data density. Narrower than MF detail because FDs have no transaction history or charts to display.

## D042 — Mutual Funds: Portfolio X-Ray, Concentration Risk & Highlights Deferred to MVP1
**Date:** 10 Mar 2026
**Decision:** The following MF analytics features are deferred from MVP0 to MVP1 — they need deeper research before implementation:
1. **Portfolio X-Ray** — Tabbed donut card showing Category, Sector, Market Cap, and Holdings (company overlap) breakdowns. Requires real aggregation logic across underlying fund portfolios, reliable data sources for scheme-level holdings, and sector/market-cap classification mapping.
2. **Concentration Risk / Portfolio Health** — Warnings for unhealthy overlap in companies, sectors, or market-cap segments across MF holdings. Thresholds (e.g., single stock >5%, sector >30%, small-cap >30%) need validation against Indian market norms and user research.
3. **Highlights card** — "Best Performer", "Portfolio XIRR", "Largest Holding" summary card. Straightforward to build but bundled with the analytics row redesign.
**Rationale:** These features depend on scheme-level portfolio data (which stocks/sectors each fund holds) that isn't available from mfapi.in. Need to research data sources (AMFI, ValueResearch, Morningstar APIs), determine refresh cadence, and validate that aggregated overlap numbers are meaningful for Indian MF portfolios. Building this with mock data risks shipping misleading analytics. Better to ship MVP0 with the simple Category donut + Fund Weightage bars (which use user's own transaction data) and add the X-Ray suite in MVP1 with real data backing.
**MVP0 keeps:** Category allocation donut (derived from user's holdings), Fund Weightage horizontal bars, simple Highlights card.
**MVP1 adds:** Portfolio X-Ray (sector, market cap, company overlap), Portfolio Health card with concentration risk checks.

## D043 — Recurring Deposits: Page Structure Mirrors FD
**Date:** 10 Mar 2026
**Decision:** RD page follows the same structure as FD: sky-blue gradient banner, analytics row (maturity timeline + bank-wise donut + highlights), data table, Add/Detail/Close modals. Personal/Family toggle with identical pattern.
**Rationale:** RD and FD are structurally closest instruments (bank-based, tenure-based, maturity-based). Consistent page structure reduces cognitive load for users who manage both.

## D044 — Recurring Deposits: Sky Blue Theme
**Date:** 10 Mar 2026
**Decision:** RD uses sky-blue color family (`#082f49` dark banner, `#0EA5E9` accents) to differentiate from FD's cyan/teal (`#0f2a2e` dark banner, `#06B6D4` accents). Nav dot is `bg-sky-500`.
**Rationale:** Sky and cyan are visually distinct but both in the blue family, communicating that RD and FD are related but different instruments.

## D045 — Recurring Deposits: No Type/Payout Variants
**Date:** 10 Mar 2026
**Decision:** RDs have no type variants (unlike FD's Regular/Tax-saving/Senior Citizen/Corporate/Flexi) and are always cumulative (no payout frequency options). No auto-renewal option.
**Rationale:** In Indian banking, RDs are simpler than FDs — always cumulative, no tax-saving variant, no payout options. The Add RD modal is therefore simpler (9 fields vs FD's 13).

## D046 — Recurring Deposits: Installment Tracking
**Date:** 10 Mar 2026
**Decision:** Table shows "X / Y" installments paid with a 4px micro-progress bar. "Missed" red badge appears when installmentsPaid < expected installments based on months elapsed. Detail modal shows "Installment Progress" section with next due date.
**Rationale:** Monthly installment tracking is the key differentiator from FDs. Users need to see at a glance which RDs are on track and which have missed payments.

## D047 — Recurring Deposits: Table 7 Columns
**Date:** 10 Mar 2026
**Decision:** Bank | Monthly Amt | Installments (X/Y + bar) | Rate | Tenure | Maturity Date | Projected Maturity. Only bank filter (no type filter). "Cumulative" tag shown below bank name.
**Rationale:** Replaces FD's Principal and Type columns with Monthly Amt and Installments to reflect RD's deposit-over-time nature.

## D048 — Recurring Deposits: Close RD Modal Simplified
**Date:** 10 Mar 2026
**Decision:** "Close RD" modal (480px) is simpler than FD's "Break FD" — no lock-in warning, no tax-saving check. Shows penalty rate (−1%), deposited total, amount received, and interest lost.
**Rationale:** RDs have no lock-in period unlike tax-saving FDs, so the premature closure flow needs fewer warnings.

## D049 — Recurring Deposits: RD Maturity Computation
**Date:** 10 Mar 2026
**Decision:** RD maturity computed using iterative quarterly compounding on monthly deposits: each month adds monthlyAmt to balance, every 3rd month the balance compounds at rate/400. "Invested" in banner = sum of (installmentsPaid × monthlyAmt), not committed total.
**Rationale:** RD interest accrues on a growing principal (unlike FD's fixed principal), requiring iterative computation. Showing actual deposited amount (not committed) gives users an accurate picture of money at work.
