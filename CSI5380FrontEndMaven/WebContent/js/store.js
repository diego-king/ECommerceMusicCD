/* 
 * Copyright (C) 2017- Group 5 - All Rights Reserved
 *  
 * 
 * Version 1.0: 
 * Design: Yicong Li
 * Author: Yicong Li
 * 
 */


/*
 * This file contains methods(functions) for interaction of 
 * CD store page.
 */


$(document).ready(function() {
    // load data
    var loadCD = function(inputData) {
        var postData = {
            category: inputData
        };
        // jquery post begins
        $.post('/CSI5380FrontEnd/getCDs', postData, function(data) {
            for (var i = 0; i < data.length; i++) {
                data[i].detailId = i;
                data[i].herf = '#' + data[i].detailId;
            }
            // generate cds
            generateCDs(data);
        });
        // jquery post ends
    };

    // load cd by default category
    loadCD($('select[name=categories]').val());

    // add a cd to the shopping cart
    var addCDToCart = function() {
        $('button.add').click(function() {
            var id = $(this).find('input[name=id]').val();
            // prepare data for post
            var postData = {
                id: id
            };
            // jquery post begins
            $.post('/CSI5380FrontEnd/addToCart', postData, function() {
                // a cd is added successfully
                alert('Your item has been successfully added to the shopping cart.');
            });
            // jquery post ends
        });
    };

    // load templates
    var generateCDs = function(inputData) {
        // clear old cds
        $('#cdArea').empty();
        // load new cds
        $('#cdArea').loadTemplate('template/cdTemplate.html',
            inputData, {append: true, success: function () {
                addCDToCart();
        }});    
    }

    // response to the change of category selection
    $('select[name=categories]').change(function() {
        // load new cds according to the category chosen
        loadCD($(this).val());
    });

});