
/**
 * após a area ser selecionado busca 
 * as congregações referentes e as adiciona na página com
 * radio
 */
$('#area').on('blur', function() {
    $('div').remove(".custom-radio");
	var area = $(this).val();
	if ( area != '' ) {			
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
 * Datatable histórico de convertdios
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-convertido-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [3, 'desc'],
        ajax : {
            url : '/convertidos/datatables/server/historico',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
            {data : 'origemConversao.nome'},
            {data : 'telefone'},
            {data: 'dataConversao', render:
                function( dataConversao ) {
                    return moment(dataConversao).format('LL');
                }
            },
            {data : 'endereco'},
            {data : 'congregacao.nome'},
            {data : 'pessoa.nome'}, 
            {data : 'pessoa.telefone'}, 
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/convertidos/editar/convertido/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/convertidos/excluir/convertido/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
           
        ]
    });
});


$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-convertido-historico-area').DataTable({
        searching : false,
        lengthMenu : [ 10, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [0, 'asc'],
        ajax : {
            url : '/convertidos/datatables/server/historico-area',
            data : 'data'
        },
        columns : [
            {data : 'nome'},
            {data : 'quantidade'}
           
        ]
    });
});




$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-convertido-historico-setor').DataTable({
        searching : false,
        lengthMenu : [ 5, 20 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [1, 'asc'],
        ajax : {
            url : '/convertidos/datatables/server/historico-setor',
            data : 'data'
        },
        columns : [
            {data : 'nome'},
            {data : 'area'},
            {data : 'quantidade'}
           
        ]
    });
});




$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-convertido-historico-setor-area').DataTable({
        searching : false,
        lengthMenu : [ 12, 15 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [0, 'asc'],
        ajax : {
            url : '/convertidos/datatables/server/historico-setor-area',
            data : 'data'
        },
        columns : [
            {data : 'area'},
            {data : 'quantidade'}
           
        ]
    });
});













