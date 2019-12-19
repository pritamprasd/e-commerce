import React, { Component } from "react";
import { Container } from "@material-ui/core";
import axios from 'axios';
import ls from 'local-storage'


class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: "",
            password: "",
            token: ""
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeUserName = this.handleChangeUserName.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
    }

    handleSubmit(event) {
        console.log("user: " + this.state.username + " pass: " + this.state.password);
        const user = {
            "userName": this.state.username,
            "password": this.state.password
        };
        axios.post("http://localhost:8089/authservice/token", user)
            .then(res => {
                this.setState({
                    "token": res.data
                })
                ls.set('token', this.state.token)
                console.log("token: " + this.state.token)
            })
            .then(() => {
                let userInforUrl = "http://localhost:8089/authservice/validate/".concat(String(this.state.token))
                console.log("User info url: " + userInforUrl)
                axios.get(userInforUrl)
                    .then(response => response.data)
                    .then(user => {
                        console.log(user)
                        ls.set('user', user.userId)
                    })
                    .catch(err => console.error(err))
            })
            .catch(err => console.error(err))

    }

    handleChangeUserName(event) {
        this.setState({ username: event.target.value });
    }

    handleChangePassword(event) {
        this.setState({ password: event.target.value });
    }


    render() {
        return (
            <div>
                <Container>
                    <form onSubmit={this.handleSubmit}>
                        <label>
                            Username:
                        <input type="text" value={this.state.username} onChange={this.handleChangeUserName} />
                        </label>
                        <label>
                            Password:
                        <input type="password" value={this.state.password} onChange={this.handleChangePassword} />
                        </label>
                        <input type="submit" value="Submit" />
                    </form>
                </Container>
            </div>
        );
    }
}

export default Home;