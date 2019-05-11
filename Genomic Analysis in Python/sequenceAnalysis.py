#!/usr/bin/env python
# coding: utf-8

# In[ ]:


#!/usr/bin/env python3
# Name: Susanna Morin (sumorin)
# “None”
'''A module containing 3 classes: 
    NucParams: keeps track of amino acids, nucleotides, and codons (RNA/DNA) from complete genome
    ProteinParams: computes molecular weight, isoelectric point, net charge, molar extinction and mass extinction 
    using protein composotion passed in from user input
    FastAreader: reads from a FastA file or from stdin '''
import sys
from itertools import zip_longest
import numpy as np
from collections import OrderedDict
class NucParams:
    '''A class that keeps track of amino acids, nucleotides, and codons (RNA/DNA) from complete genome'''
    rnaCodonTable = {
    # RNA codon table
    # U
    'UUU': 'F', 'UCU': 'S', 'UAU': 'Y', 'UGU': 'C',  # UxU
    'UUC': 'F', 'UCC': 'S', 'UAC': 'Y', 'UGC': 'C',  # UxC
    'UUA': 'L', 'UCA': 'S', 'UAA': '-', 'UGA': '-',  # UxA
    'UUG': 'L', 'UCG': 'S', 'UAG': '-', 'UGG': 'W',  # UxG
    # C
    'CUU': 'L', 'CCU': 'P', 'CAU': 'H', 'CGU': 'R',  # CxU
    'CUC': 'L', 'CCC': 'P', 'CAC': 'H', 'CGC': 'R',  # CxC
    'CUA': 'L', 'CCA': 'P', 'CAA': 'Q', 'CGA': 'R',  # CxA
    'CUG': 'L', 'CCG': 'P', 'CAG': 'Q', 'CGG': 'R',  # CxG
    # A
    'AUU': 'I', 'ACU': 'T', 'AAU': 'N', 'AGU': 'S',  # AxU
    'AUC': 'I', 'ACC': 'T', 'AAC': 'N', 'AGC': 'S',  # AxC
    'AUA': 'I', 'ACA': 'T', 'AAA': 'K', 'AGA': 'R',  # AxA
    'AUG': 'M', 'ACG': 'T', 'AAG': 'K', 'AGG': 'R',  # AxG
    # G
    'GUU': 'V', 'GCU': 'A', 'GAU': 'D', 'GGU': 'G',  # GxU
    'GUC': 'V', 'GCC': 'A', 'GAC': 'D', 'GGC': 'G',  # GxC
    'GUA': 'V', 'GCA': 'A', 'GAA': 'E', 'GGA': 'G',  # GxA
    'GUG': 'V', 'GCG': 'A', 'GAG': 'E', 'GGG': 'G'  # GxG
    }
    dnaCodonTable = {key.replace('U','T'):value for key, value in rnaCodonTable.items()}

    def __init__ (self, inString=''):
        self.seq = inString.upper()                                                 #takes in optional sequence parameter, empty string is default
        
        #creates a dictionary of amino acids, nucleotide bases, codons, all with initial values set to '0'
        self.aaComp = dict.fromkeys(NucParams.rnaCodonTable.values(),0)
        self.nucComp = dict.fromkeys('ACGTNU',0)
        self.codonComp = dict.fromkeys(NucParams.rnaCodonTable.keys(),0)
        
                
        #processes seq given as optional parameter only if non empty string
        if not self.seq:                                                             #self.seq will evaluate to false if string is empty
            self.addSequence(self.seq)
   
    def addSequence (self, inSeq):
        '''This method must accept new sequences, from the {ACGTUN} alphabet, and can be presumed to start in frame 1.'''
        self.nucComposition(inSeq)      #counts nucleotides                                      
        self.codonComposition(inSeq)    #counts codons
        self.aaComposition(inSeq)       #counts amino acids
        
    def aaComposition(self, inSeq):
        '''This method will return a dictionary of counts over the 20 amino acids and stop codons.The codon is decoded first.'''        
        for codon in range(0,len(inSeq),3):
            actual = inSeq[codon:codon+3]
            if actual in NucParams.rnaCodonTable.keys() or NucParams.dnaCodonTable.keys():                #check for key in both codon dictionaries
                if all(char in "ACGUT" for char in actual) and len(actual) == 3:
                    codon2AA = NucParams.rnaCodonTable.get(actual) or NucParams.dnaCodonTable.get(actual)  #translates codon to aa
                    #test: print('this is translation {0}: '.format(codon2AA))
                    self.aaComp[codon2AA] = self.aaComp.get(codon2AA) +1                                 #adds 1 to existing count
                    #test: print('this is amino acid count  {0}: '.format(self.aaComp.get(codon2AA)))       
        
        return self.aaComp
    def nucComposition(self, inSeq):
        '''This method returns a dictionary of counts of valid nucleotides found in the analysis. (ACGTNU}. 
        RNA nucleotides should be counted as RNA nucleotides.DNA nucleotides should be counted as DNA nucleotides. 
        N bases found will be counted also. Invalid bases will be ignored in this dictionary.'''        
        for nuc in inSeq:
            if nuc in 'ACGTNU':
                self.nucComp[nuc] = self.nucComp.get(nuc) +1                      #increments the previous count if valid base in dict
                #test: print('nuc count: {0}'.format(self.nucComp.get(nuc)))  
                #test: print('this is the nuc dict: {0}'.format(self.nucComp))
        return self.nucComp
    def codonComposition(self, inSeq):
        '''This dictionary returns counts of codons. Presume that sequences start in frame 1, accept the alphabet {ACGTUN} and 
        store codons in RNA format, along with their counts.Codons found with invalid bases and codons with N bases are discarded.
            All codon counts are stored as RNA codons, even if the input happens to be DNA. Discarded codons will not alter the frame of   subsequent codons.'''
        
        for codon in range(0,len(inSeq),3):
            actual = inSeq[codon:codon+3]                                             
            if 'T' in actual and len(actual) == 3:                                    #only enters this suite if DNA codon, contains "T"
                tCount = actual.count('T')
                dna2RNA = actual.replace('T', 'U', tCount)                   
                self.codonComp[dna2RNA] = self.codonComp.get(dna2RNA) +1               
                #test: print(self.codonComp.get(dna2RNA))
            elif all(char in "ACGU" for char in actual) and len(actual) == 3:         #checks that all chars in codon are valid
                    self.codonComp[actual] = self.codonComp.get(actual) +1            #if invalid chars in codon, it will discard the codon 
                    #test: print(self.codonComp.get(actual))               
        
        return self.codonComp
    def nucCount(self):
        '''This returns an integer value, summing every valid nucleotide {ACGTUN} found. 
        This value will exactly equal the sum over the nucleotide composition dictionary.'''        
        return sum(self.nucComp.values())    

