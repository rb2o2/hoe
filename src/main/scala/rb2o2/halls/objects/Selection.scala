package rb2o2.halls.objects

import rb2o2.halls.arena.GameObject

import javax.swing.ImageIcon

class Selection(val x: Int, val y: Int) extends GameObject {
  var sprite: ImageIcon = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/selection.png")
      .readAllBytes()
  )
}
