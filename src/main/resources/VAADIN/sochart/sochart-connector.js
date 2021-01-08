window.com_storedobject_chart_SOChart = function() {
    // Create the component
	var sochart = new sochartLib.SOChart(this.getElement());

	// Handle changes from the server-side
	this.onStateChange = function() {
		var state = this.getState();
		var rootElement = sochart.rootElement();
		var rootStyle = rootElement.style;

		if (state.width && state.width.length > 0) {
			rootStyle.width = state.width;
		}
		if (state.height && state.height.length > 0) {
			rootStyle.height = state.height;
		}
		if (state.properties) {
			var properties = state.properties;
			rootElement.id = properties.idChart;

			if (properties.minw){
				rootStyle.minWidth = properties.minw;
			}
			if (properties.maxw){
				rootStyle.maxWidth = properties.maxw;
			}
			if (properties.minh){
				rootStyle.minHeight = properties.minh;
			}
			if (properties.maxh){
				rootStyle.maxHeight = properties.maxh;
			}
		}
		
		if (state.options && state.options.length > 0) {
			sochart.updateChart(state.options);
		}
		
	};
};