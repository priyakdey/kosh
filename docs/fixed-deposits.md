# Fixed Deposits - Module PRD

**Parent document:** [docs/prd.md](prd.md)
**Design file:** [designs/assets/fixed-deposits.html](../designs/assets/fixed-deposits.html)
**Last Updated:** 10 Mar 2026

---

## 1. Overview

The Fixed Deposits module lets users track their active FDs across banks - principal, interest rate, tenure, maturity date, and projected maturity amount - in one place. FDs are simpler than MFs: fixed, deterministic returns, no market NAV, no transaction history per instrument.

This is the second investment module in Kosh (after Mutual Funds) and adapts the established page pattern to fit FD domain characteristics.

---

## 2. User Stories

| # | As a user, I can... |
|---|---------------------|
| US1 | See my total FD value (principal + accrued interest), count, weighted average rate, and total interest earned on a summary banner |
| US2 | Add an FD manually with bank, type, principal, rate, payout type, start date, tenure, and optional fields (FD number, auto-renewal, notes) |
| US3 | View auto-computed maturity date and maturity amount based on quarterly compounding |
| US4 | See a maturity timeline bar chart showing when my FDs mature over the next months/years |
| US5 | See bank-wise allocation of my FD portfolio as a donut chart |
| US6 | View key highlights: highest rate FD, next maturing, largest FD, interest earned this FY |
| US7 | Click a table row to see FD detail - interest breakdown, tenure progress, projected payout schedule |
| US8 | Mark an FD as broken in Kosh (with estimated penalty calculation) after breaking it at the bank |
| US9 | Filter and sort FDs by bank and FD type |
| US10 | See family-aggregated FD portfolio with member breakdown (if family enabled) |

---

## 3. Page Structure & Design Spec

The FD page follows the standard Kosh page structure pattern (see prd.md §6.2).

### 3.1 Page Header

- Title: "Fixed Deposits" (Fraunces, opsz 72)
- Subtitle: "Track and manage your fixed deposits"
- Action button: **Add FD** (primary gold) - single button, no import flow (D032)

### 3.2 Portfolio Toggle

- Personal / Family pill switcher - always visible (D026)
- Family tab: shows member avatars + aggregated data when family exists, invite CTA + empty state when solo (D028)

### 3.3 Summary Banner

- **Gradient:** deep cyan - `linear-gradient(135deg, #0f2a2e 0%, #0a1f24 50%, #0d1a1f 100%)` (D037)
- **Hero amount:** Total FD value = principal + accrued interest (Fraunces, text-4xl/5xl, opsz 144)
- **Change chip:** "+₹X interest earned" in emerald
- **Stat pills (4):** FDs (count) | Avg Rate (weighted) | Invested (total principal) | Interest (total accrued) - per D038
- Dot-pattern overlay at 0.04 opacity for texture

### 3.4 Analytics Row

Grid layout: `grid-cols-1 lg:grid-cols-5, gap-5`

1. **Maturity Timeline** (spans 3 cols) - Stacked bar chart, X=months, Y=maturity amount. Each bar has two segments: principal (teal) and interest (emerald). Hover shows FD details (bank, dates, principal, interest). Time range: 6M|1Y|2Y|5Y|All. Footer insight: "Next maturity: [Bank] FD on [date] — ₹[amount]" (D039)
2. **Bank-wise Allocation** (spans 2 cols) - Donut chart. Segments per bank, center = total value. Hover: expand/brighten, center text updates (D039)
3. **Highlights** (spans full 5 cols below) - Card with 4 insights: Highest Rate, Next Maturing, Largest FD, Interest This FY (D039)

### 3.5 FD Table

- **Container:** rounded-xl, card bg, bordered, overflow-hidden
- **Columns (7):** Bank | FD Type | Principal | Rate | Tenure | Maturity Date | Maturity Amt - per D040
- **Features:**
  - Only active FDs shown - no matured/broken FDs in view (D034)
  - "Maturing Soon" amber badge on Maturity Date cell for FDs ≤90 days from maturity (D034)
  - Interest payout type shown as small tag below bank name
  - Sortable: Bank, Principal, Rate, Maturity Date, Maturity Amt
  - Filterable: Bank dropdown, FD Type dropdown
  - Row click → opens FD Detail modal (D041)
  - FD Type badges use `.badge .badge-cat` pattern (consistent width, `min-width: 90px`, matching MF category badges)
  - Action buttons: edit (gold hover), mark broken (amber hover), delete (red hover)
  - Pagination: rows per page dropdown + page navigation

---

## 4. Modals

### 4.1 Add FD Modal

**Trigger:** "Add FD" button in page header
**Width:** max-w-[540px]

