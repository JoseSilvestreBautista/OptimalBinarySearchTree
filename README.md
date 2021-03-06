# OptimalBinarySearchTree
The Program finds the optimal binary search through dynamic programming. The program considers the weights associated with each searchable item and the search time cost. It stores all this information into tables to build the optimal search tree through a root table. The optimal binary search tree has a time complexity of O(n^3). <br />

Concept Used:<br />
* Dynamic Programming

## Example OptimalBinarySearchTree

Searchable Items with their weights listed. 
* pi is the probability of selecting the item correctly 
* qi is the probability of not selecting the item. 

 ![Sample Image](https://github.com/JoseSilvestreBautista/OptimalBinarySearchTree/blob/master/images/image.png)
 
Search time table for each Item.
 
 ![Sample Image](https://github.com/JoseSilvestreBautista/OptimalBinarySearchTree/blob/master/images/eTable.PNG)
 
Weight table of each searchable item. 

 ![Sample Image](https://github.com/JoseSilvestreBautista/OptimalBinarySearchTree/blob/master/images/wTable.PNG)
 
Suggested binary search tree structure.

 ![Sample Image](https://github.com/JoseSilvestreBautista/OptimalBinarySearchTree/blob/master/images/rootTable.PNG)
