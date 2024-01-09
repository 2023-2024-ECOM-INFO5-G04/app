import React, {useEffect, useState} from "react";
import {Line} from "react-chartjs-2";
import { Chart, CategoryScale, LinearScale, BarElement } from 'chart.js'
import 'chart.js/auto'
import zoomPlugin from "chartjs-plugin-zoom";

Chart.register(zoomPlugin);
Chart.register(CategoryScale, LinearScale, BarElement)

const LineChart =() => {
const [showDataset1, setShowDataset1] = useState(true);
const [showDataset2, setShowDataset2] = useState(true);

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
  ],
};

const options = {
  maintainAspectRatio: true,
  responsive: true,
  plugins: {
    zoom: {
      zoom: {
        wheel: {
          enabled: true
        },
        speed: 100,
      },
      pan: {
        enabled: true,
        speed: 100,
      }
    }
  }
}

  return (

      <Line
        data={laData}
        options={options}
      />
   
  );
};


export default LineChart;
