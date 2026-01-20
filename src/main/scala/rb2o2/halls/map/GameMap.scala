package rb2o2.halls.map

import rb2o2.halls.arena.{Bear, Char, DamageType, Field, GameObject, Hero, Hex, HexGrid, Skill, SpruceTree1, Weapon}

import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Scanner
import scala.jdk.CollectionConverters.*

class GameMap(val w: Int, val h: Int) {
  var grid = new HexGrid()
  var selected: Option[Hero] = None
}
object GameMap {
  val heroChar: Char = Char(name = "Bob Trainerton",
    st = 12, dx = 11, iq = 10, ht = 11, hp = 12, fp = 11, wil = 10, per = 10, bs = 5.5, bm = 6, dr = 2, damageSw = (1, 1), damageThr = (1, -1),
    weapons = List(Weapon(
      name = "Axe",
      swDmgType = Some(DamageType.Cutting),
      thrDmgType = None,
      swMod = Some(4),
      thrMod = None,
      skill = Skill(name = "Axe", relativeLevel = 2),
      ranged = false),
      Weapon("Mace", Some(DamageType.Crushing), None, Some(3), None, Skill("Mace", 2), false),
      Weapon("Fists", None, Some(DamageType.Crushing), None, Some(0), Skill("Brawling", 2), false),
      Weapon("Bow", None, Some(DamageType.Piercing), None, Some(1), Skill("Bow", 2), true)
    ),
    spells = List()
  )
  def simple(): GameMap = {
    val bearChar = Char("Brown Bear",
      18, 11, 6, 11, 20, 11, 9, 11, 5.5, 6, 1, (3, 0), (1, 2),
      List(Weapon("Claws", Some(DamageType.Piercing), None, Some(1), None, Skill("Brawling", 1), false),
        Weapon("Bite", Some(DamageType.Piercing), None, Some(1), None, Skill("Brawling", 1), false)),
      List()
    )
    val map = new GameMap(15, 15)
    map.grid.hexes = (for {i <- -1.until(map.w+1)
                           j <- -1.until(map.h+1)} yield
      {
        val h = new Hex(i, j)
        h.addContent(new Field(i, j))
        if (i == 7 && j == 7) h.addContent(new Hero(i, j, heroChar))
        if (i == 3 && j == 9 || i == 4 && j == 9) h.addContent(new SpruceTree1(i,j))
        if (i == 12 && j == 11) h.addContent(new Bear(i,j, bearChar))
        h
      }
    ).toList
    map
  }
  def load(dataStream: InputStream): GameMap = {
    val levelString = new String(dataStream.readAllBytes(), StandardCharsets.UTF_8)
    val scanner = new Scanner(levelString)
    val w = scanner.nextInt()
    val h = scanner.nextInt()
    val symbols: Map[(Int,Int), String] = (
      for {
        lines <- levelString.lines().toList.asScala.slice(1, h + 1).zipWithIndex
        l <- lines._1.chars.mapToObj(
          i => new String(List(i.toByte).toArray[Byte], StandardCharsets.UTF_8))
          .toList.asScala.zipWithIndex.filter(_._2 % 2==0 ).map(_._1).zipWithIndex}
      yield (lines._2, l._2) -> l._1
      ).toMap
    val legend: Map[String, Class[_ <: GameObject]] = (
      for {
        s <- levelString.lines().toList.asScala.drop(h+1)
        symb = s.substring(0,1)
        obj = Class.forName(s.substring(2)).asInstanceOf[Class[GameObject]]}
      yield symb -> obj
      ).toMap
    val map = new GameMap(w, h)
    map.grid.hexes = (
      for {
        i <- -1.until(w+1)
        j <- -1.until(h+1)}
      yield {
        val hx = new Hex(i,j)
        symbols.get((j,i))
          .filter(_ != " ")
          .foreach((s: String) => hx.addContent({
          val c: Class[_ <: GameObject] = legend(s)
          c.getDeclaredConstructors().find(_.getParameterCount == 2).get.newInstance(i, j).asInstanceOf[GameObject]
        }))
        hx
      }).toList
    map
  }

}
