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
class Hex(val x: Int, val y: Int, val xd: Double, val yd: Double) {
  def this(x: Int, y: Int) = {
    this(x, y, x + 0.5 * (y % 2), y * Math.cos(Math.PI/6))
  }
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

    grid.hexes = grid.hexes.filterNot(h => h.x == 2 && h.y == 2 || h.x == 2 && h.y == 3 || h.x == 2 && h.y == 1)
    val graph = Graph(grid)
    println(AStar.findPath(graph,
      grid.hexes.find(H => H.x == 0 && H.y == 3).get,
      grid.hexes.find(H => H.x == 6 && H.y == 1).get).map(_.map(h => s"${h.x}:${h.y}")))
    println(grid.neighbors(3,2).map(h => s"${h.x}:${h.y}"))
  }
}

case class Edge(from: Hex, to: Hex, cost: Double)

class Graph(val nodes: Seq[Hex], val edges: Seq[Edge]) {
  private val adjacencyMap: Map[Hex, Seq[(Hex, Double)]] = {
    edges.groupBy(_.from).view.mapValues(_.map(e => (e.to,e.cost))).toMap
  }
  def this(grid: HexGrid) = {
    this(grid.hexes, grid.hexes.flatMap(h => grid.neighbors(h.x, h.y).map(e => Edge(h,e,1.0))))
  }
  def neighbors(node: Hex): Seq[(Hex, Double)] = adjacencyMap.getOrElse(node, Seq.empty)
}
object AStar {
  def heuristic(a: Hex, b: Hex): Double = {
    // Евклидова дистанция как эвристика
    math.sqrt(math.pow(a.xd - b.xd, 2) + math.pow(a.yd - b.yd, 2))
  }

  def findPath(graph: Graph, start: Hex, goal: Hex): Option[List[Hex]] = {
    // Очередь с приоритетом по стоимости f = g + h
    implicit val ord: Ordering[(Double, Hex)] = Ordering.by(-_._1)
    val openSet = scala.collection.mutable.PriorityQueue[(Double, Hex)]()
    openSet.enqueue((0.0 + heuristic(start, goal), start))

    val cameFrom = scala.collection.mutable.Map[Hex, Option[Hex]]()
    val gScore = scala.collection.mutable.Map[Hex, Double]().withDefaultValue(Double.PositiveInfinity)
    gScore(start) = 0.0

    while (openSet.nonEmpty) {
      val (_, current) = openSet.dequeue()

      if (current == goal) {
        // Восстановление пути
        val path = scala.collection.mutable.ListBuffer[Hex]()
        var currOpt: Option[Hex] = Some(current)
        while (currOpt.isDefined) {
          path.prepend(currOpt.get)
          currOpt = cameFrom.get(currOpt.get).flatten
        }
        return Some(path.toList)
      }

      for ((neighbor, cost) <- graph.neighbors(current)) {
        val tentativeGScore = gScore(current) + cost
        if (tentativeGScore < gScore(neighbor)) {
          cameFrom(neighbor) = Some(current)
          gScore(neighbor) = tentativeGScore
          val fScore = tentativeGScore + heuristic(neighbor, goal)
          openSet.enqueue((fScore, neighbor))
        }
      }
    }

    // Путь не найден
    None
  }
}
