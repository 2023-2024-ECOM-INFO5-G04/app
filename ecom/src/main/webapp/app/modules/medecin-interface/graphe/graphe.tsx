import React, {useEffect, useState} from "react";
import {Line} from "react-chartjs-2";
import { Chart, CategoryScale, LinearScale, BarElement } from 'chart.js'
import 'chart.js/auto'


Chart.register(CategoryScale, LinearScale, BarElement)

const LineChart =() => {
const [showDataset1, setShowDataset1] = useState(true);
const [showDataset2, setShowDataset2] = useState(true);

const toggleDataset = (dataset) => {
  if (dataset === 'Dataset1') {
    setShowDataset1(!showDataset1);
  } else if (dataset === 'Dataset2') {
    setShowDataset2(!showDataset2);
  }
};

const labels = ["January", "February", "March", "April", "May", "June"];
const laData = {
  labels: labels,
  datasets: [
    {
      label: 'Dataset 1',
      data: [65, 59, 80, 81, 56, 55, 40],
      fill: false,
      borderColor: 'rgba(75,192,192,1)',
      backgroundColor: 'rgba(75,192,192,0.2)',
      borderWidth: 2,
      hidden: !showDataset1,
    },
    {
      label: 'Dataset 2',
      data: [35, 49, 70, 91, 46, 65, 30],
      fill: false,
      borderColor: 'rgba(255,99,132,1)',
      backgroundColor: 'rgba(255,99,132,0.2)',
      borderWidth: 2,
      hidden: !showDataset2,
    },
    // Add more datasets if needed...
  ],
};



  return (<div>
      <h2>Graphique</h2>
      <div>
        <label>
          <input
            type="checkbox"
            checked={showDataset1}
            onChange={() => toggleDataset("Dataset1")}
          />
          Show Dataset 1
        </label>
        <label>
          <input
            type="checkbox"
            checked={showDataset2}
            onChange={() => toggleDataset("Dataset2")}
          />
          Show Dataset 2
        </label>
      </div>
      <Line
        data={laData}
        options={
        {
          maintainAspectRatio: true,
          responsive: true,
        }
      }
      />
    </div>
  );
};


export default LineChart;
