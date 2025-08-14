package rb2o2.halls.arena

enum Maneuver {
  case DoNothing
  case Attack
  case AllOutAttackDouble // two attacks
  case AllOutAttackStrong // +2 to or +1 per die to dmg - best
  case AllOutAttackDetermined // +4 to skill
  case AllOutDefenseDouble // two defenses against single attack
  case AllOutDefenseIncreased // + 2 to selected active defense
  case Aim // +acc to ranged attack roll
  case Concentrate
  case Move
  case MoveAndAttack //-4 to skill
  case Ready
  case ChangePosture
}