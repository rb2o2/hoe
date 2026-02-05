package rb2o2.halls.arena

import javax.swing.ImageIcon

trait GameObject {
  var x: Int
  var y: Int
  var passable: Boolean = true
  var sprite: ImageIcon
}
