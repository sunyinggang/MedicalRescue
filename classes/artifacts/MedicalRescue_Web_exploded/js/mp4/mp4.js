function mp4Popup(src){
	this._width = 400;
	this._height = 225;
	this._src = src;
	this._zoom = arguments[1] === undefined ? 1: arguments[1]
	
	var div = this._div = document.createElement("div");
	div.style.position = "absolute";
	div.style.zIndex = 1001;
	div.style.border = "1px solid #BC3B3A";
	div.style.width = this._width * this._zoom + 'px';
	div.style.height = this._height * this._zoom + 'px';
	div.style.top = '50%';
	div.style.left = '60%';
	div.style.marginLeft = -(this._width * this._zoom)/2 + 'px';
	div.style.marginTop = -(this._height * this._zoom)/2 + 'px';
	
	var videoE = document.createElement("video");
	videoE.autoplay = "autoplay";
	var source = document.createElement("source");
	source.src = this._src
	source.type = "video/mp4";
	videoE.style.width = this._width * this._zoom + 'px';
	videoE.style.height = this._height * this._zoom + 'px';
	videoE.appendChild(source);
	var that = this
	videoE.addEventListener("ended",function(){
		that.hide()
	})
	div.appendChild(videoE)
	document.body.appendChild(div)
}
mp4Popup.prototype.hide = function(){
	this._div.style.display = 'none';
}
function mp4Overlay(_point,src,y){
	this._width = 400
	this._height = 255
	this._src = src
	this._point = _point
	this._zoom = arguments[3] === undefined ? 1: arguments[3]
	this._y = (y === false) ? -((this._height*this._zoom)/2) : y
}

mp4Overlay.prototype = new BMap.Overlay();

mp4Overlay.prototype.initialize = function(tmap){
	this._map = tmap;
	var div = this._div = document.createElement("div");
	div.style.position = "absolute";
	div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
	div.style.border = "1px solid #BC3B3A";
	div.style.width = 400 * this._zoom + 'px';
	div.style.height = 225 * this._zoom + 'px';
	
	var videoE = document.createElement("video");
	videoE.autoplay = "autoplay";
	var source = document.createElement("source");
	source.src = this._src
	source.type = "video/mp4";
	videoE.style.width = 400 * this._zoom + 'px';
	videoE.style.height = 225 * this._zoom + 'px';
	videoE.appendChild(source);
	var that = this
	videoE.addEventListener("ended",function(){
		that.hide()
	})
	div.appendChild(videoE)
	map.getPanes().labelPane.appendChild(div);
	return div;
}
mp4Overlay.prototype.draw = function(){
	var map = this._map;
	var pixel = map.pointToOverlayPixel(this._point);
	this._div.style.left = pixel.x - ((400 * this._zoom)/2) + "px";
	this._div.style.top  = pixel.y + 10 + this._y + "px";
}
