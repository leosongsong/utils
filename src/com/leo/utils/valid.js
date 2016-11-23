		$("#formID").find('input:text, textarea').filter('[data-validate-rule]').not(":submit, :reset, :image, [disabled]")
		.each(function(i,elem) {
			 var rulesParsing = $(elem).attr("data-validate-rule");
			 var getRules = /validate\[(.*)\]/.exec(rulesParsing);
			 if (!getRules)	return false;
			 var str = getRules[1];
			 var rules = str.split(/\[|,|\]/);
			 for (var i = 0; i < rules.length; i++) {
				rules[i] = rules[i].replace(" ", "");
				
				// Remove any parsing errors
				if (rules[i] === '') {
					delete rules[i];
				}
			}			 
			 			 
			 var imemode = ["min", "max", "integer"];
			 
			 for (var i = 0; i < imemode.length; i++) {
					if($.inArray(imemode[i], rules) != -1){
						$(elem).css({'-webkit-ime-mode':'disabled', '-moz-ime-mode':'disabled', '-ms-ime-mode':'disabled', 'ime-mode':'disabled'});
						$(elem).bind('keydown', validation.onlyNumber);
						break;
					}
			 }			 
		});