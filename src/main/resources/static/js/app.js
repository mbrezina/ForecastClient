"use strict";

let graphArea = document.querySelector("#graph")

function loadData() {
    var httpKlient = new XMLHttpRequest();
    httpKlient.onload = makeGraph;
    httpKlient.onerror = errorMessage;
    httpKlient.open("GET", "api/v1/dataForGraph", true);
    httpKlient.send();
}

function makeGraph() {
    const xlabels = [];
    const yrates = [];
    var rates = JSON.parse(this.response);
    getDataInGraph();
    ChartIt();

    async function getDataInGraph() {
        console.log("jsem tady")
        for (let i = 0; i < 7; i++) {
            xlabels.push(rates[i].date);
            yrates.push(rates[i].last);
        }
        xlabels.reverse();
        yrates.reverse();
    }

    async function ChartIt() {
        const ctx = document.getElementById('myChart').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: xlabels,
                datasets: [{
                    label: 'Bit Coin Rate',
                    fill: false,
                    data: yrates,
                    backgroundColor: 'rgba(231,22,15,0.71)',
                    borderColor: 'rgba(50,43,238,0.87)',
                    borderWidth: 10
                }]
            },
            options:
                {
                    legend: {
                        labels: {
                            fontSize: 20
                        }
                    },
                    tooltips: {
                        enabled: true,
                        fontSize: 20,
                        titleFontSize: 20
                    }
                }
        });
    }
}

function errorMessage(err) {
    alert("Communication error: " + err);
}
