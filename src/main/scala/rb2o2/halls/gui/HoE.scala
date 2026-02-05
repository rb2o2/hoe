package rb2o2.halls.gui

import rb2o2.halls.gui.{MapPanel, StatsPanel}
import rb2o2.halls.map.GameMap

import java.awt.{BorderLayout, Color, Dimension}
import javax.swing.{BorderFactory, JFrame, JPanel, WindowConstants}

object HoE {
  def main(args: Array[String]): Unit = {
    val frame = new HoEFrame("Halls of Enlightenment v0.1.1")
    frame.setLayout(new BorderLayout())
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(new Dimension(1480, 980))
    frame.setResizable(false)
    frame.initUI()
    frame.setVisible(true)

  }
}
