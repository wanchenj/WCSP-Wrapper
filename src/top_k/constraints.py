import math


class Constraint:
    """
    Constraint class
    """
    def __init__(self, variables, def_cost=0):
        """
        Initialize a constraint
        param variables: a list of variables
        param def_cost: default cost
        """
        self.default = def_cost
        self.variables = variables
        self.arity = len(variables)
        self.weights = {}

    def set_weight(self, assign, cost):
        """
        Set weight of an assignment of values
        """
        self.weights[assign] = cost

    def output(self):
        """
        Print the constraint in WCSP foramt
        """
        strs = "{0} {1} {2} {3}\n".format(len(self.variables),
                                          ' '.join(map(str, self.variables)),
                                          self.default, len(self.weights))
        for k, v in self.weights.items():
            strs += "{0} {1}\n".format(' '.join(map(str, k)), v)
        return strs.strip('\n')

    def get_variables(self):
        return self.variables
