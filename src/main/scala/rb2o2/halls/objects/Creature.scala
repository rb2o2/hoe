package rb2o2.halls.objects

import rb2o2.halls.arena.{Char, GameObject}

trait Creature(var char: Char) extends GameObject {}
