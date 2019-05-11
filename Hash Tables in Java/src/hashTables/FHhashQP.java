package hashTables;


// FHhashQP class --------------------------------------------
public class FHhashQP<E>
{
   protected static final int ACTIVE = 0; //occupied
   protected static final int EMPTY = 1; //available
   protected static final int DELETED = 2;
   
   static final int INIT_TABLE_SIZE = 97;
   static final double INIT_MAX_LAMBDA = 0.49;
   
   protected HashEntry<E>[] mArray;
   protected int mSize;
   protected int mLoadSize;
   protected int mTableSize;
   protected double mMaxLambda;
   
   // public methods ---------------------------------

    /**
     * overloaded constructor
     * @param tableSize takes in a table size passed in by the table generator and creates
     * a hash table with prime number size
     */
   public FHhashQP(int tableSize)
   {
      mLoadSize = mSize = 0;
      if (tableSize < INIT_TABLE_SIZE)
         mTableSize = INIT_TABLE_SIZE;
      else
         mTableSize = nextPrime(tableSize);

      allocateArray();  // uses mTableSize;
      mMaxLambda = INIT_MAX_LAMBDA;
   }

   public FHhashQP()
   {
      this(INIT_TABLE_SIZE);
   }

    /**
     * Inserts object into the hash table
     * @param x takes in a generic object that will be placed in the array using QP
     * @return true if insert was successful , otherwise returns false
     */
   public boolean insert( E x)
   {
      int bucket = findPos(x);

      if ( mArray[bucket].state == ACTIVE )
         return false;

      mArray[bucket].data = x;
      mArray[bucket].state = ACTIVE;
      mSize++;
      
      // check load factor
      if( ++mLoadSize > mMaxLambda * mTableSize )
         rehash();

      return true;
   }

    /**
     * removes object from the hash table
     * @param x generic object to be removed
     * @return true if removed, otherwise false
     */
   public boolean remove( E x )
   {
      int bucket = findPos(x);

      if ( mArray[bucket].state != ACTIVE )
         return false;

      mArray[bucket].state = DELETED;
      mSize--; // mLoadSize not dec'd because it counts any non-EMP location
      return true;
   }

    /**
     * checks if generic object is in the hash table
     * @param x object to check
     * @return true if the generic object is contained in the hash table
     */
   public boolean contains(E x ) 
   {
      return mArray[findPos(x)].state == ACTIVE;
   }

    /**
     * keeps track of the size of hash table
     * @return size of hash table
     */
   public int size()  { return mSize; }

    /**
     * Clears this hashtable
     */
   void makeEmpty()
   {
      int k, size = mArray.length;

      for(k = 0; k < size; k++)
         mArray[k].state = EMPTY;
      mSize = mLoadSize = 0;
   }

    /**
     * sets the maximum load factor
     * @param lam load factor
     * @return true if update was successful , otherwise value does not meet validation
     */
   public boolean setMaxLambda( double lam )
   {
      if (lam < .1 || lam > INIT_MAX_LAMBDA )
         return false;
      mMaxLambda = lam;
      return true;
   }
   
   // protected methods of class ----------------------

    /**
     * Uses quadratic probing to find available position
     * @param x takes in an object as parameter
     * @return will return the next available location -- or else it will return the location with x in it
     */
   int findPos( E x )
   {
      int kthOddNum = 1;
      int index = myHash(x);

      while ( mArray[index].state != EMPTY
         && !mArray[index].data.equals(x) )
      {
         index += kthOddNum; // k squared = (k-1) squared + kth odd #
         kthOddNum += 2;     // compute next odd #
         if ( index >= mTableSize )
            index -= mTableSize;
      }
      return index;
   }

   /**
    * Move elements in original table to a larger table.***
    */
   protected void rehash()
   {
      // we save old list and size then we can reallocate freely
      HashEntry<E>[] oldArray = mArray;
      int k, oldTableSize = mTableSize;;

      mTableSize = nextPrime(2*oldTableSize);
      
      // allocate a larger, empty array
      allocateArray();  // uses mTableSize;

      // use the insert() algorithm to re-enter old data
      mSize = mLoadSize = 0;
      for(k = 0; k < oldTableSize; k++)
         if (oldArray[k].state == ACTIVE)
            insert( oldArray[k].data );
   }

    /**
     * hash function that guarantees the hash value will fit in the hash table , and will be a positive
     * @param x object to be mapped to a hash value
     * @return hash value
     */
   protected int myHash(E x)
   {
      int hashVal;

      hashVal = x.hashCode() % mTableSize;
      if(hashVal < 0)
         hashVal += mTableSize;

      return hashVal;
   }

    /**
     * method takes in a value and if not prime, will return the next prime after number given
     * @param n number that will be used to calculate the next prime or return itself if prime
     * @return  itself, or next prime
     */
   protected static int nextPrime(int n)
   {
      int k, candidate, loopLim;

      // loop doesn't work for 2 or 3
      if (n <= 2 )
         return 2;
      else if (n == 3)
         return 3;

      for (candidate = (n%2 == 0)? n+1 : n ; true ; candidate += 2)
      {
         // all primes > 3 are of the form 6k +/- 1
         loopLim = (int)( (Math.sqrt((double)candidate) + 1)/6 );

         // we know it is odd.  check for divisibility by 3
         if (candidate%3 == 0)
            continue;

         // now we can check for divisibility of 6k +/- 1 up to sqrt
         for (k = 1; k <= loopLim; k++)
         {
            if (candidate % (6*k - 1) == 0)
               break;
            if (candidate % (6*k + 1) == 0)
               break;
         }
         if (k > loopLim)
            return candidate;
      }
   }

    /**
     * allocates array that makes the hash table
     */
   void allocateArray()
   {
      int k;
      
      mArray = new HashEntry[mTableSize];
      for (k = 0; k < mTableSize; k++)
         mArray[k] = new HashEntry<E>();
   }
}


