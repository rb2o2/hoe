package rb2o2.halls.arena
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon

class SlashEffect(var x: Int, var y: Int) extends GameObject {
  var sprite: ImageIcon = new ImageIcon("src/main/resources/assets/slash2.gif")
}
