import React, { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import axios from "axios";
import { Chart, CategoryScale, LinearScale, BarElement } from 'chart.js'
import 'chart.js/auto'
import zoomPlugin from "chartjs-plugin-zoom";

Chart.register(zoomPlugin);
Chart.register(CategoryScale, LinearScale, BarElement)

const LineChart = (props) => {
  const [showDataset1, setShowDataset1] = useState(true);
  const [showDataset2, setShowDataset2] = useState(true);
  const [showDataset3, setShowDataset3] = useState(true);
  const id = props.id;

  const [chartData, setChartData] = useState({ labels: [], datasets: [] });
  const getLineColor = (key) => {
    if (key === 'poids') {
      return 'red';
    } else if (key === 'epa') {
      return 'green';
    } else {
      return 'blue';
    }
  };

  useEffect(() => {
    const Data = async () => {
      try {
        const response = await axios.get(`/api/suividonnees/courbes/patient/${id}`)
        const apiData = response.data;

        const startDate = new Date("2021-01-01");
        const endDate = new Date("2023-12-31");
        const dateRange = getDates(startDate, endDate);

        // Ce que je fais, c'est que je récupère une range de date, et je fais correspondre les deux jeux de données, ce qui me permet d'avoir toute les dates et pas seulement cette des prise de mesures.
        const datasets = Object.keys(apiData[0].courbes).map(key => ({
          label: key,
          data: dateRange.map(date => apiData.find(data => data.date === date)?.courbes[key] || null),
          fill: false,
          borderColor: getLineColor(key),
          backgroundColor: 'rgba(255, 255, 255, 0.2)',
          borderWidth: 2,
          hidden: (key === 'poids' && !showDataset1) || (key === 'epa' && !showDataset2) || (key === 'imc' && !showDataset3),
        }));

        setChartData({ labels: dateRange, datasets });
      } catch (error) {
        console.error('Erreur lors de la récupération des données:', error);
      }
    };

    Data();
  }, [showDataset1, showDataset2, showDataset3, id]);

  const options = {
    maintainAspectRatio: true,
    responsive: true,
    plugins: {
      zoom: {
        zoom: {
          wheel: {
            enabled: true
          },
          speed: 1,
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
      data={chartData}
      options={options}
    />

  );
};

const getDates = (startDate, endDate) => {
  const dates = [];
  let currentDate = new Date(startDate);

  while (currentDate <= endDate) {
    dates.push(currentDate.toISOString().split('T')[0]); // Format de date AAAA-MM-JJ
    currentDate.setDate(currentDate.getDate() + 1);
  }
  return dates;
};
export default LineChart;
