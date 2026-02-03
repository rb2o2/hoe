package rb2o2.halls.arena

import scala.collection.mutable.ListBuffer

object Characters {
  def initCharacters(chars: ListBuffer[Char]): Unit = {
    chars += Char(
      "Bob Trainerton",
      12,
      11,
      10,
      11,
      12,
      11,
      10,
      10,
      5.5,
      6,
      2,
      (1, 1),
      (1, -1),
      List(
        Weapon(
          "Axe",
          Some(DamageType.Cutting),
          None,
          Some(4),
          None,
          Skill("Axe", 2),
          false
        ),
        Weapon(
          "Mace",
          Some(DamageType.Crushing),
          None,
          Some(3),
          None,
          Skill("Mace", 2),
          false
        ),
        Weapon(
          "Fists",
          None,
          Some(DamageType.Crushing),
          None,
          Some(0),
          Skill("Brawling", 2),
          false
        ),
        Weapon(
          "Bow",
          None,
          Some(DamageType.Impaling),
          None,
          Some(1),
          Skill("Bow", 2),
          true
        )
      ),
      List()
    )
    chars += Char(
      "Fred Dummyman",
      12,
      11,
      10,
      11,
      12,
      11,
      10,
      10,
      5.5,
      6,
      2,
      (1, 1),
      (1, -1),
      List(
        Weapon(
          "Axe",
          Some(DamageType.Cutting),
          None,
          Some(4),
          None,
          Skill("Axe", 2),
          false
        ),
        Weapon(
          "Mace",
          Some(DamageType.Crushing),
          None,
          Some(3),
          None,
          Skill("Mace", 2),
          false
        ),
        Weapon(
          "Fists",
          None,
          Some(DamageType.Crushing),
          None,
          Some(0),
          Skill("Brawling", 2),
          false
        ),
        Weapon(
          "Bow",
          None,
          Some(DamageType.Impaling),
          None,
          Some(1),
          Skill("Bow", 2),
          true
        )
      ),
      List()
    )
  }
}
