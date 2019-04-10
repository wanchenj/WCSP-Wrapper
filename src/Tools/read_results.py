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
            # try to get the variable name
            reg_var = re.compile(r'v\d+')
            find_var = re.findall(reg_var, line)
            if len(find_var) == 1 and line[0] == 'v':      # must be 1, otherwise it's prolly a constraint
                # this means this line is a variable, now check if the next character is var
                potential_var = find_var[0]
                # remove the 'v'
                potential_var = potential_var.replace('v', '')
                if potential_var == var:
                    # get the name in the parenthesis
                    regex = re.compile(r'\(.*\)')
                    #find the regex
                    result = re.findall(regex, line)
                    # take out the parantheses
                    result =  result[0]
                    value = value.strip()
                    if value == '1':
                        print("v{:<4}{:+<80}{:+>20}".format(var, result, 'true'))
                    else:
                        print("v{:<4}{:-<80}{:->20}".format(var, result, 'false'))
for line in file.readlines():
    line_elements = line.split(' ')
    if ("Solution" in line):
        print(line)
    elif len(line_elements) == 2:
        if line[0] == 'Weight':
            print(line)
        else:
            find_var(line_elements[0], line_elements[1])


