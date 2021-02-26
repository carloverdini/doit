import React from "react";
import {
    Switch,
    Route,
} from "react-router-dom";
import { Menu } from 'antd';
import { withRouter } from "react-router";

import { Link } from "react-router-dom";

import ListBandi from "../list-project";
import ListProjects from "../myproject";
import EditProject from "../editor-project";
import EditRuolo from "../editor-ruolo";
import ListCurriculum from "../list-curriculum";
import EditorCurriculum from "../editor-curriculum";
import ListCandidature from "../list-candidature";

class Dashboard extends React.Component {

    render() {
        return (
            <>
                <div className={"header"}>
                    <Menu mode="horizontal">
                        <Menu.Item key="bandi">
                            <Link to={"/"}>Progetti Aperti</Link>
                        </Menu.Item>
                        <Menu.Item key="progetti">
                            <Link to={"/myproject"}>I Miei Progetti</Link>
                        </Menu.Item>
                        <Menu.Item key="curriculum">
                            <Link to={"/curriculum"}>Curriculum</Link>
                        </Menu.Item>
                        <Menu.Item key="candidature">
                            <Link to={"/candidature"}>Canditature</Link>
                        </Menu.Item>
                        <Menu.Item key="Logout">
                            <Link to={"/logout"}>Logout</Link>
                        </Menu.Item>

                    </Menu>
                    <div style={{ height: 30 }}></div>
                </div>
                <div className={"content"}>
                    <Switch>
                        <Route path={"/project/:id"}>
                            <EditProject />
                        </Route>
                        <Route path={"/ruolo/:project/:id"}>
                            <EditRuolo />
                        </Route>
                        <Route path={"/curriculum/:id"}>
                            <EditorCurriculum />
                        </Route>
                        <Route path={"/curriculum"}>
                            <ListCurriculum />
                        </Route>
                        <Route path={"/candidature"}>
                            <ListCandidature />
                        </Route>
                        <Route path='/logout' component={() => {
                            //window.location.reload();
                            //return null;
                        }} />

                        <Route path={"/myproject"}>
                            <ListProjects />
                        </Route>
                        <Route path={"/"}>
                            <ListBandi />
                        </Route>

                    </Switch>
                </div>
            </>

        )
    }
}

export default withRouter(Dashboard);
