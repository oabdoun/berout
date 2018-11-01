berout
======

`berout` (for Berlin Routing) is a CLI application to calculate fastest route between
two stations and nearby stations in the Berlin transport network, reading the network
specification and the `route` and `nearby` queries from `stdin` and printing the results
to `stdout`.

Running
-------
Some test input files are located in `src/test/resources`. To run `berout` against this
samples, e.g.:
```
$ cat src/test/resources/route_nearby.txt | sbt run
```

Implementation
--------------
### Design
The design is threefold:
* `Graph`: data structures to model directed weight graphs, optimized for forward
traversal
* `GraphTool`: a graph algorithms service
* `Berout`: a CLI interface for using the `GraphTool` service, parsing the graph spec
and queries from `stdin` and formatting the query results to `stdout`

### Assumptions
* `GraphTool`: edge weights are positive and non null
* `Berout`: input is well-formed and valid

### Performance
Clearly, the implementation is meant to be used in _very_ small graphs and won't
scale up for real-life use cases.

For code simplicity and reuse reasons, `GraphTool` builds, for both types of queries,
an entire minimum spanning tree, using depth first. This could be optimized by using
breadth first and prunning on the current cost to target for `route` and on the maximum
travel time for `nearby`.

