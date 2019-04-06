from wcsp import *
import sys
import optparse

# initialize a parser
parser = optparse.OptionParser()

parser.add_option('-f', '--file', action="store", dest="file",
                  help="specify the filename")
parser.add_option('-k', '--no-kernelization', action='store_true',
                  dest='nokernelize', default=False, help='no kernelization')
parser.add_option('-M', '--message-passing', action='store_true',
                  dest='message', help='do message passing')
parser.add_option('-L', '--linear-programming', action='store_true',
                  dest='lp', help='use linear programming')

parser.add_option('-m', '--message-passing-solver',
                  action='store_true', dest='mpsolver',
                  help='use message passing solver to solve mwvc')

parser.add_option('-p', '--solver-path', action='store', dest='solverpath',
                  help='path to binary of solver')

parser.add_option('-K', '--number-of-k', action='store', dest='K',
                  help='number of K', type='int', default=1)

parser.add_option('-o', '--output-wcsp', action='store', dest='output',
                  help='temporary wcsp file', default='temp.wcsp')

parser.add_option('-O', '--output-solution', action='store', dest='sol',
                  help='path to the output solutions')

parser.add_option('-n', '--number-randomization', action='store',
                  dest='numrand', help='maximum number of times of \
                  randomization for each solution', default=10, type='int')

parser.add_option('-N', '--number-rand-cons', action='store', dest='numcons',
                  help='number of randomized constraints added\
                  for each solution', default=10, type='int')

options, args = parser.parse_args()
if not options.file:
    parser.error('Miss input file')
if not options.solverpath:
    parser.error('Miss path to WCSPLift')
# print to a file
if options.sol:
    sys.stdout = open(options.sol, 'w')

params = []
if options.nokernelize:
    params.append('-k')
if options.message:
    params.append('-M')

if options.lp:
    params.append('-L')

if options.mpsolver:
    params.append('-m')
    params.append('m')

# initialize an instance
instance = WCSP()
# update variables and constraints from a given input file
instance.read_wcsp(options.file)
# get top solutions
solutions = instance.get_topk(options.K, options.numrand, options.output,
                              options.solverpath, param=params,
                              K2=options.numcons)
# print solutions
instance.output_sols(solutions)
