
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
            url : '/discipulados/datatables/server/historicoconvertido',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
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
                    return '<a class="btn btn-success btn-sm btn-block" href="/discipulados/editar/convertido/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            }
           
        ]
    });
});



/**
 * Datatable histórico de discipulados
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-discipulado-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [3, 'desc'],
        ajax : {
            url : '/discipulados/datatables/server/historicodiscipulado',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
            {data : 'telefone'},
             {data: 'dataNascimento', render:
                function( dataNascimento ) {
                    return moment(dataNascimento).format('DD [de] MMMM');
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/discipulados/editar/convertido/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/discipulados/concluir/convertido/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
           
        ]
    });
});



/**
 * Datatable histórico de discipulados
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-concluido-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [5, 'desc'],
        ajax : {
            url : '/discipulados/datatables/server/historicoconcluido',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
            {data : 'telefone'},
             {data: 'dataNascimento', render:
                function( dataNascimento ) {
                    return moment(dataNascimento).format('DD [de] MMMM');
                }
            },
             {data: 'dataConclusao', render:
                function( dataConclusao ) {
                    return moment(dataConclusao).format('LL');
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/discipulados/editar/convertido/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
             {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/discipulados/rematricular/convertido/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
           
        ]
    });
});
















