templates = {

    creation: function (product, types) {
        debugger;
        var form = $('<form><div class="form-group"><label for="name">Product Name:</label>' +
            '<input class="form-control" id="name" type="text" value="'+product.name+'"/></div></form>');

        var select = $('<div class="form-group"><label for="types-selector">Select Types:</label>' +
            '<select class="form-control" id="types-selector"></select></div>');
        for(var i=0; i < types.length; i++){
            debugger;
            var selectEl = select.find('select');
            var type = types[i];
            var option = $('<option value="'+type.typeName+'">'+type.typeName+'</option>');
            option.appendTo(selectEl);
        }

        var textarea = $('<div class="form-group"><label>Product Description</label></div>');

        select.appendTo(form);
        return form;
    }

};