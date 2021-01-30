// Define the namespace
var sochartLib = sochartLib || {};

var themeEC4 = {
	color: [
		'#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83',
		'#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'
	]
};

sochartLib.SOChart = function(connector) {
	element = connector.getElement();
	element.innerHTML = "<div id='' class='sochart' " +
		"style='min-width:5vw;max-width:6000px;width:33vw;min-height:5vh;max-height:6000px;height:33vh;'>" +
		"</div>";

	this.rootElement = function() {
		return element.getElementsByClassName("sochart")[0];
	}

	this.updateChart = function(option) {
		if (!this.chart || this.chart == null) {
			this.chart = echarts.init(this.rootElement(), themeEC4);
			this.chart.on('click', function(params) {
				connector.onClick(toEventData(params));
			});
		}

		var jsOption = parseOption(option);
		//		console.log(jsOption);
		eval(jsOption);
		this.chart.setOption(option);
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

	function toEventData(params) {
		var eventData = {};
		var keys = ['componentType', 'seriesType', 'seriesIndex', 'seriesName', 'name', 'dataIndex', 'data', 'dataType', 'value', 'color'];
		for (var key in params) {
			if (keys.indexOf(key) >= 0) {
				eventData[key] = params[key];
			}
		}

		return eventData;
	}
};