import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Index from "./components/Index.js";
import SERP from "./components/SERP.js";


class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path="/" exact component={Index}/>
                    <Route path="/search/:query" component={SERP}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
