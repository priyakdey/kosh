## Search

Search can happen with below API:

```bash
GET https://www.nseindia.com/api/search/autocomplete?q=Vedanta
```

Not knowing the rate limit, this can help with Search - type something and then provide a search according to the query. To make sure we are doing this better, we can cache the results. **ISIN code should never change. But can the symbol name change?**

## Market data

```bash
GET https://www.nseindia.com/api/NextApi/apiClient/GetQuoteApi?functionName=getSymbolData&marketType=N&series=EQ&symbol=VEDL
```

response:
```json
{
  "equityResponse": [
    {
      "orderBook": {
        "buyPrice1": 689.5,
        "buyQuantity1": 388,
        "buyPrice2": 689.45,
        "buyQuantity2": 902,
        "buyPrice3": 689.4,
        "buyQuantity3": 324,
        "buyPrice4": 689.35,
        "buyQuantity4": 383,
        "buyPrice5": 689.3,
        "buyQuantity5": 344,
        "sellPrice1": 689.55,
        "sellQuantity1": 41,
        "sellPrice2": 689.7,
        "sellQuantity2": 374,
        "sellPrice3": 689.75,
        "sellQuantity3": 1653,
        "sellPrice4": 689.8,
        "sellQuantity4": 1671,
        "sellPrice5": 689.85,
        "sellQuantity5": 715,
        "lastPrice": 689.55,
        "totalBuyQuantity": 605712,
        "totalSellQuantity": 986642,
        "perBuyQty": 38.0387778094569,
        "perSellQty": 61.9612221905431
      },
      "metaData": {
        "identifier": "VEDLEQN",
        "companyName": "Vedanta Limited",
        "isinCode": "INE205A01025",
        "symbol": "VEDL",
        "series": "EQ",
        "marketType": "N",
        "open": 680,
        "dayHigh": 691,
        "dayLow": 677.6,
        "previousClose": 675.65,
        "averagePrice": 687.43,
        "change": 13.9,
        "pChange": 2.06,
        "basePrice": 675.65,
        "closePrice": 0,
        "indicativeClose": 0,
        "ic_change": 0,
        "ic_pchange": 0,
        "spoChange": 0,
        "spoPchange": 0,
        "symbolStatus": "NM",
        "adjPrice": 0,
        "iep": 0,
        "ieq": 0
      },
      "tradeInfo": {
        "totalTradedVolume": 3205659,
        "totalTradedValue": 2203666166.37,
        "series": "EQ",
        "lastPrice": 689.55,
        "issuedSize": 3910388057,
        "basePrice": 675.65,
        "ffmc": 1166448961063.56,
        "faceValue": 1,
        "impactCost": 0.02,
        "deliveryToTradedQuantity": 37.71,
        "applicableMargin": 17.07,
        "marketLot": null,
        "quantitytraded": 21124257,
        "deliveryquantity": 7965929,
        "totalMarketCap": 2696408084704.35,
        "secwisedelposdate": "03-Feb-2026 00:00:00"
      },
      "priceInfo": {
        "yearHightDt": "29-Jan-2026 00:00:00",
        "yearLowDt": "07-Apr-2025 00:00:00",
        "yearHigh": 769.8,
        "yearLow": 363,
        "priceBand": "608.10-743.20",
        "cmDailyVolatility": "2.12",
        "cmAnnualVolatility": "40.5",
        "tickSize": 0.05,
        "inav": 0,
        "isINav": "False",
        "ppriceBand": "No Band"
      },
      "secInfo": {
        "secStatus": "Listed",
        "listingDate": "13-May-1998 00:00:00",
        "pdSectorInd": "NIFTY 500                                         ",
        "pdSectorPe": "14.58",
        "pdSymbolPe": "15.95",
        "isSuspended": "Active",
        "basicIndustry": "Diversified Metals",
        "index": "Nifty Next 50",
        "deliveryQuantity": "7965929",
        "deliveryTotradedQuantity": "37.71",
        "securityvar": "13.57",
        "indexvar": "0",
        "extremelossMargin": "3.5",
        "varMargin": "13.57",
        "adhocMargin": "0",
        "applicableMargin": "17.07",
        "bondType": null,
        "issueDesc": null,
        "issueDate": null,
        "maturityDate": null,
        "couponRate": null,
        "nxtIpDate": null,
        "creditRating": null,
        "macro": "Commodities",
        "sector": "Metals & Mining",
        "industryInfo": "Diversified Metals",
        "indexList": [
          "NIFTY NEXT 50",
          "NIFTY 100",
          "NIFTY 200",
          "NIFTY 500",
          "NIFTY COMMODITIES",
          "NIFTY DIVIDEND OPPORTUNITIES 50",
          "NIFTY HIGH BETA 50",
          "NIFTY INDIA FPI 150",
          "NIFTY INDIA MANUFACTURING",
          "NIFTY LARGEMIDCAP 250",
          "NIFTY METAL",
          "NIFTY MNC",
          "NIFTY TOTAL MARKET",
          "NIFTY100 EQUAL WEIGHT",
          "NIFTY100 ESG",
          "NIFTY200 VALUE 30",
          "NIFTY500 EQUAL WEIGHT",
          "NIFTY500 LARGEMIDSMALL EQUAL-CAP WEIGHTED",
          "NIFTY500 MULTICAP 50:25:25",
          "NIFTY500 VALUE 50"
        ],
        "boardStatus": "Main",
        "tradingSegment": "Normal Market",
        "sessionNo": null,
        "classShare": "Equity",
        "nameOfComplianceOfficer": null,
        "sddcompliance": null
      },
      "lastUpdateTime": "04-Feb-2026 09:45:59"
    }
  ]
}
```

## What we are interested in:
- isin
- name
- nse_symbol
- exchange (NSE / BSE) - not sure how to get this...
- prevClose - T - 1 closing price