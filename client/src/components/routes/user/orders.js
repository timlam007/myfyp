import React, {useEffect} from "react";
import {Grid, Box, Divider, Button, Paper } from "@material-ui/core";
import {DocumentTitle} from "../../ui/documentTitle";
import {ordersPageDataReducer} from "../../../reducers/screens/commonScreenReducer";
import {connect, useSelector} from "react-redux";
import {LOAD_ORDERS_PAGE} from "../../../actions/types";
import {ORDERS_DATA_API} from "../../../constants/api_routes";
import {getDataViaAPI, setDefaultSearchSuggestions} from "../../../actions";
import Spinner from "../../ui/spinner";
import StarBorderIcon from '@material-ui/icons/StarBorder';
import Rating from '@material-ui/lab/Rating';
import {HTTPError} from "../../ui/error/httpError";
import history from "../../../history";


const Orders = props => {
    // let orders = getDataViaAPI(LOAD_ORDERS_PAGE, ORDERS_DATA_API, "?user_id="+(localStorage.getItem("user_id") == "null" ? 0:localStorage.getItem("user_id")), false, true);
    const orderAPIData = useSelector(state => state.ordersPageDataReducer)
    const {isSignedIn, tokenId, firstName, id} = useSelector(state => state.signInReducer)
    let ordersDoesNotExistBox = "block"
    let ordersExistBox = "none"
    // console.log("here are the orders");
    // console.log(JSON.parse(JSON.stringify(orders)));

    useEffect(() => {
        console.log("[Order]: component did mount.")

        if (!orderAPIData.hasOwnProperty("data")) {
            props.getDataViaAPI(LOAD_ORDERS_PAGE, ORDERS_DATA_API, "?user_id="+(localStorage.getItem("user_id") == "null" ? 0:localStorage.getItem("user_id")), false);
        }
        // props.getDataViaAPI(LOAD_ORDERS_PAGE, ORDERS_DATA_API, "?user_id="+(localStorage.getItem("user_id") == "null" ? 0:localStorage.getItem("user_id")), false)

        // eslint-disable-next-line
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

        // check if we got the data from the API
        if (orderAPIData.hasOwnProperty("data")) {
            if(orderAPIData["data"].length){
                ordersDoesNotExistBox = "none"
                ordersExistBox = "block"
            }
        } else if (orderAPIData.hasOwnProperty('statusCode')) {
            console.log(`[Orders]: orderAPIData.statusCode = ${orderAPIData.statusCode}`)
            return <HTTPError statusCode={orderAPIData.statusCode}/>
        }
    }

    const renderOrders = () => {

        return orderAPIData["data"].map((order) => (
            <Grid item container key={order.id} style={{ border: '1px solid #eaeaec', margin: "1rem 0" }}>
                <Grid item container justify="center" xs={5} sm={3}>
                    <img src={order.imageURL} alt={order.name}
                        style={{ height: "90%", width: "80%", paddingTop: "1rem" }} />
                </Grid>

                <Grid item container xs={7} sm={9}>
                    <Grid item container direction="column" sm={6} spacing={1}>
                        <Grid item container style={{ fontSize: "1.1rem", fontWeight: 600, paddingTop: "2rem" }}>
                            <Grid item>
                                {order.productInfo.name}
                            </Grid>
                        </Grid>

                        <Grid item style={{ fontSize: "1.1rem", fontWeight: 300 }}>
                            {order.productInfo.productBrandCategory.type}
                        </Grid>

                        <Grid item style={{ fontSize: "1.1rem", fontWeight: 300 }}>
                            <Rating
                                    style={{zIndex: "1"}}
                                    name="customized-empty"
                                    defaultValue={order.productInfo.ratings}
                                    precision={0.5}
                                    readOnly
                                    emptyIcon={<StarBorderIcon fontSize="inherit"/>}
                                />
                        </Grid>

                    </Grid>

                    <Grid item container justify="flex-end" sm={6} style={{ padding: "2rem 1rem 0 0",
                        fontWeight: "bold", fontSize: "1.1rem" }}>
                        {`Qty: ${order.quantity} x $${order.price} = $${order.quantity * order.price}`}
                    </Grid>
                </Grid>
            </Grid>
        ));
    };

    return (
        <>
            <DocumentTitle title="My Orders" />

            <Grid container justify="center" style={{ height: "100%", padding: "20px" }}>
                {/* <h1 style={{ marginTop: '30px', marginBottom: '30px' }}>My Orders</h1> */}
                
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
