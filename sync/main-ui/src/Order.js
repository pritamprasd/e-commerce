import React, { Component } from "react";
import { Card, CardContent, Typography } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';


class Order extends Component {
    constructor(props) {
        super(props)
        this.state = {
            orderId: props.orderData.orderId,
            userId: props.orderData.userId,
            orderStatus: props.orderData.orderStatus,
            orderTotal: props.orderData.orderTotal,
            products: props.orderData.products,
            createdTimestamp: props.orderData.createdTimestamp,
            modifiedTimestamp: props.orderData.modifiedTimestamp,
        }
    }


    render() {
        const classes = makeStyles({
            card: {
                minWidth: 275,
                height: 100,
            },
            title: {
                fontSize: 44,
            },
            pos: {
                marginBottom: 12,
            },
        });
        return (
            <div className="ordersPage">
                <Card className={classes.card}>
                    <CardContent>
                        <Typography className={classes.title} color="textPrimary" gutterBottom>
                            Order Id :{this.state.orderId}</Typography>
                        <Typography className={classes.pos} color="textSecondary">
                            Products : {this.state.products}</Typography>
                        <Typography className={classes.pos} color="textSecondary">
                            Order Total : {this.state.orderTotal}</Typography>
                        <Typography className={classes.pos} color="textSecondary">
                            Order Status : {this.state.orderStatus}</Typography>
                        <Typography className={classes.pos} color="textSecondary">
                            Created On : {this.state.createdTimestamp}</Typography>
                    </CardContent>
                </Card>
            </div>
        )
    }
}

export default Order