import React, { useState } from "react";

const TicketForm = ({ onSubmit }) => {
  const [releaseRate, setReleaseRate] = useState("");
  const [buyRate, setBuyRate] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ releaseRate, buyRate });
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Release Rate (ms):
        <input
          type="number"
          value={releaseRate}
          onChange={(e) => setReleaseRate(e.target.value)}
        />
      </label>
      <label>
        Buy Rate (ms):
        <input
          type="number"
          value={buyRate}
          onChange={(e) => setBuyRate(e.target.value)}
        />
      </label>
      <button type="submit">Start</button>
    </form>
  );
};

export default TicketForm;