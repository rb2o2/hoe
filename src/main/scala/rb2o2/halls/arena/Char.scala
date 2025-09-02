package rb2o2.halls.arena

case class Skill(name: String, relativeLevel: Int)

case class Weapon(name: String, 
                  swDmgType: Option[DamageType],
                  thrDmgType: Option[DamageType],
                  swMod: Option[Int],
                  thrMod: Option[Int],
                  skill: Skill,
                  ranged: Boolean)

case class Spell(name: String, skill: Skill)

enum DamageType {
  case Crushing
  case Cutting
  case Impaling
  case Piercing
  case Burn
  case Corrosive
  case Toxic
  case Explosive
}
case class Char(name: String,
                st: Int,
                dx: Int,
                iq: Int,
                ht: Int,
                var hp: Int,
                var fp: Int,
                wil: Int,
                per: Int,
                bs: Double,
                bm: Int,
                dr: Int,
                damageSw: (Int, Int),
                damageThr: (Int, Int),
                weapons: List[Weapon],
                spells: List[Spell]
               ) {
  def dodge: Int = Math.floor(bs).toInt + 3
  var x: Int = 0
  var y: Int = 0
  var shockPenalty: Int = 0
  var stunned: Boolean = false
  var surprised: Boolean = false
  var currentManeuver: Maneuver = Maneuver.DoNothing
}