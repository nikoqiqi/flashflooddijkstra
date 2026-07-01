# Optimising Flash Flood Evacuation Routes and Traffic Bottleneck Mitigation on a Highway Network

## Project Overview

This project focuses on designing an efficient evacuation route planning system during a flash flood emergency. The system models a highway network as a **weighted directed graph** and applies **Dijkstra’s Algorithm** to determine the shortest and safest route for emergency vehicles.

The main objective is to help emergency responders travel from the **Emergency Operation Center** to designated **Evacuation Centers** while:

- Avoiding flooded road sections
- Visiting mandatory residential areas
- Minimizing total travel distance
- Providing alternative evacuation routes

This project was developed for **Design and Analysis of Algorithm (CSC4202)**.

---

## Problem Scenario

During a flash flood in a highway network, several residential areas are affected and require immediate assistance. Emergency vehicles must deliver supplies and support evacuation activities.

However, some roads become inaccessible due to flooding, causing possible delays and traffic bottlenecks. Therefore, an optimal route is required to ensure emergency vehicles can reach affected areas safely and efficiently.

The system must:

1. Start from the Emergency Operation Center (Node A)
2. Visit all required residential areas:
   - Residential Area 1 (Node D)
   - Residential Area 2 (Node E)
   - Residential Area 3 (Node I)
3. Avoid the flood affected road section (Node G)
4. Reach the selected evacuation center:
   - Evacuation Center 1 (Node J)
   - Evacuation Center 2 (Node K)

---

## Objectives

- Model the highway network using a weighted directed graph.
- Implement Dijkstra’s Algorithm for shortest path calculation.
- Find the shortest evacuation route.
- Avoid unsafe flooded road sections.
- Analyze algorithm performance and correctness.

---

## Algorithm Used

### Dijkstra’s Algorithm

Dijkstra’s Algorithm is selected because the evacuation network can be represented as a weighted graph with non-negative edge weights.

The algorithm works by:

1. Assigning distance 0 to the starting node.
2. Selecting the node with the smallest known distance.
3. Updating distances of neighboring nodes.
4. Repeating until the shortest route is found.

The selected algorithm guarantees the shortest path for graphs with non-negative weights.

---

## Graph Representation

The highway network is represented as a weighted directed graph:

| Node | Description |
|---|---|
| A | Emergency Operation Center |
| B | Highway Junction 1 |
| C | Highway Junction 2 |
| D | Residential Area 1 |
| E | Residential Area 2 |
| F | Highway Interchange |
| G | Flood Affected Road Section |
| H | Highway Checkpoint |
| I | Residential Area 3 |
| J | Evacuation Center 1 |
| K | Evacuation Center 2 |

Each road connection contains a distance value representing travel cost.

---

## Features

✔ Weighted directed graph implementation  
✔ Shortest route calculation using Dijkstra Algorithm  
✔ Flood avoidance mechanism  
✔ Mandatory residential checkpoint routing  
✔ Multiple evacuation destination support  
✔ Total distance calculation  
✔ Route validity checking  

---

## Flood Avoidance Mechanism

The flood affected road section is represented by **Node G**.

During route calculation, Node G is excluded to prevent emergency vehicles from entering unsafe areas.

Example:

```java
if (v == 'G') {
    continue;
}
