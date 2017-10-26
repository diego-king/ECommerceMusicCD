// load data
var loadCD = function(inputData) {
    var postData = {
        category: inputData
    };
    $.post('/CSI5380FrontEnd/getCDs', postData, function(data) {
        for (var i = 0; i < data.length; i++) {
            data[i].detailId = i;
            data[i].herf = '#' + data[i].detailId;
        }
        generateCDs(data);
    });
};

loadCD($('select[name=categories]').val());

var addCDToCart = function() {
    $('button.add').click(function() {
        var id = $(this).find('input[name=id]').val();
        // prepare data for post
        var postData = {
            id: id
        };
        // jquery post begins
        $.post('/CSI5380FrontEnd/addToCart', postData, function(data) {
            // possible response to returning data
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

//generateCDs(data);

// response to the change of category selection
$('select[name=categories]').change(function() {
    // load new cds according to the category chosen
    loadCD($(this).val());
});
