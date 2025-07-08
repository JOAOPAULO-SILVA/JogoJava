##Jogo de Batalha de Turnos em Java
Este projeto é um jogo de batalha de turnos desenvolvido em Java como parte da disciplina de Programação de Computadores II. O objetivo é criar um jogo que simula batalhas entre heróis e monstros, aplicando conceitos fundamentais de Programação Orientada a Objetos (POO). 


##📜 Descrição
O jogo apresenta uma batalha em um campo onde heróis enfrentam monstros. Cada personagem possui atributos únicos como Pontos de Vida (HP), Força, Defesa, Destreza e Velocidade. A ordem das ações em cada turno é determinada pela velocidade dos personagens, e o resultado de cada ataque é calculado com base nos atributos do atacante e do defensor. 


🛠️ Tecnologias Utilizadas
Linguagem: Java

🎯 To-Do

[ ] Implementar Ataque a partir da classe character: Criar o método realizarAtaque na classe Character, seguindo a estrutura do polimorfismo. 

Implementar IA dos Monstros: Adicionar lógica ao ataque do monstro para que ele possa escolher um alvo, como o herói com menor HP ou defesa. 

Classes de Gerenciamento do Jogo:

Criar a Classe Turno: Desenvolver a classe Turno para gerenciar as rodadas do jogo. 

[ ] Implementar a lógica para ordenar os personagens no início de cada turno com base no atributo 

velocidade. 

[ ] 

Criar a Classe Game: Implementar a classe Game para controlar o fluxo principal. 

[ ] Adicionar métodos 

iniciarJogo() e terminarJogo(). 

[ ] Criar um "game loop" que gerencie os turnos até que todos os heróis ou monstros sejam derrotados.

[ ] Adicionar lógica para selecionar a dificuldade (Fácil, Médio, Difícil). 


Criar a Classe Log: Implementar um sistema de log para registrar todos os eventos da partida (ataques, danos, mortes, etc.), permitindo a visualização do que ocorreu no final. 

Funcionalidades Adicionais
[ ] 

Criar Gerador de Personagem Aleatório: Implementar o método estático para gerar um Monstro aleatoriamente, com atributos também aleatórios dentro de uma faixa definida. 

[ ] 
