from java.util import ArrayList
from skrib.util import TrieNode
tn = TrieNode.loadDictionary("/home/mike/WORD.LST");

#TODO fix test
gd = injector.getInstance(GameDAO)
g = gd.createNewGame(None,2)
#g2 = gd.get(g.getId())
tp = ArrayList()
tp.add( TilePlacement(Tiles.LetterTile.A,GridPosition(7,7) ))
tp.add( TilePlacement(Tiles.LetterTile.S,GridPosition(7,8) ))
tp.add( TilePlacement(Tiles.LetterTile.S,GridPosition(7,9) ))
m = Move(tp)
#print "turn number: ", g.getTurnNumber(), "player turn: ", g.getNextTurnPlayerNum()
#print "making move"
g.makeMove(m, tn)
gd.postMoveUpdate(g)
#print g.getBoardState().toString()
g2 = gd.get(g.getId())
print "turn number: ", g2.getTurnNumber(), "player turn: ", g2.getNextTurnPlayerNum()

tp2 = ArrayList()
tp2.add( TilePlacement(Tiles.LetterTile.N,GridPosition(8,7) ))
tp2.add( TilePlacement(Tiles.LetterTile.U,GridPosition(9,7) ))
tp2.add( TilePlacement(Tiles.LetterTile.S,GridPosition(10,7) ))
m2 = Move(tp2)
g.makeMove(m2, tn)
gd.postMoveUpdate(g)
print g.getBoardState().toString()

