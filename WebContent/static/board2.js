
_dragElement = null

$(document).ready(
    function(){
        $('div.tile:not(td>.tile)').mousedown(function(e){
            target = thiS
            //alert(this)
            /*if( isInner ){*/
            /*target = $(e.target).parent()[0]*/
            /*}else{*/
            /*target = this*/
            /*}*/
            //conso
            /*tile = $(this).parents('div')[0]; */
            /*//var target = e.target != null ? e.target : e.srcElement;*/
            /*target = tile*/
            _startX = e.clientX;
            _startY = e.clientY;
            /*_offsetX = ExtractNumber(target.style.left);*/
            /*_offsetY = ExtractNumber(target.style.top);*/
            offset = $(target).offset()
            _offsetX = offset.left
            _offsetY = offset.top
            console.debug(_startX, _startY, _offsetX, _offsetY, offset.left, offset.top);
            _dragElement = target;
            document.onmousemove = OnMouseMove;
            document.body.focus();
            document.onselectstart = function () { return false; };
            target.ondragstart = function() { return false; }
            return false;
        })
        document.onmouseup = OnMouseUp;
    }
);


function OnMouseMove(e) {
    if (e == null) var e = window.event; 
    console.debug(_dragElement);

    var newleft = (_offsetX + e.clientX - _startX)
    var newtop =  (_offsetY + e.clientY - _startY)
    $(_dragElement).offset( {top:newtop, left:newleft}  )
    /*_dragElement.style.left = (_offsetX + e.clientX - _startX) + 'px';*/
    /*_dragElement.style.top = (_offsetY + e.clientY - _startY) + 'px';*/
}

function OnMouseUp(e){
    if (_dragElement != null){
        //_dragElement.style.zIndex = _oldZIndex;

        // we're done with these events until the next OnMouseDown
        document.onmousemove = null;
        document.onselectstart = null;
        _dragElement.ondragstart = null;
        // this is how we know we're not dragging      
        _dragElement = null;
    }
}

/*InitDragDrop(); */
/*function InitDragDrop() {*/
/*document.onmousedown = OnMouseDown;*/
    //document.onmouseup = OnMouseUp;
//}


/*function OnMouseDown(e) {*/
/*// IE is retarded and doesn't pass the event object*/
/*if (e == null) e = window.event; */
/**/
/*// IE uses srcElement, others use target*/
/*var target = e.target != null ? e.target : e.srcElement;*/
/**/
/*_debug.innerHTML = target.className == 'drag' */
/*? 'draggable element clicked' */
/*: 'NON-draggable element clicked';*/
/**/
/*// for IE, left click == 1*/
/*// for Firefox, left click == 0*/
/*if ((e.button == 1 && window.event != null || */
/*e.button == 0) && */
/*target.className == 'drag')*/
/*{*/
/*// grab the mouse position*/
/*_startX = e.clientX;*/
/*_startY = e.clientY;*/
/**/
/*// grab the clicked element's position*/
/*_offsetX = ExtractNumber(target.style.left);*/
/*_offsetY = ExtractNumber(target.style.top);*/
/**/
/*// bring the clicked element to the front while it is being dragged*/
/*_oldZIndex = target.style.zIndex;*/
/*target.style.zIndex = 10000;*/
/**/
/*// we need to access the element in OnMouseMove*/
/*_dragElement = target;*/
/**/
/*// tell our code to start moving the element with the mouse*/
/*document.onmousemove = OnMouseMove;*/
/**/
/*// cancel out any text selections*/
/*document.body.focus();*/
/**/
/*// prevent text selection in IE*/
/*document.onselectstart = function () { return false; };*/
/*// prevent IE from trying to drag an image*/
/*target.ondragstart = function() { return false; };*/
/**/
/*// prevent text selection (except IE)*/
/*return false;*/
/*}*/
/*}*/
/**/
/**/
/*function OnMouseMove(e) {*/
/*if (e == null) */
/*var e = window.event; */
/**/
/*// this is the actual "drag code"*/
/*_dragElement.style.left = (_offsetX + e.clientX - _startX) + 'px';*/
/*_dragElement.style.top = (_offsetY + e.clientY - _startY) + 'px';*/
/**/
/*_debug.innerHTML = '(' + _dragElement.style.left + ', ' + */
/*_dragElement.style.top + ')';   */
/*}*/
/**/
/*function OnMouseUp(e)*/
/*{*/
/*if (_dragElement != null)*/
/*{*/
/*_dragElement.style.zIndex = _oldZIndex;*/
/**/
/*// we're done with these events until the next OnMouseDown*/
/*document.onmousemove = null;*/
/*document.onselectstart = null;*/
/*_dragElement.ondragstart = null;*/
/**/
/*// this is how we know we're not dragging      */
/*_dragElement = null;*/
/**/
/*_debug.innerHTML = 'mouse up';*/
/*}*/
/*}*/

function ExtractNumber(value)
{
    var n = parseInt(value);

    return n == null || isNaN(n) ? 0 : n;
}

// this is simply a shortcut for the eyes and fingers
/*function $(id)*/
/*{*/
/*return document.getElementById(id);*/
/*}*/
