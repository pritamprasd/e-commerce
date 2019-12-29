import React, { Component } from "react";
import { Grid, Button } from "@material-ui/core";
import ls from 'local-storage';
import Axios from "axios";
import Order from "./Order";

class OrdersPage extends Component {

    constructor(props) {
        super(props)
        this.state = {
            orders: []
        }
        this.onViewOrderButtonClick = this.onViewOrderButtonClick.bind(this)
    }

    onViewOrderButtonClick() {
        let config = {
            headers: {
                'token': ls.get('token') || "",
            }
        }
        let viewOrdersUrl = "http://localhost:8089/ordersservice/orders/user/" + ls.get('user')
        console.log("viewCart url: " + viewOrdersUrl)
        Axios.get(
            viewOrdersUrl,
            config
        ).then(res => {
            this.setState({
                orders: res.data
            })
        }
        ).catch((error) => {
            console.log("Error found while making query 2")
            console.error(error);
        });
    }
    createOrderTile(order){
        return(
            <Grid item xs={12} sm={6} lg={4} xl={3}>
                <Order key={order.orderId} orderData={order} />
            </Grid>
        )
    }

    render() {
        const ordersList = this.state.orders.map(o => this.createOrderTile(o))
        return (
            <div className="ordersPage">
                <Button variant="contained" color="primary" onClick={this.onViewOrderButtonClick}>
                    View Orders
                </Button>
                <Grid container spacing={8} style={{ padding: 24 }}>
                    {ordersList}
                </Grid>
            </div>
        )
    }
}

export default OrdersPage