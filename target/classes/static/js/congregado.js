// processa o auto-complete de profissoes
$(function() {
	// remove o espaco depois da virgula
	function split(val) {
		return val.split(/,\s*/);
	}

	// extrai o ultimo termo adicionado
	function extractLast(term) {
		return split(term).pop();
	}
	
	// adicioana a tag de input com a profissao no html 
	function addProfissao(titulo) {
		$('#listaProfissoes')
			.append('<input type="hidden" value="'+ titulo +'" name="profissoes">');
	}
	
	// mostra na pagina um toast com mensagem de profissoes repetidas 
    function exibeMessagem(texto) {
        $('.add-toast').append(""
          .concat('<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="2800">',
                  '<div class="toast-header">',
                  '<strong class="mr-auto">Atenção</strong>',
                  '<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">',
                  '  <span aria-hidden="true">&times;</span>',
                  '</button>',
              '</div>',
              '<div class="toast-body">', texto, '</div>',
          '</div>')
        );
        $('.toast').toast('show');
        $('.toast').on('hidden.bs.toast', function (e) {
            $(e.currentTarget).remove();
        });
    }	

	$("#autocomplete-profissoes")
		.on("keydown",	function(event) {
			if (event.keyCode === $.ui.keyCode.TAB
					&& $(this).autocomplete("instance").menu.active) {
				event.preventDefault();
			}
		})
		.autocomplete({
			source : function(request, response) {
				$.getJSON("/profissoes/titulo", {
					termo : extractLast(request.term)
				}, response);
			},
			search : function() {
				// custom minLength
				var term = extractLast(this.value);
				if (term.length < 1) {
					return false;
				}
			},
			focus : function() {
				// prevent value inserted on focus
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				console.log('1. this.value: ' + this.value)
				console.log('2. terms: ' + terms)
				console.log('3. ui.item.value: ' + ui.item.value)
				// remove a entrada atual
				terms.pop();
				console.log('4. terms: ' + terms)
				// testa se valor já foi inserido no array
				var exists = terms.includes(ui.item.value);				
				if (exists === false) {				
					// add the selected item
					terms.push(ui.item.value);
					console.log('5. terms: ' + terms)
					terms.push("");
					console.log('6. terms: ' + terms)
					this.value = terms.join(", ");
					console.log('7. this.value: ' + this.value)
					console.log('8. ui.item.value: ' + ui.item.value)
					// adiciona profissao na pagina para envio ao controller
					addProfissao(ui.item.value);
				} else {
					exibeMessagem('A Profissão <b>'+ ui.item.value +'</b> já foi selecionada.');
				}
				return false;
			}
		});
});

// histórico congregado 

$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-congregado-listagem').DataTable({
		searching: true,
		order: [[0, "desc"]],
		lengthMenu: [5, 10],
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: '/congregados/datatables/server/historico',
			data: 'data'
		},
		columns: [
			{ data: 'nome' },
			{ data: 'id' },
			{ data: 'telefone' },
			{ data: 'congregacao.nome' },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/congregados/editar/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/congregados/excluir/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
           
		]
	});
});




// para formulario ajax
/*$("#form-add").submit(function (e) {
	// bloqueia o comportamente padrao do submit
	e.preventDefault();

    var especialistaDto = {};
    especialistaDto.nome = $('#nome').val();
    especialistaDto.crm = $('#crm').val();
    especialistaDto.dtInscricao = $('#dtInscricao').val();

    var profissoes = $('#autocomplete-profissoes').val().split(',');
    profissoes.pop();
    console.log("array", profissoes);
    especialistaDto.profissoes = profissoes;

    console.log('especialista > ', especialistaDto);

    $.ajax({
    	method: "POST",
        url: "/especialistas/salvar/ajax",
        contentType : "application/json",
        data: JSON.stringify(especialistaDto),

        beforeSend: function(xhr) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
        	xhr.setRequestHeader(header, token);

            $(".is-invalid").removeClass("is-invalid");
		},
        success: function() {
        	$("#form-add").each(function () {
              this.reset();
        	});
		},
		statusCode: {
		    422: function(xhr) {
		    	console.log('status: ', xhr.status)
		    	var errors = $.parseJSON(xhr.responseText);
        	    $.each(errors, function (key, val) {

        	    	$("#" + key).addClass("is-invalid");
        	    	$("#error-" + key)
						.addClass("invalid-feedback")
						.append("<span class='error-span'>" + val + "</span>");

        	    });
		    }
		},
        error: function(xhr) {
   	        	console.log(' error : ', xhr.responseText);
        		$("#alert").addClass("alert alert-danger").text("Não foi possível salvar esta promoção");
		}
    });
});*/
