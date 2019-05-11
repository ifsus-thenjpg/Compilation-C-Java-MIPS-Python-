#!/usr/bin/env python
# coding: utf-8

# In[1]:


#!/usr/bin/env python3
# Name: Susanna Morin (sumorin)
# Bianca Reyes (bdreyes)
'''
This program imports sequenceAnalysis module. 
It is responsible for preparing the summaries and final display of the data.
output:
sequence length = 3.14 Mb

GC content = 60.2%

UAA : -  32.6 (  1041)
UAG : -  38.6 (  1230)
UGA : -  28.8 (   918)
GCA : A  14.1 ( 10605)
GCC : A  40.5 ( 30524)
GCG : A  30.5 ( 22991)
GCU : A  14.9 ( 11238)
UGC : C  67.2 (  4653)
UGU : C  32.8 (  2270)

...'''



from sequenceAnalysis import NucParams, FastAreader
def main ():
    '''A program that outputs the summaries and final display of genome parsed in'''
    myReader = FastAreader()                 #make sure to change this to use stdin
    myNuc = NucParams()                                     #instantiate new NucParams object
    for head, seq in myReader.readFasta() :                 #unpacks the header, sequence from the tuple
        myNuc.addSequence(seq)                              #adds only the sequence ot the myNuc object
                                                      
    
    #calculate gc content and sequence length
    nucsMb = myNuc.nucCount()/1000000
    
    c = myNuc.nucComp['C']
    g = myNuc.nucComp['G']
    
    gcTotal = c+g

    gcContent = (gcTotal/myNuc.nucCount())*100
    print('sequence length = {0:.2f} Mb \n\n' 'GC content = {1:.1f} % \n'.format(nucsMb, gcContent))
    
    #sort codons in alpha order, by Amino Acids (values in dict)
    # calculate relative codon usage for each codon and print  
    for codon, aa in  sorted(myNuc.rnaCodonTable.items(), key = lambda value: (value[1], value[0])):
        val = myNuc.codonComp.get(codon)/myNuc.aaComp.get(aa) 
        print ('{:s} : {:s} {:5.1f} ({:6d})'.format(codon, aa, val*100, myNuc.codonComp.get(codon)))
    

if __name__ == "__main__":
    main()
    


# In[ ]:




