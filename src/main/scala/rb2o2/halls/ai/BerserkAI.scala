package rb2o2.halls.ai

import rb2o2.halls.arena.{GameObject, Graph, Maneuver, Hex}
import rb2o2.halls.arena.Maneuver.{
  AllOutAttackDouble,
  DoNothing,
  MoveAndAttack,
  Move
}
import rb2o2.halls.map.GameMap
import rb2o2.halls.map.AStar
import rb2o2.halls.objects.{Creature, Hero}

class BerserkAI(entity: Creature) {
  def chooseManeuver(map: GameMap): (Maneuver, GameObject) = {
    val heroes = for {
      hx <- map.grid.hexes if hx.contents.exists(_.isInstanceOf[Hero])
    } yield hx // hexes with heroes
    val tuple = heroes
      .map((heroHex: Hex) => {
        map.grid.hexes
          .find(h => h.contents.contains(entity)) // optional hex with the enemy
          .map( // dummy option
            entityHex => {
              val possiblePath = AStar.findPath(
                new Graph(map.grid),
                entityHex,
                heroHex
              ) // optional list of path hexes
              val possibleLength = possiblePath.map(_.size)
              possibleLength
            }
          ) match
          case None => (DoNothing, null, Int.MaxValue) // entity not present
          case Some(length) =>
            length match {
              case Some(0) =>
                (
                  AllOutAttackDouble,
                  heroHex.contents.find(_.isInstanceOf[Hero]).get,
                  0
                )
              case None => (DoNothing, null, Int.MaxValue) // no path
              case Some(l) if l < entity.char.bm =>
                (
                  MoveAndAttack,
                  heroHex.contents.find(_.isInstanceOf[Hero]).get,
                  l
                )
              case Some(l) =>
                (Move, heroHex.contents.find(_.isInstanceOf[Hero]).get, l)
              case _ => throw RuntimeException("not valid path length")
            }
      })
      .minBy(_._3)
    (tuple._1, tuple._2)
  }
}
