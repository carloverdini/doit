import React from "react";
import {withRouter} from "react-router-dom";

import Api from "../../libs/api";
import EditorCurriculum from "../../components/curriculum/editor";
import {Alert, Spin, Descriptions} from "antd";
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
        const {match: {params: {id}}} = this.props;
        if (id != "new") {
            this.loadCurriculum(id);
        } else {
            this.setState({
                data: {
                    titolo: "",
                    descrizione: "",
                    azianda: "",
                    dataInizio: moment().format("YYYY-MM-DDTHH:mm:ss"),
                    dataFine: moment().format("YYYY-MM-DDTHH:mm:ss"),
                }
            });
        }
    }

    loadCurriculum = async (id) => {
        try {
            this.setState({loading: true});
            this.setState({
                loading: false,
                data: await Api.get(`/getCurriculum/${id}`)
            })
        } catch (e) {

        }
    }

    onSave = async () => {
        try {
            this.setState({saving: true});
            let data = this.state.data;
            if (data.id > 0) data = await Api.put(`/updateCurriculum/${data.id}`, data)
            else data = await Api.post(`/addCurriculum`, data);
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


    renderEditor(data, saving) {

        return (

            <EditorCurriculum
                onChange={this.onChange}
                onSave={this.onSave}
                data={data}
                saving={saving}
            />

        );
    }


    render() {
        const {loading, data, saving} = this.state;
        return (
            <div className={"editor-project wrapform"}>
                {loading && <p>Caricamento dati <Spin/></p>}
                {!loading && data && this.renderEditor(data, saving)}
            </div>
        );
    }
}

export default withRouter(View);
