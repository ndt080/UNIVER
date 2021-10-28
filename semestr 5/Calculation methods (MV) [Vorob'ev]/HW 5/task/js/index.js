import {DrawChart, DrawChartAllSplines} from "./charts.service.js";
import {tridiagonalMatrixAlgorithm} from "./matrixAlgorithms.js";

let xi = [2.1, 2.9, 3.6, 4.4, 5.2];
let y = [-8.1, -7.2, -6.3, -5.5, -4];
// const xi = [1.3, 1.9, 2.5, 3.4, 4]
// const y = [-4.1, -2.1, -0.5, -1.2, 2.3]

const n = 4;
const countHop = 10;
const alpha = [...y];
const h = (function (xi) {
    let h = [];
    for (let i = 1; i <= n; i++) {
        h.push(xi[i] - xi[i - 1])
    }
    return h;
})(xi);
// const h = [0.8, 0.7, 0.8, 0.8];
// const h = [0.6, 0.6, 0.9, 0.6];

const splines = {
    linear: [],
    quadratic: [],
    cubic: [],
}

let splineLinear = function (i, x, alpha, beta, xi) {
    return alpha[i] + beta[i] * (x - xi[i]);
}

let splineQuadratic = function (i, x, alpha, beta, gamma, xi) {
    return alpha[i] +
        beta[i] * (x - xi[i]) +
        (1 / 2) * gamma[i] * Math.pow(x - xi[i], 2);
}

let splineCubic = function (i, x, alpha, beta, gamma, delta, xi) {
    return alpha[i] +
        beta[i] * (x - xi[i]) +
        (1 / 2) * gamma[i] * Math.pow(x - xi[i], 2) +
        (1 / 6) * delta[i] * Math.pow(x - xi[i], 3);
}

// Linear spline
{
    const beta = Array(n + 1).fill(0);
    for (let i = 1; i <= n; i++) {
        beta[i] = (y[i] - y[i - 1]) / h[i - 1];
    }

    for (let i = 1; i <= n; i++) {
        let hops = linspace(xi[i - 1], xi[i], countHop);

        for (let hop of hops) {
            splines.linear.push({
                x: hop,
                y: splineLinear(i, hop, alpha, beta, xi),
            });
        }
    }

    console.log("## Linear spline ##");
    console.log("alpha: ", alpha);
    console.log("beta: ", beta);
    console.log("S(x): ", [...splines.linear]);
    DrawChart("linear-spline", [...splines.linear], n, countHop);
}

// Quadratic spline
{
    const beta = Array(n + 1).fill(0);
    const gamma = Array(n + 1).fill(0);

    for (let i = 1; i <= n; i++) {
        beta[i] = -beta[i - 1] + 2 * (alpha[i] - alpha[i - 1]) / h[i - 1];
        gamma[i] = (beta[i] - beta[i - 1]) / h[i - 1];
    }

    for (let i = 1; i <= n; i++) {
        let hops = linspace(xi[i - 1], xi[i], countHop);

        for (let hop of hops) {
            splines.quadratic.push({
                x: hop,
                y: splineQuadratic(i, hop, alpha, beta, gamma, xi),
            });
        }
    }

    console.log("## Quadratic spline ##");
    console.log("alpha: ", alpha);
    console.log("beta: ", beta);
    console.log("gamma: ", gamma);
    console.log("S(x): ", [...splines.quadratic]);
    DrawChart("quadratic-spline", [...splines.quadratic], n, countHop);
}

// Cubic spline
{
    const beta = Array(n + 1).fill(0);
    const delta = Array(n + 1).fill(0);

    const c = [];
    const b = [];
    const e = [];

    for (let i = 2; i < n; i++) {
        c.push(h[i - 1] / (xi[i + 1] - xi[i - 2]));
        e.push(h[i] / (xi[i + 1] - xi[i - 2]));
    }

    for (let i = 1; i <= n - 1; i++) {
        b.push(6 * ((alpha[i + 1] - alpha[i]) / h[i] - (alpha[i] - alpha[i - 1]) / h[i - 1]) / (xi[i + 1] - xi[i - 1]));
    }

    let tmpGamma = tridiagonalMatrixAlgorithm(n / 2, n - 1, c, e, [2, 2, 2], b);
    const gamma = [0, ...tmpGamma, 0];

    for (let i = 1; i <= n; i++) {
        delta[i] = (gamma[i] - gamma[i - 1]) / h[i - 1];
        beta[i] = (alpha[i] - alpha[i - 1]) / h[i - 1] + h[i - 1] * (2 * gamma[i] + gamma[i - 1]) / 6;
    }

    for (let i = 1; i <= n; i++) {
        let hops = linspace(xi[i - 1], xi[i], countHop);

        for (let hop of hops) {
            splines.cubic.push({
                x: hop,
                y: splineCubic(i, hop, alpha, beta, gamma, delta, xi),
            });
        }
    }

    console.log("## Cubic spline ##");
    console.log("alpha: ", alpha);
    console.log("beta: ", beta);
    console.log("gamma: ", gamma);
    console.log("delta: ", delta);
    console.log("S(x): ", [...splines.cubic]);
    console.log(splines.cubic);
    DrawChart("cubic-spline", [...splines.cubic], n, countHop);
}

DrawChartAllSplines(splines, n, countHop)

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


