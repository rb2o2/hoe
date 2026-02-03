package rb2o2.halls

object Utils {
  def screenXYToHexCoords(x: Int, y: Int): (Int, Int) = {
    val z = (y / (64 * Math.cos(Math.PI / 6))).toInt
    val s = (x - (z % 2) * 32) / 64
    (s, z)
  }
}
