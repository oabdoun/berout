package me.berout

import scala.collection.mutable.HashMap

/** A path between vertices in a graph, including cost */
case class Path(path: Seq[Edge], cost: Int) {}

/** A collection of graph algorithm */
object GraphTool {
  private val emptyPath = new Path(Seq.empty, 0)

  /** Finds the shortest path between 2 vertices of a graph */
  def shortestPath(graph: Graph, start: String, target: String): Seq[Edge] = {
    val traversal = minTraversal(graph, start)
    traversal.getOrElse(target, emptyPath).path
  }

  /** Finds all the paths of a graph from a starting vertex whith a cost below a threshold */
  def selectPathsByMaxCost(graph: Graph, start: String, maxCost: Int): Set[Seq[Edge]] = {
    val traversal = minTraversal(graph, start)
    traversal.filterKeys(v => v != start)
    .values
    .filter(p => p.cost <= maxCost)
    .map(p => p.path)
    .toSet
  }

  /** Generates all the possible minimum cost paths of a graph from a starting vertex */
  private def minTraversal(graph: Graph, start: String): Map[String, Path] = {
    val paths = HashMap(start -> new Path(Seq.empty, 0))
    var waypoints = List(start)
    while (!waypoints.isEmpty) {
      for (next <- graph.outEdges(waypoints.head)) {
        if (!paths.isDefinedAt(next.destination)
            || (paths(next.destination).cost > paths(next.source).cost + next.weight)) {
          paths += (next.destination -> new Path(
            paths(next.source).path :+ next,
            paths(next.source).cost + next.weight
          ))
          waypoints = waypoints :+ next.destination
        }
      }
      waypoints = waypoints.tail
    }
    paths.toMap
  }
}

