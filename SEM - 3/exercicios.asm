.data
	A: .word 10, 20, 30, 40, 50, 60, 70, 80
	B: .word 10, 20, 30, 40, 50, 60, 70, 80
.text
	# 1
	#check:
	#	li $a0, 1
	#	li $a1, 1
	#	bne $a0, $a1, exit
	#	
	#	li $v0, 1
	#	j end
	#exit:
	#	li $v0, 0
	
	
	
	
	
	# 2
	
	#sum
	#li $s0, 0
	
	#i 
	#li $t0, 0
	
	#len
	#li $s1, 8
	
	# addr (0)
	#la $s2, A
	
	#while:
	#	beq $t0, $s1, end
		
		# Define offset
	#	sll $t1, $t0, 2
		
		# Endereço base + offset
	#	add  $t2, $s2, $t1
		
		# A[i]
	#	lw $t3, 0($t2)
		
		# Soma
	#	add $s0, $s0, $t3
		
		# i++
	#	addi $t0, $t0, 1
		
		
	#	j while
	
	
	#3 
	main:
		# i
		li $s0, 2
		
		#j
		li $s1, 2
		
		la $s2, A
		
		la $s3, B
		
		
		# variavel J - 2
		sub $s1, $s1, 2
		
		# End A + offset = A[i] ($t1)
		sll $t0, $s0, 2
		add $t1, $s2, $t0 
		
		# B[J -2 ]
		sll $t2, $s1, 2
		add $t3, $s3, $t2
		
		
		# Carrega e soma
		lw $t4, 0($t3)
		addi $t4, $t4, 5
		
		# Salva		
		sw $t4, 0($t1)
	
	end: