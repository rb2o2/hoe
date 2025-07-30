package rb2o2.halls.arena

import rb2o2.halls.arena.RollResult.*

import scala.util.Random

object Rolls {
  private val rnd = new Random(System.currentTimeMillis())
  def successRoll(effective: Int): (RollResult, Int) = {
    var sum = 0
    for {d <- 1.to(3)} {
      sum += rnd.nextInt(6) + 1
      rnd.setSeed(System.currentTimeMillis())
    }
    val margin = effective - sum
    val res = sum match {
      case 3 => CriticalSuccess
      case 4 => CriticalSuccess
      case 5 if effective >= 15 => CriticalSuccess
      case 6 if effective >= 16 => CriticalSuccess
      case 18 => CriticalFailure
      case 17 if effective < 16 => CriticalFailure
      case a if effective <= a - 10 => CriticalFailure
      case a if a > effective => Failure
      case _ => Success
    }
    (res, margin)
  }

  def damageRoll(diceMod: (Int, Int)): Int = {
    var sum = 0
    for {d <- 1.to(diceMod._1)} {
      sum += rnd.nextInt(6) + 1
      rnd.setSeed(System.currentTimeMillis())
    }
    Math.max(sum + diceMod._2, 0)
  }
}

enum RollResult {
  case Success
  case CriticalSuccess
  case Failure
  case CriticalFailure
}
