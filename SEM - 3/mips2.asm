.data
	saudacao: .asciiz "Qual sua idade? "
	output: .asciiz "Sua idade é "
.text
	li $v0, 4
	la $a0, saudacao
	syscall
	
	li $v0, 5
	syscall
	
	move $t0, $v0
	li $v0, 4
	la $a0, output
	syscall
	
	li $v0, 1
	move $a0, $t0
	syscall