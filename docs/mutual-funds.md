
Source: https://www.mfapi.in/

## APIS:

### 1. Fetch list of all Mutual Funds

```bash
curl 'https://api.mfapi.in/mf?limit=1000&offset=0' \
  -H 'Accept-Language: en-US,en-IN;q=0.9,en;q=0.8,en-AU;q=0.7,en-CA;q=0.6,en-NZ;q=0.5,en-GB;q=0.4,en-ZA;q=0.3' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'DNT: 1' \
  -H 'Origin: https://www.mfapi.in' \
  -H 'Pragma: no-cache' \
  -H 'Referer: https://www.mfapi.in/' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-site' \
  -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36' \
  -H 'accept: application/json' \
  -H 'sec-ch-ua: "Not(A:Brand";v="8", "Chromium";v="144", "Google Chrome";v="144"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Linux"'
```

Response:
```json
[
    {
        "schemeCode": 100027,
        "schemeName": "Grindlays Super Saver Income Fund-GSSIF-Half Yearly Dividend",
        "isinGrowth": null,
        "isinDivReinvestment": null
    },
    {
        "schemeCode": 100028,
        "schemeName": "Grindlays Super Saver Income Fund-GSSIF-Quaterly Dividend",
        "isinGrowth": null,
        "isinDivReinvestment": null
    }
]
```

### 2. Get Mutual fund latest nav

```bash
curl 'https://api.mfapi.in/mf/125497/latest' \
  -H 'Accept-Language: en-US,en-IN;q=0.9,en;q=0.8,en-AU;q=0.7,en-CA;q=0.6,en-NZ;q=0.5,en-GB;q=0.4,en-ZA;q=0.3' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'DNT: 1' \
  -H 'Origin: https://www.mfapi.in' \
  -H 'Pragma: no-cache' \
  -H 'Referer: https://www.mfapi.in/' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-site' \
  -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36' \
  -H 'accept: application/json' \
  -H 'sec-ch-ua: "Not(A:Brand";v="8", "Chromium";v="144", "Google Chrome";v="144"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Linux"'
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

