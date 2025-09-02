package rb2o2.halls.gui

import rb2o2.halls.gui.{MapPanel, StatsPanel}
import rb2o2.halls.map.GameMap

import java.awt.{BorderLayout, Color, Dimension}
import javax.swing.{BorderFactory, JFrame, JPanel, WindowConstants}

object AppMain extends JFrame {
  def main(args: Array[String]): Unit = {
    val frame = new AppMain("Halls of Enlightenment v0.1.1")
    frame.setLayout(new BorderLayout())
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(new Dimension(1480, 980))
    frame.setResizable(false)
    frame.initUI()
    frame.setVisible(true)

  }
}

class AppMain(title: String) extends JFrame(title) {
  def initUI(): Unit = {
    val mapLog = new JPanel(new BorderLayout())
    mapLog.setPreferredSize(new Dimension(1000,980))
    val mapPanel = new MapPanel(initMap())
    mapPanel.setBackground(Color.BLACK)
//    mapPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN))
    mapPanel.setPreferredSize(new Dimension(1000, 880))
    val logPanel = new JPanel()
    logPanel.setBackground(Color.BLACK)
    logPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE))
    logPanel.setPreferredSize(new Dimension(1000, 100))
    mapLog.add(logPanel, BorderLayout.SOUTH)
    mapLog.add(mapPanel, BorderLayout.CENTER)
    val optionsStatsPanel = new JPanel()
    optionsStatsPanel.setLayout(new BorderLayout())
    optionsStatsPanel.setPreferredSize(new Dimension(480, 800))
    val optionsPanel = new JPanel()
    optionsPanel.setBackground(Color.BLACK)
    optionsPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN))
    optionsPanel.setPreferredSize(new Dimension(480, 600))
    val statsPanel = new StatsPanel()
    statsPanel.setBackground(Color.BLACK)
    statsPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW))
    statsPanel.setPreferredSize(new Dimension(480, 200))
    optionsStatsPanel.add(statsPanel, BorderLayout.NORTH)
    optionsStatsPanel.add(optionsPanel, BorderLayout.CENTER)
    add(mapLog, BorderLayout.CENTER)
    add(optionsStatsPanel, BorderLayout.EAST)
  }
  private def initMap(): GameMap = {
    GameMap.simple()
  }
}
