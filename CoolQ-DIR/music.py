from miditime.miditime import MIDITime
import subprocess
import os
import sys
import random
import string

# Instantiate the class with a tempo (120bpm is the default) and an output file destination.
musictag = sys.argv[2]
mymidi = MIDITime(180, 'data/record/' + musictag + '.mid')

# Read music from input file
# Create a list of notes. Each note is a list: [time, pitch, velocity, duration]
# [0, 60, 127, 3],  #At 0 beats (the start), Middle C with velocity 127, for 3 beats
# [10, 61, 127, 4]  #At 10 beats (12 seconds from start), C#5 with velocity 127, for 4 beats
# Sample
# [[0, 60, 127, 3],
# [3, 62, 127, 1],
# [4, 64, 127, 3],
# [7, 60, 127, 1],
# [8, 64, 127, 2],
# [10, 60, 127, 2],
# [12, 64, 127, 2]]
contents = []
with open("rhythm" + sys.argv[1] + ".txt") as file:
	for line in file:
		contents.append(line.strip())
midinotes = []
isFirstLine = True
for line in contents:
	if (isFirstLine):
		isFirstLine = False
		continue
	thisline = line.split(",")
	newline = []
	for x in range(0, 4):
		if (x < 2):
			newline.append(int(thisline[x]))
		elif (x == 2):
			newline.append(127)
		else:
			newline.append(int(thisline[x-1]))
	midinotes.append(newline)
print(midinotes) # Test purpose

# Add a track with those notes
mymidi.add_track(midinotes)

# Output the .mid file
mymidi.save_midi()

# Generate .wav file
proc = subprocess.Popen("timidity data/record/" + musictag + ".mid -Ow", stdout=subprocess.PIPE, shell=True)
proc.wait()
os.remove("data/record/" + musictag + ".mid")
