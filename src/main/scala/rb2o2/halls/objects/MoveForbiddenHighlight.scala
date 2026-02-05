package rb2o2.halls.objects

import javax.swing.ImageIcon

class MoveForbiddenHighlight(x: Int, y: Int) extends MoveHighlight(x, y) {
  sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/move_forbidden.png")
      .readAllBytes()
  )
}
