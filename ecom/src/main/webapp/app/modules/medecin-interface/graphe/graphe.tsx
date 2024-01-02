import React, {useEffect, useState} from "react";
import {Line} from "react-chartjs-2";
import { Chart, CategoryScale, LinearScale, BarElement } from 'chart.js'
import 'chart.js/auto'


Chart.register(CategoryScale, LinearScale, BarElement)
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
    },
    {
      label: 'Dataset 2',
      data: [35, 49, 70, 91, 46, 65, 30],
      fill: false,
      borderColor: 'rgba(255,99,132,1)',
      backgroundColor: 'rgba(255,99,132,0.2)',
      borderWidth: 2,
    },
    // Add more datasets if needed...
  ],
};

const config = {
  type: 'line',
  data: laData,
  options: {
    scales: {
      x: {
        type: 'category', // Utilisation de l'échelle 'category' pour l'axe des X
        labels: laData.labels // Utilisation des libellés comme données pour l'axe des X
      },
      y: {
        // Autres configurations de l'axe des Y si nécessaire
      }
    }
  }
};

const LineChart =() => {
  return (<div>
      <h2>Line Sample with Next.js</h2>
      <Line
        data={laData}
        options={
        {
          maintainAspectRatio: true,
          responsive: true,
          //scales: {
          //  x: {
          //    type: "linear", // Utilisation de l'échelle 'category' pour l'axe des X
          //    //labels: laData.labels // Utilisation des libellés comme données pour l'axe des X
          //  },
          //  y: {
          //  }
          //}
        }
      }
      />
    </div>
  );
}

export default LineChart;
