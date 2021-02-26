import React from "react";
import { withRouter } from "react-router-dom";

import Api from "../../libs/api";
import EditorProject from "../../components/project/editor";
import { Alert, Spin, Descriptions } from "antd";
import RuoliEditor from "../../components/project/ruoli-editor-list";
import ListCandidature from "../../components/project/ruoli-candidati-list";
import moment from "moment";


class View extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
            loading: false
        }
    }

    componentDidMount() {
        const { match: { params: { id } } } = this.props;
        if (id != "new") {
            this.loadProject(id);
        } else {
            this.setState({
                data: {
                    titolo: "",
                    descrizione: "",
                    dataPubblicazione: moment().format("YYYY-MM-DDTHH:mm:ss"),
                    dataScadenza: moment().format("YYYY-MM-DDTHH:mm:ss"),
                }
            });
        }
    }

    loadProject = async (id) => {
        try {
            this.setState({ loading: true });
            this.setState({
                loading: false,
                data: await Api.get(`/getProgetto/${id}`)
            })
        } catch (e) {

        }
    }

    onSave = async () => {
        try {
            this.setState({ saving: true });
            let data = this.state.data;
            if (data.id > 0) data = await Api.put(`/updateProgetto/${data.id}`, data)
            else data = await Api.post(`/addProgetto`, data);
            this.setState({
                saving: false,
                data: data
            })
        } catch (e) {

        }
    }

    onChange = data => {
        this.setState({ data: data });
    }


    canEdit = (data) => {
        if (data.proponenteProgetto) {
            return data.proponenteProgetto.username === Api.getUtente();
        }
        return true;
    }

    renderEditor(data, saving) {
        if (this.canEdit(data)) {
            return (
                <>
                    <EditorProject
                        onChange={this.onChange}
                        onSave={this.onSave}
                        data={data}
                        saving={saving}
                    />
                    <div style={{ height: 20 }}></div>
                    {data.id !== "new" && <RuoliEditor progetto={data.id} />}
                </>
            );
        }
        else {
            return (
                <>
                    <Descriptions title="Progetto">
                        <Descriptions.Item label="Titole">{data.titolo}</Descriptions.Item>
                        <Descriptions.Item label="Descrizione">{data.descrizione}</Descriptions.Item>
                        <Descriptions.Item label="Data Pubblicazione">{moment(data.dataPubblicazione).format("DD/MM/YYYY")}</Descriptions.Item>
                        <Descriptions.Item label="Data Scadenza">{moment(data.dataScadenza).format("DD/MM/YYYY")}</Descriptions.Item>
                        <Descriptions.Item label="Stato">{data.stato}</Descriptions.Item>
                        <Descriptions.Item label="Proponente">{data.proponenteProgetto.nome + " " + data.proponenteProgetto.cognome}</Descriptions.Item>
                    </Descriptions>
                    <div style={{ size: 10 }}></div>
                    <ListCandidature progetto={data.id} />
                </>
            )
        }

    }


    render() {
        const { loading, data, saving } = this.state;
        return (
            <div className={"editor-project wrapform"}>
                {loading && <p>Caricamento dati <Spin /></p>}
                {!loading && data && this.renderEditor(data, saving)}
            </div>
        );
    }
}

export default withRouter(View);
