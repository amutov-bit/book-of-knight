/**
 * Export console.log to provide uniform debug log output
 */

let PRINT_DATE = false;

let DEBUG_LEVEL = 1; // prints all if 1
let DEBUG_LEVELS = {
	'debug': 1,
	'warning': 2,
	'info' : 3,
	'error' : 4
};

export function log(text, debugLevel) {
	
	if(typeof DEBUG_LEVELS[debugLevel] != 'undefined') {
		if(DEBUG_LEVELS[debugLevel] < DEBUG_LEVEL) return;
	}
	
	if (typeof text === 'string' || text instanceof String) {
	
		var printText = '';
		
		if(PRINT_DATE) printText += (new Date()) + ' ';
		printText += text;
		
		console.log( printText );
	}
	else {
		console.dir(text);
	}
}