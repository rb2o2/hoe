package rb2o2.halls.arena

import rb2o2.halls.map.GameMap

import javax.swing.ImageIcon

class Hero(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite = new ImageIcon(getClass.getClassLoader.getResourceAsStream("assets/barbarian.png").readAllBytes())
  passable = false
  def this(x: Int, y: Int) = {
    this(x,y,GameMap.heroChar)
  }
}

class Bear(var x: Int, var y: Int, var charlist: Char) extends GameObject {
  var sprite: ImageIcon = new ImageIcon(getClass.getClassLoader.getResourceAsStream("assets/bear.png").readAllBytes())
  passable = false
}