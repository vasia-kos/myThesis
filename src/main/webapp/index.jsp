<!DOCTYPE html>
<html>
  <head>
    <title>Query statistics</title>
    <link rel="icon" href="volcano.png" />
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="style.css" />
    <!--graph!-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
    <link rel="stylesheet" type="text/css" href="graph.css" />
  </head>

  <body>
    <div class="wrapper">
      <!-- Sidebar  -->
      <nav id="sidebar">
        <div class="sidebar-header">
          <!--1o koytaki einai to lawa-->
          <div id="lawa-icon"></div>
          <h3>lawa</h3>
          <p style="font-size: 12px;">
            Locality Aware Partitioning for Efficient Query Answering Over RDF
            Data
          </p>
        </div>

        <ul class="list-unstyled components">
          <li>
            <a href="">About</a>
          </li>

          <li>
            <a href="index.html" class="active"> Query Graphs</a>
          </li>

          <li>
            <a href="">Contact</a>
          </li>
        </ul>
      </nav>

      <!-- Page Content  -->
      <div id="content" style="background-color: #dfe3ee;">
        <div class="container-fluid">
          <!--to koumpi gia na paei to side bar mesa-->
          <button id="sidebarCollapse">
            <i class="fas fa-align-left"></i>
          </button>
        </div>

        <div id="TitleOfCharts">Graphs Of Query Execution</div>
        <!-------------------------------------------------------------------------->
        <form class="DataOptions" id="myForm">
          <div class="DataOptionsRow">
            <div class="title">Dataset</div>
            <br />
            <input type="radio" id="swdf" name="swdf" value="swdf" checked />
            <label for="swdf">SWDF</label>
          </div>
          <div class="DataOptionsRow">
            <div class="title">Partitions</div>

            <input type="radio" id="twoPartition" name="partition" value="2" />
            <label for="twoPartition">2</label>
            <br />
            <input type="radio" id="fourPartition" name="partition" value="4" />
            <label for="fourPartition">4</label>
            <br />
            <input
              type="radio"
              id="eightPartition"
              name="partition"
              value="8"
            />
            <label for="eightPartition">8</label><br />
            <button
                    onclick="CallPartition()"
                    class="btn btn-info"
                    id="PartitionButton"
                    type="button">
                    Partition the Dataset
            </button>
          </div>
          <div class="DataOptionsRow">
            <div class="title">Number of Queries</div>
            <br />
            <label for="textInput">Between 1 and 299:</label>
            <input
              type="text"
              id="textInput"
              name="textInput"
              style="width: 50%;"
            /><br />
            <br/>
            <button
                    onclick="CallSubmitFunction()"
                    class="btn btn-info"
                    id="ExecuteButton"
                    type="button"
            >
              Execute Queries
            </button>

          </div>

        </form>
        <br/>
        <br/>
        <br/>
        <!--------------------------------------------------------------------------->
        <div class="Charts">
          <div class="chartColumn">
            <div id="result1">
              <h4>Query Execution time (LUBM-STAR)</h4>
              <br />

              <p id="chart1"></p>
            </div>

            <div class="graph-buttons">
              <button
                type="button"
                id="clear-button1"
                class="btn btn-info"
                onClick="clean(Chart1)"
              >
                Clear
              </button>
            </div>
            <canvas id="myChart1"></canvas>
          </div>

          <div class="chartColumn">
            <div id="result2">
              <h4>Average Data Access Reduction (LUBM)</h4>
              <br />
              <p id="chart2"></p>
            </div>

            <div class="graph-buttons">
              <button
                type="button"
                id="clear-button2"
                class="btn btn-info"
                onClick="clean(Chart2)"
              >
                Clear
              </button>
            </div>
            <canvas id="myChart2"></canvas>
          </div>

          <div class="chartColumn">
            <div id="result3">
              <h4>Partitioning time (LUBM)</h4>
              <br />
              <br />
              <p id="chart3"></p>
            </div>
            <div class="graph-buttons">
              <!--<button type="button"  class="btn btn-info" onClick="update(Chart3)">Update</button>-->
              <button
                type="button"
                id="clear-button3"
                class="btn btn-info"
                onClick="clean(Chart3)"
              >
                Clear
              </button>
            </div>
            <canvas id="myChart3"></canvas>
          </div>

          <div class="chartColumn">
            <div id="result4">
              <h4>Output Storage (LUBM)</h4>
              <br />
              <br />
              <p id="chart4"></p>
            </div>
            <div class="graph-buttons">
              <!--<button type="button"  class="btn btn-info" onClick="update(Chart4)">Update</button>-->
              <button
                type="button"
                id="clear-button4"
                class="btn btn-info"
                onClick="clean(Chart4)"
              >
                Clear
              </button>
            </div>
            <canvas id="myChart4"></canvas>
          </div>
        </div>
      </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
      integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
      integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
      crossorigin="anonymous"
    ></script>
    <script>
      $(document).ready(function () {
        $("#sidebarCollapse").on("click", function () {
          $("#sidebar").toggleClass("active");
        });
      });
    </script>
    <script src="graph.js"></script>
  </body>
</html>
