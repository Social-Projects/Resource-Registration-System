$(document).ready(
		function() {
			$("#clickmeshow").click(function() {
				$(".clonedInput").fadeIn("slow");
			});
			$("#clickmehide").click(function() {
				$(".clonedInput").fadeOut("slow");
			});
			$('#btnAdd')
					.click(function() {
								var num = $('.clonedInput').length;
								var newNum = new Number(num + 1);
								var newElem = $('#input' + num).clone().val('').attr(
										'id', 'input' + newNum).removeAttr('value');
						
								newElem.children('#myparam0').val('').attr(
										'name','parameters[' + (newNum - 1)+ '].description');
								newElem.children('#myparam1').val('').attr(
										'name','parameters[' + (newNum - 1)+ '].unitName');
								newElem.children('#myparam2').attr(
										'name','parameters[' + (newNum - 1)+ '].parametersType');
								$('#input' + num).after(newElem);
								$('#btnDel').removeAttr('disabled');

                                positionFooter();
							});

			$('#btnDel').click(function() {
				var num = $('.clonedInput').length;
				$('#input' + num).remove();
				$('#btnAdd').removeAttr('disabled');
				if (num - 1 == 1)
					$('#btnDel').attr('disabled', 'disabled');
                positionFooter();
			});

			$('#btnDel').attr('disabled', 'disabled');
		});
