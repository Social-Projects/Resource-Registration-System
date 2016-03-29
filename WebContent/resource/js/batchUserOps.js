jQuery(document)
		.ready(
				function($) {

					var resource_number;
					var tc_id;
					var tc_name;
					var selected;
					var role;

					var communityModal = $("#userCommunitySelectModal");
					var roleRegModal = $('#userRegistratorModal');

					var table = $('#datatable').DataTable(
							{
								"bSort" : false,
								"oLanguage" : {
									"sEmptyTable" : jQuery.i18n
											.prop('dataTable.sEmptyTable'),
									"sInfo" : jQuery.i18n
											.prop('dataTable.sInfo'),
									"sInfoEmpty" : jQuery.i18n
											.prop('dataTable.sInfoEmpty'),
									"sInfoFiltered" : jQuery.i18n
											.prop('dataTable.sInfoFiltered'),
									"oPaginate" : {
										"sFirst" : jQuery.i18n
												.prop('dataTable.sFirst'),
										"sLast" : jQuery.i18n
												.prop('dataTable.sLast'),
										"sNext" : jQuery.i18n
												.prop('dataTable.sNext'),
										"sPrevious" : jQuery.i18n
												.prop('dataTable.sPrevious')
									},
									"sLengthMenu" : jQuery.i18n
											.prop('dataTable.sLengthMenu'),
									"sLoadingRecords" : jQuery.i18n
											.prop('dataTable.sLoadingRecords'),
									"sProcessing" : jQuery.i18n
											.prop('dataTable.sProcessing'),
									"sSearch" : jQuery.i18n
											.prop('dataTable.sSearch'),
									"sZeroRecords" : jQuery.i18n
											.prop('dataTable.sZeroRecords')
								},

							});
					$('#datatable tbody').on('click', 'tr', function() {
						$(this).toggleClass('selected');
					});
					$("#datatable_wrapper").prepend($("#actionList"));
					function gather_data() {
						selected = [];

						var list = table.rows('.selected').nodes();

						$.each(list, function(index, element) {
							var field = $(".logindata", element);
							selected.push(field.attr("value"));
						})

						if (selected.length != 0) {
							return true;
						} else {
							bootbox.alert(jQuery.i18n
									.prop('msg.noUsersSelected'));
							return false;
						}
					}

					/* **************************** */

					$('.set-role').click(function() {
						role = $(this).attr("val");
						if (!gather_data())
							return;
						confirm_rolechange();
					});

					$('.set-community').click(function() {
						if (!gather_data())
							return;
						start_community_search();
					});

                    $('.reset-password').click(function() {
                        if (!gather_data())
                            return;
                        confirm_passwordreset();
                    });

					/* **************************** */

					function confirm_rolechange() {
						submit_rolechange();
					}

					function submit_rolechange() {
						var json = {
							"login" : selected.toString(),
							"resource_number" : resource_number,
							"role" : role
						};

						$
								.ajax({
									type : "POST",
									url : "get-all-users/batch-role-change",
									dataType : "text",
									data : JSON.stringify(json),
									contentType : 'application/json; charset=utf-8',
									mimeType : 'application/json',
									success : function(data) {
										var list = table.rows('.selected')
												.nodes();
										console.log(list.length + ": "
												+ list.toString());
										bootbox.alert(jQuery.i18n.prop(data));

										$.each(list, function(index, element) {
											var field = $(".role", element);
											field.text(jQuery.i18n
													.prop("msg.role." + role));
											$(element).removeClass('selected');
										})
										return false;
									},
									error : function(xhr, status, error) {
										bootbox
												.alert("<h3>Error performing Role change operation</h3>"
														+ xhr.responseText);
										return "";
									}
								});

					}
					/* **************************** */

					function start_community_search() {
						communityModal.modal('show');
					}
					$('#tc_search')
							.autocomplete(
									{
										serviceUrl : 'get-all-users/communities',
										paramName : "communityDesc",
										delimiter : ",",
										minChars : 3,
										autoSelectFirst : true,
										deferRequestBy : 100,
										type : "POST",

										transformResult : function(response) {
											return {
												suggestions : $
														.map(
																$
																		.parseJSON(response),
																function(item) {
																	return {
																		value : item.name
																	};
																})
											};
										},
										onSelect : function(suggestion) {
											json = {
												"communityName" : suggestion.value
											}
											$
													.ajax({
														url : "get-all-users/get-community",
														type : "POST",
														dataType : 'json',
														data : suggestion.value,
														contentType : 'application/json; charset=utf-8',
														mimeType : 'application/json',
														success : function(data) {
															tc_id = data.territorialCommunityId;
															tc_name = data.name;
														},
														error : function(xhr,
																status, error) {
															bootbox
																	.alert("<h3>Error getting the Community list. Please contact the developer.</h3>"
																			+ xhr.responseText);
															return "";
														}
													});
										}
									});

					$('.submit', communityModal)
							.click(
									function() {
										if (tc_id == null) {
											bootbox.alert("TC cant be empty");
											return;
										}

										var json = {
											"login" : selected.toString(),
											"communityId" : tc_id
										};

										$
												.ajax({
													type : "POST",
													url : "get-all-users/batch-community-change",
													dataType : "text",
													data : JSON.stringify(json),
													contentType : 'application/json; charset=utf-8',
													mimeType : 'application/json',
													success : function(data) {
														var list = table.rows(
																'.selected')
																.nodes();
														bootbox.alert(jQuery.i18n.prop(data));

														$
																.each(
																		list,
																		function(
																				index,
																				element) {
																			var field = $(
																					".community",
																					element);
																			field
																					.text(tc_name);
																			$(
																					element)
																					.removeClass(
																							'selected');
																		})
														communityModal
																.modal('hide');
														return false;
													},
													error : function(xhr,
															status, error) {
														bootbox
																.alert("<h3>Error performing the Community change operation</h3>"
																		+ xhr.responseText);
														return "";
													}
												});

									});

                    function confirm_passwordreset() {
                        var json = {
                            "login" : selected.toString()
                        };

                        $
                            .ajax({
                                type : "POST",
                                url : "get-all-users/batch-password-reset",
                                dataType : "text",
                                data : JSON.stringify(json),
                                contentType : 'application/json; charset=utf-8',
                                mimeType : 'application/json',
                                success : function(data) {
									bootbox.alert(jQuery.i18n.prop(data));
                                    return false;
								},
                                error : function(xhr, status, error) {
                                    bootbox
                                        .alert("<h3>Error performing Password reset operation</h3>"
                                            + xhr.responseText);
                                    return "";
                                }
                            });
                    }

					/* **************************** */
				});
