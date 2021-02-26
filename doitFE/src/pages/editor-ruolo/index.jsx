import React from "react";
import {withRouter} from "react-router-dom";

import Api from "../../libs/api";
import EditorRuolo from "../../components/ruolo/editor";
import {Alert, Spin, Descriptions} from "antd";
import EditorCanditati from "../../components/ruolo/editor-candidati";


class View extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: null,
            loading: false
        }
    }

    componentDidMount() {
        const {match: {params: {id, project}}} = this.props;
        if (id != "new") {
            this.loadRuolo(id);
        } else {
            this.setState({
                data: {
                    titolo: "",
                    progetto: {id: project},
                    descrizione: ""
                }
            });
        }
    }

    loadRuolo = async (id) => {
        try {
            this.setState({loading: true});
            this.setState({
                loading: false,
                data: await Api.get(`/getRuoloProgetto/${id}`)
            })
        } catch (e) {

        }
    }

    onSave = async () => {
        try {
            this.setState({saving: true});
            let data = this.state.data;
            if (data.id > 0) data = await Api.put(`/updateRuoloProgetto/${data.id}`, data)
            else data = await Api.post(`/addRuoloProgetto`, data);
            this.setState({
                saving: false,
                data: data
            })
        } catch (e) {

        }
    }

    onChange = data => {
        this.setState({data: data});
    }






    render() {
        const {loading, data, saving} = this.state;
        return (
            <div className={"editor-project wrapform"}>
                {loading && <p>Caricamento dati <Spin/></p>}
                {!loading && data && <EditorRuolo
                    onChange={this.onChange}
                    onSave={this.onSave}
                    data={data}
                    saving={saving}
                />}
                {data && <EditorCanditati ruolo={data.id} />}
            </div>
        );
    }
}

export default withRouter(View);
