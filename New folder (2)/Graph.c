//Susanna Morin
//sumorin
//pa5

#include<stdio.h>
#include<stdlib.h>
#include "Graph.h"

//GraphObj
typedef struct GraphObj
{

    List *adjList; //whose ith element contains the neighbors of vertex i
    int *color; //whose ith element is the color (white, gray, black) of vertex i, white = 1, grey=2, black = 3
    int *parent; // whose ith element is the parent of vertex i
    int *distance; //whose ith element is the distance from the (most recent) source to vertex i.
    int order; //number of vertices aka order of graph
    int size; //number of edges aka size of the graph
    int label; //label of vertex most recently used in BFS - undefined is NIL
    int *discover; //those ith element is the discovery time 
    int *finish; //whose ith element is the finish time 
    int Flag;
    int time;
} GraphObj;

/*** Constructors-Destructors *************************************************/

//returns a Graph pointing to a newly created GraphObj representing a graph having
//n vertices and no edges.
Graph newGraph(int n)
{
    // allocate memory
    Graph G = malloc (sizeof(GraphObj));
    G->adjList = malloc( (n + 1) * sizeof(List));
    G->color = malloc( (n + 1) * sizeof(int));
    G->parent = malloc( (n + 1) * sizeof(int));
    G->distance = malloc( (n + 1) * sizeof(int));
    G->discover = malloc( (n + 1) * sizeof(int));
    G->finish = malloc( (n + 1) * sizeof(int));

    //initialize new graph and adjList for each vector
    for(int i = 1; i < n + 1; i++)
    {
        G->adjList[i] = newList();
        G->color[i] = WHITE;
        G->parent[i] = NIL;
        G->distance[i] = INF;
        G->discover[i] = UNDEF;
        G->finish[i] = UNDEF;
    }
    G->order = n;
    G->size = 0;
    G->label = NIL;
    G->Flag = 0;
    G->time = 0;

    return (G);
}

//frees all dynamic memory associated with the Graph
//*pG, then sets the handle *pG to NULL
void freeGraph(Graph* pG)
{
    if (pG==NULL || *pG==NULL)
    {
        printf("Graph memory is already free\n");
        return;
    }
    //free lists
    for (int i = 1; i <= getOrder(*pG); i++)
    {
        freeList(&(*pG)->adjList[i]);
    }

    //free pointers
    free((*pG)->adjList);
    free((*pG)->color);
    free((*pG)->parent);
    free((*pG)->distance);
    free((*pG)->discover);
    free((*pG)->finish);

    //free dynamic memory and set graph pojnter to null
    free(*pG);
    *pG = NULL;
}

/*** Access functions *************************************************************/
//returns order of graph - aka number of vertices
int getOrder(Graph G)
{
    return (G->order);
}

//returns size of graph - aka number of edges
int getSize(Graph G)
{
    return (G->size);
}

//returns the source vertex most recently used in function BFS()
// or NIL if BFS() has not yet been called
int getSource(Graph G)
{
    return(G->label);
}

//precondition:1<= u <= getOrder(G)
//return the parent of vertex u in the BreadthFirst
//tree created by BFS(), or NIL if BFS() has not yet been called
int getParent(Graph G, int u)
{
    if (1>u || u > getOrder(G) )
    {
        printf("Graph Error: calling getParent() with invalid parameters\n");
        exit(1);
    }

//     if(G->parent[u] == NIL)
//         return (NIL);

    return G->parent[u];
}

//precondition:1<= u <= getOrder(G)
//returns the distance from the most recent BFS source to vertex u,
//or INF if BFS() has not yet been called
int getDist(Graph G, int u)
{
    if (1>u || u > getOrder(G))
    {
        printf("Graph Error: calling getDist() with invalid parameters\n");
        exit(1);
    }
    //returns INF if u is not reachable by s
    //check if BFS() has been called
    else if(getSource(G) == NIL){
//       printf("BFS has not been called");
      return INF;
    }
    return G->distance[u]; //calculates distance from the source 

}

//precondition:1<= u <= getOrder(G)
//precondition: getSource(G)!=NIL
//so BFS() must be called before getPath()
//u is the destination - getPath appends vertices to a list
void getPath(List L, Graph G, int u)
{
    //check to see BFS() was called
    if (1 > u || u > getOrder(G) )
    {
        printf("Graph Error: calling getPath() with invalid parameters\n");
        exit(1);
    }
    if(getSource(G) == NIL)
    {
        printf("Graph Error: calling getPath() before BFS()\n");
        exit(1);
    }
    //Case1: source = destination
    if (getSource(G) == u)
    {
        append(L,u);
    }
    //Case2: recur
    else if (G->parent[u] != NIL)  //if parent has no predecessor we cannot recur to the source
    {
       getPath(L, G, G->parent[u]);
       append(L,u);

    }
    //Case3: source not reachable from destination vertex 
    else if(G->distance[u] == INF){
      append(L, NIL);
    }
      

}
 // Pre: 1<=u<=getOrder(G)
