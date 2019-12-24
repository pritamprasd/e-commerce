import React, { Component } from "react";
import ls from 'local-storage';
import Axios from "axios";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Item from "./Item";
import { Typography } from "@material-ui/core";



class Cart extends Component {
  constructor(props) {
    super(props)
    this.state = {
      products: [],
      productsContentList: [],
      cartTotal: 0.0
    }
    this.reload = this.reload.bind(this)
    this.createProduct = this.createProduct.bind(this)
    this.onGetProductsButtonClick = this.onGetProductsButtonClick.bind(this)
  }
  onGetProductsButtonClick() {
    this.setState({
      productsContentList: []
    })
    let config = {
      headers: {
        'token': ls.get('token') || "",
      }
    }
    let viewCartUrl = "http://localhost:8089/cartservice/cart/" + ls.get('user')
    console.log("viewCart url: " + viewCartUrl)
    Axios.get(
      viewCartUrl,
      config
    )
      .then((r) => {
        r.data.productsInCart.map(productId => {
          let config = {
            headers: {
              'token': ls.get('token') || "",
            }
          }
          let getProductUrl = "http://localhost:8089/productsservice/products/" + productId
          console.log("viewCart url: " + getProductUrl)
          Axios.get(
            getProductUrl,
            config
          )
            .then(res => {
              let r = res.data
              console.log("Respose get product===========")
              console.log(r)
              this.setState({
                productsContentList: this.state.productsContentList.concat([this.createProduct(r)]),
              })
            })
            .catch((error) => {
              console.log("Error found while making query 1")
              console.error(error);
            })
          return []
        })
        this.setState({
          cartTotal: r.data.cartTotal
        })
      }
      )
      .catch((error) => {
        console.log("Error found while making query 2")
        console.error(error);
      });
  }
  reload(value) {
    this.setState({ productsContentList: this.productsContentList });
  }
  createProduct(prod) {
    console.log("CP======")
    console.log(prod)
    return (
      <Grid item xs={12} sm={6} lg={4} xl={3}>
        <Item key={prod.productId} productId={prod.productId} name={prod.productName} price={prod.productPrice} reloadParent={this.reload} />
      </Grid>
    )
  }
  render() {
    console.log("productsContentList==============")
    console.log(this.state.productsContentList)
    return (
      <div className="cartPage">
        <Button variant="contained" color="primary" onClick={this.onGetProductsButtonClick}>
          View Cart
        </Button>
        
        <Typography>Cart Total: {this.state.cartTotal}</Typography>        
        <Grid container spacing={8} style={{ padding: 24 }}>
          {this.state.productsContentList}
        </Grid>
      </div>
    );
  }


}

export default Cart;