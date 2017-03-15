
	/**
	단위 가격 처리(원단위는 모두 0으로 처리)
	원단위 unit = 10, 십단위 unit = 100
	*/
	var wonCalc = function(price, saleRate){
		var unit = 10;
		var result = 0;
		
		if(Number(saleRate) >= 1) return price;
		
		var salePrice = price * saleRate;
		var temp = price - salePrice;
		result = Math.floor( temp / unit ) * unit;
		return result;
	}
		
	/*
	true : "", null, undefined, [], {}
	*/
	var isEmpty = function(value){
		if(value == "" || value == null || value == undefined || (typeof(value) == "object" && !Object.keys(value).length)){
			return true;
		}else{
			return false;
		}
	};

	
