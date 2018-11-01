package me.berout

import scala.collection.mutable.{ HashMap, MultiMap, Set }

/** An edge of a directed weighted graph */
case class Edge(source: String, destination: String, weight: Integer) {}

/** A directed weighted graph */
class Graph {
  private val source2edges = new HashMap[String, Set[Edge]] with MultiMap[String, Edge]

  /** Adds an edge to the graph */
  def add(edge: Edge): Unit = {
    source2edges.addBinding(edge.source, edge)
  }

  /** Finds outgoing edges for a vertex. Returns empty set if vertex doesn't exist */
  def outEdges(source: String): Set[Edge] = {
    source2edges.getOrElse(source, Set.empty)
  }
}

