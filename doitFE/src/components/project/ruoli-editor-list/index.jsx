import React from "react";
import Api from "../../../libs/api";
import { Row, Col, Spin, Button } from "antd";
import { Link } from "react-router-dom";

export default class RuoliEditor extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            items: []
        }
    }
    componentDidMount() {
        if (this.props.progetto)
            this.loadRuoli(this.props.progetto)
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.progetto !== prevProps.progetto) {
            this.loadRuoli(this.props.progetto);
        }
    }

    loadRuoli = async (id) => {
        this.setState({ loading: true });
        try {
            this.setState({
                loading: false,
                items: await Api.get(`/getRuoliProgetto/${id}`)
            })
        }
        catch (e) {
            this.setState({ loading: false });
        }
    }


    eliminaRuolo = async (id) => {
        try {
            const items = this.state.items.filter((itm) => itm.id !== id);
            this.setState({
                items: items
            });
            await Api.delete(`/deleteRuoloProgetto/${id}`);
        }
        catch (e) {

        }
    }


    renderItem = (itm, i) => {
        return (
            <div key={i}>
                <Row>
                    <Col span={10}>
                        {itm.titolo}
                    </Col>
                    <Col span={10}>
                        {itm.stato}
                    </Col>
                    <Col span={2}>
                        <Link to={`/ruolo/${this.props.progetto}/${itm.id}`}>
                            Visualizza
                        </Link>
                    </Col>
                    <Col span={2}>
                        <Button type="link" onClick={() => this.eliminaRuolo(itm.id)}>Elimina</Button>
                    </Col>
                </Row>
            </div>
        )
    }


    render() {
        const { loading, items } = this.state;
        return (
            <div className={"list-rouli"}>
                <Row>
                    <Col span={20}>
                        <h2>Lista Ruoli Progetto</h2>
                    </Col>
                    <Col span={4}>
                        <Link to={`/ruolo/${this.props.progetto}/new`}>Aggiungi</Link>
                    </Col>
                </Row>
                {loading && <Spin />}
                {items.map((itm, i) => {
                    return this.renderItem(itm, i);
                })}

            </div>
        );
    }
}
