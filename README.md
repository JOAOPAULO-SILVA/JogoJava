# Jogo de Turnos

## Visão Geral do Projeto

Este projeto é um jogo de RPG de batalha por turnos, onde heróis enfrentam monstros em um campo de batalha. O sistema é modular e orientado a objetos, com classes que gerenciam a lógica do jogo, os personagens, os turnos e os eventos.

O fluxo principal é iniciado pela classe `Main`, que cria uma instância de `Game`. A classe `Game` orquestra toda a partida, desde a configuração inicial (modo de jogo, dificuldade, criação de personagens) até o loop principal da batalha e sua finalização. O `Battlefield` gerencia o tabuleiro, o posicionamento e as listas de personagens ativos. O `TurnManager` é responsável por definir a ordem das ações com base na velocidade dos personagens, e todas as ações são registradas pelo `Logger`.

## Documentação das Classes

---

### **`Main.java`**

**Visão Geral**
É a classe de entrada (entry point) da aplicação. Sua única responsabilidade é instanciar e iniciar o objeto principal do jogo, `Game`.

**Métodos**
* `public static void main(String[] args)`
    * **Descrição:** O método principal que a JVM executa. Cria um novo objeto `Game` e chama seu método `iniciar()` para começar a execução do jogo.

---

### **`Game.java`**

**Visão Geral**
A classe `Game` é o motor central do jogo. Ela gerencia o fluxo completo de uma partida, desde a configuração inicial até a conclusão da batalha. É responsável por interagir com o usuário, controlar o loop de turnos e gerenciar o estado geral do jogo.

**Atributos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `Battlefield` | `battlefield` | Referência ao campo de batalha onde os personagens estão. |
| `TurnManager` | `turnManager` | Gerencia a ordem dos turnos e o personagem atual. |
| `Scanner` | `scanner` | Captura a entrada do usuário no modo interativo. |
| `Random` | `random` | Utilizado para elementos de aleatoriedade no jogo. |
| `String` | `modoDeJogo` | Armazena o modo de jogo selecionado ("INTERATIVO", "AUTOMATICO", "LOG_ONLY"). |

**Construtores**
* `public Game()`
    * **Descrição:** Inicializa um novo jogo. Limpa os logs antigos, cria instâncias de `Scanner`, configura a batalha chamando `configurarBatalha()` e inicializa o `TurnManager`.

**Métodos Principais**
* `public void iniciar()`
    * **Descrição:** Inicia e gerencia o loop principal da batalha. O loop continua enquanto a batalha não terminar (`!turnManager.isBattleOver()`). Em cada iteração, exibe o tabuleiro, identifica o personagem do turno, e executa a ação correspondente (herói ou monstro).
* `private Battlefield configurarBatalha()`
    * **Descrição:** Guia o usuário pela configuração da partida. Pede para escolher o modo de jogo e a dificuldade. Com base nas escolhas, cria e adiciona os heróis e monstros ao campo de batalha. Retorna a instância do `Battlefield` configurada.
* `private void finalizarBatalha()`
    * **Descrição:** É chamado quando a batalha termina. Anuncia o vencedor (Heróis ou Monstros), exibe o estado final do tabuleiro e mostra todos os logs de eventos registrados pelo `Logger`.
* `private void executarTurnoHeroi(Hero heroi)`
    * **Descrição:** Gerencia o turno de um herói no modo "INTERATIVO". Pergunta ao jogador qual ação realizar (ataque fraco/forte, passar, desistir) e a executa contra um alvo escolhido.
* `private void executarTurnoHeroiAI(Hero heroi)`
    * **Descrição:** Executa o turno de um herói de forma automática (nos modos "AUTOMATICO" e "LOG_ONLY"). A IA identifica o monstro com o menor HP e o ataca.
* `private void executarTurnoMonstro(Monster monstro)`
    * **Descrição:** Executa o turno de um monstro. A IA do monstro sempre foca no herói com o menor HP e realiza um ataque.
* `private void imprimirDevagar(String mensagem, int milissegundos)`
    * **Descrição:** Método utilitário para imprimir texto no console caractere por caractere, criando um efeito de digitação.

---

### **`Battlefield.java`**

