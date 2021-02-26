import React from "react";

import Form from "../../form";
import {Row,Col, Spin} from "antd";
import {Link} from "react-router-dom";

export default class EditorProject extends React.Component{


    render() {
        const {data,onChange,onSave, saving} = this.props;
        const schema = {
            title: data.id?"Modifica ruolo": "Nuovo ruolo",
            type: "object",
            required: ["titolo", "descrizione","stato"],
            properties: {
                titolo: {type: "string", title: "Titolo"},
                descrizione: {type: "string", title: "Descrizione"},
                stato:{"type":"string",title:"Stato",enum: [ "DRAFT","OPENED", "CLOSED"]}

            }
        };

        return (
            <div className={"editor-project wrapform"}>
                <Row>
                    <Col span={20}></Col>
                    <Col span={5}>
                        <Link to={`/project/${data.progetto.id}`}>Torna al progetto</Link>
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
