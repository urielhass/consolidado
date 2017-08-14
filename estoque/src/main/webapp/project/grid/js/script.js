(function(){
	'user strict';
	
	angular.module("grid",["gridster"]);
	
	angular.module("grid").controller("gridController",Controller);
	
	function Controller(){
		var self = this;
		
		/*self.standardItems = [
            {size: {x: 2, y: 1}, position: [0, 0]},
            {size: {x: 2, y: 2}, position: [0, 2]},
            {size: {x: 1, y: 1}, position: [0, 4]},
            {size: {x: 1, y: 1}, position: [0, 5]},
            {size: {x: 2, y: 1}, position: [1, 0]},
            {size: {x: 1, y: 1}, position: [1, 4]},
            {size: {x: 1, y: 2}, position: [1, 5]},
            {size: {x: 1, y: 1}, position: [2, 0]},
            {size: {x: 2, y: 1}, position: [2, 1]},
            {size: {x: 1, y: 1}, position: [2, 3]},
            {size: {x: 1, y: 1}, position: [2, 4]}
        ];*/
		self.standardItems = [
		                      {row:0,col:0},
		                      {row:0,col:1},
		                      {row:0,col:2},
		                      {row:0,col:3},
		                      {row:0,col:4}
		];
		self.gridsterOpts = {
            minRows: 2, // the minimum height of the grid, in rows
            maxRows: 100,
            columns: 6, // the width of the grid, in columns
            colWidth: 'auto', // can be an integer or 'auto'.  'auto' uses the pixel width of the element divided by 'columns'
            rowHeight: 'match', // can be an integer or 'match'.  Match uses the colWidth, giving you square widgets.
            margins: [10, 10], // the pixel distance between each widget
            defaultSizeX: 1, // the default width of a gridster item, if not specifed
            defaultSizeY: 1, // the default height of a gridster item, if not specified
            minSizeX: 1, // minimum column width of an item
            maxSizeX: 1, // maximum column width of an item
            minSizeY: 1, // minumum row height of an item
            maxSizeY: 1, // maximum row height of an item
            mobileBreakPoint: 600, // if the screen is not wider that this, remove the grid layout and stack the items
            resizable: {
                enabled: true,
                start: function (event, uiWidget, $element) {
                }, // optional callback fired when resize is started,
                resize: function (event, uiWidget, $element) {
                }, // optional callback fired when item is resized,
                stop: function (event, uiWidget, $element) {
                } // optional callback fired when item is finished resizing
            },
            draggable: {
                enabled: true, // whether dragging items is supported
                handle: '.ddd', // optional selector for resize handle
                start: function (event, uiWidget, $element) {
                }, // optional callback fired when drag is started,
                drag: function (event, uiWidget, $element) {
                }, // optional callback fired when item is moved,
                stop: function (event, uiWidget, $element) {
                } // optional callback fired when item is finished dragging
            }
        };
	}
})();