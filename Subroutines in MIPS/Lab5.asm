############################################################################ 
# Created by:    Morin, First Susanna
#                Sumorin
#                30 November 2018 
# 
# Assignment:    Lab 5: Subroutines
#                CMPE 012, Computer Systems and Assembly Language 
#                UC Santa Cruz, Fall 2018 
#  
# Description:   A game that prompts the user to enter a given string in the alotted time 
#                if the user fails to meet the time requirement, the game is over. 
#  
# Notes:        This program is intended to be run from the MARS IDE. 
############################################################################ 

#Register Usage
#$t0 address of type prompt to be printed to user
#$t1 low order bits from time stamp (start time)
#$t2 low order bits from time stamp (end time)
#$t3 time allowed for response 
#$t4 total time user spent on input 
#$t5 address of user input 
#$t6 current char of string 
#$t7 current char of user input 
#$t8 holds conditional value 
#$t9 holds conditional val

#--------------------------------------------------------------------
# give_type_prompt
#
# input: $a0 - address of type prompt to be printed to user
#
# output: $v0 - lower 32 bit of time prompt was given in milliseconds
#--------------------------------------------------------------------
.text 
give_type_prompt:
	
	subi $sp $sp 4		#decrement the sp to allow for 4 bytes of data below original sp address location 
	sw   $ra ($sp) 		#push $ra into the stack 
			
	move $t0 $a0 		#moves input into a temp register 
	
	la   $a0 prompt 	#loads prompt string
	li   $v0 4		#$v0 stores service 4 to store strings
        syscall		
	
	la   $a0 ($t0)		#prints the string the user will need to type 
	li   $v0 4		#$v0 stores service 4 to store strings 
        syscall
	
	li   $v0 30 
	syscall 		#time stamps the current time 	 - start time 	
	
	move $v0 $a0		#move the lower order bits to return var
	
	lw   $ra ($sp)		#loads the ra from memory 
	addi $sp $sp 4		#increments the stack pointer back to original spot 
	jr   $ra 		#returns to the return address 
	
.data 
prompt: .asciiz "Type Prompt: "
#--------------------------------------------------------------------
# check_user_input_string
#
# input: $a0 - address of type prompt printed to user 
#  	 $a1 - time type prompt was given to user
# 	 $a2 - contains amount of time allowed for response
#
# output: $v0 - success or loss value (1 or 0)
#--------------------------------------------------------------------
.text
check_user_input_string:
			
	subi $sp $sp 4		#decrement the sp to allow for 4 bytes of data below original sp address location 
	sw   $ra ($sp) 		#push $ra into the stack 
	
	move $t1 $a1
	move $t3 $a2		#move time allowed for response into $t3 so it wont be overwritten 
	
	la   $a0 user_string   #reads in the user string and saves it at location user_string
	li   $a1 64
	li   $v0 8
	syscall
	
	li   $v0 30 
	syscall 		#time stamps the current time 	- end time 
	
	move $t2 $a0 		#moves the low order bits from $a0 into return register
	
	sub  $t4 $t2 $t1	#store result of end - start time into $t4
	sle  $v0 $t4 $t3	#store 1 in $v0 if user met the time limit, else store 0 
	
	beqz $v0 return		#if the user does not meet the allotted time then return 
	
	move $a0 $t0 		# moves the contents of $t0 into $a0 (has string user has to type)
	la   $a1 user_string	#loads the address of user input into $a1 so next subroutine takes it as input 
	jal compare_strings	#branches to subroutine 
				#if $v0 returns at 1 then it met the time limit and condition 
				#if $v0 returns as 0 then it met the time condition but not the string comparison 
						
return:	
	lw   $ra ($sp)		#loads the ra from memory 
	addi $sp $sp 4		#increments the stack pointer back to original spot 
	jr $ra
	
.data
user_string: .space 64
#--------------------------------------------------------------------
# compare_strings
#
# input: $a0 - address of first string to compare - type prompt 
# `	 $a1 - address of second string to compare - user input 
#
# output: $v0 - comparison result (1 == strings the same, 0 == strings not the same)
#--------------------------------------------------------------------
.text
compare_strings:
	subi $sp $sp 4		#decrement the sp to allow for 4 bytes of data below original sp address location 
	sw   $ra ($sp) 		#push $ra into the stack 
	
	move $t5 $a1		#stores user input into $t5
	
start_loop:	

	lb $t6 ($t0)		#stores the current char of result string into $t6
	lb $t7 ($t5)		#stores the current char of user input into $t7	
		
	beq  $t6 0 check_null_usr    	# if string has a null char then we check the user string to see if it also has a null char 
	beq  $t7 0 check_null_str	#if user input string has a null char then we check to see if the string to enter has a null char 
	
	seq $t8 $t6 10			#checks for newline char
	seq $t9 $t7 10
	beq $t8 $t9 exact
	b not_exact
			
	blt $t6 65 load_next_str	#if the $t6 is less than the 65 it must not be an alphabet 
	blt $t7 65 load_next_usr	#if $t7 is less then 65 it is not alphabet char

	ble $t6 122 change_case		#check if $t6 is an alphabet letter 
	ble $t7 122 change_case		#check if $t7 is an alphabet letter

call_compare:
	jal compare_chars	#jump and link to to subroutine 
	
	beqz $v0 end_loop  	#if the result argument contains a 0 that means chars are unequal 

increment:						
	addi $t0 $t0 1		#increment to the next char in user input 
	addi $t5 $t5 1		#increment to the next char in string 
	b start_loop

end_loop:	
	lw   $ra ($sp)		#loads the ra from memory  
	addi $sp $sp 4		#increments the stack pointer back to original spot 
	jr   $ra 		#should return to check_input_user_string
	
check_null_str:
	seq  $t6 $t6 0 		#if the other string also ends at same time then it is equal 
	beq  $t6 1 exact	#branch to exact label  return 1 
	beqz $t6 not_exact	#else branch to not_exact return 0
		
check_null_usr:
	seq  $t7 $t7 0 		#if the null is reacked on type prompt we check the user input 
	beq  $t7 1 exact 	#if both null - they are exact to that point 
	beqz $t7 not_exact	#otherwise one strng is longer 
exact:
	li $v0 1		#sets the return val to 1 - success
	b end_loop
	
not_exact: 
	li $v0 0		#sets the return val to 0 - failure 
	b end_loop	

load_next_usr:
	beq  $t7 32 space_detect1
	addi $t5 $t5 1		#increment to the next char in user input 
	b start_loop

load_next_str:	
	beq  $t6 32 space_detect2
	addi $t0 $t0 1		#increment to the next char in string 
	b start_loop
	
change_case: 
	bgt $t6 $t7 change_str #has to be either one or the other 
	bgt $t7 $t6 change_usr
	move $a1 $t7
	move $a0 $t6
	beq $t6 $t7 call_compare
	
change_str:
	add  $t7 $t7 32		#changes to lower case - extra credit 
	move $a1 $t7
	move $a0 $t6
	b call_compare
	
change_usr:
	add  $t6 $t6 32		#changes to lower case - extra credit 
	move $a0 $t6
	move $a1 $t7
	b call_compare
			
space_detect1:
	beq $t6 32  increment	#if both $t6 and $t7 are spaces then we increment both 
	add $t0 $t0 1		#else increment the position after the punctuation 
	b start_loop
	
space_detect2:
	beq $t7 32  increment #if both $t6 and $t7 are spaces then we increment both 
	add $t5 $t5 1 	      #else increment the position w punct 
	b start_loop																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		
#--------------------------------------------------------------------
# compare_chars
#
# input: $a0 - first char to compare (contained in the least significant byte)
# 	 $a1 - second char to compare (contained in the least significant byte)
#
# output: $v0 - comparison result (1 == chars the same, 0 == chars not the same)
#
#--------------------------------------------------------------------
.text

compare_chars:
	subi $sp $sp 4		#decrement the sp to allow for 4 bytes of data below original sp address location 
	sw   $ra ($sp) 		#push $ra into the stack 
	
	beq $a0 $a1 set_result1 #compares the chars - if equal set return var to 1
	b else			 #else not equal - set result var to 0

set_result1:
	li $v0 1		
	b return_to_caller	

else:
	li $v0 0
	b return_to_caller
		
return_to_caller:	
	lw   $ra ($sp)		#loads the ra from memory 
	addi $sp $sp 4		#increments the stack pointer back to original spot 
	jr $ra 			#should return to compare_strings 
