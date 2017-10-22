
var itemCount = 4;

// load template without library
/*$('#cartArea').load("template/shoppingItemTemplate.html", function() {
        $('form.item').first().prop('id', 0);
        for (var i = 0; i < itemCount - 1; i++) {
            $('form.item').first().clone().prop('id', i + 1).appendTo('#cartArea');
        }
        // calculate default total price 
        for (var i = 0; i < itemCount; i++) {
            var unitPrice = $('form#' + i).find('input[name=unitPrice]').val().substr(1);
            var quantity = $('form#' + i).find('input[name=quantity]').val();
            $('form#' + i).find('input[name=totalPrice]').val("$" + (unitPrice * quantity));
        }

        // response to clicking on "Delete"
        $('button.btn-danger').click(function() {
            $(this).parent().remove();
        });

        // response to change on the quantity of a item 
        $('form.item').find('input[name=quantity]').change(function() {
            var unitPrice = $(this).parent().find('input[name=unitPrice]').val().substr(1);
            var quantity = $(this).val();
            $(this).parent().find('input[name=totalPrice]').val("$" + (unitPrice * quantity));
        });
});*/


var data = [{   
    productId: 1,
    cdName: 'a',
    unitPrice: 1,
    quantity: 1,
    id: 0
}, {
    productId: 2,
    cdName: 'b',
    unitPrice: 2,
    quantity: 2,
    id: 1
}, {
    productId: 3,
    cdName: 'c',
    unitPrice: 3,
    quantity: 3,
    id: 2
}, {
    productId: 4,
    cdName: 'd',
    unitPrice: 4,
    quantity: 4,
    id: 3
}];

// calculate default total price
for (var i = 0; i < data.length; i++) {
    data[i].totalPrice = data[i].unitPrice * data[i].quantity;
}

// load template with jquery.loadTemplate
$('#cartArea').loadTemplate('template/shoppingItemTemplate.html',
        data, {append: true, success: function() {
            // response to change on the quantity of a item 
            $('form.item').find('input[name=quantity]').change(function() {
                var unitPrice = $(this).parent().find('input[name=unitPrice]').val();
                var quantity = $(this).val();
                $(this).parent().find('input[name=totalPrice]').val(unitPrice * quantity);
            });

            // response to clicking on "Delete"
            $('button.btn-danger').click(function() {
                $(this).parent().remove();
            }); 
        }});
      

// traverse all input form in cartArea
var prepareData = function() {
    var jsonData = [];
    for (var i = 0; i < data.length; i++) {
        var aItem = {};
        aItem.productId = $('form#' + i).find('input[name=productId]').val();
        aItem.cdName = $('form#' + i).find('input[name=cdName]').val();
        aItem.unitPrice = $('form#' + i).find('input[name=unitPrice]').val();
        aItem.quantity = $('form#' + i).find('input[name=quantity]').val();
        aItem.totalPrice = $('form#' + i).find('input[name=totalPrice]').val();
        jsonData.push(aItem);
    }
    console.log(jsonData);
    return JSON.stringify(jsonData);
}

// response to clicking on "Pay"
$('button#checkout').click(function() {
    // prepare data for post request
    var postData = {
        data: prepareData()
    };

    // jquery post begins
    $.post('/CSI5380FrontEnd/payOrder', postData, function(data) {
        console.log(data);
        window.location.replace(data);
    });
    // jquery post ends
});

// response to clicking on "Continue shopping"
$('button#continue').click(function() {
    // redirect to store page by sending a get request
    window.location.replace("/CSI5380FrontEnd/store")
});

