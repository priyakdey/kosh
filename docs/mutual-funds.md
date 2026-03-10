# Mutual Funds - Module PRD

**Parent document:** [docs/prd.md](prd.md)
**Design file:** [designs/assets/mutual-funds.html](../designs/assets/mutual-funds.html)
**Last Updated:** 10 Mar 2026

---

## 1. Overview

The Mutual Funds module lets users track their complete MF portfolio - holdings, transactions, returns, and allocation - in one place. It supports both manual transaction entry and bulk onboarding via CAS PDF import.

This is the first investment module in Kosh and establishes the pattern for other asset pages (Stocks, FD, RD).

---

## 2. User Stories

| # | As a user, I can... |
|---|---------------------|
| US1 | See my complete MF portfolio value, total invested, absolute & percentage returns, and XIRR on a summary banner |
| US2 | Add transactions manually (SIP, Lumpsum, Sell) with fund search, NAV auto-fetch, and broker tagging |
| US3 | Import a CAS PDF for bulk onboarding of all historical transactions |
| US4 | View fund-level detail in a modal - current value, units, NAV, SIP management, charts, transaction history |
| US5 | See my portfolio allocation by category (donut chart) and fund weightage (horizontal bars) |
| US6 | Filter and sort holdings by fund name, category, and broker |
| US7 | Toggle between redeemed and active holdings |
| US8 | See family-aggregated MF portfolio with member breakdown (if family enabled) |
| US9 | Invite family members from the Family tab empty state (if no family yet) |
| US10 | Manage SIPs - pause, edit amount/day, delete - from the fund detail modal |

---

## 3. Page Structure & Design Spec

The MF page follows the standard Kosh page structure pattern (see prd.md §6.2).

### 3.1 Page Header

- Title: "Mutual Funds" (Fraunces, opsz 72)
- Subtitle: "Track and manage your mutual fund portfolio"
- Action buttons: **Import CAS** (outlined) + **Add Transaction** (primary gold) - per D020

### 3.2 Portfolio Toggle

- Personal / Family pill switcher - always visible (D026)
- Family tab: shows member avatars + aggregated data when family exists, invite CTA + empty state when solo (D028)

### 3.3 Summary Banner

- **Gradient:** deep indigo - `linear-gradient(135deg, #1a1432 0%, #0f0e24 50%, #12102a 100%)`
- **Hero amount:** Total portfolio current value (Fraunces, text-4xl/5xl, opsz 144)
- **Change chip:** rounded-full pill with arrow + percentage vs last month (emerald for gain, red for loss)
- **Stat pills (4):** Funds | XIRR | Invested | Returns - per D021
- Dot-pattern overlay at 0.04 opacity for texture

### 3.4 Analytics Row

Grid layout: `grid-cols-1 lg:grid-cols-5, gap-5`

1. **Portfolio Value Trend** (spans 3 cols) - Line chart with smooth spline interpolation, crosshair cursor, vertical hover line, tooltip with date + value
2. **Allocation by Category** (spans 2 cols) - Donut chart with hover expand/brighten, center text updates with category name + percentage
3. **Fund Weightage** (spans 3 cols) - Horizontal bar chart showing top funds by current value as percentage of portfolio
4. **Highlights** (spans 2 cols) - Card with key insights: best performer, portfolio XIRR, largest holding

> **MVP1 upgrades (D042):** Card #2 becomes Portfolio X-Ray (tabbed donut: Category, Sector, Market Cap, Company Overlap). Card #4 becomes Portfolio Health (5 concentration risk checks with thresholds). Requires scheme-level portfolio data source — see D042 for details.

### 3.5 Holdings Table

- **Container:** rounded-xl, card bg, bordered, overflow-hidden
- **Columns (7):** Fund | Category | Invested | Current | Returns (₹ + %) | XIRR | Broker - per D022
- **Features:**
  - Sortable by any column
  - Filterable: fund name search, category dropdown, broker dropdown
  - Redeemed funds toggle (show/hide redeemed holdings)
  - SIP chip on fund name: emerald = active SIP, amber = paused SIP (D024)
  - Row click → opens Fund Detail modal (D023)
  - Action buttons: edit (gold hover), delete (red hover)
  - Pagination: rows per page dropdown + page navigation

---

## 4. Modals

### 4.1 Add Transaction Modal

**Trigger:** "Add Transaction" button in page header
**Width:** max-w-[540px]

Fields vary by transaction type (D025):

| Field | SIP | Lumpsum | Sell |
|-------|:---:|:-------:|:----:|
| Transaction Type (pill selector) | ● | ● | ● |
| Fund Name (search 37k+ schemes) | ● | ● | ● |
| Amount (₹) | ● | ● | ● |
| Recurring Day (1-28 dropdown) | ● | - | - |
| Date (date picker) | - | ● | ● |
| Folio (optional text) | ● | ● | ● |
| Broker (dropdown) | ● | ● | ● |
| Notes (optional, max 200 chars) | ● | ● | ● |

