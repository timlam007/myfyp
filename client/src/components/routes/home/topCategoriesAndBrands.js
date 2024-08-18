import React from 'react';
import {Grid} from "@material-ui/core";
import {Link} from "react-router-dom";
import log from 'loglevel';
import {MAX_PRODUCTS_PER_PAGE} from "../../../constants/constants";
import {useSelector, useDispatch} from "react-redux";
import Rating from '@material-ui/lab/Rating';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import {DETAILS_ROUTE} from "../../../constants/react_routes";
import history from "../../../history";
import {SELECT_PRODUCT_DETAIL} from "../../../actions/types";
import { signIn } from '../../../actions';

const queryType = {
    brand: 1,
    apparel: 2,
    product: 3
}

const TopCategoriesAndBrands = () => {
    const homeAPIData = useSelector(state => state.homePageDataReducer)
    const {isSignedIn, tokenId, firstName, id} = useSelector(state => state.signInReducer)
    const dispatch = useDispatch()

    const renderImageList = (imageList, filterQueryType) => {

        return imageList.map(info => {

            let filterQuery = null

            // prepare query parameters
            switch (filterQueryType) {
                case queryType.brand:
                    log.trace(`[TopCategoriesAndBrands]: filterQuery = ${filterQuery}, filterQueryType = ${filterQueryType}`)
                    return (
                        <Grid item xs={6} sm={2} key={info.title} style={{textAlign: "center"}}>
                            <Link to={`/products?q=${filterQuery}::page=0,${MAX_PRODUCTS_PER_PAGE}`}>
                                <img src={info.imageURL} alt={info.imageLocalPath} style={{width: '80%', height: '100%'}}
                                    title={info.title}/>
                            </Link>
                        </Grid>
                    )
                case queryType.apparel:
                    log.trace(`[TopCategoriesAndBrands]: filterQuery = ${filterQuery}, filterQueryType = ${filterQueryType}`)
                    return (
                        <Grid item xs={6} sm={2} key={info.title} style={{textAlign: "center"}}>
                            <Link to={`/products?q=${filterQuery}::page=0,${MAX_PRODUCTS_PER_PAGE}`}>
                                <img src={info.imageURL} alt={info.imageLocalPath} style={{width: '80%', height: '100%'}}
                                    title={info.title}/>
                            </Link>
                        </Grid>
                    )
                case queryType.product:
                    log.trace(`[TopCategoriesAndBrands]: filterQuery = ${filterQuery}, filterQueryType = ${filterQueryType}`)
                    return (
                        <Grid item container direction="column" spacing={1} xs={12} sm={6} md={3} lg={2} key={info.id}>
                            <Grid item>
                                <Link to={`${DETAILS_ROUTE}?q=product_id=${info.id}`}>
                                    <img src={info.imageURL} alt={info.name}
                                        style={{height: "250px", width: "100%"}}
                                        title={info.name}/>
                                </Link>
                            </Grid>
                            <Grid item style={{fontSize: "14px", color: "grey", maxWidth: '100%', display: 'inline-block', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis'}}>
                                {info.name}
                            </Grid>
                            <Grid item style={{fontSize: "16px", fontWeight: "bold"}}>
                                {`$${info.price}`}
                            </Grid>
                            <Grid item style={{fontSize: "14px"}}>
                                Free ship at $25
                            </Grid>
                            <Grid item>
                                <Rating
                                    style={{zIndex: "1"}}
                                    name="customized-empty"
                                    defaultValue={info.ratings}
                                    precision={0.5}
                                    readOnly
                                    emptyIcon={<StarBorderIcon fontSize="inherit"/>}
                                />
                            </Grid>
                        </Grid>
                    )
                default:
                    log.error("[TopCategoriesAndBrands]: filterQueryType is unsupported = " + filterQueryType)
                    return null
            }
        });
    };

    const renderCategoryAndBrandsList = (title, dataList, qType) => {
        localStorage.setItem("user_id", id);
        if (dataList.length) {
            if(qType != queryType.product){
                return (
                    <>
                        <Grid container style={{fontWeight: "bold",
                            fontSize: "2rem", padding: "2rem 0 0 1rem", textDecoration: "underline"}}>
                            {title}
                        </Grid>
                        <Grid container style={{padding: '2rem 2rem'}}>
                            {renderImageList(dataList, qType)}
                        </Grid>
                    </>
                )
            }
            else{
                if(isSignedIn){
                    return (
                        <>
                            <Grid container style={{fontWeight: "bold",
                                fontSize: "2rem", padding: "2rem 0 0 1rem", textDecoration: "underline"}}>
                                {title}
                            </Grid>
                            <Grid container style={{padding: '2rem 2rem'}}>
                                {renderImageList(dataList, qType)}
                            </Grid>
                        </>
                    )
                }
            }
        }
    }

    log.info("[TopCategoriesAndBrands]: Rendering TopCategoriesAndBrands Component")

    return (
        <>
            {renderCategoryAndBrandsList("#Recommendations", homeAPIData.data.products.slice(0, 6), queryType.product)}
            {renderCategoryAndBrandsList("#Shop Top Brands", homeAPIData.data.brands, queryType.brand)}
            {renderCategoryAndBrandsList("#Shop Top Categories", homeAPIData.data.apparels, queryType.apparel)}
        </>
    )
};
export default TopCategoriesAndBrands;