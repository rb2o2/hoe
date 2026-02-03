package rb2o2.halls.map

import rb2o2.halls.arena.{Graph, Hex}

object AStar {
  private def heuristic(a: Hex, b: Hex): Double = {
    // Euclidean distance
    0.01 * math.sqrt(math.pow(a.xd - b.xd, 2) + math.pow(a.yd - b.yd, 2))
  }

  def findPath(graph: Graph, start: Hex, goal: Hex): Option[List[Hex]] = {
    // Priority queue by  f = g + h
    implicit val ord: Ordering[(Double, Hex)] = Ordering.by(-_._1)
    val openSet = scala.collection.mutable.PriorityQueue[(Double, Hex)]()
    openSet.enqueue((0.0 + heuristic(start, goal), start))

    val cameFrom = scala.collection.mutable.Map[Hex, Option[Hex]]()
    val gScore = scala.collection.mutable
      .Map[Hex, Double]()
      .withDefaultValue(Double.PositiveInfinity)
    gScore(start) = 0.0

    while (openSet.nonEmpty) {
      val (_, current) = openSet.dequeue()

      if (current == goal) {
        // Path restoring
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
    // Path not found
    None
  }
}
