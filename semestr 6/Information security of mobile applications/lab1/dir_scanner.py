# dir_scanner.py

import logging
import os
import pwd


logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
logger = logging.getLogger(__name__)


def get_file_owner(file_path):
    uid = os.stat(file_path).st_uid
    return pwd.getpwuid(uid).pw_name


def is_elf_file(path):
    with open(path, 'rb') as file:
        return file.read(4) == b'\x7fELF'


def is_exec_file(file_path):
    return not os.path.isdir(file_path) \
           and not os.path.islink(file_path) \
           and is_elf_file(file_path)


def filter_owners(pattern, value):
    return not (pattern is None or pattern.search(value) is None)


def scan(dir_path, owner_pattern):
    logger.info("Start scan...")
    scan_info = {}

    try:
        for file in os.listdir(dir_path):
            file_path = os.path.join(dir_path, file)

            if not is_exec_file(file_path):
                continue

            owner = get_file_owner(file_path)

            if filter_owners(owner_pattern, owner):
                owner_info = scan_info.setdefault(owner, {'files': [], 'size': 0})
                owner_info.setdefault('files', []).append(file)
    except OSError as err:
        logger.error(err)

    logger.info("Start file size calculation...")
    for owner in scan_info:
        files_size = 0

        for file in scan_info[owner]["files"]:
            file_path = os.path.join(dir_path, file)
            files_size += os.stat(file_path).st_size

        scan_info[owner]["size"] = files_size
        logger.info("Owner: %s, count files: %d, size: %d", owner, len(scan_info[owner]["files"]), files_size)

    return scan_info
