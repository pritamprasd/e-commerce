import React, { Component } from "react";
import ApiService from "./service/ApiService"

class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: "",
            password: ""
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
        ApiService.login(user)           
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
            </div>
        );
    }
}

export default Home;