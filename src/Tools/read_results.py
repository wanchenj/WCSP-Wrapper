import math
import re
import sys



def get_total_weight(file_):
    total_weight = 0
    for line in file_.readlines():
        line_elements = line.split(' ')
        if line_elements[0] == 'Weight':
            total_weight += float(str(line_elements[1]).strip())
    return total_weight


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


# open the file
in_file = open('wcsp-output.txt', 'r')

# create an array of tuples
assignments = {}
inResults = False

# first get the total weight
total_weight = get_total_weight(in_file)
ghost_total = 0
in_file = open('wcsp-output.txt', 'r')

for line in in_file.readlines():
    line_elements = line.split(' ')
    if ("Solution" in line):
        print(line.strip())
    elif len(line_elements) == 2:
        if line_elements[0] == 'Weight':
            # get the weight
            sol_weight = float(line_elements[1])
            ghost_total += sol_weight
            print(" with a confidence of: ", (  -math.log(sol_weight/total_weight)), '  ', sol_weight, '/', total_weight)
            #print(" with a confidence of: ", -math.log(sol_weight/total_weight), '  ', sol_weight, '/', total_weight)
        else:
            find_var(line_elements[0], line_elements[1])


if ghost_total != total_weight:
    print("the weights do not add up ")