**Visão Geral**
Esta classe representa o campo de batalha. Ela gerencia um tabuleiro (grid) 2D, controla as posições dos personagens e mantém listas separadas para heróis e monstros ativos na batalha. É a responsável por toda a gestão espacial do jogo.

**Atributos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `Character[][]` | `board` | A matriz 2D que representa o tabuleiro do jogo. |
| `int` | `largura`, `altura` | As dimensões do tabuleiro. |
| `List<Hero>` | `heroes` | Lista de todos os heróis atualmente no campo de batalha. |
| `List<Monster>` | `monsters` | Lista de todos os monstros atualmente no campo de batalha. |

**Construtores**
* `public Battlefield(int largura, int altura)`
    * **Descrição:** Cria um novo campo de batalha com as dimensões especificadas. Inicializa o `board` e as listas de `heroes` e `monsters`.

**Métodos**
* `public boolean adicionarPersonagem(Character personagem, int x, int y)`
    * **Descrição:** Adiciona um personagem ao tabuleiro na posição (x, y). Realiza validações para garantir que a posição esteja dentro dos limites e não esteja ocupada. Atualiza a posição do personagem e o adiciona à lista correspondente (heróis ou monstros).
* `public void removerPersonagem(Character personagem)`
    * **Descrição:** Remove um personagem do tabuleiro (definindo sua posição como `null`) e da sua respectiva lista (`heroes` ou `monsters`).
* `public void exibirTabuleiro()`
    * **Descrição:** Imprime uma representação visual do estado atual do campo de batalha no console. Mostra o grid com as iniciais dos personagens e, em seguida, lista os heróis e monstros com seus HPs.
* `public void removerTodosHerois()`
    * **Descrição:** Remove todos os heróis do campo de batalha. É usado para o caso de desistência.
* **Getters**
    * **Descrição:** Fornece métodos públicos para acessar as listas de heróis (`getHeroes`) e monstros (`getMonsters`), bem como obter a contagem de cada um (`getNumeroDeHerois`, `getNumeroDeMonstros`).

---

### **`TurnManager.java`**

**Visão Geral**
Esta classe é responsável por gerenciar a ordem dos turnos em uma batalha. Ela inicializa a sequência de ação com base no atributo de velocidade (`speed`) dos personagens e controla o fluxo de um turno para o próximo.

**Atributos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `List<Character>` | `turnOrder` | Lista de personagens ordenada pela velocidade, que dita a ordem dos turnos. |
| `int` | `currentTurnIndex` | O índice do personagem atual na lista `turnOrder`. |
| `Battlefield` | `battlefield` | Referência ao campo de batalha para obter a lista inicial de personagens. |

**Construtores**
* `public TurnManager(Battlefield battlefield)`
    * **Descrição:** Cria um novo gerenciador de turnos. Recebe o campo de batalha, inicializa o índice de turno como 0 e chama `initializeTurnOrder()` para criar a fila de turnos.

**Métodos**
* `private void initializeTurnOrder()`
    * **Descrição:** Popula a lista `turnOrder` com todos os heróis e monstros do campo de batalha. Em seguida, ordena esta lista em ordem decrescente de velocidade (`speed`) usando um algoritmo de Bubble Sort.
* `public Character getCurrentCharacter()`
    * **Descrição:** Retorna o personagem cuja vez de jogar é a atual.
* `public void nextTurn()`
    * **Descrição:** Avança para o próximo personagem na ordem de turno. Se chegar ao final da lista, volta ao início.
* `public boolean isBattleOver()`
    * **Descrição:** Verifica e retorna `true` se a batalha terminou (se não há mais heróis ou não há mais monstros no campo).
* `public void removeCharacter(Character character)`
    * **Descrição:** Remove um personagem da ordem de turnos (geralmente quando ele é derrotado). Ajusta o índice do turno atual para evitar erros.

---

### **`Character.java`**

**Visão Geral**
É uma classe `abstract` que serve como modelo base para todos os personagens do jogo (Heróis e Monstros). Ela define os atributos e comportamentos comuns que qualquer personagem deve ter.

