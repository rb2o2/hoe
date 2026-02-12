package rb2o2.halls.ai

import rb2o2.halls.arena.{GameObject, Hex, Maneuver}
import rb2o2.halls.arena.Maneuver.{DoNothing, Move}
import rb2o2.halls.map.GameMap
import rb2o2.halls.objects.{Creature, Hero}

class CowardAI(entity: Creature) {

  def chooseManeuver(map: GameMap): (Maneuver, GameObject) = {
    val heroHexes: List[Hex] = for {
      hx <- map.grid.hexes if hx.contents.exists(_.isInstanceOf[Hero])
    } yield hx

    val entityHexOpt =
      map.grid.hexes.find(h => h.contents.contains(entity))

    (heroHexes, entityHexOpt) match {
      case (Nil, _) =>
        // No heroes on the map – nothing to run from
        (DoNothing, entity)
      case (_, None) =>
        // Entity is not placed on the map – fallback to doing nothing
        (DoNothing, entity)
      case (heroes, Some(entityHex)) =>
        val closestHeroHex =
          heroes.minBy(h => distanceSquared(entityHex, h))

        val steps = entity.char.bm
        val targetHex = moveAwayFromHero(
          map,
          entityHex,
          closestHeroHex,
          steps
        )

        val targetObject =
          targetHex.contents.headOption.getOrElse(entity)

        (Move, targetObject)
    }
  }

  private def distanceSquared(a: Hex, b: Hex): Int = {
    val dx = a.x - b.x
    val dy = a.y - b.y
    dx * dx + dy * dy
  }

  private def moveAwayFromHero(
      map: GameMap,
      start: Hex,
      heroHex: Hex,
      steps: Int
  ): Hex = {
    var current = start
    var remaining = steps

    while (remaining > 0) {
      val neighbors = map.grid
        .neighbors(current.x, current.y)
        .filterNot((h: Hex) =>
          h.contents.exists((c: GameObject) => !c.passable)
        )
      if (neighbors.isEmpty) {
        remaining = 0
      } else {
        // Pick neighbor that is farthest from the hero hex
        val next = neighbors.maxBy(h => distanceSquared(h, heroHex))
        current = next
        remaining -= 1
      }
    }

    current
  }
}
