package rb2o2.halls.arena

import javax.swing.ImageIcon

class SpruceTree1(var x: Int, var y: Int) extends GameObject {
  var sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/tree_spruce1.png")
      .readAllBytes()
  )
  passable = false
}
