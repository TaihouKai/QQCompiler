import sys
import re
from random import randint

# Example: 3d12+5d10-2d8
msg = sys.argv[1]
commands = []
results = []
operators = []
output = ""

# list operators
for x in range(0, len(msg)):
	if (msg[x].isdigit() == False and msg[x].isalpha() == False):
		operators.append(msg[x])

# list commands
cleanMsg = msg
for x in range(0, len(operators)):
	cleanMsg = cleanMsg.replace(operators[x], ",")
commands = cleanMsg.split(",")

# calculate results for each
for x in range(0, len(commands)):
	thisCommand = commands[x].replace("d", ",")
	thisCommand = thisCommand.replace("D", ",")
	params = thisCommand.split(",")
	result = 0;
	if (len(params) == 1):
		result = int(params[0])
		results.append(result)
		continue
	for y in range(0, int(params[0])):
		result = result + randint(1, int(params[1]))
	results.append(result)

# calculate final result
finalResult = results[0]
output = output + str(results[0])
for x in range(0, len(operators)):
	if (operators[x] == "+"):
		finalResult = finalResult + results[x+1]
		output = output + " + " + str(results[x+1])
	elif (operators[x] == "-"):
		finalResult = finalResult - results[x+1]
		output = output + " - " + str(results[x+1])
output = output + " = " + str(finalResult)

# print result
if (len(results) > 1):
	print(output)
else:
	print(finalResult)