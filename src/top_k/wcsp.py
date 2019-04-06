from constraints import *
from math import ceil
import json
import operator
import random
from subprocess import check_output


class WCSP:
    """
    class of WCSP
    """
    def __init__(self):
        """
        Intialize a WCSP instance
        """
        self.constraints = []
        self.mdom = 0  # the maximum domain
        self.vs = 0  # the number of variables
        self.cs = 0  # the number of constraints
        self.bound = 0  # the upper bound
        self.doms = []  # the domains of variables

    def read_wcsp(self, filename=''):
        """
        Update the instance from a given input file
        param filename: path to the input file
        """
        with open(filename, 'r') as f:
            # first line
            line = f.readline().strip().split()
            name = line[0]
            self.vs, self.mdom, self.cs, self.bound = list(map(int, line[1:]))
            # second line specifies domains of variables
            self.doms = list(map(int, f.readline().strip().split()))
            while True:
                line = f.readline()
                if not line:
                    break
                line = line.strip().split()
                num_vars = int(line[0])
                con_vars = list(map(int, line[1: num_vars + 1]))
                def_cost = int(line[-2])
                assign_num = int(line[-1])
                self.constraints.append(Constraint(con_vars, def_cost))

                for i in range(assign_num):
                    line = f.readline().strip().split()
                    a = tuple(map(int, line[:-1]))
                    c = float(line[-1])
                    self.constraints[-1].set_weight(a, c)

    def write_wcsp(self, filename='', K2=10, opt=None):
        """
        write to a file in order to call WCSPLift solver
        param filename: path to the output file
        param K2: number of additional constraints
        param opt: best solutions from previous runs
        """
        K1 = 5
        K2 = min(K2, int(ceil(self.vs / K1)))
        with open(filename, 'w+') as f:
            f.write('wcsp {0} {1} {2} {3}\n'.format(self.vs, self.mdom,
                                                    self.cs +
                                                    (0 if not opt else K2),
                                                    self.bound))
            f.write(' '.join(list(map(str, self.doms))) + '\n')

            cons_str = ""
            for constraint in self.constraints:
                cons_str += constraint.output() + '\n'
            if opt:
                tvars = [j for j in range(self.vs)]
                # randomize order of variables
                random.shuffle(tvars)
                for i in range(K2):
                    temp_assigns = set()
                    for assign, _w in opt:
                        t = tuple([assign[tvars[j]] for j in
                                   range(i * K1, min(self.vs, (i + 1) * K1))])
                        temp_assigns.add(t)

                    # temporary constraint
                    temp_con = Constraint(tvars[i * K1: (i + 1) * K1])
                    for assign in temp_assigns:
                        # the cost of this constraint is the upper bound
                        temp_con.set_weight(assign, self.bound)
                    cons_str += temp_con.output() + '\n'
            f.write(cons_str.strip('\n'))

    def run_solver(self, filename, solver_path, param=[]):
        """
        Call WCSP solver
        param filename: wcsp file to run
        param solver_path: path to the wcsp solver
        param param: parameters of the solver
        param K2: number of constraints to add
        """
        ret = check_output([solver_path] + [p for p in param] +
                           [filename]).decode('utf-8')
        ret = ret.split('\n')
        assignment = [0 for i in range(self.vs)]
        counter = 0
        for i in range(len(ret)):
            if ret[i] == 'Best assignments:':
                counter = i
                break
        if counter == len(ret) - 1:
            # in case the solution is not available
            sys.exit(-1)
        for i in range(counter + 2, len(ret) - 2):
            line = ret[i].split()
            assignment[int(line[0])] = int(line[1])

        return [assignment, float(ret[-2].split(':')[1])]

    def get_topk(self, K, K3, filename, solver_path, param=None, K2=10):
        """
        Get top K wcsp results
        param K: the number of results
        param K3: number of times to randomly generate a subset of variables
        param solver: path to the solver
        """
        opt = []
        for i in range(K):
            if i == 0:
                self.write_wcsp(filename, opt=opt)
                opt.append(self.run_solver(filename, solver_path, param))
            else:
                assigns = []
                for k in range(K3):
                    self.write_wcsp(filename, opt=opt, K2=K2)
                    assigns.append(self.run_solver(filename, solver_path,
                                                   param))
                assigns.sort(key=operator.itemgetter(1))
                # add the optimal assignment to solutions
                opt.append(assigns[0])
        return opt

    def output_sols(self, opt):
        # print all solutions
        for i, a in enumerate(opt):
            print('Solution {0}'.format(i+1))
            print('Weight {0}'.format(a[1]))
            for j, v in enumerate(a[0]):
                print('{0} {1}'.format(j, v))
            print('------------')
