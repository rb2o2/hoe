package rb2o2.halls.objects

import rb2o2.halls.arena.{Char, GameObject}

import javax.swing.ImageIcon

class Bear(var x: Int, var y: Int, var charlist: Char)
    extends Creature(charlist) {
  var sprite: ImageIcon = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/bear.png")
      .readAllBytes()
  )
  passable = false
}
