package rb2o2.halls.arena

import javax.swing.ImageIcon

class Highlight(var x: Int, var y: Int) extends GameObject {
  var sprite = new ImageIcon(getClass.getClassLoader.getResourceAsStream("assets/highlight.png").readAllBytes())
}
