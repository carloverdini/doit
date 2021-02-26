import React from "react";

import Form from "../../form";
import {Alert, Spin} from "antd";

export default class EditorProject extends React.Component{


    render() {
        const {data,onChange,onSave, saving} = this.props;
        const schema = {
            title: data.id?"Modifica progetto": "Nuovo progetto",
            type: "object",
            required: ["titolo", "descrizione","dataPubblicazione","dataScadenza","stato"],
            properties: {
                titolo: {type: "string", title: "Titolo"},
                descrizione: {type: "string", title: "Descrizione"},
                dataPubblicazione: {type: "string", title: "Data Pubblicazione"},
                dataScadenza: {type: "string", title: "Data Scadenza"},
                stato:{"type":"string",title:"Stato",enum: [ "DRAFT","OPENED", "CLOSED"]}

            }
        };

        return (
            <div className={"editor-project wrapform"}>
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
