import React, {useEffect, useState} from "react";
import {Line} from "react-chartjs-2";
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
  const getRandomColor = () => {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  };

  useEffect(() => {
    const Data = async () => {
      try {
        const response = await axios.get(`/api/suividonnees/courbes/patient/${id}`)
        const apiData = response.data;
        const labels = apiData.map(data => data.date);
        const datasets = Object.keys(apiData[0].courbes).map(key => ({
          label: key,
          data: apiData.map(data => data.courbes[key]),
          fill: false,
          borderColor: getRandomColor(),
          backgroundColor: 'rgba(255, 255, 255, 0.2)',
          borderWidth: 2,
          hidden: (key === 'poids' && !showDataset1) || (key === 'epa' && !showDataset2) || (key === 'imc' && !showDataset3),
        }));

        setChartData({ labels, datasets });
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
        data={chartData}
        options={options}
      />

  );
};


export default LineChart;
