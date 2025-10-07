package rb2o2.halls.arena

import javax.swing.ImageIcon

class Hero(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite = new ImageIcon(getClass.getClassLoader.getResourceAsStream("assets/barbarian.png").readAllBytes())
  passable = false
}

class Bear(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite: ImageIcon = new ImageIcon(getClass.getClassLoader.getResourceAsStream("assets/bear.png").readAllBytes())
  passable = false
}