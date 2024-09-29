import React from "react";
import "./App.css";
import { BrowserRouter as Router } from "react-router-dom";
import Application from "./containers/Application";
import rootReducer from "./redux/reducer";
import { configureStore } from "@reduxjs/toolkit";
import { Provider } from "react-redux";
import { loadAnonymousConfig } from "./redux/actions";

function App() {
  const store = configureStore({
    reducer: rootReducer,
  });

  store.dispatch(loadAnonymousConfig());

  return (
    <Provider store={store}>
      <Router>
        <Application />
      </Router>
    </Provider>
  );
}

export default App;