**Atributos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `String` | `name` | Nome do personagem. |
| `int` | `speed` | Velocidade, usada para determinar a ordem de ataque. |
| `int` | `strength` | Força, influencia o dano físico. |
| `int` | `HP` | Pontos de Vida (Health Points) atuais. |
| `int` | `maxHP` | Pontos de Vida máximos do personagem. |
| `int` | `resistance` | Resistência, reduz o dano recebido. |
| `int` | `dexterity` | Destreza, influencia a chance de acerto e dano de certos ataques. |
| `CharacterType` | `tipo` | O tipo do personagem (ex: PALADIN, ORC). |
| `int` | `posX`, `posY` | Coordenadas do personagem no campo de batalha. |

**Construtores**
* `public Character(...)`
    * **Descrição:** Construtor que inicializa todos os atributos básicos de um personagem.

**Métodos**
* `public abstract AttackResult realizarAtaque(Character alvo, AttackType tipoAtaque)`
    * **Descrição:** Método abstrato que define a assinatura para a ação de ataque. Cada subclasse (`Hero`, `Monster`) deve implementar sua própria lógica de ataque.
* `public int receberDano(int dano)`
    * **Descrição:** Calcula o dano real sofrido após subtrair a resistência e atualiza o HP do personagem. Garante que o HP não fique negativo. Retorna o dano real causado.
* **Getters e Setters**
    * **Descrição:** Fornece métodos públicos para acessar e modificar os atributos do personagem (ex: `getName()`, `setHP(int hp)`).

---

### **`Hero.java`**

**Visão Geral**
Representa um personagem do tipo Herói, estendendo a classe `Character`. Esta classe implementa a lógica específica para os heróis, incluindo a definição de seus atributos com base na classe e um sistema de ataque complexo com diferentes tipos de golpes e chances de acerto crítico.

**Construtores**
* `public Hero(String nome, String tipoHeroiString)`
    * **Descrição:** Cria um novo herói. Converte a `String` do tipo para o `enum` `CharacterType` e chama `setAttributes()` para definir os status iniciais com base na classe do herói.

**Métodos**
* `private void setAttributes()`
    * **Descrição:** Define os atributos (HP, força, velocidade, etc.) do herói com base em seu `CharacterType`, buscando os valores constantes da classe `CharacterAttributes`.
* `@Override public AttackResult realizarAtaque(Character alvo, AttackType tipoAtaque)`
    * **Descrição:** Implementação do método de ataque para heróis. A lógica varia enormemente dependendo da classe do herói e se o ataque é `FRACO` ou `FORTE`. Calcula a chance de acerto, o dano base, e o dano crítico, e então executa o ataque, registrando o resultado no `Logger`.
* `@Override public String toString()`
    * **Descrição:** Retorna uma representação em `String` formatada do herói e seus atributos.

---

### **`Monster.java`**

**Visão Geral**
Representa um personagem do tipo Monstro, estendendo a classe `Character`. Implementa a lógica para os inimigos, que têm atributos gerados aleatoriamente dentro de um intervalo e uma lógica de ataque mais simples em comparação com os heróis.

**Construtores**
* `public Monster(String nome, String tipoCharacterString)`
    * **Descrição:** Cria um novo monstro. Converte a `String` do tipo para o `enum` `CharacterType` e chama `setAttributes()` para definir seus status.

**Métodos**
* `private int getRandom(int min, int max)`
    * **Descrição:** Método utilitário para gerar um número inteiro aleatório dentro de um intervalo `[min, max]`.
* `private void setAttributes()`
    * **Descrição:** Define os atributos do monstro de forma aleatória usando o método `getRandom()`. Os intervalos de mínimo e máximo para cada atributo são obtidos da classe `CharacterAttributes`.
* `@Override public AttackResult realizarAtaque(Character alvo, AttackType tipoAtaque)`
    * **Descrição:** Implementação do método de ataque para monstros. É uma lógica de ataque direta: o dano é igual à força (`strength`) do monstro. O resultado é registrado no `Logger`.
* `@Override public String toString()`
    * **Descrição:** Retorna uma representação em `String` formatada do monstro e seus atributos.

---

### **`CharacterGenerator.java`**

**Visão Geral**
É uma classe utilitária (fábrica) responsável por criar instâncias de `Hero` e `Monster` de forma aleatória. Ela garante que os nomes dos personagens sejam únicos e registra a criação de novos personagens no `Logger`.

