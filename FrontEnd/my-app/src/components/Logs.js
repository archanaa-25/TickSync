import React from "react";

const Logs = ({ logs }) => {
  return (
    <div>
      <h2>Logs</h2>
      <ul>
        {logs.map((log, index) => (
          <li key={index}>
            {log.time} - {log.message}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Logs;