int getDiscover(Graph G, int u){
  if(1 > u || u > getOrder(G)){
     printf("Graph Error: calling getDiscover() with invalid parameters\n");
     exit(1);
  }
//    else if(G->Flag == 0){
//       return UNDEF; //DFS has not been called 
//     }
    return G->discover[u]; //returns discover time for vertex u 
  
}
 // Pre: 1<=u<=getOrder(G)
int getFinish(Graph G, int u){
  if(1 > u || u > getOrder(G)){
     printf("Graph Error: calling getFinish() with invalid parameters\n");
     exit(1);
  }
  
//    else if(G->Flag == 0){
//       return UNDEF; //DFS has not been called 
//     }
    return G->finish[u]; //returns finish time for vertex u 
  
  
}

/*** Manipulation procedures *************************************************/

//deletes all edges of G, restoring it to its original
//(no edge) state - a null graph
void makeNull(Graph G)
{
    if(G == NULL)
    {
        printf("Error: calling makeNull() on NULL graph\n");
        exit(1);
    }
    //iterate through and clear all lists
    for(int i = 0; i < G->order; i++)
    {
        clear(G->adjList[i]);
    }
    //clears number of edges
    G->size = 0;
}

//precondition: 1<= u <= getOrder(G)
//precondition: 1<= v <= getOrder(G)
//inserts a new edge joining u and v
void addEdge(Graph G, int u, int v)
{
    if (u < 1 || v < 1 || u > getOrder(G) || v > getOrder(G) )
    {
        printf("Graph Error: calling addEdge() with invalid parameters\n");
        exit(1);
    }
    addArc(G,v,u); //u is added to the adjacency List of v
    addArc(G,u,v); //v to the adjacency List of u
    G->size--;
}

//precondition: 1<= u <= getOrder(G)
//precondition: 1<= v <= getOrder(G)
// inserts a new directed edge from u to v,
//i.e. v is added to the adjacency List of u (but not u to the adjacency List of v)
void addArc(Graph G, int u, int v)
{
    if (u < 1 || v < 1 || u > getOrder(G) || v > getOrder(G) )
    {
        printf("Graph Error: calling addArc() with invalid parameters\n");
        exit(1);
    }
  //make a reference to a list
    List L = G->adjList[u];
  
    //Case1: list is empty
    if(length(L) == 0)
    {
        append(L,v);
        G->size++;
        return;
    }
   //check if v > than last element 
   if(back(L) < v){
      append(L,v);
      G->size++;
     return;
    }
    //cheks if v is < than starting from the front 
    moveFront(L);
    while(index(L) >= 0){
    if(v < get(L)){
      insertBefore(L,v);
      G->size++;
      return;    
    }else
      moveNext(L);
  }
   
}

// runs the BFS algorithm on the Graph G with source s, setting the color, distance, parent,
//and source fields of G accordingly
void BFS(Graph G, int s)
{
  //initialize the source  
  G->label = s;
  
   if(G == NULL)
    {
        printf("Error: calling makeNull() on NULL graph\n");
        exit(1);
    }
    for(int i = 1; i <= G->order; i++)
    {
        G->color[i] = WHITE;
        G->parent[i] = NIL;
        G->distance[i] = INF;
    }
    //initialize the source vertex
    G->color[s] = GREY;
    G->parent[s]= NIL;
    G->distance[s] = 0;

    //initialize queue to be empty
    List Queue = newList(); //will use List as a queue with append as push and pop as delete()
    append(Queue, s);

    //when the 'queue' is non empty
    int x;
    while(length(Queue) != 0)
    {
//         printf("\n\nQUEUE before dequeue: \n");
//         printListStdOut(Queue);
        moveFront(Queue); 
        x = get(Queue); // will get the data at the front
        delete(Queue);
//         printf("we have deleted %d and now we are discovering its neighbors: \n", x); //test
      
//         printf("QUEUE after dequeue: \n");
//         printListStdOut(Queue);  
        if(length(G->adjList[x]) == 0){
          //do nothing
        }else{
          moveFront(G->adjList[x]); //go to the front of the list of vector x
        //iterate through adjList for each vector x
//           int y = get(G->adjList[x]); //test
//           printf("a neighbor for%d is: %d\n",x,  y); //test
//           printListStdOut(G->adjList[x]); //test
//           printf("processing adj list for vertex %d...\n", x);
        for(int i = 1; i <= length(G->adjList[x]); i++)
        {
            
            int y = get(G->adjList[x]); //y will be the vector in adj list of vector x
//             printf("in for loop vertex: %d\n", y);
            if(G->color[y] == WHITE) //if we haven't discovered y
            {
                G->color[y] = GREY; //label vector as discovered - but neighbors yet to be discovered
                G->distance[y] = G->distance[x] +1;
                G->parent[y] = x;
                append(Queue,y); //enqueue next vertex to the queue

            }
//             printf("QUEUE while processing adj list: \n");
//             printListStdOut(Queue);
            moveNext(G->adjList[x]); //move down the adjList of vector x 
        }
        }

    }
    G->color[x] = BLACK; //all vectors in adj list have been discovered for vector x
    freeList(&Queue);
}
 // Pre: length(S)==getOrder(G)
