import { useRef, useEffect } from "react";
import { Chart } from "chart.js";

const MyChart = ({ data, options }) => {
  const chartRef = useRef(null);
  const canvasRef = useRef(null);

  useEffect(() => {
    if (chartRef.current) {
      chartRef.current.destroy(); // Destroy the previous chart instance
    }

    chartRef.current = new Chart(canvasRef.current, {
      type: "bar", // or "line", "pie", etc.
      data,
      options,
    });

    return () => {
      if (chartRef.current) {
        chartRef.current.destroy(); // Cleanup on component unmount
      }
    };
  }, [data, options]); // Re-render the chart if data or options change

  return <canvas ref={canvasRef} />;
};

export default MyChart;