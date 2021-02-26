import React from "react";
import Api from "../../../libs/api";
import {Row, Col, Spin, Button, Modal, Input} from "antd";
import {Link} from "react-router-dom";

export default class RuoliEditor extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            items: [],
            selectItem: null
        }
    }

    componentDidMount() {
        if (this.props.ruolo && this.props.ruolo > 0)
            this.loadCandidati(this.props.ruolo)
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.ruolo !== prevProps.ruolo && this.props.ruolo > 0) {
            this.loadCandidati(this.props.ruolo);
        }
    }

    loadCandidati = async (id) => {
        this.setState({loading: true});
        try {
            this.setState({
                loading: false,
                items: await Api.get(`/getCandidatureRuoloProgetto/${id}`),
            })
        } catch (e) {
            this.setState({loading: false});
        }
    }


    valutaCandidato = async (itm, valutazione) => {
        try {
            this.setState({loading: true});
            await Api.post(`/addValutazione`, {
                esito: valutazione,
                candidatura: {
                    id: itm.id
                }
            });
            this.loadCandidati(this.props.ruolo);
        } catch (e) {
        }
    }


    getStato = (s) => {
        switch (s) {
            case "CFRMD":
                return "Confermato";
            case "RJCTD":
                return "Rifiutato";
            case "ACTVD":
                return "Da valutare";
        }
        return "";
    }


    renderItem = (itm, i) => {
        return (
            <div key={i}>
                <Row>
                    <Col span={5}>
                        {itm.candidato.nome}
                    </Col>
                    <Col span={8}>
                        {itm.presentazione}
                    </Col>
                    <Col span={5}>
                        {this.getStato(itm.stato)}
                    </Col>
                    <Col span={3}>
                        {itm.stato === "ACTVD" && (

                            <Button type={"link"} onClick={() => this.valutaCandidato(itm, true)}>
                                Accetta
                            </Button>


                        )}
                    </Col>
                    <Col span={3}>
                        {itm.stato === "ACTVD" && (
                            <Button type={"link"} onClick={() => this.valutaCandidato(itm, false)}>
                                Rifiuta
                            </Button>
                        )}
                    </Col>
                </Row>
            </div>
        )
    }


    render() {
        const {ruolo} = this.props;
        if (ruolo > 0) {
            const {loading, items} = this.state;
            return (
                <div className={"list-candidati"}>
                    <Row>
                        <Col span={20}>
                            <h2>Lista Candidati</h2>
                        </Col>
                    </Row>
                    {loading && <Spin/>}
                    {items.map((itm, i) => {
                        return this.renderItem(itm, i);
                    })}

                </div>
            );
        }
        return null;
    }
}
