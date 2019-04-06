# open the file
file = open('wcsp-output.txt', 'r')

# create an array of tuples
assignments = {}
inResults = False

for line in file.readlines():
    if ("Best assignments:" in line) or inResults:
      inResults = True
      line_elements = line.split(' ')
      print(line)
      if len(line_elements) == 2:
        assignments[line_elements[0]] = line_elements[1]
        print(line)

