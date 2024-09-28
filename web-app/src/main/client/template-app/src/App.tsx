import React from "react";
import "./App.css";
import { BrowserRouter as Router } from "react-router-dom";
import Application from "./containers/Application";

function App() {
  return (
    <Router>
      <Application />
    </Router>
  );
}

export default App;
