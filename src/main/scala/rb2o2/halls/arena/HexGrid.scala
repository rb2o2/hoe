package rb2o2.halls.arena

import rb2o2.halls.map.AStar

import javax.swing.ImageIcon
import scala.collection.mutable.ListBuffer

class HexGrid {
  var hexes: List[Hex] = List.empty[Hex]
  def neighbors(x: Int, y: Int): List[Hex] = hexes
    .find(h => h.x == x && h.y == y)
    .map(h1 =>
      hexes.filter(hs => h1.neighbors.exists(n => n._1 == hs.x && n._2 == hs.y))
    )
    .getOrElse(List())

}

/** hex is upright i.e. point up, connected horizontally.
  */

object HexGrid {
  def main(args: Array[String]): Unit = {
    val grid = new HexGrid()
    for {
      h <- 0.to(6)
      w <- 0.to(8)
    }
      grid.hexes = grid.hexes :+ new Hex(w, h)

    grid.hexes = grid.hexes.filterNot(h =>
      h.x == 2 && h.y == 2 || h.x == 2 && h.y == 3 || h.x == 2 && h.y == 1
    )
    val graph = Graph(grid)
    println(
      AStar
        .findPath(
          graph,
          grid.hexes.find(H => H.x == 0 && H.y == 3).get,
          grid.hexes.find(H => H.x == 6 && H.y == 1).get
        )
        .map(_.map(h => s"${h.x}:${h.y}"))
    )
    println(grid.neighbors(3, 2).map(h => s"${h.x}:${h.y}"))
  }
}
