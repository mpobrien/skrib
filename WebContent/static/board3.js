$(document).ready(
    function(){
        console.debug('yo')
        //$("#mytiles").sortable();
        //$('#mytiles').draggable( {connectToSortable: '#mytiles', snap:'.DOUBLE_WORD'})
        //$('#mytiles').draggable( {connectToSortable: '#mytiles', snap:'.DOUBLE_WORD'})
        $('div.tile:not(td>.tile)').draggable( {connectToSortable: '#mytiles', snap:'.DOUBLE_WORD', snapMode:'inner'})
        $('#mytiles').sortable()
        //$('#mytiles .tile').draggable( {snap:'td', snapMode:'outer'})
        //$('div.tile:not(td>.tile)').draggable()
    })