| Field | Required | Notes |
|-------|:---:|-------|
| Bank | Yes | Dropdown: SBI, HDFC Bank, ICICI Bank, Kotak, Axis, PNB, BoB, Federal, IDBI, IndusInd, Yes Bank, Bajaj Finance, Other |
| FD Type | Yes | Dropdown: Regular, Tax-saving, Senior Citizen, Corporate, Flexi/Sweep |
| Principal (₹) | Yes | Currency input |
| Interest Rate (%) | Yes | Decimal input |
| Interest Payout | Yes | Dropdown: Cumulative, Monthly, Quarterly, Annually |
| Start Date | Yes | Date picker |
| Tenure | Yes | Years + Months dropdowns with presets: 6M, 1Y, 2Y, 3Y, 5Y, Custom |
| Maturity Date | Auto | Computed from start date + tenure (read-only) |
| Maturity Amount | Auto | Computed using quarterly compounding for cumulative (read-only) |
| Auto-Renewal | No | Checkbox |
| FD Number | No | Text input |
| Notes | No | Text input, max 200 chars |

- Maturity date and amount auto-compute as user fills in start date, tenure, principal, and rate (D033)
- For non-cumulative FDs, maturity amount = principal (interest paid out periodically)

### 4.2 FD Detail Modal

**Trigger:** Click any row in FD table
**Width:** max-w-[720px] (D041)

Sections:
1. **Compact summary banner** - Current Value, Interest Earned, Principal, Rate, Tenure, Start Date, Maturity Date, Days Remaining (cyan gradient mini-banner)
2. **Interest Breakdown table** - Quarterly compounding schedule for cumulative FDs; projected payout schedule (dates + amounts) for non-cumulative FDs (D036)
3. **Tenure Progress bar** - Visual progress from start to maturity, percentage complete
4. **Actions** - Edit FD, Mark as Broken, Delete FD

**Edit FD** reuses the Add FD modal with title changed to "Edit Fixed Deposit" and all fields pre-filled from the current FD's data. Maturity auto-recomputes on any field change.

### 4.3 Mark as Broken Modal

**Trigger:** "Mark as Broken" button in FD Detail modal
**Width:** max-w-[480px] (D035)

Content:
- **Tracking disclaimer banner** - "This only updates your Kosh records. To actually break this FD, visit your bank's net banking portal or branch."
- **Estimate note** - "Below is an estimate of the penalty impact. Actual penalty terms may vary by bank."
- Original rate vs penalty rate (original − 1%)
- Full maturity amount vs estimated amount received
- Interest lost due to premature withdrawal
- Tax-saving FDs: action disabled with "Banks do not allow premature withdrawal for this FD type" warning
- "Remove from Portfolio" button (red)

After confirming, FD is removed from active view in Kosh.

### 4.4 Family Invite Modal

**Trigger:** "Invite Family Member" button in Family tab empty state
**Fields:** Email (required) + Relationship (required: Spouse/Partner, Parent, Sibling, Child, Other)
**No name field** - name comes from invitee's profile on account creation (D028)
**Learn more** link → `https://docs.kosh.app/family-portfolios` (external, new tab)

---

## 5. Data Model

### 5.1 Entities

**Fixed Deposit**
- Bank (string)
- FD Type (enum: Regular, Tax-saving, Senior Citizen, Corporate, Flexi/Sweep)
- Principal (decimal, ₹)
- Interest Rate (decimal, annual %)
- Interest Payout (enum: Cumulative, Monthly, Quarterly, Annually)
- Start Date (date)
- Tenure Months (int)
- Maturity Date (computed: start date + tenure months)
- Maturity Amount (computed: quarterly compounding for cumulative, principal for non-cumulative)
- Auto-Renewal (boolean)
- FD Number (string, optional)
- Notes (string, optional, max 200)
- Status (computed: Active | Maturing Soon | Matured | Broken)
- Break Date (date, optional, if broken)

### 5.2 Computed Fields

| Field | Computation |
|-------|-------------|
| Maturity Date | startDate + tenureMonths |
| Maturity Amount (cumulative) | P × (1 + r/400)^(4t) where r=annual rate, t=years (D033) |
| Maturity Amount (non-cumulative) | Principal (interest paid out periodically) |
| Current Value | Principal + accrued interest (pro-rated to today) |
| Accrued Interest | (maturityAmt − principal) × min(elapsed%, 1) |
| Days Remaining | maturityDate − today |
| Weighted Avg Rate | Σ(principal_i × rate_i) / Σ(principal_i) |
| Interest This FY | Interest earned between Apr 1 and today |
| Status | Active if >90 days to maturity; Maturing Soon if ≤90 days; Matured if past maturity; Broken if break date set |

### 5.3 Key Rules

- Only active FDs (Active + Maturing Soon) shown in the table (D034)
- Matured and broken FDs are removed from view entirely (D034)
- All maturity calculations use quarterly compounding (D033)
- Tax-saving FDs have a 5-year lock-in - banks do not allow premature withdrawal (D035)
- Marking an FD as broken in Kosh is a tracking action - estimated 1% penalty rate reduction shown (D035)
- No interest payout tracking as transactions - projected schedule shown in detail modal only (D036)

