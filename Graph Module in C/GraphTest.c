#sumorin

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#include "Graph.h"


int main(int argc, char * argv[])
{

        Graph G = NULL;
//         Graph T = NULL;
        List S; 
        int n = 100;
  
      //create a graph 
        G = newGraph(n);
//         addArc(G, 1, 2);
//         addArc(G, 2, 3);
//         addArc(G, 2, 5);
//         addArc(G, 2, 6);
//         addArc(G, 3, 4);
//         addArc(G, 3, 7);
//         addArc(G, 4, 3);
//         addArc(G, 4, 8);
//         addArc(G, 5, 1);
//         addArc(G, 5, 6);
//         addArc(G, 6, 7);
//         addArc(G, 7, 6);
//         addArc(G, 7, 8);
//         addArc(G, 8, 8);
          


         
     //create a list that will be in vector label sorted order {1,2,3...n} to feed into DFS
//          S = newList();
//          for(int i = 1; i <= getOrder(G);i++ ){
//            append(S, i);
//          }

//        //done processing the file, so we print adjacency list 
//         printGraphStdOut(G, S);
//         DFS(G,S)
//       //print list of vectors in label sorted order 
//         printf("Printing the list of vector labels in sorted order: ");
//         printListStdOut(S);
//       //first run of DFS on G
//       //S is the list that represents the order each vertex will be visited, initially it is vertex label sorted order 
//         DFS(G, S);
//         printf("The list has been modified and now contains the order of vertices will follow on the second run: ");
// //         printListStdOut(S);
//         T = transpose(G);
// //         printGraphStdOut(T, S);
//         DFS(T,S);
//         printf("DFS has now been run a seconn time using G_Transpose and modified list of vectors finished in decreasing time order.\n ");
//         printf("List containing topological sorted order of strongly connected components .\n ");
//         printListStdOut(S);
        

  
  //--------------------------------------------------------------------------------
          addArc(G, 64, 4);
          addArc(G, 64, 3);
          addArc(G, 42, 2);
          addArc(G, 2, 64);
          addArc(G, 4, 2);
          addArc(G, 3, 42);
  

          S = newList();
         for(int i = 1; i <= 100 ;i++ ){
           prepend(S, i);
         }
         printGraphStdOut(G, S);
         printListStdOut(S);
        
          DFS(G, S);
          int y = getFinish(G, 64);
          printf("get finish of G is: %d ", y);
        
        //second run of DFS
          DFS(G, S);
          int x = getFinish(G, 64);
          printf("get finish of G on second run  is: %d ", x);
        
  
  
  //free the memory allocated 
        freeList(&S);
        freeGraph(&G);        

}
