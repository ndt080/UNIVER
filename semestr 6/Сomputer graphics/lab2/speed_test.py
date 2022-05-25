from PIL import Image
from matplotlib import pyplot as plt
import os
import time


def main():
    DIRECTORY = 'check'
    start = time.time()
    for i in range(1000):
        for i, file in enumerate(os.listdir(DIRECTORY)):
            im = Image.open(f'{DIRECTORY}{os.sep}{file}')
            filename = im.filename
            size = im.size
            dpi = im.info.get('dpi')
            mode = im.mode
            compression = im.info.get('compression')
    print(time.time() - start)


if __name__ == '__main__':
    main()
