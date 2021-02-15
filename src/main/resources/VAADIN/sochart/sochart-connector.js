window.com_storedobject_chart_SOChart = function() {
	// Create the component
	var sochart = new sochartLib.SOChart(this);

	// Handle changes from the server-side
	this.onStateChange = function() {
		sochart.updateChart();
	};
};