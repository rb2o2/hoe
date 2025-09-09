package rb2o2.halls.arena

import javax.swing.ImageIcon

class Hero(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite = new ImageIcon("src/main/resources/assets/barbarian.png")
  passable = false
}

class Bear(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite: ImageIcon = new ImageIcon("src/main/resources/assets/bear.png")
  passable = false
}