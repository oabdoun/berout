package me.berout

import scala.collection.mutable.{ HashMap, MultiMap, Set }

case class Edge(source: String, destination: String, weight: Integer) {}

class Graph {
  private val source2edges = new HashMap[String, Set[Edge]] with MultiMap[String, Edge]

  def add(edge: Edge): Unit = {
    source2edges.addBinding(edge.source, edge)
  }

  def outEdges(source: String): Set[Edge] = {
    source2edges.getOrElse(source, Set.empty)
  }
}

