import React, { Component } from "react";

class Product extends Component {
    constructor(props) {
        super(props)
        this.state = {
            name: props.name,
            price: props.price,
            productId: props.productId
        }
    }
    componentDidUpdate(newProps){
        if(this.state.productId !== newProps.productId){
            this.setState({
                name : newProps.name,
                price: newProps.price,
                productId: newProps.productId
            })
        }
    }
    render() {
        return (
            <div key={this.state.productId}>
                <h3>Product Name :{this.state.name}</h3>
                <h3>Product Price in Rupees : {this.state.price}</h3>
            </div>
        );
    }
}

export default Product;