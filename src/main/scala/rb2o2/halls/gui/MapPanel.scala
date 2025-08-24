package rb2o2.halls.gui

import rb2o2.halls.map.GameMap

import java.awt.{Graphics, Graphics2D, Toolkit}
import javax.swing.JPanel

class MapPanel(map: GameMap) extends JPanel {

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
        val viewCoordsx = hex.xd
        val viewCoordsy = hex.yd
        g2d.drawImage(img.getImage, viewCoordsx.toInt, viewCoordsy.toInt, this)
      }
    }

}
