package rb2o2.halls.arena

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.StdIn

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

    def startBattle(characters: ListBuffer[Char]): String = {
      "q"
    }

    @tailrec
    def selectCharLoop(selected: ListBuffer[Char]): String = {
      def createCharacterLoop(): ListBuffer[Char] = ListBuffer()

      val selectableChars = ListBuffer[Char]()
      println("\nSelect characters from Roster [1]-[4] or [C]reate new character. " +
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
        case "1" => selectCharLoop(selected :+ characters(0))
        case "2" => selectCharLoop(selected :+ characters(1))
        case "3" => selectCharLoop(selected :+ characters(2))
        case "4" => selectCharLoop(selected :+ characters(3))
        case "c" => selectCharLoop(selected :++ createCharacterLoop())
      }
    }

    var command = ""
    while {
      command = selectCharLoop(selectedChars)
      command != "q"
    } do ()
  }
}
