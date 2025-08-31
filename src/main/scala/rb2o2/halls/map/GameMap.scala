package rb2o2.halls.map

import rb2o2.halls.arena.{Field, Hero, Hex, HexGrid, SpruceTree1}

class GameMap(val w: Int, val h: Int) {
  var grid = new HexGrid()
  var selected: Option[Hero] = None
}
object GameMap {
  def simple(): GameMap = {
    val map = new GameMap(15, 15)
    map.grid.hexes = (for {i <- -1.until(map.w+1)
                           j <- -1.until(map.h+1)} yield
      {
        val h = new Hex(i, j)
        h.addContent(new Field(i, j))
        if (i == 7 && j == 7) h.addContent(new Hero(i, j))
        if (i == 3 && j == 9 || i == 4 && j == 9) h.addContent(new SpruceTree1(i,j))
        h
      }
    ).toList
    map
  }

}
