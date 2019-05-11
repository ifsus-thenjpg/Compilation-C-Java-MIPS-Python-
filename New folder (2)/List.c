//-----------------------------------------------------------------------------
// List.c
//ADT class that manages a doubly linked list with a cursor that is used to insert an element before/after to help in
// indirectly sorting a list of strings by using the indices of the list.
//-----------------------------------------------------------------------------

//Susanna Morin 
//sumorin
//pa5

#include<stdio.h>
#include<stdlib.h>
#include "List.h"

 
// // private types --------------------------------------------------------------
// NodeObj
typedef struct NodeObj{
  int data;
  struct NodeObj* prev;
  struct NodeObj* next;
} NodeObj;

// Node
typedef NodeObj* Node;

//ListObj
typedef struct ListObj{
  Node front;
  Node back;
  Node cursor;
  int length;
  int index;
} ListObj;

// constructor of the Node type
Node newNode(int data_param) {
 Node N = malloc(sizeof(NodeObj));
//  assert(N!=NULL); 
 N->data = data_param;
 N->prev = NULL;
 N->next = NULL;
  
 return(N);
}

// freeNode()
// destructor for the Node type
void freeNode(Node* pN){
  if( pN!=NULL && *pN!=NULL ){
  free(*pN);
  *pN = NULL;
  }
}


// Constructors-Destructors ---------------------------------------------------

//Constructor
//Creates a new empty list.
List newList(void){
  List L;
  L = malloc(sizeof(ListObj));
  L->front = L->back = NULL;
  L->length = 0;
  L->index = -1;
  return(L);
}

// frees all heap memory associated with its List* argument, 
// and sets *pL to NULL
void freeList(List *pL){
  //when you call this method from Lex use freeList(&L )
  if(pL!=NULL && *pL!=NULL) { 
      clear(*pL);
      free(*pL);
      *pL = NULL;
   }
}

// Access functions -----------------------------------------------------------
// Access functions
// Returns the number of elements in this List
int length(List L){
  if( L == NULL ){
    printf("List Error: calling length() on empty List reference\n");
//     exit(1);
   }
   return(L->length);
}

// If cursor is defined, returns the index of the cursor element, 
// otherwise returns -1.
int index(List L){
//   if(L->cursor == NULL){
//     L->index = -1;
//     }
   if (L == NULL){
    printf("List Error: calling index() on empty List reference\n");
    exit(1);
    }
    return (L->index);
  }
void setIndex(List L, int index){
  L->index = index;
}
 // Returns front element. Pre: length()>0
int front(List L){
  if (L->length <= 0){
    printf("List Error: calling front() on empty List reference\n");
    exit(1);
    }  
    return (L->front->data);
  }

// Returns back element. Pre: length()>0
int back(List L){
  if (L->length <= 0){
    printf("List Error: calling back() on empty List reference\n");
    exit(1);
  }    
    return (L->back->data);
}

// Returns cursor element. Pre: length()>0, index()>=0
int get(List L){
  if (L->length<= 0 || L->index < 0){
    printf("List Error: calling get() on empty List reference\n");
    exit(1);
  }
    return L->cursor->data;
}

// Returns true if and only if this List and L are the same
// integer sequence. The states of the cursors in the two Lists
// are not used in determining equality.
int equals(List A, List B){
  int eq = 0;
   Node N = NULL;
   Node M = NULL;

   if( A==NULL || B==NULL ){
    printf("List Error: calling equals() on NULL List reference\n");
    exit(1);
   }

   eq = ( A->length == B->length );
   N = A->front;
   M = B->front;
   while( eq && N!=NULL){
      eq = (N->data==M->data);
      N = N->next;
      M = M->next;
   }
   return eq;
}

// Manipulation procedures ----------------------------------------------------
// Resets this List to its original empty state.
void clear(List L){
   while(length(L) > 0){
      deleteFront(L);
  }
  
  
  L->index = -1;
  L->length = 0;
  L->front = NULL;
  L->back = NULL;
  L->cursor = NULL;
}

// If List is non-empty, places the cursor under the front element,
// otherwise does nothing.
void moveFront(List L){
  if( L == NULL ){
    printf("List Error: calling moveFront() on NULL List reference\n");
    exit(1);
  }
//   if( length(L) == 0 ){
//     printf("List Error: calling moveFront() on an empty List \n");
//   }
  if (L->length > 0) {
    L->cursor = L->front;
    L->index = 0;  
  }
}
  
  // If List is non-empty, places the cursor under the back element,
// otherwise does nothing.
void moveBack(List L){
  if (L->length > 0) {
    L->cursor = L->back;
    L->index = L->length -1;
    }
  }
  
// If cursor is defined and not at front, moves cursor one step toward
// front of this List, if cursor is defined and at front, cursor becomes
// undefined, if cursor is undefined does nothing.
void movePrev(List L){
  //valid place to move the cursor
  if (L->index > 0) {
    L->cursor = L->cursor->prev;
    L->index--;
  }else if (L->index == 0) { //runs off the edge
    L->cursor = NULL;
    L->index = -1; 
  }
}
  
// If cursor is defined and not at back, moves cursor one step toward
// back of this List, if cursor is defined and at back, cursor becomes
// undefined, if cursor is undefined does nothing.
void moveNext(List L){
  if (L->index < L->length - 1) {
    L->cursor = L->cursor->next;
    L->index++;
  } else if (L->index == L->length - 1) { //about to run off the edge
    L->cursor = NULL;
    L->index = -1;
}

}
  
