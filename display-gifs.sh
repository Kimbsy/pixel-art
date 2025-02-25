#!/bin/bash

# Directory containing GIFs
GIF_DIR="/home/kimbsy/Projects/pixel-art"

# Function to get a random GIF file from the directory
get_random_gif() {
    find "$GIF_DIR" -type f -iname "*.gif" | shuf -n 1
}

# Open the initial random GIF in fullscreen
mpv --fs --loop-file=inf --no-osc --no-osd-bar --background='color' --really-quiet "$(get_random_gif)" &

# Store the PID of sxiv to later kill it
CURRENT_PID=$!

# Change the GIF every 10 seconds
while true; do
    sleep 20
    # Open the new GIF first before killing the old one
    mpv --fs --loop-file=inf --no-osc --no-osd-bar --background='color' --really-quiet "$(get_random_gif)" &
    NEW_PID=$!
    sleep 1  # Small delay to ensure the new instance is open
    kill $CURRENT_PID
    CURRENT_PID=$NEW_PID
done
