export const DrawChart = function (name, spline, n, countHop) {
    let charts = document.getElementById('charts');
    let chart = document.createElement(`canvas`);
    chart.setAttribute("id", name);
    charts.appendChild(chart);

    let splineData = [];
    for (let i = 0; i <= spline.cubicX.length; i++) {
        splineData.push({
            x: spline.cubicX[i],
            y: spline.cubicY[i],
        })
    }

    // let datasets = [];
    // for (let i = 1; i <= n; i++) {
    //     let color = "#" + ((1 << 24) * Math.random() | 0).toString(16);
    //
    //     datasets.push({
    //         data: splineData.splice(0, countHop),
    //         label: `${name} ${i}`,
    //         cubicInterpolationMode: 'monotone',
    //         borderColor: color,
    //     });
    // }
    // console.log(datasets)

    let ctx = chart.getContext('2d');
    let myChart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: [{
                data: splineData,
                label: `sadsd`,
                cubicInterpolationMode: 'monotone',
                borderColor: "#0de3dd",
            }]
        }
    });
}
