import React, { useState } from 'react';
import ExchangeForm from './components/ExchangeForm';
import ExchangeResult from './components/ExchangeResult';

const App = () => {
  const [result, setResult] = useState('');
  return (
    <div>
      <h1>Money Exchange Machine</h1>
      <ExchangeForm setResult={setResult} />
      <ExchangeResult result={result} />
    </div>
  );
};

export default App;
