$(document).ready(
		function() {
			$("#clickmeshow").click(function() {
				$(".clonedInput").show("slow", function() {

				});
			});
			$("#clickmehide").click(function() {
				$(".clonedInput").hide("slow", function() {

				});
			});
			$('#btnAdd')
					.click(
							function() {
								var num = $('.clonedInput').length;
								var newNum = new Number(num + 1);
								var newElem = $('#input' + num).clone().attr(
										'id', 'input' + newNum);
								newElem.children('#myparam0').attr(
										'name',
										'parameters[' + (newNum - 1)
												+ '].description');
								newElem.children('#myparam1').attr(
										'name',
										'parameters[' + (newNum - 1)
												+ '].unitName');
								newElem.children('#myparam2').attr(
										'name',
										'parameters[' + (newNum - 1)
												+ '].parametersType');
								$('#input' + num).after(newElem);
								$('#btnDel').attr('disabled', '');

							});

			$('#btnDel').click(function() {
				var num = $('.clonedInput').length;
				$('#input' + num).remove();
				$('#btnAdd').attr('disabled', '');
				if (num - 1 == 1)
					$('#btnDel').attr('disabled', 'disabled');
			});

			$('#btnDel').attr('disabled', 'disabled');
		});
