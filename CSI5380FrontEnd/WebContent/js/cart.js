
var itemCount = 6;

$('#cartArea').load("template/shoppingItemTemplate.html", function() {
        $('form.item').first().prop('id', 0);
        for (var i = 0; i < itemCount - 1; i++) {
            $('form.item').first().clone().prop('id', i + 1).appendTo('#cartArea');
        }
        // calculate default total price 
        for (var i = 0; i < itemCount; i++) {
            var unitPrice = $('form#' + i).find('input[name=unitPrice]').val();
            var quantity = $('form#' + i).find('input[name=quantity]').val();
            $('form#' + i).find('input[name=totalPrice]').val(unitPrice * quantity);
        }
});


// response to clicking on "Delete"
$('form.item').find('button.btn-danger').click(function() {
    $(this).parent().remove();
});

// response to change on the quantity of a item 
$('form.item').find('input[name=quantity]').change(function() {
    var unitPrice = $(this).parent().find('input[name=unitPrice]').val();
    var quantity = $(this).val();
    $(this).parent().find('input[name=totalPrice]').val(unitPrice * quantity);
});