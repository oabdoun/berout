package me.berout

import org.scalatest._

class BeroutSpec extends FunSpec {

  def runApp(input: String): String = {
    val out = new java.io.ByteArrayOutputStream

    Console.withIn(getClass.getResourceAsStream(s"/${input}")) {
      Console.withOut(out) {
        Berout.main(Array.empty[String])
      }
    }

    out.flush
    new String(out.toByteArray)
  }

  describe("the berout application") {
    describe("for a valid input") {
      it("outputs the shortest path") {
        val out = runApp("route_one.txt")
        assert(out == "A -> C -> B: 130\n")
      }

      it("outputs one shortest path per query") {
        val out = runApp("route_two.txt")
        assert(out == "A -> C -> E: 310\nD -> E -> A: 780\n")
      }

      it("outputs an error message if no route exists") {
        assert(runApp("route_nopath.txt") == "Error: No route from A to D\n")
        assert(runApp("route_novertex.txt") == "Error: No route from Foo to Bar\n")
      }
    }
  }
}

