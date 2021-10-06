{
    let f = (x) => Math.cos(8 * x - 17) + 8 * x;
    let xArr = [-2, -1, 0, 1, 2];

    xArr.forEach((x) => {
        let res = f(x);
        console.log(`Result ${res};\t x=${x}`)
    });
}

//Сузить отрезки отделённости корней до размера 10−2 с помощью метода бисекций
{
    let f = (x) => Math.cos(8 * x - 17) + 8 * x;
    let a = 0.0;
    let b = 1.0;

    console.log(`Start segment: [a = ${a}, b = ${b}]`)

    while (Math.abs(a - b) > 2e-2) {
        let x = (a + b) / 2;
        console.log(`\tSegment: [a = ${a}, b = ${b}]`);
        (f(x) * f(a)) < 0 ? b = x : a = x;
    }

    console.log(`Final segment: [a = ${a}, b = ${b}]`)
}

//Решить с точностью 𝜀 = 10−5 указанное уравнение методом простых итераций.
{
    let phi = (x) => -Math.cos(8.0 * x - 17.0) / 8.0;
    let xCurrent = (0.109375 + 0.125) / 2;
    let xPrevious = 0.109375;
    let iteration = 0;

    while (Math.abs(xCurrent - xPrevious) > 1e-5) {
        xPrevious = xCurrent;
        xCurrent = phi(xCurrent);
        iteration++;
    }
    console.log(`Count iteration: ${iteration}; x = ${xCurrent}`)
}

//Решить с точностью 𝜀 = 10−5 указанное уравнение методом Ньютона.
{
    let f = (x) => Math.cos(8 * x - 17) + 8 * x;
    let fDerivative = (x) => -8 * Math.sin(8 * x - 17) + 8;

    let xCurrent = (0.109375 + 0.125) / 2;
    let xPrevious = 0.109375;
    let iteration = 0;

    while (Math.abs(xCurrent - xPrevious) > 1e-5) {
        iteration++;
        xPrevious = xCurrent;
        xCurrent -= f(xPrevious) / fDerivative(xPrevious);
    }
    console.log(`Count iteration: ${iteration}; x = ${xCurrent}`)
}
