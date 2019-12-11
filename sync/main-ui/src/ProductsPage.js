import React, { Component } from "react";
import Product from "./Product";
import "./ProductsPage.css";
import ls from 'local-storage';
import Axios from "axios";


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
            "http://localhost:8091/products",
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
        console.log("in render 1")
        const productsList = this.state.products.map((prod) => <Product key={prod.productId} productId={prod.productId} name={prod.productName} price={prod.productPrice} />)
        console.log(productsList)
        return (
            <div className="productsPage">
                <button onClick={this.onGetProductsButtonClick}>
                    Get Products</button>
                <h2>Products</h2>
                {productsList}
            </div>
        );
    }
}

export default ProductsPage;