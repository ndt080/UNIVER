export const DrawChart = function (name, splineData, n, countHop) {
    let charts = document.getElementById('charts');
    let chart = document.createElement(`canvas`);
    chart.setAttribute("id", name);
    charts.appendChild(chart);

    let datasets = [];
    for (let i = 1; i <= n; i++) {
        let color = "#" + ((1 << 24) * Math.random() | 0).toString(16);

        datasets.push({
            data: splineData.splice(0, countHop),
            label: `${name} ${i}`,
            borderColor: color,
        });
    }

    console.log(datasets)
    let ctx = chart.getContext('2d');
    let myChart = new Chart(ctx, {
        type: 'line',
        data: {datasets: datasets}
    });
}

export const DrawChartAllSplines = function (splines, n, countHop) {
    let charts = document.getElementById('charts');
    let chart = document.createElement(`canvas`);
    chart.setAttribute("id", "common");
    charts.appendChild(chart);
    let ctx = chart.getContext('2d');
    let datasets = [];

    for (let i = 1; i <= n; i++) {
        datasets.push({
            data: splines.linear.splice(0, countHop),
            label: `Liner spline ${i}`,
            borderColor: "#7679f3",
        });

        datasets.push({
            data: splines.quadratic.splice(0, countHop),
            label: `Quadratic spline ${i}`,
            borderColor: "#f9a23c",
        });

        datasets.push({
            data: splines.cubic.splice(0, countHop),
            label: `Cubic spline ${i}`,
            borderColor: "#e51863",
        });
    }

    let myChart = new Chart(ctx, {
        type: 'line',
        data: {datasets: datasets}
    });
}
