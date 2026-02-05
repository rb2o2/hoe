package rb2o2.halls.arena

import scala.collection.mutable.ListBuffer

class Hex(val x: Int, val y: Int, val xd: Double, val yd: Double) {
  def this(x: Int, y: Int) = {
    this(
      x,
      y,
      64 * (x + 0.5 * (y % 2)) + 32,
      64 * (y * Math.cos(Math.PI / 6)) + 37
    )
  }
  def neighbors: List[(Int, Int)] = {
    val parity = Math.abs(y) % 2
    List(
      (x - 1, y),
      (x + parity - 1, y + 1),
      (x + parity, y + 1),
      (x + 1, y),
      (x + parity, y - 1),
      (x + parity - 1, y - 1)
    )
  }
  val contents: collection.mutable.ListBuffer[GameObject] =
    new ListBuffer[GameObject]()
  def addContent(content: GameObject): Unit = {
    if (content.x == x && content.y == y) contents += content
  }
}
