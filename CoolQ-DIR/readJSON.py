# coding: utf-8 


import json
import sys
from collections import OrderedDict


# Change values in json object
# Write to json file
def write_json(player, changeType, changeValue):
    # generate json
    data = read_json("PlayersData.json")
    if (changeType.lower() == "view" and player.lower() != "all"):
        # Check if exist
        if (player not in data):
            print("Cannot find this player!")
            return
        # View One Player
        printValue = ""
        printValue = printValue + "Player: " + player + "\n"
        for attribute in data[player]:
            printValue = printValue + attribute + ": " + str(data[player][attribute]) + "\n"
        print(printValue)
    elif (changeType.lower() == "view" and player.lower() == "all"):
        # View All
        printValue = ""
        for thisPlayer in data:
            printValue = printValue + "Player: " + thisPlayer + "\n"
            for attribute in data[thisPlayer]:
                printValue = printValue + attribute + ": " + str(data[thisPlayer][attribute]) + "\n"
            printValue = printValue + "\n"
        print(printValue)
    elif (changeType.lower() == "create"):
        # Check if exist
        if (player in data):
            print("Player existed!")
            return
        # Create
        printValue = ""
        printValue = printValue + "Player: " + player + ", created\n"
        newPlayerData = changeValue.split(",")
        newPlayerAttributes = []
        newPlayerValues = []
        for x in range(0, len(newPlayerData)):
            thisAttribute = newPlayerData[x].split("=")
            newPlayerAttributes.append(thisAttribute[0].replace(" ", ""))
            newPlayerValues.append(thisAttribute[1].replace(" ", ""))
        thisdata = "{"
        for x in range(0, len(newPlayerAttributes)):
            thisdata = thisdata + "\"" + newPlayerAttributes[x] + "\"" + ": " + str(newPlayerValues[x])
            if (x != len(newPlayerAttributes)-1):
                thisdata = thisdata + ", "
        thisdata = thisdata + "}"
        data[player] = json.loads(thisdata, object_pairs_hook=OrderedDict)
        for attribute in data[player]:
            printValue = printValue + attribute + ": " + str(data[player][attribute]) + "\n"
        print(printValue)
        modify_file(data)
    elif (changeType.lower() == "delete"):
        # Check if exist
        if (player not in data):
            print("Cannot find this player!")
            return
        # Delete
        for thisPlayer in data:
            if (thisPlayer == player):
                del data[thisPlayer]
                break
        print("Player " + player + ", deleted")
        modify_file(data)
    else:
        # Check if exist
        if (player not in data):
            print("Cannot find this player!")
            return
        # Modify
        printValue = "Modifying\n"
        printValue = printValue + "Player: " + player + "\n"
        originalValue = data[player][changeType]
        if (changeValue[0] == "+"):
            data[player][changeType] = data[player][changeType] + int(changeValue[1:])
        elif (changeValue[0] == "-"):
            data[player][changeType] = data[player][changeType] - int(changeValue[1:])
        newValue = data[player][changeType]
        printValue = printValue + changeType + ", " + str(originalValue) + " -> " + str(newValue) + "\n"
        print (printValue)
        modify_file(data)


# Modify JSON file
def modify_file(jsonObj):
    # Test: create a new JSON instead
    with open("PlayersData.json", 'w+') as f:
        json.dump(jsonObj, f, indent=4)


# Read a json file
# Return a json object
def read_json(filename):
    data = ""
    with open(filename, 'r') as file:
        filecontent = file.read()
        data = json.loads(filecontent, object_pairs_hook=OrderedDict)
    return data


# Main function
# sys.argv[0] -> <file_name>
# sys.argv[1] -> <player_name>  -> <name> (or "all")            -> e.g. "Saber"
# sys.argv[2] -> <change_type>  -> "HP/MP/SAN/..." or <action>  -> e.g. "HP" or "View" or "Create"
# sys.argv[3] -> <change_value> -> <value> or <none>            -> e.g. "+20" or "" or "<attribute1>=<value1>, ..."
#
# Print out required data to pass to JAVA
def main():
    if (str(sys.argv[2]).lower() != "view" and str(sys.argv[2]).lower() != "create" and str(sys.argv[2]).lower() != "delete"):
        # Modify values
        # Return: NULL
        # Print:
        # # Player: <player_name>
        # # <change_type>, <original_value> -> <new_value>
        # File modification needed
        # # python readJSON.py <player_name> <player_attribute> <change_value>
        write_json(str(sys.argv[1]), str(sys.argv[2]), str(sys.argv[3]))
    elif (str(sys.argv[2]).lower() == "view"):
        # View player data
        # Return: NULL
        # Print:
        # # Player: <player_name>
        # # <attribute1>: <value1>
        # # <attribute2>: <value2>
        # # ...
        # # python readJSON.py <player_name> View
        write_json(str(sys.argv[1]), str(sys.argv[2]), "")
    elif (str(sys.argv[2]).lower() == "delete"):
        # Delete player data
        # Return: NULL
        # Print:
        # # Player: <player_name>, deleted
        # # python readJSON.py <player_name> Delete
        write_json(str(sys.argv[1]), str(sys.argv[2]), "")
    elif (str(sys.argv[2]).lower() == "create"):
        # Create new player data
        # Return: NULL
        # Print:
        # # Player: <player_name>, created
        # # <attribute1>: <value1>
        # # <attribute2>: <value2>
        # # ...
        # File modification needed
        # # python readJSON.py <player_name> Create <attribute1>=<value1>,<attribute2>=<value2>
        write_json(str(sys.argv[1]), str(sys.argv[2]), str(sys.argv[3]))

# Declare main function
if __name__ == "__main__":
    main()

