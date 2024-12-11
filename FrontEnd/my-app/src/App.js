import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { Bar } from "react-chartjs-2";
import chart from "chart.js/auto"; // Import Chart.js for manual chart destruction
import './App.css';

const App = () => {
  const [capacity, setCapacity] = useState(100);
  const [releaseRate, setReleaseRate] = useState(1000); // in milliseconds
  const [buyRate, setBuyRate] = useState(2000); // in milliseconds
  const [releaseAmount, setReleaseAmount] = useState(10);
  const [buyAmount, setBuyAmount] = useState(5);
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [
      {
        label: "Available Tickets",
        data: [],
        backgroundColor: "#72a0c1bd",
      },
    ],
  });

  const [realTimeData, setRealTimeData] = useState([]); // To hold real-time ticket data

  const chartRef = useRef(null); // Reference for the chart instance

  // Initialize ticket pool
  const initializeTicketPool = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8081/api/tickets/initialize",
        null,
        { params: { capacity } }
      );
      alert(response.data);
    } catch (error) {
      console.error("Error initializing ticket pool:", error);
    }
  };

  // Start operations
  const startOperations = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8081/api/tickets/start",
        null,
        {
          params: {
            releaseRate,
            buyRate,
            releaseAmount,
            buyAmount,
          },
        }
      );
      alert(response.data);

      // Start polling for real-time updates
      startPolling();
    } catch (error) {
      console.error("Error starting ticket operations:", error);
    }
  };

  // Poll real-time updates
  const startPolling = () => {
    const interval = setInterval(async () => {
      try {
        // Poll for available tickets from the backend
        const response = await axios.get("http://localhost:8081/api/tickets/status");
        const availableTickets = response.data;

        setRealTimeData((prev) => [
          ...prev,
          {
            time: new Date().toLocaleTimeString(),
            tickets: availableTickets,
          },
        ]);

        if (availableTickets === 0) clearInterval(interval); // Stop polling when sold out
      } catch (error) {
        console.error("Error fetching real-time data:", error);
      }
    }, 1000);
  };

  // Update chart data
  useEffect(() => {
    if (chartRef.current) {
      // Destroy the previous chart instance if it exists
      if (chartRef.current.chart) {
        chartRef.current.chart.destroy();
      }

      // Set new chart data
      setChartData((prev) => ({
        ...prev,
        labels: realTimeData.map((entry) => entry.time),
        datasets: [
          {
            ...prev.datasets[0],
            data: realTimeData.map((entry) => entry.tickets),
          },
        ],
      }));
    }
  }, [realTimeData]);

  return (
  <div className="App">
    <header className="App-header">
      <h1>Real-Time Ticketing System</h1>
    </header>
    <div className="layout-container">
      <div className="input-container">
        <div>
          <label>Ticket Capacity:</label>
          <input
            type="number"
            value={capacity}
            onChange={(e) => setCapacity(e.target.value)}
          />
        </div>

        <div>
          <label>Release Rate (ms):</label>
          <input
            type="number"
            value={releaseRate}
            onChange={(e) => setReleaseRate(e.target.value)}
          />
        </div>

        <div>
          <label>Buy Rate (ms):</label>
          <input
            type="number"
            value={buyRate}
            onChange={(e) => setBuyRate(e.target.value)}
          />
        </div>

        <div>
          <label>Release Amount:</label>
          <input
            type="number"
            value={releaseAmount}
            onChange={(e) => setReleaseAmount(e.target.value)}
          />
        </div>

        <div>
          <label>Buy Amount:</label>
          <input
            type="number"
            value={buyAmount}
            onChange={(e) => setBuyAmount(e.target.value)}
          />
        </div>

        {/* Button Container */}
        <div className="button-container">
          <button onClick={initializeTicketPool}>Initialize</button>
          <button onClick={startOperations}>Start</button>
        </div>
      </div>

    
        <div className="grid-container">
          <h3>Available Tickets</h3>
          <p>{realTimeData.length > 0 ? realTimeData[realTimeData.length - 1].tickets : capacity}</p>
        </div>
    </div>

      {/* <div style={{ marginTop: "20px" }}>
        <Bar ref={chartRef} data={chartData} options={{ responsive: true }} />
      </div> */}

    <div className="chart-container">
      <Bar ref={chartRef} data={chartData} options={{ responsive: true }} />
    </div>
      
  </div>



);
};

export default App;