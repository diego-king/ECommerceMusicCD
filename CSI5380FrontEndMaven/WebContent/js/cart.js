/* 
 * Copyright (C) 2017- I MUSIC - All Rights Reserved
 *  
 * 
 * Version 1.0: 
 * Design: Yicong Li
 * Author: Yicong Li
 * 
 */


/*
 * This file contains methods(functions) for interaction of 
 * the shopping cart.
 */

$(document).ready(function() {
    // load data 
    var loadShoppingCart = function() {
        // jquery get begins
        $.get('/CSI5380FrontEnd/goToCart', function(data) {
            console.log(data);
            // calculate default total price
            for (var i = 0; i < data.length; i++) {
                data[i].totalPrice = data[i].price * data[i].quantity;
                data[i].formId = i;
            }
            // generate the shopping cart
            generateCart(data);
        });
        // jquery get ends
    }

    // call the loading process
    loadShoppingCart();

    // load template with jquery.loadTemplate
    var generateCart = function(inputData) {
        // clear the old cart
        $('#cartArea').empty();
        // load the new cart
        $('#cartArea').loadTemplate('template/shoppingItemTemplate.html',
            inputData, {append: true, success: function() {
                // response to change on the quantity of a item 
                $('form.item').find('input[name=quantity]').change(function() {
                    var unitPrice = $(this).parent().parent().find('span.price').text();
                    var quantity = $(this).val();
                    $(this).parent().parent()
                        .find('span.totalPrice').text((unitPrice * quantity).toFixed(2));
                });

                // response to enter in quantity
                $('form.item').find('input[name=quantity]').bind('keypress', function(event) {
                    if (event.keyCode == 13) {
                        var unitPrice = $(this).parent().parent().find('span.price').text();
                        var quantity = $(this).val();
                        $(this).parent().parent()
                            .find('span.totalPrice').text((unitPrice * quantity).toFixed(2));

                        return false;
                    }
                });

                // response to clicking on "Delete"
                $('button.btn-danger').click(function() {  
                    $(this).parent().parent().parent().remove();
                    var postData = {
                        id : $(this).parent().parent().parent().find('input[name=productId]').val()
                    };
                    $.post('/CSI5380FrontEnd/deleteFromCart', postData);
                }); 
            }});
    }
          

    // traverse all input form in cartArea
    var prepareData = function() {
        var jsonData = [];
        for (var i = 0; i < $('form.item').length; i++) {
            var aItem = {};
            aItem.productId = $('form#' + i).find('input[name=productId]').val();
            aItem.unitPrice = $('form#' + i).find('span.price').text();
            aItem.quantity = $('form#' + i).find('input[name=quantity]').val();
            jsonData.push(aItem);
        }
        console.log(jsonData);
        return JSON.stringify(jsonData);
    }

    // response to clicking on "Pay"
    $('a#checkout').click(function() {
        // prepare data for post request
        var postData = {
            data: prepareData()
        };

        // jquery post begins
        $.post('/CSI5380FrontEnd/payOrder', postData);
        // jquery post ends
    });
});



