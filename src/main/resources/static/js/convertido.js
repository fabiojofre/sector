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
 * após a area ser selecionado busca 
 * as congregações referentes e as adiciona na página com
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

/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-convertido-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/convertidos/datatables/server/historico',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
            {data : 'obsConversao'},
            {data : 'telefone'},
            {data : 'endereco'},
            {data : 'congregacao.nome'},
            {data : 'pessoa.nome'},
            {data : 'pessoa.telefone'},
            {data: 'dataConversao', render:
                function( dataConsulta ) {
                    return moment(dataConsulta).format('LLL');
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/convertidos/editar/convertido/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/convertido/excluir/convertido/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/convertidos/finalizar/consulta/'
                    + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            }
        ]
    });
});


















