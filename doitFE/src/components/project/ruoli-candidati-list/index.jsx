import React from "react";
import Api from "../../../libs/api";
import { Row, Col, Spin, Button, Modal, Input } from "antd";
import { Link } from "react-router-dom";

export default class RuoliEditor extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            items: [],
            candidature: [],
            selectItem: null,
            saving: false,
            presentazione: ""
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
                candidature: await Api.get("/getCandidatureUtente"),
                items: await Api.get(`/getRuoliProgetto/${id}`)
            })
        } catch (e) {
            this.setState({ loading: false });
        }
    }


    getCandidatura = (itm) => {
        const { candidature } = this.state;
        for (const c of candidature) {
            if (c.ruoloProgetto.id === itm.id) {
                return c;
            }
        }
        return false;
    }

    addCandidatura = async () => {
        try {
            this.setState({ saving: true });
            const c = await Api.post("/addCandidatura", {
                presentazione: this.state.presentazione,
                ruoloProgetto: {
                    id: this.state.selectItem.id
                }
            });
            this.setState({
                selectItem: null,
                presentazione: null,
                candidature: [
                    ...this.state.candidature,
                    c
                ],
                saving: false
            })
        }
        catch (e) {
            this.setState({ saving: false });
        }
    }
    removeCandidatura = async (itm) => {
        try {
            const nc = this.state.candidature.filter(i => i.id !== itm.id);
            await Api.delete(`/deleteCandidatura/${itm.id}`);
            this.setState({
                candidature: nc
            })
        }
        catch (e) {

        }
    }

    renderItem = (itm, i) => {
        const candidatura = this.getCandidatura(itm);
        return (
            <div key={i}>
                <Row>
                    <Col span={10}>
                        {itm.titolo}
                    </Col>
                    <Col span={10}>
                        {itm.description}
                    </Col>
                    <Col span={2}>
                        {candidatura && (
                            <Button type={"link"} onClick={() => this.removeCandidatura(candidatura)}>
                                Rimuovi
                            </Button>
                        )}
                        {!candidatura && (
                            <Button type={"link"} onClick={() => this.setState({ selectItem: itm })}>
                                Candidami
                            </Button>
                        )}
                    </Col>
                </Row>
            </div>
        )
    }

    renderModal = () => {
        const { selectItem, saving } = this.state;
        if (selectItem) {
            return (
                <Modal
                    title={`Candidati a ${selectItem.titolo}`}
                    visible={true}
                    onOk={this.addCandidatura}
                    onCancel={() => {
                        this.setState({ selectItem: null, presentazione: "" })
                    }}
                >
                    <Input
                        placeholder={"Presentazione"}
                        value={this.state.presentazione}
                        onChange={(v) => this.setState({ presentazione: v.target.value })} />
                    {saving && <p>Salvataggio <Spin /></p>}
                </Modal>
            )
        }
    }


    render() {
        const { loading, items } = this.state;
        return (
            <div className={"list-rouli"}>
                <Row>
                    <Col span={20}>
                        <h2>Lista Ruoli Progetto</h2>
                    </Col>
                </Row>
                <Row>
                    <Col span={20}>
                        <h4><i>Di seguito i ruoli aperti a cui è possibile candidarsi</i></h4>
                    </Col>
                </Row>

                {loading && <Spin />}
                {items.map((itm, i) => {
                    return this.renderItem(itm, i);
                })}
                {this.renderModal()}

            </div>
        );
    }
}
