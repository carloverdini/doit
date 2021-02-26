import React from "react";

import Form from "../../form";
import {Row,Col, Spin} from "antd";
import {Link} from "react-router-dom";

export default class EditorProject extends React.Component{


    render() {
        const {data,onChange,onSave, saving} = this.props;
        const schema = {
            title: data.id?"Modifica curriculum": "Nuovo curriculum",
            type: "object",
            required: ["titolo", "descrizione","azienda","dataInizio"],
            properties: {
                titolo: {type: "string", title: "Titolo"},
                descrizione: {type: "string", title: "Descrizione"},
                azienda: {type: "string", title: "Azienda"},
                dataInizio: {type: "string", title: "data Inizio"},
                dataFine: {type: "string", title: "data Fine"},

            }
        };

        return (
            <div className={"editor-project wrapform"}>
                <Row>
                    <Col span={20}></Col>
                    <Col span={5}>
                        <Link to={`/curriculum`}>Torna ai curriculum</Link>
                    </Col>
                </Row>
                <Form
                    formData={data}
                    schema={schema}
                    onChange={e => {
                        onChange(e.formData)
                    }}
                    onSubmit={onSave}
                />
                {saving && <p><Spin/> Salvataggio in corso</p>}
            </div>
        );
    }
}
