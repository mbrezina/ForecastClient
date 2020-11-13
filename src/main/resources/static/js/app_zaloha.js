"use strict";

let graphArea = document.querySelector("#graph")

async function loadData() {
    var httpKlient = new XMLHttpRequest();
    console.log("jsem taddddyyy");
    httpKlient.onload = makeGraph;
    httpKlient.onerror = errorMessage;
    httpKlient.open("GET", "/", true);
    httpKlient.send();


}

async function makeGraph() {
    const xlabels = [];
    const yrates = [];

    var temperatureObjectList = JSON.parse(temperatureSerie.replace(/&quot;/g, '"'));

    getDataInGraph();
    ChartIt();

}

async function getDataInGraph() {

        for (let i = 0; i < temperatureObjectList.length; i++) {
            xlabels.push(temperatureObjectList[i].day);
            yrates.push(temperatureObjectList[i].temperature);
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
                    label: 'Temperature Overview',
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
                        fontSize: 40,
                        titleFontSize: 40
                    }
                }
        });

}

function errorMessage(err) {
    alert("Communication error: " + err);
}
