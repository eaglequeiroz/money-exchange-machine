import React, { useState } from "react";
import { exchangeMoney, increaseCoinInventory } from "../api";

const ExchangeForm = ({ setResult }) => {
  const [bill, setBill] = useState("");
  const [maxCoins, setMaxCoins] = useState(false);
  const [coinUpdates, setCoinUpdates] = useState({ 1: 0, 5: 0, 10: 0, 25: 0 });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!bill) return;
    const result = await exchangeMoney(parseInt(bill), maxCoins);
    setResult(result);
  };

  const handleIncreaseCoins = async () => {
    try {
      const response = await increaseCoinInventory(coinUpdates);
      setResult(response.data);
    } catch (error) {
      setResult('Error: Unable to update coin inventory');
    }
  };

  return (
    <div>
      <h2>Exchange Money</h2>
      <form onSubmit={handleSubmit} className="p-4 border rounded shadow-md">
        <label className="block mb-2">Enter Bill Amount ($):</label>
        <input
          type="number"
          value={bill}
          onChange={(e) => setBill(e.target.value)}
          className="p-2 border rounded w-full"
          required
        />
        
        <label className="flex items-center mt-2">
        <p>
          Maximize Coins?
          <input
            type="checkbox"
            checked={maxCoins}
            onChange={(e) => setMaxCoins(e.target.checked)}
            className="mr-2"
          />
          </p>
        </label>
        <button type="submit" className="mt-3 bg-blue-500 text-white p-2 rounded w-full">
          Exchange
        </button>
      </form>

      <h2>Increase Coin Inventory</h2>
      {Object.keys(coinUpdates).map((coin) => (
        <div key={coin}>
          <label>{coin} cent coins: </label>
          <input
            type="number"
            value={coinUpdates[coin]}
            onChange={(e) => setCoinUpdates({ ...coinUpdates, [coin]: Number(e.target.value) })}
          />
        </div>
      ))}
      <button onClick={handleIncreaseCoins}>Increase Coins</button>
    </div>
  );
};

export default ExchangeForm;
