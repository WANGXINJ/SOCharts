// Define the namespace
var sochartLib = sochartLib || {};

sochartLib.SOChart = function (element) {
	element.innerHTML = "<div id='' class='sochart' " + 
		"style='min-width:5vw;max-width:6000px;width:33vw;min-height:5vh;max-height:6000px;height:33vh;'>" + 
		"</div>";

	this.updateChart = function(option) {
		if(!this.chart || this.chart == null) {
			this.chart = echarts.init(this.rootElement());
		}

		var jsOption = parseOption(option);
//		console.log(jsOption);
		eval(jsOption);
		this.chart.setOption(option);
	}

	this.rootElement = function () {
		return element.getElementsByClassName("sochart")[0];
	}
	
	function parseOption(option) {
		var json = JSON.parse(option);
		var keyVarMap = new Map();
		var js = "";
		for (var key in json) {
			var functionPrefix = "@function@";
			if (!key.startsWith(functionPrefix))
				continue;

			var varName = key.substring(functionPrefix.length) + "Function";
			js += "var " + varName + " = " + json[key] + ";\n";
			keyVarMap.set(key, varName);
			delete json[key];
		}

		option = JSON.stringify(json);
		keyVarMap.forEach(function(varName, key) {
			option = option.replace("\"" + key + "\"", varName);
		});

		return js + "\n" + "option = " + option;
	}
};