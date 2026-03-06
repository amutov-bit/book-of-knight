
user = new User();

function User() {

    this.apiToken = 'a49f3cc21e25f810afaa2ed0a74a5956';
    this.gameID = '56e129e884817911ec56157d';
    this.baseUrl = 'http://52.36.74.187:3000/';
    this.cryptoKey = '';
    this.pId = '56d0c0be8481790b6d1cb4b8'; // Jeff
    this.balance = 0;
    this.isConnected = false;
    
    console.log('User created');
    
}

User.prototype.onGetBalanceCallback = function () {
	console.log('onGetBalanceCallback default callback');
	return this.balance;
}

User.prototype.onUpdateBalanceCallback = function () {
	console.log('onUpdateBalanceCallback default callback');
	return true;
}

User.prototype.getCryptoKey = function () {

	var that = this;
	this.isConnected = false;
	
	this.decrypt();
	
    $.ajax({
        type: "GET",
        url: this.baseUrl + 'login/' + this.gameID + '/' + this.apiToken + '/' + this.pId
    }).done(function (data) {
        console.log(data);
        that.cryptoKey = data;
        that.isConnected = true;
    });        

    console.log('request crypto key ');
    
}

User.prototype.getBalance = function () {
    var now = new Date().toUTCString();
    var message = now + '/balance/'+this.pId;
    var hash = CryptoJS.HmacSHA256(message, this.cryptoKey);
    var authorizationToken = this.gameID+':'+hash.toString();
    
    var that = this;
    this.isConnected = false;
    
    $.ajax({
        type: "GET",
        headers: {"Authentification": authorizationToken, 'X-Date': now},
        url: this.baseUrl + 'balance/' + this.pId
    }).done(function (data) {
        console.log(data);
        that.balance = data.balance;
        that.onGetBalanceCallback(data.balance);
        that.isConnected = true;
    });
        
}

User.prototype.updateBalance = function (delta) {
	
    console.log('delta is ' + delta);
    
    var that = this;
    
    var now = new Date().toUTCString();
    delta = Math.floor(delta);
    var bodyData = '{"delta": "' + delta + '", "event": "update_balance"}';
    console.log(bodyData);
    var balanceUrl = this.baseUrl + 'balance/' + this.pId;
    var message = now + '/balance/' + this.pId + bodyData;
    var hash = CryptoJS.HmacSHA256(message, this.cryptoKey);
    var authorizationToken = this.gameID + ':' + hash.toString();

    $.ajax({
        type: "PUT",
        headers: {"Authentification": authorizationToken, 'X-Date': now},
        url: this.baseUrl + 'balance/' + this.pId ,
        data: bodyData
    }).done(function (data) {
        console.log(data);
        that.balance = data.balance;
        that.onUpdateBalanceCallback(data.balance);
    });

}

User.prototype.decrypt = function () {
	
	var token = getUrlVars()["token"];
	//console.log('token ' + token);
	
	if(typeof token == 'undefined') return;
	
	token = urldecode(token);
	var iv = token.substr(0, 24);
	var crypted = token.substr(24, token.length - 24);
	
    var keyBase64 = CryptoJS.enc.Base64.parse(this.apiToken);
	var ivBase64 = CryptoJS.enc.Base64.parse(iv);
	
    //console.log('decrypt: ' + crypted + ', ' + keyBase64 + ', ' + iv);
	    
	var plain = CryptoJS.AES.decrypt(crypted, keyBase64, {iv: ivBase64}).toString(CryptoJS.enc.Utf8);
	var parts = plain.split(':');
	this.pId = parts[0];
	this.cryptoKey = parts[1];
	//console.log(plain);
	//console.log('cryptoKey: ' + this.cryptoKey);
	//console.log('playerID: ' + this.pId);
	
}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function urldecode(url) {
	return decodeURIComponent(url.replace(/\+/g, ' '));
}
