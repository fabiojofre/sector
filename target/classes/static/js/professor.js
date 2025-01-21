$(document).ready(function () {
	moment.locale('pt-BR');
    var table = $('#table-professor').DataTable({
    	searching: true,
    	order: [[ 0, "asc" ]],
    	lengthMenu: [5, 10],
        processing: true,
        serverSide: true,
        responsive: true,
        ajax: {
            url: '/professores/datatables/server/historico',
            data: 'data'
        },
        columns: [
            {data: 'id'},
            {data: 'nome'},
            {data: 'telefone'},
            {orderable: false, 
             data: 'id',
                "render": function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/professores/editar/professor/'+ 
                    	id +'" role="button"><i class="fas fa-edit"></i></a>';
                }
            }
        ]
    });
});    
