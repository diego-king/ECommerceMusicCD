var data = [{
    productId: '1',
    cdName: 'cd #1',
    herf: '#11',
    detailId: '11',
    imagePath: 'image/cd/cd001.jpg',
    cdDeatil: 'Anim pariterry richardson ad t skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probab',
    unitPrice: '1234'
}, {
    productId: '2',
    cdName: 'cd #2',
    herf: '#22',
    detailId: '22',
    imagePath: 'image/cd/cd002.jpg',
    cdDeatil: 'Anim pariterry richardson ad t skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probab',
    unitPrice: '1234'
}, {
    productId: '3',
    cdName: 'cd #3',
    herf: '#33',
    detailId: '33',
    imagePath: 'image/cd/cd003.jpg',
    cdDeatil: 'Anim pariterry richardson ad t skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probab',
    unitPrice: '1234'
}];

// load data
$.post('/CSI5380FrontEnd/getCDs', {category: $('select[name=categories]').val()}, function(data) {
    //generateCDs(data);
    console.log(data);
});

// load templates
var generateCDs = function(inputData) {
    $('#cdArea').loadTemplate('template/cdTemplate.html',
        inputData, {append: true, success: function () {
            $('button.add').click(function() {
                alert('Add!');
            });
    }});    
}

generateCDs(data);

// response to the change of category selection
$('select[name=categories]').change(function() {
    var postData = {
        category: $(this).val()
    };
    console.log($(this).val());
    /*$.post('/CSI5380FrontEnd/getCDs', postData, function(data) {
        generateCDs(data);
    })*/
});