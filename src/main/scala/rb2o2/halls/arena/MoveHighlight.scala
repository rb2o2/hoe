package rb2o2.halls.arena

import javax.swing.ImageIcon

class MoveHighlight(var x: Int, var y: Int) extends GameObject {
  var sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/move.png")
      .readAllBytes()
  )

}
class MoveForbiddenHighlight(x: Int, y: Int) extends MoveHighlight(x, y) {
  sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/move_forbidden.png")
      .readAllBytes()
  )
}
