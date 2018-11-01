package me.berout

import org.scalatest._

class GraphToolTest extends FunSpec {
  describe("the graph tool") {
    describe("shortest path algorithm") {
      describe("for a simple acyclic graph") {
        val sag = new Graph
        val e1 = Edge("A", "B", 1)
        val e2 = Edge("A", "C", 2)
        val e3 = Edge("B", "D", 3)
        val e4 = Edge("C", "D", 4)
        sag.add(e1)
        sag.add(e2)
        sag.add(e3)
        sag.add(e4)

        it("finds the shortest paths") {
          assert(Seq(e1, e3).equals(GraphTool.shortestPath(sag, "A", "D")))
          assert(Seq(e1).equals(GraphTool.shortestPath(sag, "A", "B")))
          assert(Seq(e2).equals(GraphTool.shortestPath(sag, "A", "C")))
          assert(Seq(e3).equals(GraphTool.shortestPath(sag, "B", "D")))
          assert(Seq(e4).equals(GraphTool.shortestPath(sag, "C", "D")))
        }

        it("returns empty result for non existing start and target") {
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "foo", "D")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "A", "bar")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "foo", "bar")))
        }

        it("returns empty result for same start and target") {
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "A", "A")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "B", "B")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "C", "C")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(sag, "D", "D")))
        }
      }
    }
  }
}
 
