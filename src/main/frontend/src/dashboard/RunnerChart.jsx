// -----------------------------------------------------------
//
// RunnerChart
//
// Manage the main application
//
// -----------------------------------------------------------

import React from 'react';
import Chart from "../component/Chart";


class RunnerChart extends React.Component {


  constructor(props) {
    super();
    this.state = {
      runner: props.runnerDisplay,
      type: props.type,
      title: props.title,
      timestamp: props.timestamp
    };
  }

  componentDidUpdate(prevProps) {

    if (prevProps.timestamp !== this.props.timestamp) {
      // console.log("RunnerChart.componentDidUpdate: Change");
      this.setState({
        runner: this.props.runnerDisplay,
        timestamp: this.props.timestamp
      });
    }
  }

  render() {
    return (
      <div style={{border: "1px solid", padding: "5px"}}>
        {this.state.type === "Executions" && this.state.runner && this.state.runner.performance &&
          <Chart type="HorizontalBar" dataList={this.getExecutions()} oneColor={true}
                 options={{
                   title: this.state.title,
                   showXLabel: false,
                   showYLabel: false,
                   width: 200,
                   height: 100,
                   showGrid: false
                 }}
                 title="execution"/>
        }
      </div>
    )

  }


  /**
   * Graph expect [{value:123, label="Month"}]
   *
   * runner contains {performance : {listIntervals: [
   *    {slot: "103D00:15", executions: 0, sumOfExecutionTime: 0, executionsSucceeded: 0, executionsFailed: 0,…}
   *    }}
   */
  getExecutions() {
    const result = [];
    this.state.runner.performance.listIntervals.forEach((element, _index, _array) => {
      let record = {label: element.slot, value: element.executions};
      result.push(record);
    });
    return result;

  }
}

export default RunnerChart;
