import React from "react";
import { Spin, Alert } from 'antd';
import Api from "../../libs/api";
import Form from "../../components/form";

export default class Login extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            data: {
                username:"carlo.verdini@studenti.unicam.it",
                password:"doit"
            },
            login: false,
            error: null
        }
    }

    doLogin = async () => {
        try {
            this.setState({login: true});
            await Api.login(this.state.data);
                this.props.onLogin();
        } catch (e) {
            this.setState({login: false, error: "Utente/Password errati"});
        }
    }

    render() {

        const schema = {
            title: "Login",
            type: "object",
            required: ["username", "password"],
            properties: {
                username: {type: "string", title: "Utente"},
                password: {type: "string", title: "Password"}
            }
        };


        return (
            <div className={"login-form"}>
                <Form
                    formData={this.state.data}
                    schema={schema}
                    onChange={e => {
                        this.setState({data: e.formData})
                    }}
                    onSubmit={this.doLogin}
                />
                {this.state.login && <Spin />}
                {this.state.error && <Alert message={this.state.error} type="error" />}
            </div>
        );
    }
}
