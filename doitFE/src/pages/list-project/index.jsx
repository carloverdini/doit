import React from "react";

import { Spin, Row, Col } from 'antd';
import {
    Link
} from "react-router-dom";
import Api from "../../libs/api";


export default class ListProject extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            items: [],
            loading: false,
        }
    }
    componentDidMount() {
        this.loadList();
    }

    loadList = async () => {
        try {
            this.setState({ loading: true });
            this.setState({
                items: await Api.get("/getProgetti"),
                loading: false
            });
        }
        catch (e) {
            this.setState({
                items: [],
                loading: false
            });
        }
    }

    renderItem = (item, key) => {
        const isMine = item.proponenteProgetto.username === Api.getUtente();
        return (
            <div className={"project"} key={key}>
                <Row>
                    <Col span={20}>
                        <h4>{item.titolo}</h4>
                    </Col>
                    <Col span={4}>
                        <Link to={`/project/${item.id}`}>
                            {isMine ? "Modifica" : "Visualizza"}
                        </Link>
                    </Col>
                </Row>
            </div>
        )
    }


    render() {
        const { loading, items } = this.state;
        return (
            <div>
                <Row>
                    <Col span={20}><h2>Lista progetti</h2></Col>
                </Row>
                <Row>
                    <Col span={20}><h4><i>Di seguito la lista di tutti i progetti aperti, i progetti modificabili sono quelli creati dall'utente.</i></h4></Col>
                </Row>
                <Row>
                    <Col span={20}><hr></hr></Col>
                </Row>
                { loading && <Spin />}
                {
                    items.map((itm, i) => {
                        return this.renderItem(itm, i);
                    })
                }
            </div >
        );
    }
}
