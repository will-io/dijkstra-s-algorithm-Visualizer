Willy Esquivel-Lopez


The Graph structure implements the use of HashMaps which store an ArrayList of vertices and Edges.
The implementation was made with Big-O in mind and also ease of implementation when creating the graph and what functionalities would be used more frequently and what will be used less. 
HashMaps have a quick look up time (get/contains == O (1)) while ArrayList would be easy to implement in order to connect the links between vertices and Edges, which also has a quick lookup/ add  (get/add== O(1)). 
Only remove has an overhead of O(n) which is not used frequently. 