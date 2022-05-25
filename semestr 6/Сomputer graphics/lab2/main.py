import os
import tkinter as tk
from PIL import Image, ImageTk


class App(tk.Tk):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        self.title('Image analysis')
        self.resizable(False, False)

        # Image Frame
        self.image_frame = tk.Frame(master=self)
        
        self.image = tk.Label(master=self.image_frame, width=400, height=300)
        self.image.grid(row=0, column=0)
        self.image_frame.grid(row=0, column=0, padx=10, pady=10, sticky='nsew')

        # Info
        self.info_frame = tk.Frame(master=self)
        self.info_frame.columnconfigure(0, weight=1)
        self.info_frame.columnconfigure(1, weight=1)
        
        self.filename_label = tk.Label(master=self.info_frame, text='Filename:')
        self.filename_label.grid(row=0, column=0, pady=5, padx=5, sticky='e')
        self.filename = tk.Label(master=self.info_frame)
        self.filename.grid(row=0, column=1, pady=5, padx=5, sticky='w')

        self.size_label = tk.Label(master=self.info_frame, text='Size:')
        self.size_label.grid(row=1, column=0, pady=5, padx=5, sticky='e')
        self.size= tk.Label(master=self.info_frame)
        self.size.grid(row=1, column=1, pady=5, sticky='w')

        self.dpi_label = tk.Label(master=self.info_frame, text='DPI:')
        self.dpi_label.grid(row=2, column=0, pady=5, padx=5, sticky='e')
        self.dpi = tk.Label(master=self.info_frame)
        self.dpi.grid(row=2, column=1, pady=5, sticky='w')

        self.depth_label = tk.Label(master=self.info_frame, text='Depth:')
        self.depth_label.grid(row=3, column=0, pady=5, padx=5, sticky='e')
        self.depth = tk.Label(master=self.info_frame)
        self.depth.grid(row=3, column=1, pady=5, sticky='w')

        self.compression_label = tk.Label(master=self.info_frame, text='Compression:')
        self.compression_label.grid(row=4, column=0, pady=5, padx=5, sticky='e')
        self.compression = tk.Label(master=self.info_frame)
        self.compression.grid(row=4, column=1, pady=5, sticky='w')

        self.button = tk.Button(master=self.info_frame, text='Next', command=self.to_next)
        self.button.grid(row=5, columnspan=2, sticky='nsew')

        self.info_frame.grid(row=1, column=0, padx=10, pady=10, sticky='nsew')

        # Start
        self.gen = self.introspect()
        info = next(self.gen)
        self.fill(info) 


    def introspect(self):
        DIRECTORY = 'check'
        for file in os.listdir(DIRECTORY):
            im = Image.open(f'{DIRECTORY}{os.sep}{file}')

            yield {'im': im, 'filename': im.filename, 'size': im.size, 'dpi': im.info.get('dpi'), 
                    'depth': im.mode, 'compression': im.info.get('compression')}

    def to_next(self):
        try:
            info = next(self.gen)
        except:
            self.gen = self.introspect()
            info = next(self.gen)
        self.fill(info)

    def fill(self, info):
        info['im'].thumbnail((400, 300))
        image = ImageTk.PhotoImage(info['im'])
        self.image.configure(image=image)
        self.image.image = image

        self.filename.config(text=info['filename'])
        self.size.config(text=info['size'])
        self.dpi.config(text=info['dpi'])
        self.depth.config(text=info['depth'])
        self.compression.config(text=info['compression'] if info['compression'] else 'None')


def main():
    App().mainloop()


if __name__ == '__main__':
    main()
