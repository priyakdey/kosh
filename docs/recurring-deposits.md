# Recurring Deposits - Module PRD

**Parent document:** [docs/prd.md](prd.md)
**Design file:** [designs/assets/recurring-deposits.html](../designs/assets/recurring-deposits.html)
**Last Updated:** 10 Mar 2026

---

## 1. Overview

The Recurring Deposits module lets users track their active RDs across banks — monthly installment amount, interest rate, tenure, installments paid vs total, maturity date, and projected maturity amount — in one place. RDs are structurally closest to FDs (bank-based, tenure-based, maturity-based) but with monthly installments instead of a single lump sum.

This is the third investment module in Kosh (after Mutual Funds and Fixed Deposits) and adapts the established FD page pattern to fit RD domain characteristics.

---

## 2. User Stories

| # | As a user, I can... |
|---|---------------------|
| US1 | See my total RD value (deposited + accrued interest), count, weighted average rate, and total interest earned on a summary banner |
| US2 | Add an RD manually with bank, monthly installment, rate, start date, tenure, and optional fields (RD number, notes) |
| US3 | View auto-computed maturity date and projected maturity amount based on quarterly compounding of monthly deposits |
| US4 | See a maturity timeline bar chart showing when my RDs mature over the next months/years |
| US5 | See bank-wise allocation of my RD portfolio as a donut chart (by total deposited) |
| US6 | View key highlights: highest rate RD, next maturing, largest RD, interest earned this FY |
| US7 | Click a table row to see RD detail — installment progress, deposit growth table, projected maturity |
| US8 | Track installments paid vs expected, with "Missed" badge when behind schedule |
| US9 | Close an RD in Kosh (with estimated penalty calculation) after closing it at the bank |
| US10 | Filter and sort RDs by bank |
| US11 | See family-aggregated RD portfolio with member breakdown (if family enabled) |

---

## 3. Page Structure & Design Spec

The RD page follows the standard Kosh page structure pattern, mirroring FD (D043).

### 3.1 Page Header

- Title: "Recurring Deposits" (Fraunces, opsz 72)
- Subtitle: "Track and manage your recurring deposits"
- Action button: **Add RD** (primary gold) — single button, no import flow

### 3.2 Portfolio Toggle

- Personal / Family pill switcher — always visible (D026)
- Family tab: shows member avatars + aggregated data when family exists, invite CTA + empty state when solo (D028)

### 3.3 Summary Banner

- **Gradient:** deep sky blue — `linear-gradient(135deg, #082f49 0%, #0c1e2e 50%, #0d1a1f 100%)` (D044)
- **Hero amount:** Total RD value = total deposited + accrued interest (Fraunces, text-4xl/5xl, opsz 144)
- **Change chip:** "+₹X interest earned" in emerald
- **Stat pills (4):** RDs (count) | Avg Rate (weighted) | Invested (total deposited so far) | Interest (total accrued) — per D043
- Dot-pattern overlay at 0.04 opacity for texture

### 3.4 Analytics Row

Grid layout: `grid-cols-1 lg:grid-cols-5, gap-5`

1. **Maturity Timeline** (spans 3 cols) — Stacked bar chart, X=months, Y=maturity amount. Each bar has two segments: deposited (sky-700) and interest (emerald). Time range: 6M|1Y|2Y|5Y|All. Footer insight: "Next maturity: [Bank] RD on [date] — ₹[amount]"
2. **Bank-wise Allocation** (spans 2 cols) — Donut chart. Segments per bank based on total deposited so far. Center = total deposited. Hover: expand/brighten, center text updates
3. **Highlights** (spans full 5 cols below) — 4 cards: Highest Rate, Next Maturing, Largest RD (by total deposited), Interest This FY

### 3.5 RD Table

- **Container:** rounded-xl, card bg, bordered, overflow-hidden
- **Columns (7):** Bank | Monthly Amt | Installments | Rate | Tenure | Maturity Date | Projected Maturity — per D047
- **Features:**
  - Only active RDs shown — no closed RDs in view
  - "Maturing Soon" amber badge on Maturity Date cell for RDs ≤90 days from maturity
  - "Missed" red badge when installmentsPaid < expected installments based on months elapsed (D046)
  - "Cumulative" tag shown below bank name (all RDs are cumulative)
  - Installments column: "X / Y" text + 4px micro-progress bar (sky fill)
  - Sortable: Bank, Monthly Amt, Installments, Rate, Maturity Date, Projected Maturity
  - Filterable: Bank dropdown only (no type filter — D045)
  - Row click → opens RD Detail modal
  - Pagination: rows per page dropdown + page navigation

