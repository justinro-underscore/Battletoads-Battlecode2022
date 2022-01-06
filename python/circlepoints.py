import sys
import math

# Calculates all of the points given a radius squared
def calc_points(r2):
  pts = []
  r = math.ceil(math.sqrt(r2))
  # Generate points in the first quadrant of a circle
  for x in range(r + 1):
    for y in range(r + 1):
      # Check if the point is inside the radius squared
      if (x*x) + (y*y) <= (r2):
        # Multiply to get the coords in the other quadrants
        for mult in [(1, 1), (1, -1), (-1, 1), (-1, -1)]:
          pt = (x * mult[0], y * mult[1])
          if not pt in pts:
            pts.append(pt)

  # Sort them by closest to the origin
  pts.sort(key=lambda pt: (pt[0]*pt[0]) + (pt[1]*pt[1]))
  return pts

# Prints the points so they can be pasted into java code
def print_pts(pts):
  formatted_pts = list(map(lambda pt: f'{{{pt[0]},{pt[1]}}}', pts))
  print(f'{{{",".join(formatted_pts)}}}')

try:
  if len(sys.argv) > 1:
    radius_squared = int(sys.argv[1])
    print_pts(calc_points(radius_squared))
  else:
    print('python3 circlepoints.py {{radius_squared}}')
except RuntimeError as e:
  print('ERROR: ' + str(e))