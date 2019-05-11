//sumorin

#ifndef LIST_H
#define LIST_H

// Exported Types --------------------------------------------------------------
typedef struct ListObj* List;

// Constructors-Destructors ---------------------------------------------------
List newList(void);
//Constructor
//Creates a new empty list.
void freeList(List *pL);
// frees all heap memory associated with its List* argument,
// and sets *pL to NULL
// Access functions -----------------------------------------------------------
int length(List L);
// Access functions
// Returns the number of elements in this List
int index(List L);
// If cursor is defined, returns the index of the cursor element,
// otherwise returns -1.
int front(List L);
 // Returns front element. Pre: length()>0
int back(List L);
// Returns back element. Pre: length()>0
int get(List L);
// Returns cursor element. Pre: length()>0, index()>=0
int equals(List A, List B);
// Returns true if and only if this List and L are the same
// integer sequence. The states of the cursors in the two Lists
// are not used in determining equality.
// Manipulation procedures ----------------------------------------------------
void clear(List L);
// Resets this List to its original empty state.
void moveFront(List L);
// If List is non-empty, places the cursor under the front element,
// otherwise does nothing.
void moveBack(List L);
// If List is non-empty, places the cursor under the back element,
// otherwise does nothing.
void movePrev(List L);
// If cursor is defined and not at front, moves cursor one step toward
// front of this List, if cursor is defined and at front, cursor becomes
// undefined, if cursor is undefined does nothing.
void moveNext(List L);
// If cursor is defined and not at back, moves cursor one step toward
// back of this List, if cursor is defined and at back, cursor becomes
// undefined, if cursor is undefined does nothing.
void prepend(List L, int data);
// Insert new element into this List. If List is non-empty,
// insertion takes place before front element.
void append(List L, int data);
// Insert new element into this List. If List is non-empty,
    // insertion takes place after back element.
void insertBefore(List L, int data);
// Insert new element before cursor.
// Pre: length()>0, index()>=0
void insertAfter(List L, int data);
// Inserts new element after cursor.
// Pre: length()>0, index()>=0
void deleteFront(List L);
 // Deletes the front element. Pre: length()>0
void deleteBack(List L);
// Deletes the back element. Pre: length()>0
void delete(List L);
// Deletes cursor element, making cursor undefined.
// Pre: length()>0, index()>=0
//delete current
void setIndex(List L, int index);
// Other operations -----------------------------------------------------------
void printList(FILE* out, List L);
// prints the L to the file pointed to by out, formatted as a
// space-separated string.
void printListStdOut(List L);
//prints a list to std out
List copyList(List L);
// Returns a new List representing the same integer sequence as this
// List.  The cursor in the new list is undefined, regardless of the
// state of the cursor in this List. This List is unchanged.
#endif
