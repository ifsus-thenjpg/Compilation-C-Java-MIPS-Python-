############################################################################ 
# Created by:    Morin, First Susanna
#                Sumorin
#                15 November 2018 
# 
# Assignment:    Lab 4: ASCII Decimal to 2SC
#                CMPE 012, Computer Systems and Assembly Language 
#                UC Santa Cruz, Fall 2018 
#  
# Description:   Read a string input and convert numerical ASCII characters into a two’s complement binary number. 
#                Print a two’s complement integer and two's compliment binary number 
#  
# Notes:        This program is intended to be run from the MARS IDE. 
#		Before running this program, enter program arguments in Execute Tab, in Program Aguments section 
#		of Text Segment window by first enabling "Program arguments provided in MIPS" under Settings
############################################################################ 

#####pseudo code#####
#read program arguments
#print program arguments as an ASCII string 

##print the sum in decimal 
#split the ASCII string into two arguments (val1, val2)
#parse val1 and val2 into [1 to 3 ASCII characters] [-/or empty][0-9][0-9]
#subtract: subtract 48 from each ASCII char
#decimal: 
#   char1 = 10^1 *char(1)
#   char2 =  10^0 *char(2)
#  decimal = char1 + char2
# negative: multiply decimal by -1
#  if char0 == ASCII code 45 branch to decimal 
# add decimal1 to decimal2 
#print 

#print sum in 2sc binary 
# result = decimalSum;
#do while (result !=0)
#   result = result/2
#   rem = result %2
#   store remainder as string
#depending on MSB sign extend to 32 bits 
#print 


#Register Usage#
#$t0 user input
#$t1 user input
#    pointer to address of the array
#$t2 stores 1 byte of data from program argument, 
#    temp reg that holds the sum 
#    holds value of the remainder (hi register)
#$t3 return val for sle condition 
#$t4 result variable intitialized at 0 
#    holds the result of logical operator (true/false), 
#    holds the byte loaded from the array of sum
#$t5 for computation of digit (ASCII to decimal),
#    hold result of logical operation (true/false)
#    flag for neg sign 
#$t6 flag for negative sign
#    holds the ascii value of decimal, holds the value of quotient (lo register),  
#    holds the result of the and operation between mask and sum
#$t7 loop counter 
#    holds the mask 
#$s0 sum of two values
#$s1 first value 
#$s2 second value 
.text

           lw $t0 ($a1)				     #loads the address of the first program argument and stores it into $t0
           lw $t1 4($a1)		             #loads the address of the second program argument and stores it into $t1 
  
print_args:
           la $a0 output_decimal			     #will output a string 
           jal PrintStr					     #calls on the sub program that executes the syscall for print string 
                        
           la $a0 ($t0)                                     #loads the address for first program argument value 
           jal PrintStr                                     #calls on the sub program that executes the syscall for print string 
   
           la $a0 space                                     #enters a space between the two program arguments represented as decimals for output 
           jal PrintStr                                     #calls on the sub program that executes the syscall for print string 
   
           la $a0 ($t1)                                     #loads the address for the second program argument 
           jal PrintStr                                     #calls on the sub program that executes the syscall for print string 
      
           la $a0 newline                                   #enters a newline after the arguments 
           jal PrintStr                                     #calls on the sub program that executes the syscall for print string 

loop_arg1:
           lb   $t2 ($t0)		             #loads a byte of data starting from memory location $t0 into memory location at $t2
           beq  $t2 0 neg1			     #loop condition: if we are at a null char ($t3 == 0) then we will end loop, otherwise continue 
           nop					     #nop added after branch instruction to prevent unpredictable program execution 
           beq  $t2 45 negative1   		     #if $t4 stores a "-" in ASCII(45 in deciml)sign then branch to the label for negative values 
           nop					     #nop added after branch instruction to prevent unpredictable program execution 
           sub  $t5 $t2 48			     #subtract 48 from ascii val to get first digit 
           mul  $s1 $s1 10                  	     # result = result * (10+digit)
           add  $s1 $s1 $t5
           addi $t0 $t0 1			     #increment the "loop counter"
           b loop_arg1			     	     #continue the loop 

  
neg1:
           beq $t6 1 signed_rep1  
           nop
reset_registers:
           li $t5 0				     #loads the value 0 into the temp reg 5 (result variable)
           li $t6 0				     #resets $t6 (negative flag) to store value 0              

loop_arg2:
           lb   $t2 ($t1)		             #loads a byte of data starting from memory location $t0 into memory location at $t2
           beq  $t2 0  neg2			     #loop condition: if we are at a null char ($t3 == 0) then we will end loop, otherwise continue 
           nop					     #nop added after branch instruction to prevent unpredictable program execution 
           beq  $t2 45 negative2   		     #if $t4 stores a "-" in ASCII(45 in deciml)sign then branch to the label for negative values 
           nop					     #nop added after branch instruction to prevent unpredictable program execution 
           sub  $t5 $t2 48			     #subtract 48 from ascii val to get first digit 
           mul  $s2 $s2 10                   	     # result = result * (10+digit)
           add  $s2 $s2 $t5
           addi $t1 $t1 1			     #increment the "loop counter"
           b loop_arg2			     	     #continue the loop       
     
neg2: 
           beq $t6 1 signed_rep2                   
           nop  

sum:
           add  $s0 $s1 $s2       		#add the two decimal numbers and store in $s0 register     

           li $t0 0				#restore the temp reg - 
           li $t1 0			        #restore the temp reg - address of array 
           li $t2 0 				#restore the remp reg - sum 
	   la $t1 array		        	#load address of array into temp reg	    
	   move $t2 $s0      		        #moves the sum into temp reg 
	   li   $t5 0				#restores $t5 to 0 
	   slti $t5 $s0 0 			#if sum < 0, store 1 in $t0, meaning it is negative, otherwise continue 
           beq  $t5 1   add_neg_sign           #if result of AND logical operation is 1 then sum is neg and we mult by -1 and set the neg flag as 1 
init_array: 
           sgt $t4 $t2 99 		#if the sum is greater than 99 we know it is 3 digits (100-999) except this program only has a range of [-128,126]
           beq $t4 1   three_dig
           sgt $t4 $t2 9 		#if the sum is greater than 9 we know it is 2 digits (10-99)
           beq $t4 1   two_dig		
           slti $t4 $t2 10	        #if the sum is less than 10 we know it is 1 digit (0-9)      
           beq $t4 1   one_dig
           b print_decimal 
three_dig:
 	   div  $t2 $t2 100 		#divide the sum by 100 
           mflo $t6  			#save the quotient 
           addi $t6 $t6 48		#make the decimal an ASCII value 
           mfhi $t2			#initialize the new sum which is the remainder 
           sb   $t6 ($t1) 		#store the mflo + 48 into the array mem
 	   addi $t1 $t1 1		#increment the offset 
 	   b init_array 
two_dig:
 	   div  $t2 $t2 10 		#divide the sum by 10
           mflo $t6  			#save the quotient 
           addi $t6 $t6 48		#make the decimal an ASCII value 
           mfhi $t2			#initialize the new sum which is the remainder 
           sb   $t6 ($t1) 		#store the mflo + 48 into the array mem
 	   addi $t1 $t1 1		#increment the offset 
 	   b init_array 

one_dig:          
	   addi $t2 $t2 48 		#convert from decimal to ASCII rep 
	   sb   $t2 ($t1)
print_decimal:
	   la $a0 output_sum           #will output a string 
           jal PrintStr	 
	   
	   la $a0 array 		#load address to the array 
	   jal PrintStr 		#call on subroutine that executes print string        
	
           la $a0 newline        	#enters a newline after the arguments 
           jal PrintStr                #calls on the sub program that executes the syscall for print string 	
              
	   la $a0 output_2SC		#load address to the prompt for 2sc
	   jal PrintStr 		#call on subroutine that executes print string  
	   li  $t7 0x80000000          #initialize mask 
binary_converter:   
	   beqz $t7 EC
	   and  $t6 $s0 $t7	        #use logical and opetation to isolate bits - bit by bit 
	   
	   beqz $t6 print_0   		#print 0 
	   b print_1			#else print 1

print_0:
	   li $a0 48 			#prints 0
	   jal PrintChar
	   srl $t7 $t7 1		#increments the offset
	   b binary_converter

print_1:
    	   li $a0 49 			#prints 1
	   jal PrintChar
	   srl $t7 $t7 1		#increments the offset 
	   b binary_converter

EC:
	   li $t1 0 			#will be used to increment the counter 
	   li $t2 0 			#will be used for the array location 
	   li $t3 0 	   		#will be used as logical return value 
	   li $t4 0 			#will be used to store the the current value being loaded from the array 
	   la $t1 array 		#loads the address to the array containing the sum 
	   
	   la $a0 newline          	#enters a newline after the arguments 
           jal PrintStr                #calls on the sub program that executes the syscall for print string 	
           
           la $a0 output_morse	        #will output a string 
           jal PrintStr		        #calls on the sub program that executes the syscall for print string 
           
