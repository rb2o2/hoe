package rb2o2.halls.objects

import rb2o2.halls.arena.GameObject

import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon

class SlashEffect(var x: Int, var y: Int) extends GameObject {
  var sprite: ImageIcon = new ImageIcon(
    getClass.getClassLoader
      .getResourceAsStream("assets/slash2.gif")
      .readAllBytes()
  )
}
