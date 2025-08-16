package rb2o2.halls.arena

import rb2o2.halls.arena.DamageType.{Cutting, Impaling}
import rb2o2.halls.arena.Maneuver.*
import rb2o2.halls.arena.RollResult.CriticalSuccess

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.util.{Success, Try, Failure as Fail}

object ArenaMain {
  private val characters: ListBuffer[Char] = ListBuffer()
  Characters.initCharacters(characters)

  def main(args: Array[String]): Unit = {
    println("Welcome to HoE Arena iter0!")
    var command: String = ""
    while {
      command = menuLoop()
      command.toLowerCase() match {
        case "n" => newGameLoop()
        case _ => command = "q"
      }
      command.toLowerCase != "q"
    } do ()
    println("Thanks for playing! Bye!")
  }

  private def menuLoop(): String = {
    println()
    println("Main menu:\n---------")
    println("[N]ew game")
    println("[Q]uit")
    scala.io.StdIn.readLine().strip()
  }

  private def newGameLoop(): Unit = {
    val selectedChars = ListBuffer[Char]()

    def chooseAttackTarget(char: Char, characters: ListBuffer[Char]): Char = {
      println("choose target")
      val it = Iterator.from(1)
      for {targets <- characters.filterNot(_ == char)
           n = it.next()} {
        println(s"$n -- ${targets.name}")
      }
      var cmd = Try(StdIn.readLine().toInt)
      while (cmd match
        case Fail(_) => true
        case Success(a: Int) if a > characters.size - 1 || a < 1 => true
        case _ => false) {
        println("wrong number")
        cmd = Try(StdIn.readLine().toInt)
      }
      characters.filterNot(_ == char)(cmd.get - 1)
    }

    def chooseAttackWeapon(char: Char): Weapon = {
      println("choose weapon")
      val it2 = Iterator.from(1)
      for {w <- char.weapons
           n = it2.next()} {
        println(s"$n ) ${w.name} -- ${w.skill}")
      }
      var cmd = Try(StdIn.readLine().toInt)
      while (cmd match {
        case Fail(_) => true
        case Success(a: Int) if a < 1 || a > char.weapons.size => true
        case _ => false
      }) {
        println("wrong number")
        cmd = Try(StdIn.readLine().toInt)
      }
      char.weapons(cmd.get - 1)
    }

    def dealDamage(char: Char, target: Char, weapon: Weapon, man: (Maneuver, Option[Char])): Unit = {
      val dmg = if (man._1 == AllOutAttackStrong) {
        Rolls.damageRoll((char.damageSw._1, Math.max(
          char.damageSw._2 + weapon.swMod.getOrElse(0) + 2,
          char.damageSw._2 + weapon.swMod.getOrElse(0) + char.damageSw._1
        )))
      } else {
        Rolls.damageRoll((char.damageSw._1, char.damageSw._2 + weapon.swMod.getOrElse(0)))
      }
      val damageDealt = Math.floor((dmg - target.dr) * (weapon.swDmgType match
        case Some(Cutting) => 1.5
        case Some(Impaling) => 2.0
        case None => weapon.thrDmgType match
          case Some(Cutting) => 1.5
          case Some(Impaling) => 2.0
          case _ => 1.0
        case _ => 1.0
        )).toInt
      target.hp -= damageDealt
      target.shockPenalty = Math.max(-4, -1 * damageDealt)
      println(s"you deal $damageDealt damage")
    }

    def attack(char: Char,
               man: (Maneuver, Option[Char]),
               characters: ListBuffer[Char]): Unit = {
      val target = chooseAttackTarget(char, characters)
      val weapon = chooseAttackWeapon(char)
      for {_ <- 1.to(if (man._1 == AllOutAttackDouble) 2 else 1)} {
        val atk = Rolls.successRoll(char.dx + weapon.skill.relativeLevel +
          (if (man._1 == AllOutAttackDetermined) 4 else 0))
        println(s"attacking ${target.name}! roll is : ${atk._1.toString}")
        atk match {
          case (RollResult.Success, _) =>
            val ddg = if (target.currentManeuver != AllOutDefenseIncreased) target.dodge
            else target.dodge + 2
            if (target.currentManeuver != AllOutAttackDetermined &&
              target.currentManeuver != AllOutAttackDouble &&
              target.currentManeuver != AllOutAttackStrong &&
              target.currentManeuver != AllOutDefenseDouble
            ) {
              Rolls.successRoll(ddg) match
                case (RollResult.Success, _) => println("enemy evades your blow")
                case (RollResult.CriticalSuccess, _) => println("enemy evades your blow")
                case (RollResult.Failure, _) | (RollResult.CriticalFailure, _) =>
                  println("You hit!")
                  dealDamage(char, target, weapon, man)
            }
            else if (target.currentManeuver == AllOutDefenseDouble) {
              Rolls.bestOfTwo(Rolls.successRoll(ddg), Rolls.successRoll(ddg)) match
                case RollResult.Success | RollResult.CriticalSuccess =>
                  println("enemy evades your blow")
                case RollResult.Failure | RollResult.CriticalFailure =>
                  println("You hit!")
                  dealDamage(char, target, weapon, man)

            } else {
              dealDamage(char, target, weapon, man)
            }
          case (RollResult.CriticalSuccess, _) =>
            println("You critically hit!")
            dealDamage(char, target, weapon, man)
          case (RollResult.Failure, _) => println("You miss!")
          case (RollResult.CriticalFailure, _) => println("You critically miss!")
        }
      }
    }

    def startBattle(characters: ListBuffer[Char]): String = {
      def applyManeuver(char: Char, man: (Maneuver, Option[Char])): Unit = {
        println(s"${char.name} executes ${man._1.toString} ${man._2.map("over " + _.name)}")
        val maneuver = man._1
        char.currentManeuver = maneuver
        maneuver match
          case DoNothing =>
          case Attack | AllOutAttackStrong | AllOutAttackDetermined | AllOutAttackDouble =>
            attack(char, man, characters)
          case _ => println("not implemented")
      }

      @tailrec
      def chooseManeuver(character: Char): (Maneuver, Option[Char]) = {
        println(s"It's ${character.name}'s turn. You are at ${character.shockPenalty} due to shock. Choose maneuver'")
        val mm = new mutable.HashMap[String, Maneuver]()
        val it = Iterator.from(1)
        for {m <- Maneuver.values} {
          val n = it.next()
          mm.put(n.toString, m)
          println(s"$n -- ${m.toString}")
        }
        val com = StdIn.readLine()
        Try(com.toInt) match
          case Success(a: Int) if a <= Maneuver.values.length && a > 0 => (mm(a.toString), None)
          case _ =>
            println("Error choosing maneuver. Try again")
            chooseManeuver(character)
      }

      val whoTurn = LazyList.from(0).flatten(_ => characters).iterator
      while (characters.count(_.hp > 0) > 1) {
        var character = whoTurn.next()
        while (character.hp <= 0) character = whoTurn.next()
        val maneuver = chooseManeuver(character)
        applyManeuver(character, maneuver)
      }
      println(s"The winner is ${characters.filter(_.hp > 0).head.name}")
      "q"
    }

    @tailrec
    def selectCharLoop(selected: ListBuffer[Char]): String = {
      def createCharacterLoop(): ListBuffer[Char] = ListBuffer()

      val selectableChars = ListBuffer[Char]()
      println("\nSelect characters from Roster [1]-[4] or [C] to create new character. " +
        "[R] when drafted all combatants.\n[Q] to quit to main menu")
      println("Selected: " + selected.map(_.name).mkString(", "))
      println("Roster:\n------\n")
      var command = ""
      val order = Iterator.from(1)
      for {char <- characters
           num = order.next()} {
        selectableChars += char
        println(s"[$num]: ${char.name}")
      }
      command = StdIn.readLine().strip()
      command.toLowerCase() match {
        case "r" => startBattle(selected)
        case "q" => "q"
        case "1" => selectCharLoop(selected :+ characters.head)
        case "2" => selectCharLoop(selected :+ characters(1))
        case "3" => selectCharLoop(selected :+ characters(2))
        case "4" => selectCharLoop(selected :+ characters(3))
        case "c" => selectCharLoop(selected :++ createCharacterLoop())
        case _ => "q"
      }
    }

    var command = ""
    while {
      command = selectCharLoop(selectedChars)
      command != "q"
    } do ()
  }
}
