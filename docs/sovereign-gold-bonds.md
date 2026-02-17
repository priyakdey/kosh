# SGB Market Data — NSE (Research Notes)

## Source
- National Stock Exchange of India (NSE)
- Public, undocumented endpoint used by NSE website

## API
```bash
GET https://www.nseindia.com/api/sovereign-gold-bonds
```

## Sample Response (Single Object)
```json
{
  "symbol": "SGBJUN30",
  "ltP": "16000",
  "prevClose": "15784.2",
  "issue_price": "5041",
  "per": "1.37",
  "chn": "215.8",
  "perChange30d": 3.78,
  "perChange365d": 89.79,
  "maturityDate": "JUN30",
  "series": "GB",
  "meta": {
    "companyName": "SOVEREIGN GOLD BONDS 2.50% JUNE 2030 SR-I 2022-23",
    "isin": "IN0020220045",
    "listingDate": "2022-07-07"
  }
}
```

## Interesting / Useful Fields
- `symbol` → NSE trading ticker (e.g. `SGBJUN30`)
- `meta.isin` → **Primary identifier**
- `meta.companyName` → Canonical instrument name
- `issue_price` → RBI issue price (₹/gram)
- `maturityDate` → Maturity shorthand (e.g. JUN30)
- `ltP` → Last traded (intraday) price
- `perChange30d` → 30-day return
- `perChange365d` → 1-year return

## T-1 (Previous Trading Day) Price

- **Field to use:** `prevClose`
- Represents official NSE closing price of last completed trading session
- Recommended for:
    - EOD valuation
    - Daily snapshots
    - Return calculations
## Notes

- 1 bond = 1 gram of gold
- Coupon (2.5% p.a., semi-annual) is **not included** in API
- Coupon must be modeled separately
- Low liquidity is normal for SGBs
