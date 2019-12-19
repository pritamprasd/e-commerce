import React, { Component } from "react";
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import axios from 'axios';
import ls from 'local-storage'


class Item extends Component {
    constructor(props) {
        super(props)
        this.state = {
            name: props.name,
            price: props.price,
            productId: props.productId,
            reloadParent: props.reloadParent
        }
        this.onRemoveFromCartButtonClick = this.onRemoveFromCartButtonClick.bind(this)
    }
    componentDidUpdate(newProps) {
        if (this.state.productId !== newProps.productId) {
            this.setState({
                name: newProps.name,
                price: newProps.price,
                productId: newProps.productId
            })
        }
    }
    onRemoveFromCartButtonClick() {
        let delFromCartUrl = "http://localhost:8089/cartservice/cart/" + ls.get('user') + "/" + this.state.productId;
        console.log("delFromCartUrl: " + delFromCartUrl)
        let config = {
            headers: {
                'token': ls.get('token') || "",
            }
        }
        axios.delete(delFromCartUrl, config)
        .then(this.state.reloadParent())
            .catch(err => console.error(err))
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
            <div key={this.state.productId}>
                <Card className={classes.card}>
                    <CardContent>
                        <Typography className={classes.title} color="textPrimary" gutterBottom>
                            Product Name :{this.state.name}</Typography>

                        <Typography className={classes.pos} color="textSecondary">
                            Price in Rupees : {this.state.price}
                        </Typography>
                    </CardContent>
                    <CardActions>
                        <Button size="small" onClick={this.onRemoveFromCartButtonClick}>Delete from cart</Button>
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default Item;