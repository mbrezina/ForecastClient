document.addEventListener("DOMContentLoaded", function (event) {
    var temperatureObjectList = JSON.parse(temperatureSerie.replace(/&quot;/g, '"'));
    console.log(temperatureSerie);
    console.log(temperatureObjectList);

    var array = [];
        for (let i = 0; i < temperatureObjectList.length; i++) {
        var date_try = temperatureObjectList[i].day
            console.log(date_try)
        var date_new = new Date(date_try)
        console.log(date_new)

        var temp = Number(temperatureObjectList[i].temperature).valueOf()
        console.log(temp)
        array.push({
            //date: temperatureObjectList[i].day, value: +temperatureObjectList[i].temperature
            //date: new Date(temperatureObjectList[i].day), value: +temperatureObjectList[i].temperature
            date: date_new, value: temp
        })
    }
    drawChart(array);
})


function drawChart(data) {

    var chartDiv = document.getElementById("chartGraph");

    var svgWidth = chartDiv.clientWidth;
    //var svgHeight = chartDiv.clientHeight;
    var svgHeight = 500;

    //var svgWidth = 600, svgHeight = 400;

    var margin = {top: 20, right: 20, bottom: 30, left: 40};
    var width = svgWidth - margin.left - margin.right;
    var height = svgHeight - margin.top - margin.bottom;

    var svg = d3.select('svg')
        .attr("width", svgWidth)
        .attr("height", svgHeight);

    var g = svg.append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var x = d3.scaleTime()
        .rangeRound([0, width]);

    var y = d3.scaleLinear()
        .rangeRound([height, 0])

    var line = d3.line()
        .x(function (d) {
            return x(d.date)
        })
        .y(function (d) {
            return y(d.value)
        })

    x.domain(d3.extent(data, function (d) {
        return d.date
    }));
    y.domain(d3.extent(data, function (d) {
        return d.value
    }));

    g.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x))
        .select(".domain")
        .remove();

    g.append("g")
        .call(d3.axisLeft(y))
        .append("text")
        .attr("fill", "#000")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", "0.71em")
        .attr("text-anchor", "end")
        .text("Temperature");

    g.append("path")
        .datum(data)
        .attr("fill", "none")
        .attr("stroke", "steelblue")
        .attr("stroke-linejoin", "round")
        .attr("stroke-linecap", "round")
        .attr("stroke-width", 1.5)
        .attr("d", line);


}