morse_code:
	   lb   $t4 ($t1)    		#loads the address of the array into temp reg 
           beq  $t4 45 print_neg       
	   beq  $t4 48 print_morse0
	   beq  $t4 49 print_morse1
	   beq  $t4 50 print_morse2
	   beq  $t4 51 print_morse3
	   beq  $t4 52 print_morse4
	   beq  $t4 53 print_morse5
	   beq  $t4 54 print_morse6
	   beq  $t4 55 print_morse7
	   beq  $t4 56 print_morse8
	   beq  $t4 57 print_morse9
	   beq  $t4 0  end_morse
	   	   
print_neg:
	   la $a0 morse_neg	#loads address where the neg sign is stored 
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop 
	   
           
print_morse0:
	   la $a0 morse0	#loads address where morse symbol for 0 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop 

print_morse1:
	   la $a0 morse1	#loads address where morse symbol for 1 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop 

print_morse2:
	   la $a0 morse2	#loads address where morse symbol for 2 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop 

print_morse3: 
	   la $a0 morse3	#loads address where morse symbol for 3 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse4:
	   la $a0 morse4	#loads address where morse symbol for 4 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse5:
	   la $a0 morse5	#loads address where morse symbol for 5 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse6:
	   la $a0 morse6	#loads address where morse symbol for 6 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse7:
	   la $a0 morse7	#loads address where morse symbol for 7 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse8:
	   la $a0 morse8	#loads address where morse symbol for 8 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop

print_morse9:
	   la $a0 morse9	#loads address where morse symbol for 9 is stored in mem
	   jal PrintStr		#prints the string 
	   addi $t1 $t1 1	#increment 1 byte 
	   
	   la $a0 space 	#loads address where the space string is stored
	   jal PrintStr		#prints the string
	   
	   b morse_code	        #branches back to the morse loop	    

end_morse:
           la $a0 newline      #enters a newline after the arguments 
           jal PrintStr        #calls on the sub program that executes the syscall for print string 
	   b Exit

#subprograms
Exit:  
           li $v0 10	        #$v0 stores 10
           syscall             #loads service 10 to terminate program 

add_neg_sign:
	   li   $t0 0x2d	#stores a "-" in temp reg 
	   sb   $t0 ($t1)      #stores the neg sign in the array  
	   mul  $t2 $t2 -1
	   addi $t1 $t1 1      #increment the reg 
	   b init_array

PrintStr: 
           li $v0 4           #$v0 stores 4
           syscall	       #loads service 4 to print string 
           jr $ra

        
PrintChar: 
           li $v0 11          #$v0 stores service 11
           syscall	      #loads service 11 to print char
           jr $ra   

         
negative1:   
           li   $t6 1	        #stores 1 in $t6 and we will use this as a flag 
           addi $t0 $t0 1      #increment the "loop counter"
           b loop_arg1
          
negative2:   
          li   $t6 1	 	#stores 1 in $t6 and we will use this as a flag 
          addi $t1 $t1 1	#increment the "loop counter"
          b loop_arg2          
          
signed_rep1:
          sub $s1 $zero $s1	#makes the value stored in $s1 negative by subtracting from zero 
          b reset_registers    #jumps back to given address       
          
signed_rep2:
          sub $s2 $zero $s2    #makes the value stored in $s1 negative by subtracting from zero 
          b sum          

.data 
output_decimal: .asciiz  "You entered the decimal numbers: \n"
space: .asciiz " "
newline: .asciiz "\n"
output_sum: .asciiz "\nThe sum in decimal is: \n"
output_2SC: .asciiz "\nThe sum in two's complement binary is: \n"
output_morse: .asciiz "\nThe sum in Morse code is: \n"
array: .space 5 
morse_neg: .byte 0x2d 0x0
morse0: .byte 0x2d 0x2d 0x2d 0x2d 0x2d 0x0
morse1: .byte 0x2e 0x2d 0x2d 0x2d 0x2d 0x0
morse2: .byte 0x2e 0x2e 0x2d 0x2d 0x2d 0x0
morse3: .byte 0x2e 0x2e 0x2e 0x2d 0x2d 0x0
morse4: .byte 0x2e 0x2e 0x2e 0x2e 0x2d 0x0
morse5: .byte 0x2e 0x2e 0x2e 0x2e 0x2e 0x0
morse6: .byte 0x2d 0x2e 0x2e 0x2e 0x2e 0x0
morse7: .byte 0x2d 0x2d 0x2e 0x2e 0x2e 0x0
morse8: .byte 0x2d 0x2d 0x2d 0x2e 0x2e 0x0
morse9: .byte 0x2d 0x2d 0x2d 0x2d 0x2e 0x0