void DFS(Graph G, List S){
  G->time = 0;
  
  if (length(S)!= getOrder(G)){
       printf("Graph Error: calling DFS() with incompatible list length \n");
       exit(1);
  }
//   printListStdOut(S);
//   printf("we are in the DFS function");
  
  G->Flag =1; 
  //for each vertex u element of vertexSet(G) initialize the color to white and parent to NIL
  for(int x = 1; x <= G->order; x++)
    {   
        G->color[x] = WHITE;
        G->parent[x] = NIL;
    }
    
    List L = copyList(S); //move the list that will determine the order of vertices DFS processes 
    clear(S); // clear S so that we can append finish times in decreasing order 
    //main loop
    for(moveFront(L); index(L) != -1; moveNext(L))
    {  
//       printf("INSIDE DFS: before deleting the vector being discovered \n");
//       printListStdOut(L);
      int x = get(L); //get vertex at current cursor (starting from beg of list)
//       printf("INSIDE DFS: current vector being discovered is: %d\n", x);
//       printf("INSIDE DFS: after deleting the vector being discovered \n");
//       printListStdOut(L);
      if(G->color[x] == WHITE){
          Visit(G, x, S);
        }
        
    }
  freeList(&L);
}
//helper function for DFS
void Visit(Graph G, int x, List S){
//   printf("INSIDE Visit():  \n");
//   printListStdOut(S);            
  
  G->color[x] = GREY;
  G->discover[x] = (++G->time);
//   printf("time is now: %d  \n", G->time);
  List L = G->adjList[x];
//   printListStdOut(L);
  for(moveFront(L); index(L) != -1; moveNext(L)){
    int y = get(L);

 //     printf("printing y  \n");
//     printf( "y : %d ", y);
//     printf("INSIDE Visit(): after deleting the vector being discovered \n");
//     printListStdOut(S);
    if(G->color[y] == WHITE){
      G->parent[y] = x;
      Visit(G, y, S);
    }
    
  }
  G->color[x] = BLACK;
  G->finish[x] = (++G->time);

  //as vertices finish we push them onto the stack
  prepend(S, x);
}

/*** Other operations ******************************************************/
//prints the adjacency list representation of G to the file pointed to by out
void printGraph(FILE* out, Graph G)
{

    if (G == NULL )
    {
        printf("Graph Error: calling printGraph() on empty Graph\n");
        exit(1);
    }
    fprintf(out, "Adjacency list representation of G: \n");
    for(int i = 1; i<= G->order; i++)
    {
        List L = G->adjList[i];
        
        if(length(L) == 0){
        fprintf(out, "%d: \n", i);
        }else{
        fprintf(out, "%d: ", i);
        printList(out,L);
        fprintf(out, "\n");
        }
    }
}
//prints to std out 
void printGraphStdOut(Graph G, List S)
{

    if (G == NULL )
    {
        printf("Graph Error: calling printGraph() on empty Graph\n");
        exit(1);
    }
    printf("Adjacency list representation of G: \n");
    for(moveFront(S); index(S) != -1; moveNext(S))
    {
        int i = get(S);
        List L = G->adjList[i];
        
        if(length(L) == 0){
        printf("%d: \n", i);
        }else{
        printf("%d: ", i);
        printListStdOut(L);
//         printf("\n");
        }
    }
}

Graph transpose(Graph G){
    Graph T = newGraph(getOrder(G)); //make new graph called transpose
    
    if (G == NULL) {
      printf("Error: transpose called on NULL Graph.\n");
      exit(1);
    }
	
    for (int x = 1; x <= getOrder(T); x++) {
      List L= G->adjList[x]; //make a list reference that for each vector containing the vectors it points to 
        for (moveFront(L); index(L) != -1; moveNext(L)) {
          int y = get(L);
          addArc(T,y,x); //reverse the edge direction and now the neighbors point to each vector  
        }
    }
    return (T);
  
  }
  Graph copyGraph(Graph G){
    if(G == NULL)
      {
          fprintf(stderr, "Error: cannot call copyGraph() on NULL Graph.\n");
          exit(1);
      }

    Graph Copy = newGraph(getOrder(G));
    
    for(int i = 1; i <= getOrder(G); i++)
    {
        List L = G->adjList[i];  
        for (moveFront(L); index(L) != -1; moveNext(L)) 
        {
            addArc(Copy, i, get(L));
        }
    }
    return Copy;
}




