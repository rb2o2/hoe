package rb2o2.halls.objects

import rb2o2.halls.arena.GameObject

import javax.swing.ImageIcon

class Field(val x: Int, val y: Int) extends GameObject {
  var sprite = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/hex_field.png")
      .readAllBytes()
  )

}
