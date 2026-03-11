# Kosh - Product Requirements Document

**Product:** Kosh (कोष - "treasury")
**Version:** MVP0
**Last Updated:** 08 Mar 2026
**Author:** CPO (Chief Product Officer)
**Stakeholder:** CEO (Priyak Dey)

---

## 1. Product Vision

Kosh is a personal and household financial wealth tracking application purpose-built for the Indian market. It gives individuals, couples, and families a single place to see their complete financial picture - income, expenses, investments, liabilities, and protection.

**Core insight:** Indian households manage money collectively, but no tool treats the household as a first-class concept. Kosh does.

**One-line pitch:** Your complete financial operating system - built for India, designed for families.

---

## 2. Target Users

### Primary
- **Indian salaried professionals (25-45)** who have diversified finances across multiple instruments (MF SIPs, stocks, FDs, PPF, NPS, loans, insurance) and want a unified view.
- **Couples** who want to manage finances together - see combined net worth, household income, shared goals - while keeping individual finances organized.

### Secondary
- **Families** (parents + adult children) who want visibility into the household's overall financial health.

### User Personas
| Persona | Profile | Pain Point |
|---------|---------|------------|
| Rahul (32) | Software engineer, ₹2.4L/mo income, 6 MF SIPs, 2 FDs, home loan, term insurance | Tracks finances in 4 different apps + a spreadsheet. No single view of net worth. |
| Priya (30) | Product manager, ₹1.8L/mo income, 3 MF SIPs, PPF, health insurance | Wants to see combined household finances with Rahul. Currently relies on a shared Google Sheet. |

---

## 3. Product Principles

1. **Household-first.** The family/couple view is not an afterthought - it's the primary design target. Individual view is a subset.
2. **Data density over aesthetics.** Users come to see numbers. Never sacrifice information for visual flair. Use progressive disclosure - summary first, details on demand.
3. **India-native.** Real instrument names (SIP, SWP, XIRR, NAV), real AMC names, amounts in lakhs/crores, Indian fiscal year (Apr-Mar). No "localized western app" feel.
4. **Manual entry, by design.** Kosh does not connect to bank accounts. Users own their data completely. This is a feature, not a limitation - it means zero credential sharing and full data control.
5. **Equality in the household.** Family members are equal participants. No "owner" vs "member" hierarchy. No percentage comparisons between partners. The household view celebrates the combined picture.
6. **Privacy by design.** Data encrypted at rest and in transit. No bank credentials ever collected. Each family member controls what they share.

---

## 4. MVP0 Scope

### 4.1 Modules

| Module | Personal View | Family View | Key Features | Module PRD |
|--------|:---:|:---:|---|---|
| **Dashboard** | Yes | Yes | Net worth overview, key metrics, quick summaries of all modules | Pending |
| **Income** | Yes | Yes | Monthly income tracking, source categorization, trends | Pending |
| **Expenses** | Yes | Yes | Categorized expenses, monthly trends, recurring expense tracking | Pending |
| **Mutual Funds** | Yes | Yes | SIP tracking, portfolio allocation, fund-level detail, XIRR, CAS import | [mutual-funds.md](mutual-funds.md) |
| **Stocks** | Yes | Yes | Holdings, P&L, sector allocation | Pending |
| **Fixed Deposits** | Yes | Yes | Bank, tenure, rate, maturity tracking | [fixed-deposits.md](fixed-deposits.md) |
| **Recurring Deposits** | Yes | Yes | Monthly RD tracking, maturity projection | [recurring-deposits.md](recurring-deposits.md) |
| **Loans** | Yes | Yes | Home/personal/car loan EMI tracking, outstanding balance | Pending |
| **Credit Cards** | Yes | Yes | Outstanding amount, due dates, utilization | Pending |
| **Term Insurance** | Yes | Yes | Policies, coverage, premium schedule | Pending |
| **Health Insurance** | Yes | Yes | Policies, coverage, family members covered | Pending |

### 4.2 Cross-Cutting Features

