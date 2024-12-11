import React, { useRef, useEffect } from "react";
import { Chart, CategoryScale, LinearScale, BarElement, Title, Tooltip } from "chart.js";

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip);

const ChartComponent = ({ data, options }) => {
  const chartRef = useRef(null);
  let chartInstance = useRef(null);

  useEffect(() => {
    // Destroy previous chart instance if it exists
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    // Create new chart instance
    const ctx = chartRef.current.getContext("2d");
    chartInstance.current = new Chart(ctx, {
      type: "bar", // or "line", "pie", etc.
      data,
      options,
    });

    return () => {
      // Cleanup on unmount
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [data, options]);

  return <canvas ref={chartRef}></canvas>;
};

export default ChartComponent;