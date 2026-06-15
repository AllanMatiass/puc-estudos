.data
array: .word 10, 20, 30, 40, 50   # lista de ints
array2: .word 10, 20, 30, 40, 50   # lista de ints
array3: .word 10, 20, 30, 40, 50   # lista de ints
.text
main:

	li $s0, 2 #i
	li $s1, 3 #j
	
	la $s2, array #basemem
	la $s3, array2 #basemem2
	la $s4, array3
	
	sub $s1, $s1, 2
	
	sll $t0, $s1, 2
	add $t1, $t0, $s3
	lw $t2, 0($t1) #A[i]
	
	
	
	
	sll $t4, $s0, 2
	add $t4, $t4, $s2
	lw $t3, 0($t4) #B[i]
	
	sll $t5, $s0, 2
	add $t5, $t5, $s4
	add $t6, $t2, $t3
	sw $t6, 0($t5)
	
	