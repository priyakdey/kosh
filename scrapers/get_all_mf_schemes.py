import datetime
import json
from pprint import pprint
import time

import requests
from tqdm import tqdm

LIMIT = 1000

HEADERS = {
    "User-Agent": "KoshMFIngest/1.0 (personal use; contact=reach@priyakdey.com)",
    "Accept": "application/json",
    "From": "reach@priyakdey.com",
}


def fetch_schemes(s, offset: int) -> list[dict[str, str]]:
    response = s.get(
        f"https://api.mfapi.in/mf?limit={LIMIT}&offset={offset}", timeout=10
    )
    status = response.status_code
    if status != 200:
        print(
            f"ERROR: error fetching data. Got back code: {status} and response = {response.json()}"
        )
        return []

    return response.json()


def dump_json(file_path: str, mfs: list[dict[str, str]]) -> None:
    data = {}
    data["fetched_at"] = str(datetime.datetime.now(datetime.UTC))
    data["source"] = "https://api.mfapi.in/mf"
    data["user_agent"] = HEADERS["User-Agent"]
    data["count"] = len(mfs)
    data["data"] = mfs
    
    print(f"Writing to {file_path}")

    with open(file_path, "a") as fp:
        json.dump(data, fp, indent=4)


def main():
    mfs: list[dict[str, str]] = []

    with requests.Session() as s:
        s.headers.update(HEADERS)

        with tqdm(desc="Fetching MF schemes", unit="schemes", dynamic_ncols=True) as pbar:
            done = False
            while not done:
                resp = fetch_schemes(s, len(mfs))
                if not resp:
                    done = True
                    
                mfs.extend(resp)
                pbar.update(len(mfs))
                print("Sleeping for 1 minute")
                time.sleep(60)
    
    print("Done fetching all schemes")

    dump_json("mf_schemes.json", mfs)


if __name__ == "__main__":
    main()
