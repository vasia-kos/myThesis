//let numberOfQueries;
//let partition;

let myChart1 = document.getElementById("myChart1").getContext("2d");
let myChart2 = document.getElementById("myChart2").getContext("2d");
let myChart3 = document.getElementById("myChart3").getContext("2d");
let myChart4 = document.getElementById("myChart4").getContext("2d");
// Global Options
Chart.defaults.global.defaultFontFamily = "Lato";
Chart.defaults.global.defaultFontSize = 16;
Chart.defaults.global.defaultFontColor = "black";

var Chart1 = new Chart(myChart1, {
  type: "bar", // bar, horizontalBar, pie, line, doughnut, radar, polarArea
  data: {
    labels: ["BLAP"],
  },
  options: {
    legend: {
      display: false,
    },
    scales: {
      xAxes: [
        {
          barPercentage: 0.9,
        },
      ],
      yAxes: [
        {
          display: true,
          ticks: {
            suggestedMin: 0, // minimum will be 0, unless there is a lower value.
            // OR //
            beginAtZero: true, // minimum value will be 0.
          },
        },
      ],
    },
  },
});
let Chart2 = new Chart(myChart2, {
  type: "bar", // bar, horizontalBar, pie, line, doughnut, radar, polarArea
  data: {
    labels: ["Vertical/BLAP"],
    dataset:[25],


  },
  options: {
    legend: {
      display: false,
    },
    scales: {
      yAxes: [
        {
          display: true,
          ticks: {
            suggestedMin: 0, // minimum will be 0, unless there is a lower value.
            // OR //
            beginAtZero: true, // minimum value will be 0.
          },
        },
      ],
    },
  },
});
let Chart3 = new Chart(myChart3, {
  type: "bar", // bar, horizontalBar, pie, line, doughnut, radar, polarArea
  data: {
    labels: ["BLAP"],
  },
  options: {
    legend: {
      display: false,
    },
    scales: {
      yAxes: [
        {
          display: true,
          ticks: {
            suggestedMin: 0, // minimum will be 0, unless there is a lower value.
            // OR //
            beginAtZero: true, // minimum value will be 0.
          },
        },
      ],
    },
  },
});
let Chart4 = new Chart(myChart4, {
  type: "bar", // bar, horizontalBar, pie, line, doughnut, radar, polarArea
  data: {
    labels: ["BLAP"],
  },
  options: {
    legend: {
      display: false,
    },
    scales: {
      yAxes: [
        {
          display: true,
          ticks: {
            suggestedMin: 0, // minimum will be 0, unless there is a lower value.
            // OR //
            beginAtZero: true, // minimum value will be 0.
          },
        },
      ],
    },
  },
});

function update(chart, var1) {

  chart.data.datasets.push({
    data: [var1],
    backgroundColor: ["#E0BBE4", "#957DAD", "#D291BC"],
  });

  chart.update();
}

function clean(chart) {
  chart.data.datasets.pop();

  chart.update();
}


var par2;
function CallPartition() {

  var radioValue = $("input[name='partition']:checked").val();

  $.ajax({
    url: "test2",
    type: "POST",
    data: { radioValue: radioValue},
    success: function (data) {
      document.getElementById("chart2").innerHTML =
          "BLAP :  " + 25 + "  %";

      const a = data.BLAPpartition/60;
      const str_a = a.toString();
      const result = Number(str_a.slice(0, 4));
      document.getElementById("chart3").innerHTML =
          "BLAP :  " + data.BLAPpartition +"  sec"+"(" +  result +" min)";
      document.getElementById("chart4").innerHTML =
          "BLAP :  " + data.BLAPstorage + "  MB";
      update(Chart3,data.BLAPpartition);
      update(Chart2,25);
      update(Chart4,data.BLAPstorage);
    },
  });

}



function CallSubmitFunction() {
  //alert("p");
  var radioValue = $("input[name='partition']:checked").val();
  var numberQueries = $("input[name='textInput']").val();


  $.ajax({
    url: "test",
    type: "POST",
    data: { radioValue: radioValue, numberQueries: numberQueries },
    success: function (data) {
      document.getElementById("chart1").innerHTML =
        "BLAP :  " + data.BLAPexeTime + "  msec";



      update(Chart1, data.BLAPexeTime);
    },
  });
}

/*CHARTS*/
