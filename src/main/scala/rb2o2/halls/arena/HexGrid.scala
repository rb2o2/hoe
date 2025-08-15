package rb2o2.halls.arena

import scala.collection.mutable.ListBuffer

class HexGrid {
  var hexes: List[Hex] = List.empty[Hex]
  def neighbors(x: Int, y: Int): List[Hex] = hexes.find(h => h.x == x && h.y == y)
    .map(h1 => hexes.filter(hs => h1.neighbors.exists(n => n._1 == hs.x && n._2 == hs.y))).getOrElse(List())

}

trait GameObject {
  var x: Int
  var y: Int
  var passable: Boolean = true
}

/**
 * hex is upright i.e. point up, connected horizontally.
 * */
class Hex(val x: Int, val y: Int) {
  def neighbors: List[(Int, Int)] = {
    val parity = y % 2
    List((x-1, y), (x+parity-1, y+1), (x+parity, y+1), (x+1, y), (x+parity, y-1), (x+parity-1, y-1))
  }
  private val contents: collection.mutable.ListBuffer[GameObject] = new ListBuffer[GameObject]()
  def addContent(content: GameObject): Unit = {
    if (content.x == x && content.y == y) contents += content
  }
}


object HexGrid {
  def main(args: Array[String]): Unit = {
    val grid = new HexGrid()
    for {h <- 0.to(6)
         w <- 0.to(8)}
      grid.hexes = grid.hexes :+ new Hex(w, h)

    println(grid.neighbors(3,2).map(h => s"${h.x}:${h.y}"))
  }
}