- **Personal/Family toggle** - pill switcher on every data page. Always visible, even for solo users (D026). Solo users see an invite CTA on the Family tab (D028).
- **Add/Edit/Delete flows** - every data type has a modal-based entry form with smart defaults, dropdowns for known instruments, easy edit/delete.
- **Dark mode (primary) / Light mode (secondary)** - theme toggle, persisted in localStorage.
- **Auth** - Google Sign-In (MVP0). Email/password planned for future.

### 4.3 Explicitly Out of Scope (MVP0)

- Auto-sync with banks or investment platforms (Kosh is not a certified FIU)
- Tax computation or 80C tracking
- Budget planning or alerts
- Mobile app (web-first, responsive comes in engineering phase)
- Multi-currency support
- Export/reports

---

## 5. User Modes & Family Model

### 5.1 Individual Mode
Single user's financial picture. All data is theirs. Personal/Family toggle is still visible - Family tab shows an empty state with an invite CTA (D026, D028).

### 5.2 Household Mode
A user invites another user (via link) to form a "Family." This enables:
- A **Family portfolio** alongside the Personal portfolio
- A **Personal / Family** pill toggle appears on every data page
- Family view aggregates data from all members
- Each member's individual data remains private unless shared via the family view

### 5.3 Family Member Equality
- All family members are equal - no "owner" or "admin" role labels displayed
- No percentage breakdowns of "who owns what" in the household view
- The household view shows combined totals and each member's contribution as a flat amount (not a comparison)

---

## 6. Information Architecture

### 6.1 Navigation Structure
```
Sidebar (260px, collapsible to 68px)
├── Dashboard
├── Income
├── Expense
├── Assets (group label)
│   ├── Mutual Funds
│   ├── Stocks
│   ├── Fixed Deposits
│   └── Recurring Deposits
├── Liabilities (group label)
│   ├── Loans
│   └── Credit Cards
├── Protection (group label)
│   ├── Term Insurance
│   └── Health Insurance
└── Profile (bottom, above sidebar border)
```

### 6.2 Page Structure Pattern (every data page)
1. **Page header** - Title (Fraunces) + subtitle + action buttons (month selector, Add button)
2. **Portfolio toggle** - Personal/Family pill switcher (if family exists) + member avatars
3. **Summary banner** - Module-themed gradient background, hero amount, change chip, stat pills
4. **Analytics row** - Grid of chart cards (trend chart, donut/bar chart, highlights)
5. **Data table** - Tabbed (e.g., All Entries / Recurring), sortable, with pagination
6. **Add/Edit modal** - Triggered by Add button, blurred backdrop, form fields

---

## 7. Data Entry Philosophy

Manual entry is a core product decision, not a compromise. Rationale:
- **Security:** No banking credentials ever leave the user's control
- **Data ownership:** Users have full control over what data exists
- **Accuracy:** Users consciously verify and enter their financial data
- **Privacy:** Kosh never has API access to any financial institution

