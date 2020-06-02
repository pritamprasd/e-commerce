import React, { Component } from "react";
import Product from "./Product";
import ls from 'local-storage';
import Axios from "axios";
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import {API_GATEWAY_URL} from './Constants'



class ProductsPage extends Component {
    constructor(props) {
        super(props)
        this.state = {
            products: []
        }
        this.onGetProductsButtonClick = this.onGetProductsButtonClick.bind(this)
        this.handleGetAllProductData = this.handleGetAllProductData.bind(this)
    }
    handleGetAllProductData(allProductsData) {
        if (this.state.products !== allProductsData) {
            this.setState({ products: allProductsData })
        }
    }
    onGetProductsButtonClick() {
        let config = {
            headers: {
                'token': ls.get('token') || "",
            }
        }
        Axios.get(
            API_GATEWAY_URL +"/productsservice/products",
            config
        )
            .then(r => r.data.content)
            .then((data) => this.handleGetAllProductData(data))
            .catch((error) => {
                console.log("Error found while making query")
                console.error(error);
                this.handleGetAllProductData([])
            });

    }
    render() {
        const productsList = this.state.products
            .map((prod) => this.createProduct(prod))
        console.log(productsList)
        return (
            <div className="productsPage">
                <Button variant="contained" color="primary" onClick={this.onGetProductsButtonClick}>
                    Get Products
                </Button>
                <Grid container spacing={8} style={{ padding: 24 }}>
                    {productsList}
                </Grid>
            </div>
        );
    }

    createProduct(prod) {
        return (
            <Grid item xs={12} sm={6} lg={4} xl={3}>
                <Product key={prod.productId} productId={prod.productId} name={prod.productName} price={prod.productPrice} />
            </Grid> 
        )
    }
}

export default ProductsPage;