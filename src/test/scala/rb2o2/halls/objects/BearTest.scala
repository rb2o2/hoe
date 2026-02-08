package rb2o2.halls.objects

import org.scalatest.flatspec.AnyFlatSpec
import rb2o2.halls.map.GameMap
import rb2o2.halls.ai.BerserkAI
import rb2o2.halls.arena.GameObject
import rb2o2.halls.arena.Maneuver.Move

class BearTest extends AnyFlatSpec {
  "AggressiveAI" should "Choose Move maneuver when not very close" in {
    val map = GameMap.simple()
    val bearHex = map.grid.hexes
      .find(_.contents.exists((o:GameObject) => o.isInstanceOf[Bear]))
      .get
    val bear = bearHex.contents
      .find(_.isInstanceOf[Bear])
      .get.asInstanceOf[Creature]
    assert {
      new BerserkAI(bear).chooseManeuver(map)._1 == Move
    }
  }
}
