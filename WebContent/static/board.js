var dragItem;
var dragOffsetX;
var dragOffsetY;
var positionsX = []
var positionsY = []

/**//**//**//**//**//**//*function resetMyTiles(){<!--{{{-->*/
/**//**//**//**//**//**//*$('#resetTiles').click(*/
/**//**//**//**//**//**//*function(){*/
/**//**//**//**//**//**//*$('.tile:not(td>.tile)').css('position','static')*/
/**//**//**//**//**//**//*}*/
/**//**//**//**//**//**//*);*/
/**//**//**//**//**//**//*}<!--}}}-->*/

/*function randomInt(min, max){*/
/*return Math.floor(Math.random() * (max - min + 1)) + min;*/
/*}*/

/*function shuffleMyTiles(){*/
/*resetMyTiles();*/
/*tiles = $('#sortable .tile').remove();*/
/*//$('#sortable .tile').remove();*/
/*while(tiles.length > 0 ){*/
/*randIndex = randomInt(0,tiles.length-1);*/
/*$('#sortable').append( tiles.splice(randIndex,1) )*/
/*}*/
/*}*/

$(document).ready(function() {
        /*$('#resetTiles').click(function(){ resetMyTiles() } );*/
        /*$('#shuffleTiles').click(function(){ shuffleMyTiles() } );*/
        document.onselectstart = function() {return false;} // ie*/
        document.onmousedown = function() {return false;} // mozilla*/
        $('.tile:not(td>.tile)').mousedown(function(e){
            e.preventDefault()
            dragItem = this;
            offset = $(this).offset()
            dragOffsetX = e.pageX - offset.left;
            dragOffsetY = e.pageY - offset.top;
            $(this).addClass('dragging')
            $(document).bind('mousemove',
                function(e){
                $(dragItem).offset( {top: e.pageY - dragOffsetY+1, left: e.pageX - dragOffsetX +1} );
                }
                )
            })

        function distance(o1, o2){
        console.debug('called')
            var x = o1.top - o2.top
            var y = o1.left - o2.left
            return Math.sqrt(x*x + y*y)
        }

        $(document).mouseup(
                function(e){
                mindist = null;
                gridspots = $('#mainboard td')
                tilePos = $(dragItem).offset()
                closest = null
                for( var g=0;g<gridspots.length;g++){
                gridspotOffset = $(gridspots[g]).offset()
                dist = distance(gridspotOffset, tilePos)
                if( mindist == null || dist < mindist ){
                mindist = dist
                closest = gridspots[g]
                }
                }
                //tablepost = $('#mainboard').offset();*/
                $(document).unbind('mousemove');
                $(dragItem).removeClass('dragging')
                if( closest != null && mindist < 40 && ! $(closest).hasClass('occupied') ){
                $(dragItem).offset({top:$(closest).offset().top, left:$(closest).offset().left}  )
                }
                }
        )
});