// Insert new element into this List. If List is non-empty,
// insertion takes place before front element.
void prepend(List L, int data){
  Node N = newNode(data);

  if( L ==NULL ){
    printf("List Error: calling prepend() on NULL List reference\n");
    exit(1);
  }
  if( length(L)<= 0 ) { 
    L->front = L->back = N; 
  }else{ 
    L->front->prev = N; 
    N->next = L->front;
    L->front = N; 
  if(L->index != -1){
    L->index++;
  }
  }
  
  L->length++;
 
  
}

// Insert new element into this List. If List is non-empty,
// insertion takes place after back element.
void append(List L, int data){
  Node N = newNode(data);

   if( L ==NULL ){
      printf("List Error: calling append() on NULL List reference\n");
//       exit(1);
   }
   if( length(L)<= 0 ) { 
      L->front = L->back = N; 
   }else{ 
      L->back->next = N; 
      N->prev = L->back;
      L->back = N; 
   }
  
   L->length++; 
}
  
// Insert new element before cursor.
// Pre: length()>0, index()>=0
void insertBefore(List L, int data){
  if(L->length <= 0 || L->index < 0){
    printf("List Error: calling insertBefore() on empty List reference\n");
    exit(1);
  }

  Node N = newNode(data);

  if (L->index == 0) {
    N->next = L->cursor;
    L->cursor->prev = N;
    L->front = N;
  } else {
    Node temp = L->cursor->prev; //store temp and we dont have to worry about order and losing info
    temp->next = N;
    N->next = L->cursor;
    L->cursor->prev = N;
    N->prev = temp;
  }
  L->length++;
  L->index++;


}
  
// Inserts new element after cursor.
// Pre: length()>0, index()>=0
void insertAfter(List L, int data){
  if(L->length <= 0 || L->index < 0){
    printf("List Error: calling insertAfter() on empty List reference\n");
    exit(1);;
  }
  Node N = newNode(data);

  if (L->index == L->length - 1) {
    L->cursor->next = N;
    N->prev = L->cursor;
    L->back = N;
  }else {
    Node temp = L->cursor->next;
    temp->prev = N;
    N->prev = L->cursor;
    L->cursor->next = N;
    N->next = temp;
  }
  L->length++;  
  }
// Deletes the front element. Pre: length()>0
void deleteFront(List L){
   Node N = NULL;

  if( L==NULL ){
    printf("List Error: calling deleteFront() on NULL List reference\n");
    exit(1);
  }
  if( length(L) <= 0 ){
    printf("List Error: calling deleteFront on an empty List\n");
    exit(1);
  }
  N = L->front;
 if (length(L) == 1 ){ 
    L->front = L->back = NULL;
    L->index--;
    
  }else{
    L->front = L->front->next;
    L->front->prev = NULL;
    L->index--;
  }
  freeNode(&N);
  L->length--;
  
}
  
// Deletes the back element. Pre: length()>0
void deleteBack(List L){ 
 Node N = NULL;
  if( L==NULL ){
    printf("List Error: calling deleteBack() on NULL List reference\n");
    exit(1);
  }
  if( length(L)<=0 ){
    printf("List Error: calling deleteBack on an empty List\n");
    exit(1);
  }
  N = L->back;
  //one node front = back  
  if( length(L) == 1 ) { 
    L->back = L->front = NULL;
    L->index=-1;
  }else{ 
    L->back = L->back-> prev;
    L->back->next = NULL;
    if(index(L) == length(L) -1){
      L->index=-1;
    }
  }
  freeNode(&N);
  L->length--;
}
  
// Deletes cursor element, making cursor undefined.
// Pre: length()>0, index()>=0
void delete(List L){
  Node N = NULL;
  N = L->cursor;
  
  if( L==NULL ){
    printf("List Error: calling delete() on NULL List reference\n");
    exit(1);
   }
  if( length(L)<= 0 && L->index < 0 ){
    printf("List Error: calling delete() on an empty List\n");
    exit(1);
  }else if (L->front == L->back) { //length == 1
    L->front = L->back = NULL;
    L->length = 0;
  }else if (L->cursor == L->front) { //length <= 2 and in front
    L->front = L->cursor->next;
    L->front->prev =NULL;
    L->length--;
  }else if (L->cursor == L->back) {
    L->back = L->cursor->prev;
    L->back->next = NULL;
    L->length--;
  }else { //length >= 3 and in middle
    Node tempPrev = L->cursor->prev;
    Node tempNext = L->cursor->next;

    tempPrev->next = tempNext;
    tempNext->prev =tempPrev;
    L->length--;
    }
  freeNode(&N);
  L->cursor = NULL; //marks as undefined
  L->index = -1; //marks as undefined
  
}
  
// Other operations -----------------------------------------------------------
// prints the L to the file pointed to by out, formatted as a 
// space-separated string.
void printList(FILE* out, List L){
Node N = NULL;

  if( L==NULL ){
    printf("List Error: calling printList() on NULL List reference\n");
    exit(1);
  }

  for(N = L->front; N != NULL; N = N->next){
    fprintf(out, "%d ", N->data);
  }
}

//prints to stdout 
void printListStdOut(List L){
Node N = NULL;

  if( L==NULL ){
    printf("List Error: calling printList() on NULL List reference\n");
    exit(1);
  }

  for(N = L->front; N != NULL; N = N->next){
    printf("%d ", N->data);
  }
  printf("\n");
}
  
// Returns a new List representing the same integer sequence as this
// List.  The cursor in the new list is undefined, regardless of the
// state of the cursor in this List. This List is unchanged.
List copyList(List L){
  List copy = newList();
  
    if(L->length <= 0){
      return(copy);
   
    }else{
      for(moveFront(L); index(L)>=0; moveNext(L)){
      append(copy, get(L));
  }
     moveFront(L);
    return (copy);
      
    }
}


  
  


  


