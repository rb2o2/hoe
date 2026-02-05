package rb2o2.halls.arena

class Graph(val nodes: Seq[Hex], val edges: Seq[Edge]) {
  private val adjacencyMap: Map[Hex, Seq[(Hex, Double)]] = {
    edges.groupBy(_.from).view.mapValues(_.map(e => (e.to, e.cost))).toMap
  }
  def this(grid: HexGrid) = {
    this(
      grid.hexes,
      grid.hexes.flatMap(h =>
        grid.neighbors(h.x, h.y).map(e => Edge(h, e, 1.0))
      )
    )
  }
  def neighbors(node: Hex): Seq[(Hex, Double)] =
    adjacencyMap.getOrElse(node, Seq.empty)
}
