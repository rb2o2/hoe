package rb2o2.halls.gui

import rb2o2.halls.Utils
import rb2o2.halls.arena.{Hero, Graph, HexGrid, Highlight, MoveHighlight, Selection}
import rb2o2.halls.map.{AStar, GameMap}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Graphics, Graphics2D, Toolkit}
import javax.swing.JPanel

class MapPanel(map: GameMap) extends JPanel {
  addMouseMotionListener(new MouseAdapter {
    override def mouseMoved(e: MouseEvent): Unit = {
      val mx = e.getX
      val my = e.getY
//      println(s"$mx : $my")
      val sz = Utils.screenXYToHexCoords(mx, my)
      map.grid.hexes.filter(hex => hex.contents.exists(_.isInstanceOf[Selection]))
        .map(h => h.contents.remove(h.contents.indexOf(h.contents.find(_.isInstanceOf[Selection]).get)))
      map.grid.hexes.find(hex => hex.x == sz._1 && hex.y == sz._2)
        .foreach(h => h.addContent(new Selection(h.x,h.y)))
      map.grid.hexes.filter(hex => hex.contents.exists(_.isInstanceOf[MoveHighlight]))
        .foreach(h => h.contents.filter(_.isInstanceOf[MoveHighlight])
          .foreach(c => h.contents.remove(h.contents.indexOf(c))))
      val gridPass = new HexGrid()
      gridPass.hexes = map.grid.hexes.filterNot(_.contents.exists(c => !c.passable && !c.isInstanceOf[Hero]))
      val graph = Graph(gridPass)


      map.selected.foreach(hero =>
        /*println(AStar.findPath(graph,
          map.grid.hexes.find(_.contents.indexOf(hero) != -1).get,
          map.grid.hexes.find(hex => hex.x == sz._1 && hex.y == sz._2).get
        ))*/
          AStar.findPath(graph,
              map.grid.hexes.find(_.contents.indexOf(hero) != -1).get,
              map.grid.hexes.find(hex => hex.x == sz._1 && hex.y == sz._2).get
          ).map(list => list.drop(1).foreach(h => h.addContent(new MoveHighlight(h.x, h.y)))))

//      println(map.grid.hexes.find(hex => hex.x == sz._1 && hex.y == sz._2).get.contents)

      repaint()
    }
  })
  addMouseListener(new MouseAdapter {
    override def mouseClicked(e: MouseEvent): Unit = {
      val sz = Utils.screenXYToHexCoords(e.getX, e.getY)
      println(s"${sz._1}, ${sz._2}")
      val heroMaybe: Option[Hero] = map.grid.hexes.find(h => h.contents.exists(_.isInstanceOf[Hero]) && h.x == sz._1 && h.y == sz._2)
        .flatMap(_.contents.find(_.isInstanceOf[Hero])).map(_.asInstanceOf[Hero])
      // println(heroMaybe)
      map.selected = heroMaybe
      map.grid.hexes.filter(hex => hex.contents.exists(_.isInstanceOf[Highlight]))
        .map(h => h.contents.remove(h.contents.indexOf(h.contents.find(_.isInstanceOf[Highlight]).get)))
      map.selected match {
        case None => ()
        case Some(hero) => map.grid.hexes.find(_.contents.contains(hero))
          .foreach(h => h.contents.insert(h.contents.indexOf(hero), new Highlight(h.x, h.y)))
      }
      repaint()
    }
  })

  override def paintComponent(graphics: Graphics): Unit =
    super.paintComponent(graphics)
    doDrawing(graphics)
    Toolkit.getDefaultToolkit.sync()

  private def doDrawing(g: Graphics): Unit =
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    for (hex <- map.grid.hexes) {
      val hexContents = hex.contents.toArray
//      Sorting.quickSort(hexContents)(Ordering[Int].on((p: GameObject) => p.zIndex))

      for (cont <- hexContents) {
        val img = cont.sprite
        val viewCoordsX = hex.xd
        val viewCoordsY = hex.yd
        g2d.drawImage(img.getImage, viewCoordsX.toInt, viewCoordsY.toInt, this)
      }
    }

}
