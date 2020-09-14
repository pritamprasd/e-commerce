import React, { Component } from "react";
import {
    Route,
    NavLink,
    HashRouter
} from "react-router-dom";
import ProductsPage from "./ProductsPage";
import Cart from "./Cart";
import Home from "./Home";
import "./index.css";
import OrdersPage from "./OrdersPage";


class Main extends Component {
    render() {
        return (
            <div>

                <HashRouter>
                    <div>
                        <h1>E-Com Service</h1>
                        <ul className="header">
                            <li><NavLink exact to="/">Home</NavLink></li>
                            <li><NavLink to="/products">Products</NavLink></li>
                            <li><NavLink to="/cart">Cart</NavLink></li>
                            <li><NavLink to="/orders">Orders</NavLink></li>
                        </ul>
                        <div className="content">
                            <Route exact path="/" component={Home} />
                            <Route path="/products" component={ProductsPage} />
                            <Route path="/cart" component={Cart} />
                            <Route path="/orders" component={OrdersPage} />
                        </div>
                    </div>
                </HashRouter>


            </div>

        );
    }
}

export default Main;