package rb2o2.halls.map

import rb2o2.halls.arena.{Hex, HexGrid, Field}

class GameMap(val w: Int, val h: Int) {
  var grid = new HexGrid()

}
object GameMap {
  def simple(): GameMap = {
    val map = new GameMap(15, 15)
    map.grid.hexes = (for {i <- -1.until(map.w+1)
                           j <- -1.until(map.h+1)} yield
      {val h = new Hex(i, j)
      h.addContent(new Field(i, j))
      h}
    ).toList
    map
  }
}
