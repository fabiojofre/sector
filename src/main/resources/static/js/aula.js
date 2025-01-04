


// histórico aulas
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-aula-efetuada').DataTable({
		searching: false,
		order: [[0, "desc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/aulas/datatables/server/aula',
			data: 'data'
		},
		columns: [
			{ data: 'id' },
			{ data: 'licao.nome' },
			{
				data: 'dataAula', render:
					function(dataAula) {
						return moment(dataAula).format('LL');
					}
			},
			{ data: 'professor.nome' },
			{
				data: 'convertidos[].nome', render:
					function(convertidos) {
						return (
							 convertidos.toLocaleString('convertido.nome')
						)
					}
			},
			{
				orderable: false, data: 'id', "render": function(id) {
					return '<a class="btn btn-success btn-sm btn-block" href="/aulas/editar/'
						+ id + '" role="button"><i class="fas fa-edit"></i></a>';
				}
			},
			{
				orderable: false,
				data: 'id',
				"render": function(id) {
					return '<a class="btn btn-danger btn-sm btn-block" href="/aulas/excluir/' +
						id + '" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
				}
			}
		]
	});
});



// histórico aulas 2
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-aula-efetuada2').DataTable({
		searching: false,
		order: [[0, "desc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/aulas/datatables/server/aula2',
			data: 'data'
		},
		columns: [
			{ data: 'id' },
			{ data: 'licao.nome' },
			{
				data: 'dataAula', render:
					function(dataAula) {
						return moment(dataAula).format('LL');
					}
			},
			{ data: 'professor.nome' },
			{
				data: 'convertidos[].nome', render:
					function(convertidos) {
						return (
							convertidos.toLocaleString('convertido.nome')
						)
					}
			},
			{
				orderable: false, data: 'id', "render": function(id) {
					return '<a class="btn btn-success btn-sm btn-block" href="/aulas/editar/'
						+ id + '" role="button"><i class="fas fa-edit"></i></a>';
				}
			},
			{
				orderable: false,
				data: 'id',
				"render": function(id) {
					return '<a class="btn btn-danger btn-sm btn-block" href="/aulas/excluir/' +
						id + '" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
				}
			}
		]
	});
});
