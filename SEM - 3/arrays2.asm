.data
	A: .word 10, 20, 30, 40, 50, 60, 70, 80
	B: .word 10, 20, 30, 40, 50, 60, 70, 80
.text
	# i
	li $s0, 4
	# j
	li $s1, 4
	
	la $s2, A
	
	la $s3, B
	
	
	# indice B[j - 2]
	add $t0, $s1, -2
	sll $t0, $t0, 2
	add $t0, $s3, $t0
	
	lw $t0, 0($t0)
	add $t0, $t0, 5
	
	# indice A[i]
	sll $t1, $s0, 2
	add $t1, $s2, $t1
	sw $t0, 0($t1)
	
	 
	
	