class FastAreader :
    ''' 
    Define objects to read FastA files.
    
    instantiation: 
    thisReader = FastAreader ('testTiny.fa')
    usage:
    for head, seq in thisReader.readFasta():
        print (head,seq)
    '''
    def __init__ (self, fname=''):
        '''contructor: saves attribute fname '''
        self.fname = fname
            
    def doOpen (self):
        ''' Handle file opens, allowing STDIN.'''
        if self.fname is '':
            return sys.stdin
        else:
            return open(self.fname)
        
    def readFasta (self):
        ''' Read an entire FastA record and return the sequence header/sequence'''
        header = ''
        sequence = ''
        
        with self.doOpen() as fileH:
            
            header = ''
            sequence = ''
            
            # skip to first fasta header
            line = fileH.readline()
            while not line.startswith('>') :
                line = fileH.readline()
            header = line[1:].rstrip()

            for line in fileH:
                if line.startswith ('>'):
                    yield header,sequence
                    header = line[1:].rstrip()
                    sequence = ''
                else :
                    sequence += ''.join(line.rstrip().split()).upper()

        yield header,sequence
class ProteinParam :
    '''Class computes molecular weight, isoelectric point, net charge, molar extinction and mass extinction using 
    protein composotion passed in from user input. '''    
# These tables are for calculating:
#     molecular weight (aa2mw), along with the mol. weight of H2O (mwH2O)
#     absorbance at 280 nm (aa2abs280)
#     pKa of positively charged Amino Acids (aa2chargePos)
#     pKa of negatively charged Amino acids (aa2chargeNeg)
#     and the constants aaNterm and aaCterm for pKa of the respective termini


