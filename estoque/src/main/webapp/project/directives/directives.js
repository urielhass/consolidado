var compareTo = function() {
  return {
    require: "ngModel",
    scope: {
      otherModelValue: "=compareTo"
    },
    link: function(scope, element, attributes, ngModel) {

      ngModel.$validators.compareTo = function(modelValue) {
        return modelValue == scope.otherModelValue;
      };

      scope.$watch("otherModelValue", function() {
        ngModel.$validate();
      });
    }
  };
};

project.directive("compareTo", compareTo);

project.directive("dateFormat",function(){
	return {
		restrict: "A",
		require: "ngModel",
		link: function(scope, element, attrs, ngModel){
			var dateViewFormat = /(\d{2})\/(\d{2})\/(\d{4})/;
			var dateModelFormat = /(\d{4})-(\d{2})-(\d{2})/;
			
			ngModel.$formatters.push(formatter);
			ngModel.$parsers.push(parser);
			
			function formatter(value){
				if(dateModelFormat.test(value)){
					return value.replace(dateModelFormat,"$3/$2/$1");
				}
				
				return value;
			}
			
			function parser(value){
				if(dateViewFormat.test(value)){
					return value.replace(dateViewFormat,"$3-$2-$1T00:00:00Z");
				}
				
				return null;
			}
			
			element.on("keyup",function(e){
				//if(e.which >= 48 && e.which <= 57){
					var value = e.target.value;
					value = value.replace(/\D/g,"");
					value = value.replace(/(\d{2})(\d+)/g,"$1/$2");
					value = value.replace(/(\d{2})\/(\d{2})(\d+)/g,"$1/$2/$3");
					value = value.replace(/(\d{2})\/(\d{2})\/(\d{4}).*/g,"$1/$2/$3");
					e.target.value = value;
					ngModel.$setViewValue(value);
				//}
			});
		}
	};
});