(function($) {
	"use strict";

	var nomesMeses = [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ];

	// Conteiner de filtro atualmente em exibicao.
	var conteinerEmExibicao = null;

	/**
	 * Esconde os conteineres de filtro e agrupamento.
	 */
	function esconderConteineres() {
		$("div[id^='conteiner']").hide();
	}

	/**
	 * Retorna a string passada por parametro com a primeira letra maiuscula.
	 */
	function getNome(texto) {
		return texto.substring(0, 1).toUpperCase() + texto.substring(1);
	}

	$(document).ready(function() {

		// ******************************************
		// Filtro de faixa salarial.
		// ******************************************
		var pisoSalario = $("meta[name='pisoSalario']").attr("content");
		var tetoSalario = $("meta[name='tetoSalario']").attr("content");
		$("#valorFaixaSalarial").ionRangeSlider({
			type : "double",
			grid : true,
			min : pisoSalario,
			max : tetoSalario,
			from : pisoSalario,
			to : tetoSalario,
			onChange : mudouFaixaSalarial,
			prefix : "R$"
		});
		//var sliderFaixaSalarial = $("#valorFaixaSalarial").data("ionRangeSlider");
		function mudouFaixaSalarial(dados) {
			if (dados.from === pisoSalario && dados.to === tetoSalario) {
				$("#excluirFiltroFaixaSalarial").click();
			} else {
				$("#excluirFiltroFaixaSalarial").show();
				$("#detalheFiltroFaixaSalarial").show();
				$("#detalheFiltroFaixaSalarial").text("R$ " + dados.from + " à R$ " + dados.to);
			}
		}
		$("#excluirFiltroFaixaSalarial").click(function(e) {
			$("#excluirFiltroFaixaSalarial").hide();
			$("#detalheFiltroFaixaSalarial").hide();
			$("#detalheFiltroFaixaSalarial").text("");
			e.preventDefault();
			return false;
		});

		// ******************************************
		// Filtro de data.
		$('#valorData').datepicker({
			autoclose : true,
			todayHighlight : true
		});
		$("#valorData").change(function() {
			if ($(this).val() === "") {
				$("#excluirFiltroData").click();
			} else {
				$("#excluirFiltroData").show();
				$("#detalheFiltroData").show();
				var data = new Date($(this).val());
				$("#detalheFiltroData").text(nomesMeses[data.getMonth()] + "/" + data.getFullYear());
			}
		});
		$("#excluirFiltroData").click(function(e) {
			$("#excluirFiltroData").hide();
			$("#detalheFiltroData").hide();
			$("#detalheFiltroData").text("");
			$('#valorData').val("");
			e.preventDefault();
			return false;
		});
		// ******************************************
		// Filtro de cargo.
		// ******************************************
		$('#valorCargo').select2({
			width : '100%'
		});
		$("#valorCargo").change(function() {
			var quantidadeSelecionada = $('#valorCargo option:selected').length;
			if (quantidadeSelecionada === 0) {
				$("#excluirFiltroCargo").click();
			} else {
				$("#excluirFiltroCargo").show();
				$("#detalheFiltroCargo").show();
				$("#detalheFiltroCargo").text(quantidadeSelecionada);
			}
		});
		$("#excluirFiltroCargo").click(function(e) {
			$("#excluirFiltroCargo").hide();
			$("#detalheFiltroCargo").hide();
			//$("#valorCargo").prop("selected", false);
			$("#valorCargo").val(null).trigger('change');
			e.preventDefault();
			return false;
		});
		// ******************************************
		// Filtro de item.
		// ******************************************
		$('#valorItemFolha').select2({
			width : '100%'
		});
		$("#valorItemFolha").change(function() {
			var quantidadeSelecionada = $('#valorItemFolha option:selected').length;
			if (quantidadeSelecionada === 0) {
				$("#excluirFiltroItemFolha").click();
			} else {
				$("#excluirFiltroItemFolha").show();
				$("#detalheFiltroItemFolha").show();
				$("#detalheFiltroItemFolha").text(quantidadeSelecionada);
			}
		});
		$("#excluirFiltroItemFolha").click(function(e) {
			$("#excluirFiltroItemFolha").hide();
			$("#detalheFiltroItemFolha").hide();
			//$("#valorItemFolha").prop("selected", false);
			$("#valorItemFolha").val(null).trigger('change');
			e.preventDefault();
			return false;
		});

		// ******************************
		// Quando os botoes de filtro e agrupamento foram
		// clicados, exibe os conteineres relacionados.
		$("#filtros a").click(function(e) {
			var nome = getNome($(this).attr("id"));
			if (conteinerEmExibicao === null || conteinerEmExibicao.attr("id") !== $("#conteiner" + nome).attr("id")) {
				esconderConteineres();
				conteinerEmExibicao = $("#conteiner" + nome);
				conteinerEmExibicao.show("slow");
				e.preventDefault();
				return false;
			}
		});
		// ******************************

		// Quando o usuario clicar sobre a pagina, verifica se é
		// fora do
		// conteiner de filtro
		// atualmente ativo e, se for, esconde o conteiner de
		// filtro.
		$(document).click(function(e) {
			if (conteinerEmExibicao !== null && !conteinerEmExibicao.is(e.target) && conteinerEmExibicao.has(e.target).length === 0) {
				conteinerEmExibicao.hide('slow');
				conteinerEmExibicao = null;
			}
		});

		return false;
	});

})(jQuery);