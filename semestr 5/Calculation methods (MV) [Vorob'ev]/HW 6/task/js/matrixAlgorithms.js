export function tridiagonalMatrixAlgorithm(middle, matrixSize, columnA, columnB, columnC, vecF) {
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
