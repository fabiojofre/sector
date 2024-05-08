
/**
 * após a area ser selecionado busca 
 * as congregações referentes e as adiciona na página com
 * radio
 */
$('#area').on('blur', function() {
	$('div').remove(".custom-radio");
	var area = $(this).val();
	if (area != '') {
		$.get("/congregacoes/congregacao/area/" + area, function(result) {

			var ultimo = result.length - 1;

			$.each(result, function(k, v) {

				if (k == ultimo) {
					$("#congregacoes").append(
						'<div class="custom-control custom-radio">'
						+ '<input class="custom-control-input" type="radio" id="customRadio' + k + '" name="congregacao.id" value="' + v.id + '" required>'
						+ '<label class="custom-control-label" for="customRadio' + k + '">' + v.nome + '</label>'
						+ '<div class="invalid-feedback">Congregacao é obrigatória</div>'
						+ '</div>'
					);
				} else {
					$("#congregacoes").append(
						'<div class="custom-control custom-radio">'
						+ '<input class="custom-control-input" type="radio" id="customRadio' + k + '" name="congregacao.id" value="' + v.id + '" required>'
						+ '<label class="custom-control-label" for="customRadio' + k + '">' + v.nome + '</label>'
						+ '</div>'
					);
				}
			});
		});
	}
});


$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-convite-pendente').DataTable({
		searching: false,
		order: [[3, "asc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/convites/datatables/server/listapendentes',
			data: 'data'
		},
		columns: [
			{ data: 'id' },
			{ data: 'tipo.nome' },
			{
				data: 'dataEvento', render:
					function(dataEvento) {
						return moment(dataEvento).format('LL');
					}
			},
			{
				orderable: false,
				data: 'id',
				"render": function(id) {
					return '<a class="btn btn-danger btn-sm btn-block" href="/convites/excluir/' +
						id + '" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
				}
			}
		]
	});
});



$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-convite-liberado').DataTable({
		searching: false,
		order: [[3, "asc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/convites/datatables/server/listaliberados',
			data: 'data'
		},
		columns: [
			{ data: 'id' },
			{ data: 'tipo.nome' },
			{
				data: 'dataEvento', render:
					function(dataEvento) {
						return moment(dataEvento).format('LL');
					}
			},
			{ data: 'congregacao.nome' },
			{
				data: 'congregacoes[].nome', render:
					function(congregacoes) {
						return (
							congregacoes.toLocaleString('congregacao.nome')
						)
					}
			},
			{
				orderable: false, data: 'id', "render": function(id) {
					return '<a class="btn btn-success btn-sm btn-block" href="/convites/editar/'
						+ id + '" role="button"><i class="fas fa-edit"></i></a>';
				}
			},
			{
				orderable: false,
				data: 'id',
				"render": function(id) {
					return '<a class="btn btn-danger btn-sm btn-block" href="/convites/excluir/' +
						id + '" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
				}
			}
		]
	});
});



$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-convite-recebido').DataTable({
		searching: false,
		order: [[3, "asc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/convites/datatables/server/listarecebidos',
			data: 'data'
		},
		columns: [
			{ data: 'id' },
			{ data: 'tipo.nome' },
			{
				data: 'dataEvento', render:
					function(dataEvento) {
						return moment(dataEvento).format('LL');
					}
			},
			{ data: 'congregacao.nome' }
		]
	});
});







