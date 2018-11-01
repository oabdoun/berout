package me.berout

import org.scalatest._

class GraphToolTest extends FunSpec {
  describe("the graph tool") {
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

      describe("shortest path algorithm") {
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

      describe("max cost select algorithm") {
        it("finds the vertices within max cost") {
          assert(Set(Seq(e1)).equals(GraphTool.selectPathsByMaxCost(sag, "A", 1)))
          assert(Set(Seq(e1), Seq(e2)).equals(GraphTool.selectPathsByMaxCost(sag, "A", 2)))
          assert(Set(Seq(e1), Seq(e2)).equals(GraphTool.selectPathsByMaxCost(sag, "A", 3)))
          assert(Set(Seq(e1), Seq(e2), Seq(e1, e3)).equals(GraphTool.selectPathsByMaxCost(sag, "A", 4)))
          assert(Set(Seq(e1), Seq(e2), Seq(e1, e3)).equals(GraphTool.selectPathsByMaxCost(sag, "A", 100)))
        }

        it("finds no path for non existing start") {
          assert(GraphTool.selectPathsByMaxCost(sag, "foo", 100).isEmpty)
        }

        it("finds no path for terminal point") {
          assert(GraphTool.selectPathsByMaxCost(sag, "D", 100).isEmpty)
        }

        it("finds no path for null cost") {
          assert(GraphTool.selectPathsByMaxCost(sag, "A", 0).isEmpty)
        }
      }
    }

    describe("for a simple cyclic graph") {
      val scg = new Graph
      val f1 = Edge("A", "B", 1)
      val f2 = Edge("B", "A", 1)
      val f3 = Edge("A", "C", 4)
      val f4 = Edge("C", "A", 3)
      val f5 = Edge("C", "C", 1)
      val f6 = Edge("B", "C", 2)
      val f7 = Edge("C", "D", 2)
      scg.add(f1)
      scg.add(f2)
      scg.add(f3)
      scg.add(f4)
      scg.add(f5)
      scg.add(f6)
      scg.add(f7)

      describe("shortest path algorithm") {
        it("finds the shortest paths") {
          assert(Seq(f1, f6, f7).equals(GraphTool.shortestPath(scg, "A", "D")))
          assert(Seq(f2).equals(GraphTool.shortestPath(scg, "B", "A")))
          assert(Seq(f6, f7).equals(GraphTool.shortestPath(scg, "B", "D")))
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(scg, "C", "C")))
        }

        it("returns empty result for non-existing path") {
          assert(Seq.empty[Edge].equals(GraphTool.shortestPath(scg, "D", "A")))
        }
      }

      describe("max cost select algorithm") {
        it("finds the vertices within max cost") {
          assert(Set(Seq(f1)).equals(GraphTool.selectPathsByMaxCost(scg, "A", 1)))
          assert(Set(Seq(f1), Seq(f1, f6)).equals(GraphTool.selectPathsByMaxCost(scg, "A", 3)))
          assert(Set(Seq(f1), Seq(f1, f6)).equals(GraphTool.selectPathsByMaxCost(scg, "A", 4)))
          assert(Set(Seq(f1), Seq(f1, f6), Seq(f1, f6, f7)).equals(GraphTool.selectPathsByMaxCost(scg, "A", 5)))
          assert(Set(Seq(f1), Seq(f1, f6), Seq(f1, f6, f7)).equals(GraphTool.selectPathsByMaxCost(scg, "A", 100)))
        }

        it("finds no path for non existing start") {
          assert(GraphTool.selectPathsByMaxCost(scg, "bar", 100).isEmpty)
        }

        it("finds no path for terminal point") {
          assert(GraphTool.selectPathsByMaxCost(scg, "D", 100).isEmpty)
        }

        it("finds no path for null cost") {
          assert(GraphTool.selectPathsByMaxCost(scg, "A", 0).isEmpty)
        }
      }
    }
  }
}
 
