package rb2o2.halls.arena

case class Weapon(
    name: String,
    swDmgType: Option[DamageType],
    thrDmgType: Option[DamageType],
    swMod: Option[Int],
    thrMod: Option[Int],
    skill: Skill,
    ranged: Boolean
)
