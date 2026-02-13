package rb2o2.halls.ai

import rb2o2.halls.arena.{GameObject, Hex, Maneuver}
import rb2o2.halls.arena.Maneuver.{DoNothing, Move}
import rb2o2.halls.map.GameMap
import rb2o2.halls.objects.Creature

import scala.util.Random

class WanderingAI(entity: Creature) {

  def chooseManeuver(map: GameMap): (Maneuver, GameObject) = {
    val entityHexOpt =
      map.grid.hexes.find(h => h.contents.contains(entity))

    entityHexOpt match {
      case None =>
        (DoNothing, entity)
      case Some(startHex) =>
        val steps = entity.char.bm
        val finalHex = randomWalk(map, startHex, steps)
        val target = finalHex.contents.headOption.getOrElse(entity)
        (Move, target)
    }
  }

  private def randomWalk(
      map: GameMap,
      start: Hex,
      steps: Int
  ): Hex = {
    var current = start
    var remaining = steps

    while (remaining > 0) {
      val neighbors = map.grid
        .neighbors(current.x, current.y)
        .filter(h =>
          // "non occupied": no non-passable objects present
          !h.contents.exists((o: GameObject) => !o.passable)
        )

      if (neighbors.isEmpty) {
        remaining = 0
      } else {
        val nextIndex = Random.nextInt(neighbors.size)
        current = neighbors(nextIndex)
        remaining -= 1
      }
    }

    current
  }
}

