import React, {useEffect, useState} from "react";
import {Grid, Box, Divider, Button, Paper } from "@material-ui/core";
import {DocumentTitle} from "../../ui/documentTitle";
import {ordersPageDataReducer} from "../../../reducers/screens/commonScreenReducer";
import {connect, useSelector, useDispatch} from "react-redux";
import {LOAD_ORDERS_PAGE} from "../../../actions/types";
import {ORDERS_DATA_API} from "../../../constants/api_routes";
import {getDataViaAPI, setDefaultSearchSuggestions} from "../../../actions";
import Spinner from "../../ui/spinner";
import log from 'loglevel';
import Cookies from 'js-cookie';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import Rating from '@material-ui/lab/Rating';
import {HTTPError} from "../../ui/error/httpError";
import history from "../../../history";
import {ADD_TO_CART, SELECT_PRODUCT_DETAIL} from "../../../actions/types";
import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart';
import {SHOPPERS_PRODUCT_INFO_COOKIE} from "../../../constants/cookies";


const Orders = props => {
    const orderAPIData = useSelector(state => state.ordersPageDataReducer)
    const {isSignedIn, tokenId, firstName, id} = useSelector(state => state.signInReducer)
    const dispatch = useDispatch()
    const addToCart = useSelector(state => state.addToCartReducer)
    let ordersDoesNotExistBox = "block"
    let ordersExistBox = "none"
    const [productQuantity, setProductQuantity] = useState(1)

    useEffect(() => {
        console.log("[Order]: component did mount.")

        if (!orderAPIData.hasOwnProperty("data")) {
            props.getDataViaAPI(LOAD_ORDERS_PAGE, ORDERS_DATA_API, "?user_id="+(localStorage.getItem("user_id") == "null" ? 0:localStorage.getItem("user_id")), false);
        }
    }, [ordersPageDataReducer]);

    if(!isSignedIn){
        history.push("/")
    }

    if (orderAPIData.isLoading) {
        console.log("[Orders]: loading")
        return <Spinner textComponent={
            <Grid container direction="column" spacing={1} style={{
                alignItems: "center", fontSize: "1.1rem", fontWeight: "bold"}}>
                <Grid item>
                    Please wait! ........ loading ......
                  </Grid>
            </Grid>}/>
    } else {
        if (orderAPIData.hasOwnProperty("data")) {
            console.log(orderAPIData["data"]);
            if(orderAPIData["data"].length){
                ordersDoesNotExistBox = "none"
                ordersExistBox = "block"
            }
        } else if (orderAPIData.hasOwnProperty('statusCode')) {
            console.log(`[Orders]: orderAPIData.statusCode = ${orderAPIData.statusCode}`)
            return <HTTPError statusCode={orderAPIData.statusCode}/>
        }
    }

    // set the cart products in the cookie
    const dispatchAddToCart = newAddToCart => {
        Cookies.set(SHOPPERS_PRODUCT_INFO_COOKIE, newAddToCart, {expires: 7});
        log.info(`[Product Detail] dispatchAddToCart productQty = ${JSON.stringify(newAddToCart)}`)
        dispatch({
            type: ADD_TO_CART,
            payload: newAddToCart
        })
    }

    const visitProduct = product_id => () => {
        history.push('/products/details?q=product_id='+product_id);
    }

    const handleAddToBagBtnClick = product_id => () => {
        log.info(`[Product Detail] Product is added to cart`)
        let newAddToCart = addToCart

        // add new product to cart
        if (newAddToCart.hasOwnProperty("productQty") === false) {
            newAddToCart = {
                totalQuantity: productQuantity,
                productQty: {
                    [product_id]: productQuantity
                }
            }
        } else {
            let totalQuantity = 0
            newAddToCart.productQty[product_id] = productQuantity
            newAddToCart.totalQuantity = 0

            for (const [, qty] of Object.entries(newAddToCart.productQty)) {
                totalQuantity += qty
            }
            newAddToCart.totalQuantity += totalQuantity
        }
        dispatchAddToCart(newAddToCart)
    }

    const renderOrders = () => {
        return orderAPIData["data"].map((order) => (
            <Grid item container key={order.id} style={{ border: '1px solid #eaeaec', margin: "1rem 0", padding: "1rem" }}>
                <Grid item container justify="space-between" alignItems="center">
                    <Grid item>
                        <p><b>Order Date:</b> {new Date(parseInt(order.timestamp) * 1000).toLocaleDateString()}</p>
                    </Grid>
                </Grid>

                <Grid item container justify="space-between" alignItems="center">
                    <Grid item>
                        <p><b>Shipping Address: </b>{order.addressFirstLine}{order.addressSecondLine ? " ":""}{order.addressSecondLine}</p>
                    </Grid>
                </Grid>
                <Grid item container justify="space-between" alignItems="center">
                    <Grid item>
                        <p><b>No Of Items: </b>{order.orderItems.length}</p>
                    </Grid>
                </Grid>
                <Grid item container justify="space-between" alignItems="center">
                    <Grid item>
                        <p><b>Total Amount: </b>{(order.amount/100).toLocaleString('en-US', {style: 'currency', currency: 'USD'})}</p>
                    </Grid>
                </Grid>
                <Divider style={{ width: '100%', margin: "1rem 0" }} />
    
                {order.orderItems.map(item => (
                    <Grid item container key={item.id} style={{ marginBottom: "1rem" }}>
                        <Grid item container justify="center" xs={5} sm={3}>
                            <img src={item.imageURL} alt={item.name} style={{ height: "90%", width: "80%" }} />
                        </Grid>
                        <Grid item container xs={7} sm={9}>
                            <Grid item container direction="column" sm={6} spacing={1}>
                                <Grid item container style={{ fontSize: "1.1rem", fontWeight: 600 }}>
                                    <Grid item>
                                        {item.name}
                                    </Grid>
                                </Grid>
                                <Grid item style={{ fontSize: "1.1rem", fontWeight: 300 }}>
                                    {item.productInfo?.productBrandCategory?.type}
                                </Grid>
                                <Grid item style={{ fontSize: "1.1rem", fontWeight: 300 }}>
                                    <Rating
                                        style={{ zIndex: "1" }}
                                        name="customized-empty"
                                        defaultValue={item.productInfo?.ratings}
                                        precision={0.5}
                                        readOnly
                                        emptyIcon={<StarBorderIcon fontSize="inherit" />}
                                    />
                                </Grid>
                            </Grid>
                            <Grid item container justify="flex-end" sm={6} style={{ fontWeight: "bold", fontSize: "1.1rem" }}>
                                {`Qty: ${item.quantity} x ${item.price.toLocaleString('en-US', {style: 'currency', currency: 'USD'})} = ${(item.quantity * item.price).toLocaleString('en-US', {style: 'currency', currency: 'USD'})}`}
                            </Grid>
                            <Grid>
                            <Grid item style={{ display: "flex", fontSize: "1.1rem", fontWeight: 300 }}>
                                <Grid style={{padding: "5px"}}>
                                <Button
                                    style={{
                                        height: 44, color: 'white',
                                        fontWeight: "bold", backgroundColor: "#AB0000"
                                    }}
                                    fullWidth
                                    startIcon={<AddShoppingCartIcon/>}
                                    onClick={handleAddToBagBtnClick(item.productId)}
                                >
                                    ADD TO BAG
                                </Button>
                                </Grid>
                                <Grid style={{padding: "5px"}}>
                                <Button
                                    style={{
                                        height: 44, color: 'black',
                                        fontWeight: "bold", backgroundColor: "#ddd"
                                    }}
                                    fullWidth
                                    onClick={visitProduct(item.productId)}
                                >
                                    VISIT PRODUCT
                                </Button>
                                </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                ))}
            </Grid>
        ));
    };    

    return (
        <>
            <DocumentTitle title="My Orders" />

            <Grid container justify="center" style={{ height: "100%", padding: "20px" }}>
                
                <Grid item xs={12} sm={11} md={7} style={{display: ordersExistBox}}>
                    <h1 style={{ marginTop: '30px', marginBottom: '30px' }}>My Orders</h1>
                    <Divider />
                    {renderOrders()}
                </Grid>

                <Grid item xs={12} sm={11} md={7} style={{display: ordersDoesNotExistBox}}>
                    <h1 style={{ marginTop: '30px', marginBottom: '30px' }}>My Orders</h1>
                    <Divider />
                    <Grid container justify="center" style={{ height: "100%", marginTop: "20px" }}>Nothing ordered yet.</Grid>
                </Grid>

            </Grid>
        </>
    );
}

export default connect(null, {getDataViaAPI})(Orders);
