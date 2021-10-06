let xArr = [0, 0.25, 0.5, 0.75, 1]
let yArr = [1, 0.290564, -0.401567, -1.15475, -2.11522]

function calcDividedDifference(degree, xArr, yArr) {
    let dividedDifference = [];
    let firstIter = true;

    for (let n = 0; n < degree; n++) {
        dividedDifference[n] = [];

        for (let i = 0; i < degree - n; i++) {
            if (firstIter) {
                dividedDifference[n].push(
                    (yArr[i + 1] - yArr[i]) / (xArr[i + 1] - xArr[i])
                );
            } else {
                dividedDifference[n].push(
                    (dividedDifference[n-1][i + 1] - dividedDifference[n-1][i]) /
                    (xArr[i + 1 + n] - xArr[i + n - 1])
                );
            }
        }
        firstIter = false;
    }
    return dividedDifference;
}
console.log(calcDividedDifference(4, xArr, yArr))

let weightFactors = function (xArr){
    let res = []
    xArr.forEach( (xI, i) => {
        let currentV = 1;
        xArr.forEach( (xJ, j) => {
            if(i != j){
                currentV *= (xI - xJ);
            }
        });
        res.push(1/currentV);
    });
    return res;
}

let weightFactorArr = weightFactors(xArr)
console.log("Weight factor: ", weightFactorArr)

let calcBarycentricForm = function (x, xArr, yArr, weightFactorArr) {
    let numerator = 0;
    let denominator = xArr.reduce((res, xi, i) => {
        let tmp = weightFactorArr[i]/(x - xi);
        numerator += yArr[i] * tmp;
        return res + tmp;
    });

    return numerator / denominator;
}
console.log(calcBarycentricForm(0.8, xArr, yArr, weightFactorArr))


/*------------------- TASK 2 --------------*/
// let yArr = [5, 7, 8, 10]
// let xArr = [0, 1, 3, 6]
// console.log(calcDividedDifference(3, xArr, yArr))
