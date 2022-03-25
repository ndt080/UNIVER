from scipy.optimize import linprog


def get_result(x_arr):
    return [
        600 - 0.3 * x_arr[0] - 0.2 * x_arr[1] - 0.4 * x_arr[2],
        700 - 0.2 * x_arr[0] - 0.3 * x_arr[1] - 0.2 * x_arr[2],
        500 - 0.2 * x_arr[0] - 0.1 * x_arr[1] - 0.1 * x_arr[2],
    ]


def get_default_bounds():
    x1_bounds = (0, None)
    x2_bounds = (0, None)
    x3_bounds = (0, None)
    return x1_bounds, x2_bounds, x3_bounds


def calc_simplex(vec, A_ub, b_ub, bounds):
    return linprog(vec, A_ub=A_ub, b_ub=b_ub, bounds=bounds, method='simplex', options={'disp': True})


vec1 = [-720, -620, -760]
vecB_ub = [600, 700, 500]

A = [[0.3, 0.2, 0.4],
     [0.2, 0.3, 0.2],
     [0.2, 0.1, 0.1]]

default_bounds = get_default_bounds()
simplexResult = calc_simplex(vec1, A, vecB_ub, default_bounds)
xArr = simplexResult.get('x')
yArr = get_result(xArr)

print("\nThe result of executing the simplex method:")
print(simplexResult)
print("y1: {}; y2: {}; y3: {}".format(yArr[0], yArr[1], yArr[2]))

