package rb2o2.halls.objects

import rb2o2.halls.arena.{Char, GameObject}
import rb2o2.halls.map.GameMap

import javax.swing.ImageIcon

class Hero(var x: Int, var y: Int, var charlist: Char)
    extends Creature(charlist) {
  var sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/barbarian.png")
      .readAllBytes()
  )
  passable = false
  def this(x: Int, y: Int) = {
    this(x, y, GameMap.heroChar)
  }
}
