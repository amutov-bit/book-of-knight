
client = new Client();

function Client() {
	
//	this.serverAddress = 'ws://92.247.51.140:8100';
	this.serverAddress = 'ws://10.100.1.132:8100';
//	this.serverAddress = 'ws://vm:8100';
	
	this.isConnected = false;
	this.spinEnded = false;
	
    this.connection = null;
    this.sessionId = '';
    this.token = '';
    this.gameId = 'planet-rocks';
    this.gameParams = {
    	betPerLine : 1,
	    linesSelected : 20,
	    credits : 0,
	    denom : 1
    };

	console.log('Client created');
}

Client.prototype.constructor = Client;

Client.prototype.getToken = function () {
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    
    if(typeof vars['token'] != 'undefined') {
    	this.token = decodeURIComponent(vars['token'].replace(/\+/g, ' '));
    	console.log('token: ' + this.token);
    }
    else {
    	this.token = 'demo';
    	this.gameId = 'fruity-mania-demo';
    	console.log('token: ' + this.token);
    };
}

Client.prototype.connect = function () {
	if(this.isConnected == false) {
		var _this = this;
		
		this.spinEnded = false;
		this.isLoggedIn = false;
		
		this.connection = new WebSocket(this.serverAddress);
		
		this.connection.onerror = this.onError;
		
		this.connection.addEventListener('open', function(e) { _this.onOpen(_this); });
		this.connection.addEventListener('close', function(e) { _this.onClose(_this); });
		this.connection.addEventListener('message', function(e) { _this.onMessage(e, _this); });
	} 
	else {
		console.log('client already connected');
	}
}

Client.prototype.disconnect = function () {
	
	if(this.isConnected) {
		this.connection.close();
		console.log('Client::disconnect');
	}
	else {
		console.log('client not connected');
	}
}

Client.prototype.login = function () {
	
	if(this.isConnected) {
	
		this.getToken();
	
		var message = {
				'cmd': 'login',
				'token': this.token,
				'game': this.gameId,
				'comment': 'Token auth'
				};
		this.connection.send(JSON.stringify(message));
		console.log('Client::login, token: ' + this.token);
	}
	else {
		console.log('client not connected');
	}
}

Client.prototype.getBalance = function () {
	
	if(this.isConnected) {
		var message = { 'cmd': 'balance' };
		this.connection.send(JSON.stringify(message));
		console.log('Client::getBalance');
	}
	else {
		console.log('client not connect');
	}
}

/**
 * Request server to make a spin
 */
Client.prototype.spin = function () {
	
	console.log('Client::spin');
	
	if(this.isConnected) {
		this.spinEnded = false;
		var message = { 'cmd': 'spin', 'params' : this.gameParams };
		this.connection.send(JSON.stringify(message));
	}
	else {
		console.log('client not connected');
	}
}

Client.prototype.onMessage = function (e, _this) {
	//console.dir(e);
	response = JSON.parse(e.data);
	switch(response.cmd){
		
		case 'login':
			if(response.success == true) {
				console.log('I\'m in!');
				_this.spinEnded = true;
				_this.isLoggedIn = true;
				_this.onLoginCallback(response.balance, (_this.token == "demo"? 1 : 0));
			}
			else {
				_this.isLoggedIn = false;
				_this.onErrorCallback(response.err);
			}
			break;
			
		case 'spin':
			if(response.success == true) {
				console.log('Game outcome:');
				_this.spinEnded = true;
				_this.onSpinCallback(response.outcome);
				console.dir(response.outcome);
			}
			_this.isLoggedIn = response.isLoggedIn;
			break;
			
		case 'balance':
			console.log('Balance');
			this.balance = response.balance;
			break;	
			
		default:
			console.log('unknown message, ' + e.data);
			break;
	}
}

Client.prototype.onError = function (error) {
	console.log('Error detected: ' + error);
}

Client.prototype.onClose = function(_this){
	console.log('Connection closed');
	this.isConnected = false;
	_this.onCloseCallback();
}

Client.prototype.onOpen = function(_this) {
	console.log('Connection open!');
	this.isConnected = true;
	_this.onConnectCallback();
}

//Default callbacks for game to hook up to
Client.prototype.onSpinCallback = function() {}
Client.prototype.onConnectCallback = function() {}
Client.prototype.onCloseCallback = function() {}
Client.prototype.onLoginCallback = function() {}
Client.prototype.onErrorCallback = function() {}

