import React, {useEffect} from "react";
import {Grid} from "@material-ui/core";
import {DocumentTitle} from "../../ui/documentTitle";
import {ordersPageDataReducer} from "../../../reducers/screens/commonScreenReducer";
import {connect, useSelector} from "react-redux";
import {LOAD_ORDERS_PAGE} from "../../../actions/types";
import {ORDERS_DATA_API} from "../../../constants/api_routes";
import {getDataViaAPI, setDefaultSearchSuggestions} from "../../../actions";
import Spinner from "../../ui/spinner";

const Orders = props => {
    // let orders = getDataViaAPI(LOAD_ORDERS_PAGE, ORDERS_DATA_API, "?user_id="+(localStorage.getItem("user_id") == "null" ? 0:localStorage.getItem("user_id")), false, true);
    const orderAPIData = useSelector(state => state.ordersPageDataReducer)
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

            console.log(orderAPIData["data"])

        } else if (orderAPIData.hasOwnProperty('statusCode')) {
            console.log(`[Orders]: orderAPIData.statusCode = ${orderAPIData.statusCode}`)
            return <HTTPError statusCode={orderAPIData.statusCode}/>
        }
    }

    return (
        <Grid container justify="center" style={{paddingTop: "2rem"}}>
            <Grid item container xs={10} sm={5} lg={3} direction="column">
                <DocumentTitle title="Orders"/>
                <Grid item style={{alignSelf: "center", paddingBottom: "1rem"}}>
                    <h1 style={{fontSize: "2.5rem"}}>Orders</h1>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default connect(null, {getDataViaAPI})(Orders);
