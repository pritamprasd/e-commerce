import React, { Component } from "react";
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import axios from 'axios';
import ls from 'local-storage'
import {API_GATEWAY_URL} from './Constants'



class Product extends Component {
    constructor(props) {
        super(props)
        this.state = {
            name: props.name,
            price: props.price,
            productId: props.productId
        }
        this.onAddToCartButtonClick = this.onAddToCartButtonClick.bind(this)
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
    onAddToCartButtonClick() {
        let addToCartUrl = API_GATEWAY_URL +"/cartservice/cart/" + ls.get('user') + "/" + this.state.productId;
        console.log("addTocartUrl: " + addToCartUrl)
        let config = {
            headers: {
                'token': ls.get('token') || "",
            }
        }
        axios.post(addToCartUrl,null,config)
            .then()
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
                        <Button size="small" onClick={this.onAddToCartButtonClick}>Add to cart</Button>
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default Product;