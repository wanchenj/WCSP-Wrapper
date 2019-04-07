import re
import sys

print()

# open the file
file = open('wcsp-output.txt', 'r')

# create an array of tuples
assignments = {}
inResults = False

def find_var(var, value):
    # open the original swc file:
    swc = open(sys.argv[1], 'r')

    # read the list and find the variable with the same name
    for line in swc.readlines():
        line = line.strip()
        if line != '':
            if line[0] == 'v':
                # this means this line is a variable, now check if the next character is var
                if line[1] == var:
                    # get the name in the parenthesis
                    regex = re.compile(r'\(.*\)')
                    #find the regex
                    result = re.findall(regex, line)
                    # take out the parantheses
                    result =  result[0]
                    status = ' false '
                    value = value.strip()
                    if value == '1':
                        status = " true "
                    print('The variable: ', result, ' is ', status)

for line in file.readlines():
    line_elements = line.split(' ')
    if ("Solution" in line):
        print(line)
    elif len(line_elements) == 2:
        if line[0] == 'Weight':
            print(line)
        else:
            find_var(line_elements[0], line_elements[1])