---

## 4. Modals

### 4.1 Add RD Modal

**Trigger:** "Add RD" button in page header
**Width:** max-w-[540px]

| Field | Required | Notes |
|-------|:---:|-------|
| Bank | Yes | Dropdown: SBI, HDFC Bank, ICICI Bank, Kotak, Axis, PNB, BoB, Federal Bank, IDBI, IndusInd, Yes Bank, Post Office, Other |
| Monthly Installment (₹) | Yes | Currency input |
| Interest Rate (%) | Yes | Decimal input |
| Start Date | Yes | Date picker |
| Tenure | Yes | Years + Months inputs with presets: 6M, 1Y, 2Y, 3Y, 5Y, Custom |
| Maturity Date | Auto | Computed from start date + tenure (read-only) |
| Projected Maturity Amt | Auto | Computed using quarterly compounding on monthly deposits (read-only) |
| RD Number | No | Text input |
| Notes | No | Text input, max 200 chars |

- Simpler than FD: no Type dropdown, no Interest Payout dropdown, no Auto-renewal checkbox (D045)
- Maturity date and amount auto-compute as user fills in start date, tenure, monthly installment, and rate

### 4.2 RD Detail Modal

**Trigger:** Click any row in RD table
**Width:** max-w-[720px]

Sections:
1. **Compact summary banner** — Current Value, Interest Earned, Total Deposited, Rate, Tenure, Installments X/Y, Start Date, Days Remaining (sky-blue gradient mini-banner)
2. **Installment Progress** — Visual bar from start date to maturity date, "X/Y installments paid" label, next installment due date (D046)
3. **Deposit Growth table** — Month-by-month: Month | Deposit | Interest Accrued | Running Total (cap 20 rows)
4. **Actions** — Edit RD, Close RD, Delete RD

**Edit RD** reuses the Add RD modal with title changed to "Edit Recurring Deposit" and all fields pre-filled.

### 4.3 Close RD Modal

**Trigger:** "Close RD" button in RD Detail modal
**Width:** max-w-[480px] (D048)

Content:
- **Tracking disclaimer banner** — "This only updates your Kosh records. To actually close this RD, visit your bank's net banking portal or branch."
- **Estimate note** — "Below is an estimate of the penalty impact. Actual penalty terms may vary by bank."
- Original rate vs penalty rate (original − 1%)
- Total deposited vs estimated amount received
- Interest lost due to premature closure
- No lock-in warning (RDs have no lock-in period — D048)
- "Remove from Portfolio" button (red)

### 4.4 Family Invite Modal

Same as FD — Email (required) + Relationship (required), learn more link to `docs.kosh.app/family-portfolios` (D028).

---

## 5. Data Model

### 5.1 Entities

**Recurring Deposit**
- Bank (string)
- Monthly Installment (decimal, ₹)
- Interest Rate (decimal, annual %)
- Start Date (date)
- Tenure Months (int)
- Installments Paid (int, 0 to tenureMonths)
- Maturity Date (computed: start date + tenure months)
- Maturity Amount (computed: iterative quarterly compounding on monthly deposits)
- RD Number (string, optional)
- Notes (string, optional, max 200)
- Status (computed: Active | Maturing Soon | Missed | Closed)
- Close Date (date, optional, if closed)

### 5.2 Computed Fields

| Field | Computation |
|-------|-------------|
| Maturity Date | startDate + tenureMonths |
| Maturity Amount | Iterative: each month adds monthlyAmt, every 3rd month compounds at rate/400 (D049) |
| Total Deposited | installmentsPaid × monthlyAmt |
| Current Value | Iterative compounding on installmentsPaid months of deposits |
| Accrued Interest | currentValue − totalDeposited |
| Expected Installments | Months elapsed from startDate to today (capped at tenureMonths) |
| Missed | installmentsPaid < expectedInstallments |
| Days Remaining | maturityDate − today |
| Weighted Avg Rate | Σ(totalDeposited_i × rate_i) / Σ(totalDeposited_i) |
| Interest This FY | Interest earned between Apr 1 and today (approximate) |

### 5.3 Key Rules

- Only active RDs (Active + Maturing Soon) shown in the table
- Closed RDs are removed from view entirely
- All maturity calculations use quarterly compounding on monthly deposits (D049)
- RDs have no type variants — always cumulative (D045)
- RDs have no lock-in period (D048)
- "Invested" = sum of (installmentsPaid × monthlyAmt), not committed total (D049)
- "Missed" badge triggers when installmentsPaid < months elapsed since start (D046)

---

## 6. Personal vs Family View

