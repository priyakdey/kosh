
## Instruments to track

- **Fixed / Guaranteed**
	- - **Savings Account**
	- **Fixed Deposit (FD)**
	- **Recurring Deposit (RD)**
- **Government / Retirement**
	- Provident Fund (PF / EPF / Trust PF)
	- Public Provident Fund (PPF)
	- **National Pension System (NPS)**
		- Tier-I
		- Tier-II (optional)
- **Market-Linked**
	- **Mutual Funds (MF)**
		- Equity
		- Debt
		- Hybrid
		- Liquid / Arbitrage
	- **Equity Stocks**
		- Plain cash market (no F&O, no intraday)
	- **Bonds**
		- Government Bonds
		- Corporate Bonds / Debentures
	- **Gold Instruments**
		- Sovereign Gold Bonds (SGB)
		- Gold ETFs
		- Physical gold (optional/manual)
- **Assets**
	- Real Estate
		- Residential
		- Commercial
		- Land




For NSE data:
- [equity data](stocks.md)
- [sgb](./sovereign-gold-bonds)

Use these headers to be safe for polling:
```python
uAgent = "Mozilla/5.0 (Windows NT 10.0; rv:109.0) Gecko/20100101 Firefox/118.0"

headers = {
    "User-Agent": uAgent,
    "Accept": "*/*",
    "Accept-Language": "en-US,en;q=0.5",
    "Accept-Encoding": "gzip, deflate",
    "Referer": "https://www.nseindia.com/get-quotes/equity?symbol=HDFCBANK",
}
```
