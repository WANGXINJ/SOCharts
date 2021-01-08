// Define the namespace
var sochartLib = sochartLib || {};

sochartLib.SOChart = function (element) {
	element.innerHTML = "<div id='' class='sochart' " + 
		"style='min-width:5vw;max-width:6000px;width:33vw;min-height:5vh;max-height:6000px;height:33vh;'>" + 
		"</div>";

	this.updateChart = function(options) {
//		console.warn(options);
		
		if(!this.chart || this.chart == null) {
			this.chart = echarts.init(this.rootElement());
		}

		this.chart.setOption(JSON.parse(options));
	}

	this.rootElement = function () {
		return element.getElementsByClassName("sochart")[0];
	}
};