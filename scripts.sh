# join horizontally
convert +append foo.png bar.png out.png

# join vertically
convert -append foo.png bar.png out.png

# scale https://legacy.imagemagick.org/Usage/resize/
convert foo.png -scale 500% out.png
