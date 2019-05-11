//sumorin

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#include "Graph.h"

#define MAX_LEN 160

int main(int argc, char * argv[])
{

    FILE *in, *out;
    char line[MAX_LEN]; //where each line is read
    int u,v,n,sentinel = 0;
    Graph G = NULL;
    Graph T = NULL;
    List S;
    List L;

    // check command line for 2 command line arguments :
    // if two args: in and out are not specified program with - unsuccessfull termination
    if( argc != 3 )
    {
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        exit(1);
    }

    // open files for reading and writing
    in = fopen(argv[1], "r");
    out = fopen(argv[2], "w");

    //checks for invalid input file, if null exits program with - unsuccessfull termination
    if( in==NULL )
    {
        printf("Unable to open file %s for reading\n", argv[1]);
        exit(1);
    }

    //checks for invalid output file, if null exits program with - unsuccessfull termination
    if( out==NULL )
    {
        printf("Unable to open file %s for writing\n", argv[2]);
        exit(1);
    }

    int counter1 = 1;
    while(fgets(line, MAX_LEN, in) != NULL)
    {
        if(counter1 == 1)
        {
            //initialize a new graph of order n
            sscanf(line," %d", &n);
            G = newGraph(n);
            counter1++;
        }
        else
        {
            //scan line and parse it into two ints
            sscanf(line," %d %d", &u, &v);
            //check to see that we are still in pt 1 of input
            if(sentinel != v && sentinel != u)
            {
                //since we are still in pt 1 of input - add a directed edge from u to v
                addArc(G,u,v);
            }
              
         }
       
     }
  
    //done processing the file, so we print adjacency list 
        printGraph(out, G);
         
     //create a list that will be in vector label sorted order {1,2,3...n} to feed into DFS
         S = newList();
         for(int i = 1; i <= getOrder(G);i++ ){
           append(S, i);
         }
//          printList(out, S);
    //start computing the strongly connected components of G
         DFS(G, S); //run DFS the first time on the adj list in vector label sorted order 
//          fprintf(out, "here is the list after first run of DFS");
//          printList(out, S);
         T = transpose(G);
//          printGraph(out, T); ///////test 
         DFS(T, S); //run DFS the second time on the stack list from first run of DFS, the stack list from this run will be topological sorted order 
        
//         fprintf(out, "here is the list after second run of DFS");
//         printList(out, S);
   //now we start printing the connected components of G using the stack using List ADT
        int count =0;
        for(moveFront(S); index(S) != -1; moveNext(S)){
         int check = get(S);
          if(getParent(T, check) == NIL){
            count++;
          }
        }
        
        fprintf(out, "\nG contains %d strongly connected components: \n", count);
        int i = 0;
        L = newList(); //create a list to hold the strongly connected component(s)
        for (moveBack(S); index(S)>=0; movePrev(S)){
        //start from the back of the list representing the bottom of stack 
          //read vertices until one has a NIL as parent, then print 
           int v = get(S);
           prepend(L, v);
            if(getParent(T, v) == NIL){
              i++; //increment the number of SCC
              fprintf(out, "Component %d: ", i);
              printList(out, L);
              fprintf(out, "\n");
              clear(L); //clear L so we can reuse it for next SCC          
            }          
        }
    //free memory

    freeGraph(&T);
    freeList(&L);
    freeList(&S);
    freeGraph(&G);
//close the input/output files
    fclose(in);
    fclose(out);

}
