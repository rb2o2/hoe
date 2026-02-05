package rb2o2.halls.objects

import rb2o2.halls.arena.{GameObject, MoveHighlight}

import javax.swing.ImageIcon

class MoveHighlight(var x: Int, var y: Int) extends GameObject {
  var sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/move.png")
      .readAllBytes()
  )

}
