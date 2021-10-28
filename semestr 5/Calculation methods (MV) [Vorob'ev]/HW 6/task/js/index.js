// import {tridiagonalMatrixAlgorithm} from "./matrixAlgorithms.js";

let x = [0, 8, 8, 4];
let y = [0, 0, 1, 2];
let ti = [1, 2, 3, 4];

const n = 3;
const countHop = 10;
const h = 1;

const spline = {
    cubicX: [],
    cubicY: [],
    interpolPolynomial: {
        x: [],
        y: [],
    }
}

let splineCubic = function (i, t, alpha, beta, gamma, delta, ti) {
    return alpha[i] +
        beta[i] * (t - ti[i]) +
        (1 / 2) * gamma[i] * Math.pow(t - ti[i], 2) +
        (1 / 6) * delta[i] * Math.pow(t - ti[i], 3);
}

// Cubic spline
let buildCubicSpline = (t, out, nameObj, ti) => {
    const alpha = [...t];
    const beta = Array(n + 1).fill(0);
    const delta = Array(n + 1).fill(0);

    const c = [];
    const b = [];
    const e = [];

    for (let i = 2; i < n; i++) {
        c.push(h / (ti[i + 1] - ti[i - 2]));
        e.push(h / (ti[i + 1] - ti[i - 2]));
    }

    for (let i = 1; i <= n - 1; i++) {
        b.push(6 * ((alpha[i + 1] - alpha[i]) / h - (alpha[i] - alpha[i - 1]) / h) / (ti[i + 1] - ti[i - 1]));
    }

    let tmpGamma = tridiagonalMatrixAlgorithm(1, n - 1, c, e, [2, 2], b);
    const gamma = [0, ...tmpGamma, 0];

    for (let i = 1; i <= n; i++) {
        delta[i] = (gamma[i] - gamma[i - 1]) / h;
        beta[i] = (alpha[i] - alpha[i - 1]) / h + h * (2 * gamma[i] + gamma[i - 1]) / 6;
    }

    for (let i = 1; i <= n; i++) {
        let hops = linspace(ti[i - 1], ti[i], countHop);

        for (let hop of hops) {
            spline[nameObj].push(splineCubic(i, hop, alpha, beta, gamma, delta, ti));
        }
    }

    console.log("## Cubic spline ##");
    console.log("alpha: ", alpha);
    console.log("beta: ", beta);
    console.log("gamma: ", gamma);
    console.log("delta: ", delta);
    console.log(out, [...spline[nameObj]]);
}
buildCubicSpline(x, "x(ti) = ",'cubicX', ti);
buildCubicSpline(y, "y(ti) = ", 'cubicY', ti);
// DrawChart("cubic-spline", spline, n, countHop);

function linspace(a, b, n) {
    if (typeof n === "undefined") {
        n = Math.max(Math.round(b - a) + 1, 1);
    }
    if (n < 2) {
        return n === 1 ? [a] : [];
    }
    let ret = Array(n);
    n--;
    for (let i = n; i >= 0; i--) {
        ret[i] = ((i * b + (n - i) * a) / n).toFixed(3);
    }
    return ret;
}

function tridiagonalMatrixAlgorithm(middle, matrixSize, columnA, columnB, columnC, vecF) {
    let alpha = Array(matrixSize + 1).fill(0);
    let beta = Array(matrixSize + 1).fill(0);
    let mu = Array(matrixSize + 1).fill(0);
    let nu = Array(matrixSize + 1).fill(0);

    alpha[0] = columnB[0] / columnC[0];
    beta[0] = vecF[0] / columnC[0];

    for (let i = 0; i < middle - 1; i++) {
        let denominator = columnC[i + 1] - columnA[i] * alpha[i];
        alpha[i + 1] = columnB[i + 1] / denominator;
        beta[i + 1] = (vecF[i + 1] - columnA[i] * beta[i]) / denominator;
    }

    mu[matrixSize - 1] = columnA[matrixSize - 2] / columnC[matrixSize - 1]
    nu[matrixSize - 1] = vecF[matrixSize - 1] / columnC[matrixSize - 1]


    for (let i = matrixSize - 2; i > middle - 1; i--) {
        let denominator = columnC[i] - columnB[i] * mu[i + 1];
        mu[i] = columnA[i - 1] / denominator;
        nu[i] = (vecF[i] - columnB[i] * nu[i + 1]) / denominator;
    }

    let y = Array(matrixSize).fill(0);
    y[middle] = (nu[middle] - mu[middle] * beta[middle - 1]) / (1 - mu[middle] * alpha[middle - 1]);

    for (let i = middle - 1; i > -1; i--) {
        y[i] = beta[i] - alpha[i] * y[i + 1];
    }

    for (let i = middle; i < matrixSize - 1; i++) {
        y[i + 1] = nu[i + 1] - mu[i + 1] * y[i]
    }

    return y;
}

// Interpolating  Polynomial
{
    for (let i = 1; i <= n; i++) {
        let hops = linspace(ti[i - 1], ti[i], countHop);

        for (let hop of hops) {
            spline.interpolPolynomial.x.push(funcG(x, hop, ti));
            spline.interpolPolynomial.y.push(funcG(y, hop, ti));
        }
    }

    console.log("## Interpolating  Polynomial ##");
    console.log("x: ", spline.interpolPolynomial.x);
    console.log("y: ", spline.interpolPolynomial.y);
}


function funcG(points, hop, t){
    let sum = 0;
    points.forEach((point, i) => {
        let lambda = 1;
        t.forEach((item, j) => {
            if(i !== j) {
                lambda *= (hop - t[j]) / (t[i] - t[j]);
            }
        });
        sum += lambda * point;
    })
    return sum;
}