---

## 6. Personal vs Family View

### Personal View (default)
- User's own FDs, charts, and table
- All analytics reflect individual FD portfolio only

### Family View
- Aggregated totals across all family members
- Member avatars shown below toggle
- Combined FD value, count, weighted avg rate, total interest
- FD table shows all members' FDs with a "Member" column
- Charts reflect combined portfolio

### No-Family State (D026, D028)
- Toggle always visible, even for solo users
- Family tab shows empty state with invite CTA
- "Invite Family Member" button opens invite modal

---

## 7. Key Product Decisions

| Decision | Summary |
|----------|---------|
| D032 | Single "Add FD" button - no import flow. Manual entry only. |
| D033 | Quarterly compounding standard: P × (1 + r/400)^(4t) |
| D034 | Only active FDs shown. Matured/broken removed from view. "Maturing Soon" badge for ≤90 days. |
| D035 | Mark FD as broken (tracking action, not actual bank break). Estimated penalty display. Tax-saving FDs have 5-year lock-in. |
| D036 | No interest payout tracking as transactions. Projected schedule in detail modal. |
| D037 | Banner gradient: deep cyan |
| D038 | 4 stat pills: FDs, Avg Rate, Invested, Interest |
| D039 | 3 charts: Maturity Timeline (bar) + Bank-wise Allocation (donut) + Highlights (card) |
| D040 | Table: 7 columns. No Status column - only active FDs shown. |
| D041 | FD Detail as 720px modal with interest breakdown and progress bar. |

Full decision details: [docs/decisions.md](decisions.md)

---

## 8. Mock Data

8 active FDs for personal view:

| Bank | Type | Principal | Rate | Payout | Start | Tenure | Notes |
|------|------|-----------|------|--------|-------|--------|-------|
| SBI | Regular | ₹5,00,000 | 7.10% | Cumulative | Oct 2023 | 36mo | Matures Oct 2026 |
| SBI | Tax-saving | ₹1,50,000 | 6.50% | Cumulative | Jan 2024 | 60mo | 5yr lock-in, matures Jan 2029 |
| HDFC Bank | Regular | ₹3,00,000 | 7.25% | Quarterly | Apr 2024 | 24mo | Maturing Soon (≤90 days), matures Apr 2026 |
| ICICI Bank | Regular | ₹2,50,000 | 6.90% | Cumulative | Nov 2024 | 18mo | Matures May 2026 |
| Kotak | Senior Citizen | ₹4,00,000 | 7.75% | Monthly | Mar 2024 | 36mo | Matures Mar 2027 |
| Axis | Regular | ₹2,00,000 | 7.00% | Cumulative | Jan 2025 | 18mo | Matures Jul 2026 |
| Bajaj Finance | Corporate | ₹3,50,000 | 8.10% | Cumulative | Jun 2024 | 24mo | Matures Jun 2026 |
| Federal Bank | Regular | ₹1,80,000 | 7.30% | Annually | Nov 2024 | 18mo | Matures May 2026 |

Family (Arjun's FDs - 4 additional):

| Bank | Type | Principal | Rate | Payout | Start | Tenure | Notes |
|------|------|-----------|------|--------|-------|--------|-------|
| HDFC Bank | Regular | ₹4,00,000 | 7.25% | Cumulative | May 2024 | 24mo | Matures May 2026 |
| SBI | Regular | ₹2,00,000 | 7.10% | Cumulative | Aug 2024 | 24mo | Matures Aug 2026 |
| Kotak | Regular | ₹3,00,000 | 7.50% | Quarterly | Feb 2024 | 36mo | Matures Feb 2027 |
| ICICI Bank | Tax-saving | ₹1,50,000 | 6.90% | Cumulative | Apr 2024 | 60mo | Matures Apr 2029 |

---

## 9. MF → FD Adaptation Map

| MF Concept | FD Equivalent |
|------------|---------------|
| XIRR | Interest rate (fixed, known) |
| NAV | N/A |
| Units | N/A |
| Transaction history | N/A — single deposit |
| SIP template | N/A |
| CAS Import | N/A — manual only |
| Fund search (37k) | Bank dropdown (15 banks) |
| Category (auto) | FD Type (user-selected) |
| Broker | Bank |
| Redeemed toggle | N/A — only active FDs shown |
| Indigo gradient | Cyan gradient |

---

## 10. Open Questions / Future

- **Auto-renewal tracking:** Auto-create new FD record when matured FD auto-renews (post-MVP0)
- **TDS tracking:** Track TDS deducted on FD interest (post-MVP0)
- **FD laddering suggestions:** Recommend maturity spread for liquidity (out of scope)
- **Interest income integration:** Link FD interest to Income module when built (post-MVP0)