### Personal View (default)
- User's own RDs, charts, and table
- All analytics reflect individual RD portfolio only

### Family View
- Aggregated totals across all family members
- Member avatars shown below toggle
- Combined RD value, count, weighted avg rate, total interest
- RD table shows all members' RDs with a "Member" column
- Charts reflect combined portfolio

### No-Family State (D026, D028)
- Toggle always visible, even for solo users
- Family tab shows empty state with invite CTA
- "Invite Family Member" button opens invite modal

---

## 7. Key Product Decisions

| Decision | Summary |
|----------|---------|
| D043 | RD page mirrors FD structure — banner, analytics, table, modals |
| D044 | Sky-blue gradient banner (#082f49), sky-500 accents, bg-sky-500 nav dot |
| D045 | No type/payout variants. Always cumulative. No auto-renewal. Simpler Add modal (9 fields vs FD's 13). |
| D046 | Installment tracking: X/Y with micro-progress bar. "Missed" badge when behind schedule. |
| D047 | Table: 7 columns. Bank, Monthly Amt, Installments, Rate, Tenure, Maturity Date, Projected Maturity. Bank filter only. |
| D048 | Close RD modal simplified — no lock-in warning. Penalty rate = original − 1%. |
| D049 | RD maturity uses iterative quarterly compounding on monthly deposits. "Invested" = actual deposited, not committed. |

Full decision details: [docs/decisions.md](decisions.md)

---

## 8. Mock Data

8 active RDs for personal view:

| Bank | Monthly Amt | Rate | Start | Tenure | Paid | Notes |
|------|-------------|------|-------|--------|------|-------|
| SBI | ₹10,000 | 6.80% | Jul 2024 | 24mo | 20 | Matures Jul 2026 |
| HDFC Bank | ₹25,000 | 7.00% | Mar 2025 | 36mo | 12 | Matures Mar 2028 |
| ICICI Bank | ₹5,000 | 6.50% | May 2025 | 12mo | 8 | **Missed** (10 expected, 8 paid) |
| Kotak | ₹15,000 | 7.25% | Jul 2025 | 60mo | 8 | Matures Jul 2030 |
| Post Office | ₹3,000 | 6.70% | Jul 2024 | 60mo | 20 | Matures Jul 2029 |
| Axis | ₹8,000 | 6.90% | Sep 2025 | 24mo | 6 | Matures Sep 2027 |
| PNB | ₹20,000 | 6.40% | Apr 2025 | 12mo | 11 | Matures Apr 2026 |
| Federal Bank | ₹12,000 | 7.10% | Nov 2025 | 36mo | 4 | Matures Nov 2028 |

Family (Arjun's RDs — 4 additional):

| Bank | Monthly Amt | Rate | Start | Tenure | Paid | Notes |
|------|-------------|------|-------|--------|------|-------|
| SBI | ₹10,000 | 6.80% | Apr 2024 | 36mo | 23 | Matures Apr 2027 |
| ICICI Bank | ₹20,000 | 7.10% | Jun 2024 | 24mo | 21 | Matures Jun 2026 |
| Kotak | ₹5,000 | 6.60% | Jul 2025 | 12mo | 8 | Matures Jul 2026 |
| HDFC Bank | ₹15,000 | 7.00% | Sep 2025 | 60mo | 6 | Matures Sep 2030 |

---

## 9. FD → RD Adaptation Map

| FD Concept | RD Equivalent |
|------------|---------------|
| Principal (lump sum) | Monthly Installment × installments paid |
| Single deposit | Monthly recurring deposits |
| FD Type (Regular, Tax-saving, etc.) | N/A — always cumulative |
| Interest Payout (Cumulative, Monthly, etc.) | Always cumulative |
| Auto-renewal | N/A |
| "Mark as Broken" | "Close RD" (simpler, no lock-in) |
| Tax-saving lock-in | N/A — no lock-in |
| Static principal tracking | Installments paid vs total tracking |
| Type filter dropdown | N/A — bank filter only |
| Cyan gradient (#0f2a2e) | Sky-blue gradient (#082f49) |
| Interest Breakdown table | Deposit Growth table (month-by-month) |
| Tenure Progress bar | Installment Progress bar + next due date |

---

## 10. Open Questions / Future

- **Auto-debit tracking:** Detect missed installments from bank integration (post-MVP0)
- **RD maturity payout:** Auto-create income entry when RD matures (post-MVP0)
- **TDS tracking:** Track TDS deducted on RD interest (post-MVP0)
- **RD laddering:** Suggest staggered RD start dates for monthly liquidity (out of scope)
- **Interest income integration:** Link RD interest to Income module when built (post-MVP0)
