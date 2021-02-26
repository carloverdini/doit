import React from "react";
import 'antd/dist/antd.css';
import "./App.css";

import Dashboard from "./pages/dashboard";
import Login from "./pages/login";
import UserContext from "./components/user-context";
import {
    BrowserRouter as Router,
} from "react-router-dom";

export default class App extends React.Component{


    constructor(props) {
        super(props);
        this.state = {
            logged:false
        }
    }

    render(){
        const {logged} = this.state;
        return (
            <UserContext.Provider value={{}}>
                <div className="App">
                    {logged && <Router><Dashboard /></Router>}
                    {!logged && <Login onLogin={()=>this.setState({logged:true})} />}
                </div>
            </UserContext.Provider>
        );
    }
}
