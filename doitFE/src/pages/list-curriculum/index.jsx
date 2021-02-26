import React from "react";

import { Spin, Row, Col } from 'antd';
import {
    Link
} from "react-router-dom";
import Api from "../../libs/api";
import moment from "moment";

export default class ListCurriculum extends React.Component {

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
                items: await Api.get("/getCurriculumUtente"),
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
        return (
            <div className={"curriculum"} key={key}>
                <Row>
                    <Col span={5}>
                        <h4>{item.titolo}</h4>
                    </Col>
                    <Col span={4}>
                        <h4>{moment(item.dataInizio).format("DD/MM/YYYY")}</h4>
                    </Col>
                    <Col span={4}>
                        <h4>{moment(item.dataFine).format("DD/MM/YYYY")}</h4>
                    </Col>
                    <Col span={8}>
                        <h4>{item.descrizione}</h4>
                    </Col>
                    <Col span={3}>
                        <Link to={`/curriculum/${item.id}`}>
                            Modifica
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
                    <Col span={20}><h2>Il mio curriculum</h2></Col>
                    <Col span={4}><Link to={"/curriculum/new"}>Aggiungi</Link></Col>
                </Row>
                <Row>
                    <Col span={20}><h4><i>Aggiungi le competenze e le attività svolte nella tua carriera professionale</i></h4></Col>
                </Row>
                <Row>
                    <Col span={20}><hr></hr></Col>
                </Row>
                {loading && <Spin />}
                {items.map((itm, i) => {
                    return this.renderItem(itm, i);
                })}
            </div>
        );
    }
}
