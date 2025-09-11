import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const ChartSection = ({ chartResultList, chartLoading, chartSearchError, chartTitle }) => {
  const [chartData, setChartData] = useState(null);

  useEffect(() => {
    if (chartResultList && chartResultList.length > 0) {
      const labels = chartResultList.map(item => item.storeName);
      const salesData = chartResultList.map(item => item.salesAmount);
      const carsData = chartResultList.map(item => item.numberOfCars);

      setChartData({
        labels: labels,
        datasets: [
          {
            label: '販売額',
            data: salesData,
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1,
            yAxisID: 'y1',
          },
          {
            label: '販売台数',
            data: carsData,
            backgroundColor: 'rgba(255, 99, 132, 0.6)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
            yAxisID: 'y2',
          },
        ],
      });
    } else {
      setChartData(null);
    }
  }, [chartResultList]);

  if (chartSearchError) {
    return <div className="message error">{chartSearchError}</div>;
  }

  if (chartLoading) {
    return <div className="loading-message">グラフデータ取得中...</div>;
  }

  if (!chartData) {
    return <div className="no-results-message">グラフ表示可能なデータがありません。</div>;
  }

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: chartTitle,
      },
    },
    scales: {
      y1: {
        type: 'linear',
        display: true,
        position: 'left',
        title: {
          display: true,
          text: '販売額'
        }
      },
      y2: {
        type: 'linear',
        display: true,
        position: 'right',
        grid: {
          drawOnChartArea: false,
        },
        title: {
          display: true,
          text: '販売台数'
        }
      },
    },
  };

  return (
    <div className="chart-container">
      <Bar data={chartData} options={options} />
    </div>
  );
};

export default ChartSection;