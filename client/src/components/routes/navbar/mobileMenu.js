import React from 'react';
import log from "loglevel";
import {Grid, IconButton, Menu} from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";
import BagButton from "./bagButton";
import ShoppingCart from '@material-ui/icons/ShoppingCart';

export default function MobileMenu(props) {

    log.info(`[MobileMenu]: Rendering MobileMenu Component`)
    return (
        <Menu
            anchorEl={props.mobileMoreAnchorEl}
            anchorOrigin={{vertical: 'top', horizontal: 'right'}}
            id={props.mobileMenuId}
            keepMounted
            transformOrigin={{vertical: 'top', horizontal: 'right'}}
            open={props.isMobileMenuOpen}
            onClose={props.handleMobileMenuClose}>
            <MenuItem onClick={props.authBtnHandler} style={{padding: "0 0.7rem 0 0"}}>
                <Grid container alignItems="center">
                    <Grid item>
                        <IconButton aria-label="account of current user"
                                    aria-controls="primary-search-account-menu"
                                    aria-haspopup="true"
                                    color="inherit">
                            {props.authIcon}
                        </IconButton>
                        {props.authLabel}
                    </Grid>
                </Grid>
            </MenuItem>
            <MenuItem onClick={props.bagBtnHandler} style={{padding: "0 0.7rem 0 0"}}>
                <Grid container alignItems="center">
                    <Grid item xs={7}>
                        <IconButton color="inherit">
                            <BagButton/>
                        </IconButton>
                        Bag
                    </Grid>
                </Grid>
            </MenuItem>
            <MenuItem onClick={props.orderPageBtnHandler} style={{padding: "0 0.7rem 0 0", display: (props.isSignedIn ? 'block': 'none')}}>
                <Grid container alignItems="center">
                    <Grid item xs={7}>
                        <IconButton color="inherit">
                            <ShoppingCart/>
                        </IconButton>
                        My Orders
                    </Grid>
                </Grid>
            </MenuItem>
        </Menu>
    );
};
