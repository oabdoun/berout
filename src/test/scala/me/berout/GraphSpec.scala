package me.berout

import org.scalatest._

class GraphSpec extends FunSpec {
  describe("An graph") {
    val graph = new Graph
    val e1 = new Edge("Foo", "Bar", 2)
    val e2 = new Edge("Foo", "Baz", 4)
    val e3 = new Edge("Bar", "Foo", 2)
    val e4 = new Edge("Biz", "Boz", 8)
    graph.add(e1)
    graph.add(e2)
    graph.add(e3)
    graph.add(e4)

    it("should have no out edge for non existing vertex") {
      assert(graph.outEdges("Buz").isEmpty)
    }

    it("should return out edges based on their source") {
      assert(Set(e1, e2).equals(graph.outEdges("Foo")))
      assert(Set(e3).equals(graph.outEdges("Bar")))
      assert(Set(e4).equals(graph.outEdges("Biz")))
    }

    it("should not return edges based on their destination") {
      assert(graph.outEdges("Baz").isEmpty)
      assert(graph.outEdges("Boz").isEmpty)
    }
  }
}