To make manual entry fast and painless:
- Smart dropdowns with real AMC names, bank names, fund names
- Sensible defaults (today's date, common categories)
- Inline validation with clear error messages
- Quick duplicate/repeat for recurring entries
- Bulk entry consideration for things like MF transactions

---

## 8. Landing Page

### 8.1 Purpose
Convert visitors into signed-up users. Communicate what Kosh is, why it's different, and why it's trustworthy - in that order.

### 8.2 Section Flow (conversion funnel)
| # | Section | Purpose |
|---|---------|---------|
| 1 | Navbar | Navigation + Login CTA |
| 2 | Hero | Value proposition + dashboard mockup for instant credibility |
| 3 | Marquee | Breadth of features at a glance |
| 4 | Features | "What does it do?" - 6 cards |
| 5 | How It Works | "How easy is it?" - 3 steps |
| 6 | Built for India | Differentiation - Indian instruments, INR-native, household model |
| 7 | Family Tracking | Unique selling point - household mockup with equal member display |
| 8 | Stats Strip | Credibility - ₹0 free, 15+ asset types, 2 min setup |
| 9 | Trust/Security | Remove #1 objection - data ownership, no bank credentials, encryption |
| 10 | Pricing | Remove cost objection - free early access, feature list |
| 11 | FAQ | Address remaining concerns |
| 12 | Final CTA | Convert |
| 13 | Footer | Navigation, legal, social links |
| 14 | Auth Modal | Google sign-in |

### 8.3 FAQ Order (by user mental journey)
1. What is Kosh?
2. Is Kosh really free?
3. How is Kosh different from other finance apps?
4. Is my financial data secure?
5. Does Kosh connect to my bank accounts?
6. What does "family tracking" mean?
7. What happens when paid plans launch?

**Rationale:** Security and bank connectivity are top concerns for Indian fintech users - they come before niche features like family tracking.

### 8.4 Auth
- Google Sign-In (primary, active)
- Email/password (teased as "Coming Soon" with disabled input)
- Legal consent (Terms of Service + Privacy Policy) shown below auth options

---

## 9. Design System Summary

Detailed tokens and component specs live in CLAUDE.md. Key highlights:

| Token | Value |
|-------|-------|
| Primary brand color | Gold #C9A84C |
| Dark background | #0F0F0F |
| Card background | #161616 |
| Border | #2A2A2A |
| Display font | Fraunces (variable, opsz 40/72/144) |
| Body font | Plus Jakarta Sans |
| Positive | #22C55E (emerald) |
| Negative | #EF4444 (red) |
| Currency format | ₹ + Indian number system (lakhs/crores) |

---

## 10. Product Decisions Log

All product and UX decisions with rationale are tracked in `docs/decisions.md`. Key decisions that shape this PRD:

- **D007:** Three view modes - My View, Our View, per-member view
- **D008:** Family model - invite-based, Personal/Family tab on every page
- **D009:** 100% manual entry for MVP0 (CAS PDF import added for MF onboarding per D012)
- **D011:** Design language calibrated from existing HTML work
- **D012–D025:** Mutual Funds module decisions - see [docs/mutual-funds.md](mutual-funds.md) §8
- **D026:** Personal/Family toggle always visible, even for solo users
- **D027:** Notification panel in topbar, self-contained per page
- **D028:** Family invite flow - email + relationship, no name field
- **D032–D041:** Fixed Deposits module decisions - see [docs/fixed-deposits.md](fixed-deposits.md) §7
- **D043–D049:** Recurring Deposits module decisions - see [docs/recurring-deposits.md](recurring-deposits.md) §7

---

## 11. Open Questions

| # | Question | Status |
|---|----------|--------|
| OQ1 | Dashboard layout and key metrics - CEO + CPO to brainstorm | Open |
| OQ2 | PPF / NPS / PF page designs - scope confirmed, design pending | Open |
| OQ3 | Notification system - notification panel designed (D027), backend implementation pending | Open |
| OQ4 | Data export / reporting | Deferred post-MVP0 |

**Note:** Mutual Funds module PRD is complete - see [docs/mutual-funds.md](mutual-funds.md). Fixed Deposits module PRD is complete - see [docs/fixed-deposits.md](fixed-deposits.md). Recurring Deposits module PRD is complete - see [docs/recurring-deposits.md](recurring-deposits.md).

---

## Appendix A: File Map

| File | Purpose |
|------|---------|
| `CLAUDE.md` | Complete design system contract, component specs, project intelligence |
| `docs/prd.md` | This document - product requirements |
| `docs/decisions.md` | Decision log with rationale |
| `docs/mutual-funds.md` | Mutual Funds module PRD |
| `docs/fixed-deposits.md` | Fixed Deposits module PRD |
| `docs/recurring-deposits.md` | Recurring Deposits module PRD |
| `designs/landing.html` | Landing page design |
| `designs/` | All HTML design pages (CPO creates) |
| `ui/` | React frontend (Engineer phase) |
| `src/` | Spring Boot backend |