# As written, these are accessed as class attributes, for example:
# ProteinParam.aa2mw['A'] or ProteinParam.mwH2O

    aa2mw = {
        'A': 89.093,  'G': 75.067,  'M': 149.211, 'S': 105.093, 'C': 121.158,
        'H': 155.155, 'N': 132.118, 'T': 119.119, 'D': 133.103, 'I': 131.173,
        'P': 115.131, 'V': 117.146, 'E': 147.129, 'K': 146.188, 'Q': 146.145,
        'W': 204.225, 'F': 165.189, 'L': 131.173, 'R': 174.201, 'Y': 181.189
        }

    mwH2O = 18.015
    aa2abs280= {'Y':1490, 'W': 5500, 'C': 125}

    aa2chargePos = {'K': 10.5, 'R':12.4, 'H':6}
    aa2chargeNeg = {'D': 3.86, 'E': 4.25, 'C': 8.33, 'Y': 10}
    aaNterm = 9.69
    aaCterm = 2.34
    
    

    def __init__ (self, protein):
        '''Computes and saves the aaComposition dictionary as an attribute.'''  
        validAAs = ['A', 'C', 'D', 'E','F', 'G', 'H', 'I', 'L', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'Y', 'W']
        upperAA = protein.upper()                                 #convert string into uppercase
        pureAAs = ''.join(aa for aa in upperAA if aa in validAAs) #remove unwanted characters
        noDup = "".join(OrderedDict.fromkeys(pureAAs))            #remove duplicates                     
        self.aaComp = dict.fromkeys(validAAs, 0)                  #self. puts the dictionary into namespace of object
        j = 0
        for values in self.aaComp:
            while j < len(noDup):
                self.aaComp[noDup[j]] = pureAAs.count(noDup[j])
                j += 1
        self.noDup = noDup                                       #makes string of valid aa available to other methods
        
    def aaCount (self):
        '''This method will return a single integer count of valid amino acid characters found.'''
        return sum(self.aaComp.values())                        #sum() returns the sum of all items in an iterable


    def pI (self):
        '''Estimates Isolelectric point by finding the particular pH that yields a neutral net Charge (close to 0),
        accurate to 2 decimal points.'''
        lowest = 10000000
        for pH in np.arange(0.0,14.0,.01):                                #0-14 range
            current = self._charge_(pH)
            if current < lowest and current >= 0 :
                lowest = current
                lowestPH = pH
        return lowestPH

    def aaComposition (self) :
        '''This method is to return a dictionary keyed by single letter Amino acid code, 
        and having associated values that are the counts of those amino acids in the sequence.'''
        return self.aaComp

    def _charge_ (self, pH):
        '''This method calculates the net charge on the protein at a specific pH (specified as a parameter of this method).'''
        posSum = 0
        for aa, pCharge in ProteinParam.aa2chargePos.items():
            posSum += self.aaComp.get(aa)*(10**pCharge/(10**pCharge + 10**pH))
        
        negSum = 0
        for aa, nCharge in ProteinParam.aa2chargeNeg.items():
            negSum += self.aaComp.get(aa)*(10**pH/(10**nCharge + 10**pH)) 
        
        nTerm = 10**ProteinParam.aaNterm / (10**ProteinParam.aaNterm + 10**pH)
        cTerm = 10**pH / (10**ProteinParam.aaCterm + 10**pH)
        return ((posSum + nTerm ) - (negSum + cTerm ))

    def molarExtinction (self):
        '''Estimates the molar extinction coefficient of a protein from knowledge of its amino acid composition (at 280 nm).'''     
        y = self.aaComp.get('Y')*ProteinParam.aa2abs280.get('Y')    #calculates the product of number of aa and extinction coefficient
        w = self.aaComp.get('W')*ProteinParam.aa2abs280.get('W')
        c = self.aaComp.get('C')*ProteinParam.aa2abs280.get('C')  
        return y+w+c                                                #sums up product for each Y,W,C present in protein
        
    def massExtinction (self):
        '''Calculates the Mass extinction coefficient from the Molar Extinction coefficient by dividing by the 
        molecularWeight of the corresponding protein (at 280 nm).'''
        myMW =  self.molecularWeight()
        return self.molarExtinction() / myMW if myMW else 0.0

    def molecularWeight (self):
        '''Calculates the molecular weight (MW) of the protein sequence.'''
        r = 0
        result = ProteinParam.mwH2O
        for aa in self.aaComp:
            while r < len(self.noDup):
                result += self.aaComp.get(self.noDup[r])*(ProteinParam.aa2mw.get(self.noDup[r])- ProteinParam.mwH2O)
                r += 1  
        return result