- Fund search queries the local scheme catalog (37,394 schemes from mfapi.in)
- NAV auto-fetched from mfapi.in on fund + date selection; units auto-computed (amount ÷ NAV)
- SIP type creates a recurring template - Kosh auto-records monthly installments (D024)

**Validation:** Zod discriminated union per transaction type (D025). See `docs/decisions.md` D025 for schema.

### 4.2 Fund Detail Modal

**Trigger:** Click any row in holdings table
**Width:** max-w-[820px]

Sections:
1. **Summary banner** - Fund name, current value, units, latest NAV, invested amount, returns (₹ + %), XIRR
2. **SIP management** (if SIP exists) - Shows active/paused SIP with amount + recurring day. Actions: Pause/Resume, Edit (amount + day), Delete. Past transactions preserved on delete (D024)
3. **Value Over Time chart** - Line chart of fund's value history
4. **Transaction Timeline chart** - Bar/scatter chart showing individual transactions over time
5. **Transaction history table** - Date, Type, Amount, Units, NAV, Folio columns. Paginated.

### 4.3 CAS Import Modal

**Trigger:** "Import CAS" button in page header

Multi-step flow:
1. **Upload** - Drag-and-drop or file picker for CAS PDF. Password field (many CAS PDFs are password-protected)
2. **Processing** - Progress indicator while parsing PDF
3. **Preview** - Table of parsed transactions with broker tagging dropdowns per folio (D017). User reviews and confirms.
4. **Done** - Success confirmation with summary (X funds imported, Y transactions)

- **Source:** CAS reports from MFCentral.com or CAMSOnline
- **Stamp duty:** Parsed from CAS, rolled into parent transaction cost, not displayed separately (D018)

### 4.4 Family Invite Modal

**Trigger:** "Invite Family Member" button in Family tab empty state
**Fields:** Email (required) + Relationship (required: Spouse/Partner, Parent, Sibling, Child, Other)
**No name field** - name comes from invitee's profile on account creation (D028)
**Learn more** link → `https://docs.kosh.app/family-portfolios` (external, new tab)

---

## 5. Data Model

### 5.1 Entities

**Holding**
- Fund scheme (schemeCode, schemeName, ISIN)
- Folio number
- Broker (user-tagged, D017)
- Category (auto-detected from scheme name, D016)
- Units (sum of all transaction units)
- Invested amount (sum of buy transactions)
- Current value (units × latest NAV)

**Transaction**
- Date
- Type: SIP | Lumpsum | Sell
- Amount (₹)
- Units
- NAV at transaction date
- Folio number
- Broker
- Notes (optional)

**SIP Template** (D024)
- Fund (schemeCode)
- Amount (₹)
- Recurring day (1-28)
- Status: Active | Paused
- Created date
- Paused date (if applicable)

### 5.2 Computed Fields

| Field | Computation |
|-------|-------------|
| Current Value | units × latest NAV |
| Returns (₹) | current value − invested amount |
| Returns (%) | (returns ÷ invested) × 100 |
| XIRR | Computed from actual transaction cashflow history (D014) |
| Category | Auto-detected from scheme name patterns (D016) |
| Portfolio totals | Sum across all holdings |

### 5.3 Key Rules

- Each scheme-folio combination is a separate holding (D015)
- No distinction between Regular and Direct plans in UI - they're different schemes with different ISINs (D015)
- SIP creates a recurring template; Kosh auto-records monthly installments (D024)
- Deleting a SIP preserves past transactions (D024)

---

## 6. Data Sources

### 6.1 MFAPI (mfapi.in)

External API for scheme catalog and NAV data.

**Scheme catalog** - List of all mutual fund schemes

```
GET https://api.mfapi.in/mf?limit=1000&offset=0
```

Response:
```json
[
    {
        "schemeCode": 100027,
        "schemeName": "Grindlays Super Saver Income Fund-GSSIF-Half Yearly Dividend",
        "isinGrowth": null,
        "isinDivReinvestment": null
    }
]
```

**Latest NAV** - Current NAV for a specific scheme

```
GET https://api.mfapi.in/mf/{schemeCode}/latest
```

Response:
```json
{
    "meta": {
        "fund_house": "SBI Mutual Fund",
        "scheme_type": "Open Ended Schemes",
        "scheme_category": "Equity Scheme - Small Cap Fund",
        "scheme_code": 125497,
        "scheme_name": "SBI Small Cap Fund - Direct Plan - Growth",
        "isin_growth": "INF200K01T51",
        "isin_div_reinvestment": null
    },
    "data": [
        {
            "date": "03-02-2026",
            "nav": "186.70090"
        }
    ],
    "status": "SUCCESS"
}
```

### 6.2 CAS PDF

- **Source:** MFCentral.com or CAMSOnline
- **Contents:** Complete transaction history - folios, fund names, transaction dates, amounts, units, NAV per transaction
- **Stamp duty:** Parsed and rolled into parent transaction cost (D018)
- **Broker info:** Not present in CAS - user tags brokers during import preview (D017)

### 6.3 Local Scheme Catalog

- **File:** `docs/mf_schemes.json` - 37,394 schemes snapshot (fetched 2026-02-04)
- **Purpose:** Fund name autocomplete in Add Transaction modal
- **Update strategy:** Periodic refresh from mfapi.in

---

## 7. Personal vs Family View

### Personal View (default)
- User's own holdings, charts, and table
- All analytics reflect individual portfolio only

### Family View
- Aggregated totals across all family members
- Member avatars shown below toggle
- Combined portfolio value, returns, XIRR
- Holdings table shows all members' holdings
- Charts reflect combined portfolio

### No-Family State (D026, D028)
- Toggle always visible, even for solo users
- Family tab shows empty state with invite CTA
- "Invite Family Member" button opens invite modal

---

## 8. Key Product Decisions

| Decision | Summary                                                                           |
| -------- | --------------------------------------------------------------------------------- |
| D012     | CAS PDF import (primary) + manual Add Transaction (secondary) for data onboarding |
| D013     | SIP is a transaction type, not a separate entity. No SIP tab.                     |
| D014     | XIRR computed from actual transaction cashflows                                   |
| D015     | No Regular vs Direct distinction - separate holdings by scheme-folio              |
| D016     | Category auto-detected from scheme name patterns                                  |
| D017     | Broker is user-tagged metadata, not auto-detected                                 |
| D018     | Stamp duty rolled into transaction cost, not shown separately                     |
| D019     | No sector exposure chart - replaced with Fund Weightage bars                      |
| D020     | Two header buttons: Import CAS + Add Transaction                                  |
| D021     | 4 stat pills: Funds, XIRR, Invested, Returns                                      |
| D022     | Holdings table: 7 columns (no Units/NAV columns)                                  |
| D023     | Fund detail shown in 820px modal, not page replacement                            |
| D024     | SIP as recurring transaction template with pause/edit/delete                      |
| D025     | Zod discriminated union for type-based form validation                            |
| D026     | Personal/Family toggle always visible                                             |
| D027     | Notification panel in topbar, self-contained per page                             |
| D028     | Family invite: email + relationship, no name field                                |
| D042     | Portfolio X-Ray, Concentration Risk & Highlights deferred to MVP1                 |

Full decision details: [docs/decisions.md](decisions.md)

---

## 9. Open Questions / Future

### MVP1 — Portfolio Analytics (D042)

These features are deferred from MVP0 and need research before implementation:

- **Portfolio X-Ray (tabbed donut):** Sector exposure, market-cap distribution, and company overlap views aggregated across all MF holdings. Requires a data source for scheme-level portfolios (which stocks/sectors each fund holds). Candidates to research: AMFI portfolio disclosures, ValueResearch API, Morningstar India API, scraping monthly AMC factsheets.
- **Concentration Risk / Portfolio Health card:** 5 checks — stock concentration (single company >5% warn, >8% danger), top-5 overlap (>20% warn, >30% danger), sector balance (>30% warn, >40% danger), market-cap skew (small-cap >30% or large-cap >70% warn), fund concentration (single fund >25% of portfolio warn). Thresholds need validation against Indian market norms.
- **Highlights card upgrade:** Current simple 3-box card (best performer, XIRR, largest holding) may evolve into a dynamic insights feed once analytics data is richer.
- **Research TODOs:**
  - [ ] Identify reliable API/data source for scheme-level holdings (sector, stock, market-cap breakdown)
  - [ ] Determine data refresh cadence (monthly factsheet cycle vs. daily)
  - [ ] Validate concentration risk thresholds with Indian market benchmarks (Nifty 50 sector weights, typical MF portfolio distributions)
  - [ ] Design the X-Ray footer contextual risk styling (amber/red tints when a tab's data shows concentration issues)
  - [ ] User research: do Indian retail MF investors understand/value "concentration risk" framing?

### Other Future Items

- **CAS auto-import:** Scheduled periodic import from MFCentral (post-MVP0)
- **Regular vs Direct comparison:** Side-by-side view of expense ratios and returns (post-MVP0)
- **Goal-based tracking:** Link funds to financial goals (post-MVP0)
- **Fund recommendations:** Based on portfolio gaps (out of scope)
- **Historical NAV charts:** Full NAV history per fund from mfapi.in (post-MVP0)
