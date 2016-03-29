jQuery(document).ready(function($) {
	
	$('#w-input-search').autocomplete({
		serviceUrl: 'decs',
		paramName: "descTag",
		minChars: 2,
		autoSelectFirst: true,
		deferRequestBy: 200
	});
	
} );
