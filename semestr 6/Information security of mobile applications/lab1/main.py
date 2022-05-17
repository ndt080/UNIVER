# main.py

import logging
import json
import re
import sys
import dir_scanner


logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
logger = logging.getLogger(__name__)


def save_scan_info(scan_out):
    out_file_path = 'dist/scan_out.json'
    logger.info("Saving the scan result to '%s'", out_file_path)

    with open(out_file_path, 'w', encoding='utf-8') as f:
        json.dump(scan_out, f, ensure_ascii=False, indent=4)

    logger.info("Saved")


def main():
    if len(sys.argv) < 2:
        logger.error("Invalid arguments")

        print('\nArguments: ')
        print('\tpath - [required] Path to the scan folder')
        print('\tpattern - [omitempty] Pattern of filtering files by owner')
        return

    pattern = re.compile(sys.argv[2]) if len(sys.argv) == 3 else None

    scan_out = dir_scanner.scan(sys.argv[1], pattern)
    save_scan_info(scan_out=scan_out)


if __name__ == '__main__':
    main()
