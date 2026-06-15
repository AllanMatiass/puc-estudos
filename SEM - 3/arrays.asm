.data
	array: .word 10, 20, 30, 40, 50, 60, 70, 80

.text
	# i = $t0
	li $t0, 0
	
	#len
	li $s1, 8
	
	# endereco array
	la $s2, array
	while:
		sll $t1, $t0, 2 # indice atual
		
		
		# valor no indice
		add $t2, $s2, $t1
		lw $t3, 0($t2)
		
		add $s0, $s0, $t3 # sum = s0
		add $t0, $t0, 1 # i++
		
		beq $s1, $t0, exit
		j while
		
	exit:	