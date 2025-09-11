package rb2o2.halls.map

import rb2o2.halls.arena.{Bear, Char, DamageType, Field, Hero, Hex, HexGrid, Skill, SpruceTree1, Weapon}

class GameMap(val w: Int, val h: Int) {
  var grid = new HexGrid()
  var selected: Option[Hero] = None
}
object GameMap {
  def simple(): GameMap = {
    val heroChar = Char("Bob Trainerton",
      12, 11, 10, 11, 12, 11, 10, 10, 5.5, 6, 2, (1, 1), (1, -1),
      List(Weapon("Axe", Some(DamageType.Cutting), None, Some(4), None, Skill("Axe", 2), false),
        Weapon("Mace", Some(DamageType.Crushing), None, Some(3), None, Skill("Mace", 2), false),
        Weapon("Fists", None, Some(DamageType.Crushing), None, Some(0), Skill("Brawling", 2), false),
        Weapon("Bow", None, Some(DamageType.Piercing), None, Some(1), Skill("Bow", 2), true)
      ), List()
    )
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

}
