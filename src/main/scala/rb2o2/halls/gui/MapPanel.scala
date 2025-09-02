package rb2o2.halls.gui

import rb2o2.halls.Utils
import rb2o2.halls.arena.{Graph, Hero, Hex, HexGrid, Highlight, MoveForbiddenHighlight, MoveHighlight, Selection}
import rb2o2.halls.map.{AStar, GameMap}

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{Graphics, Graphics2D, Toolkit}
import javax.swing.JPanel

class MapPanel(map: GameMap) extends JPanel {
  private def removeHighlights(): Unit = {
    map.grid.hexes.filter(hex => hex.contents.exists(_.isInstanceOf[Highlight]))
      .map(h => h.contents.remove(h.contents.indexOf(h.contents.find(_.isInstanceOf[Highlight]).get)))
  }
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
          AStar.findPath(graph,
              map.grid.hexes.find(_.contents.indexOf(hero) != -1).get,
              map.grid.hexes.find(hex => hex.x == sz._1 && hex.y == sz._2).get
          ).map(list =>
            for {(h,ind) <- list.drop(1).zipWithIndex}
              h.addContent(
                if (ind < hero.charlist.bm)
                  new MoveHighlight(h.x, h.y)
                else
                  new MoveForbiddenHighlight(h.x, h.y)))
      )
      repaint()
    }
  })
  addMouseListener(new MouseAdapter {
    override def mousePressed(e: MouseEvent): Unit = {
      def select(heroClicked: Hero) : Unit = {
        map.selected = Some(heroClicked)
        removeHighlights()
        map.selected match {
          case None => ()
          case Some(hero) => map.grid.hexes.find(_.contents.contains(hero))
            .foreach(h => h.contents.insert(h.contents.indexOf(hero), new Highlight(h.x, h.y)))
        }
      }
      def moveHero(hero: Hero, hex: Hex): Unit = {
        val gridPass = new HexGrid()
        gridPass.hexes = map.grid.hexes.filterNot(_.contents.exists(c => !c.passable && !c.isInstanceOf[Hero]))
        val graph = Graph(gridPass)
        val path = AStar.findPath(graph,
          map.grid.hexes.find(_.contents.indexOf(hero) != -1).get,
          map.grid.hexes.find(h => h.x == hex.x && h.y == hex.y).get
        )
        removeHighlights()
        path.foreach(p => {
          for {(h,ind) <- p.zipWithIndex
               if ind < hero.charlist.bm + 1} {
            map.grid.hexes.find(_.contents.indexOf(hero) != -1)
              .foreach(hex => hex.contents.remove(hex.contents.indexOf(hero)))
            hero.x = h.x
            hero.y = h.y
            map.grid.hexes.find(_ == h)
              .foreach(hex => hex.addContent(hero))
            Thread.sleep(200)
            update(getGraphics)
            getComponents.foreach(c=>c.update(c.getGraphics))
          }
        })
      }
      val sz = Utils.screenXYToHexCoords(e.getX, e.getY)
      println(s"${sz._1}, ${sz._2}")
      val hexClicked = map.grid.hexes.find(h => h.x == sz._1 && h.y == sz._2).get
      val heroClicked: Option[Hero] = hexClicked.contents.find(_.isInstanceOf[Hero]).map(_.asInstanceOf[Hero])
      map.selected match {
        case Some(hero) => heroClicked match {
          case None => moveHero(hero, hexClicked)
          case _ => select(hero)
        }
        case None => heroClicked match {
          case None => ()
          case Some(hero) =>
            select(hero)
        }
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
      for (cont <- hexContents) {
        val img = cont.sprite
        val viewCoordsX = hex.xd - 32
        val viewCoordsY = hex.yd - 37
        g2d.drawImage(img.getImage, viewCoordsX.toInt, viewCoordsY.toInt, this)
      }
    }
}
