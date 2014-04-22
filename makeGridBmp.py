#!/usr/bin/env python3

# Use the Python Imaging Library, install with `sudo pip install pillow`
# or with a local package manager (such as `pacman` or `apt-get`) with
# `sudo pacman -S python-pillow`.
from PIL import Image

# Create a grid and save to `filename` given a `gridSize` as (width,height),
# such as (16,16), and gridPoints as a list of points ((x1,y1),(x2,y2),...),
# such as ((0,0),(1,1),(2,0)).
def createGrid(gridSize, gridPoints, filename):
  img = Image.new('RGB', gridSize, "white")
  pixels = img.load()

  for point in gridPoints:
    pixels[point[0], point[1]] = (0,0,0)

  img.save(filename)

gridSize = (16, 16)
gridPoints = ((0, 0), (1, 1), (2, 2), (2,0))
createGrid(gridSize,gridPoints,"test.bmp")
