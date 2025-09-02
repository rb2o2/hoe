package rb2o2.halls.arena

import javax.swing.ImageIcon

class MoveHighlight(var x: Int, var y: Int) extends GameObject {
  var sprite = new ImageIcon("src/main/resources/assets/move.png")

}
class MoveForbiddenHighlight(x: Int, y: Int) extends MoveHighlight(x, y) {
  sprite = new ImageIcon("src/main/resources/assets/move_forbidden.png")
}