**Atributos Estáticos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `Random` | `random` | Instância para gerar números aleatórios. |
| `String[]` | `nomesHerois`, `nomesMonstros` | Listas de nomes pré-definidos para heróis e monstros. |
| `List<String>` | `nomesUsados` | Armazena os nomes já utilizados para evitar repetição. |

**Métodos Estáticos**
* `public static Hero gerarHeroiAleatorio()`
    * **Descrição:** Cria um herói com um nome e tipo aleatórios.
* `public static Monster gerarMonstroFacil() / Medio() / Dificil()`
    * **Descrição:** Criam um monstro de uma dificuldade específica, escolhendo aleatoriamente entre os tipos de monstros permitidos para aquele nível.
* `private static String getUniqueName(String[] Nomes)`
    * **Descrição:** Seleciona um nome aleatório de uma lista de nomes, garantindo que ele ainda não tenha sido usado na partida atual.

---

### **`Logger.java`**

**Visão Geral**
É uma classe utilitária estática que fornece um sistema centralizado de registro de eventos. Todas as ações importantes do jogo (criação de personagens, ataques, mortes, etc.) são enviadas para o `Logger`, que as armazena com um timestamp.

**Atributos Estáticos**
| Tipo | Nome do Atributo | Descrição |
| :--- | :--- | :--- |
| `List<String>` | `logs` | Armazena todas as mensagens de log registradas. |
| `DateTimeFormatter` | `timeFormatter` | Formata a hora para o timestamp do log. |

**Métodos Estáticos**
* `public static void log(String message)`
    * **Descrição:** Imprime a mensagem no console e a adiciona à lista `logs` com um timestamp no formato `[HH:mm:ss]`.
* `public static void exibirLogs()`
    * **Descrição:** Imprime no console todos os logs armazenados, dentro de um cabeçalho formatado.
* `public static void limparLogs()`
    * **Descrição:** Apaga todos os logs armazenados. Usado no início de um novo jogo.

---

## Enums e Classes Utilitárias de Dados

---

### **`CharacterAttributes.java`**

**Visão Geral**
É uma classe utilitária que serve como um repositório central para todas as constantes de atributos dos personagens. Ela contém os valores base de HP, força, velocidade, etc., para cada classe de herói, e os valores mínimos e máximos para os atributos dos monstros. Isso facilita o balanceamento e a manutenção do jogo.

**Atributos**
* A classe contém exclusivamente atributos `public static final int`.
    * **Heróis:** Constantes individuais para cada atributo de `PALADIN`, `WIZARD`, `ARCHER` e `STEALTH`.
    * **Monstros:** Constantes `_MIN` e `_MAX` para cada atributo de `ORC`, `WITCH` e `DRAGON`.

**Construtores**
* `private CharacterAttributes()`
    * **Descrição:** O construtor é privado para impedir que esta classe utilitária seja instanciada.

---

### **`CharacterType.java`**

**Visão Geral**
É uma enumeração (`enum`) que define todos os tipos de personagens possíveis no jogo. Centraliza os tipos válidos, evitando o uso de `Strings` e os erros que elas podem causar.

**Valores**
* **Heróis:** `PALADIN`, `WIZARD`, `ARCHER`, `STEALTH`.
* **Vilões:** `DRAGON`, `ORC`, `WITCH`.

---

### **`AttackType.java`**

**Visão Geral**
Enumeração que define os tipos de ataque que um personagem pode executar. Usada principalmente na lógica de ataque dos heróis para diferenciar entre um golpe normal e um especial.

**Valores**
* `FRACO`: Representa um ataque padrão, geralmente com maior chance de acerto e menor dano.
* `FORTE`: Representa um ataque especial, geralmente com menor chance de acerto, mas maior potencial de dano.

---

### **`AttackResult.java`**

**Visão Geral**
Enumeração que define os possíveis resultados de uma tentativa de ataque. É retornada pelos métodos `realizarAtaque` para indicar o que aconteceu.

**Valores**
* `ERROU`: Indica que o ataque falhou em atingir o alvo.
* `ACERTOU`: Indica que o ataque atingiu o alvo e causou dano normal.
* `CRITICAL_HIT`: Indica que o ataque foi um acerto crítico, causando dano aumentado.
