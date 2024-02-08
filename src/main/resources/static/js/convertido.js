/**
 * busca as especialidades com auto-complete
 */
$("#congregacao").autocomplete({
    source: function (request, response) {
        $.ajax({
            method: "GET",
            url: "/congregacoes/nome",
            data: {
            	termo: request.term
			},
            success: function (data) {
            	response(data);
            }
        });
    }
});

/**
 * após a congregacoes ser selecionado busca 
 * os médicos referentes e os adiciona na página com
 * radio
 */
$('#area').on('blur', function() {
    $('div').remove(".custom-radio");
	var area = $(this).val();
	if ( area != null ) {			
		$.get( "/congregacoes/congregacao/area/" + area , function( result ) {
				
			var ultimo = result.length - 1; 
			
			$.each(result, function (k, v) {
				
				if ( k == ultimo ) {
	    			$("#congregacoes").append( 
	    				 '<div class="custom-control custom-radio">'	
	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="congregacao.id" value="'+ v.id +'" required>'
						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
						+  '<div class="invalid-feedback">Congregacao é obrigatória</div>'
						+'</div>'
	    			);
				} else {
	    			$("#congregacoes").append( 
	    				 '<div class="custom-control custom-radio">'	
	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="congregacao.id" value="'+ v.id +'" required>'
						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
						+'</div>'
	        		);	            				
				}
		    });
		});
	}
});	


















