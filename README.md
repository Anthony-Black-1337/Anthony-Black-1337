

<h1>CONTEUDO DO VIDEO<h1>

<h2>< Implements > linha 28 </h2>
  
As interfaces são padrões definidos através de contratos ou especificações. Um contrato define um determinado conjunto de métodos que serão implementados nas classes que assinarem esse contrato. Uma interface é 100% abstrata, ou seja, os seus métodos são definidos como abstract, e as variáveis por padrão são sempre constantes (static final).

Uma interface é definida através da palavra reservada “interface”. Para uma classe implementar uma interface é usada a palavra “implements”.

Como a linguagem Java não tem herança múltipla, as interfaces ajudam nessa questão, pois bem se sabe que uma classe pode ser herdada apenas uma vez, mas pode implementar inúmeras interfaces. As classes que forem implementar uma interface terão de adicionar todos os métodos da interface ou se transformar em uma classe abstrata.


<h2>< Modificador Final > linha 30 </h2>

É usado em vários contextos: classes, métodos, atributos e variáveis.

Classes final

Uma classe com este modificador não pode ser estendida, isto é, não pode ter classes que herdam dela.

Isso é importante para garantir que uma determinada implementação não tenha seu comportamento modificado. Isso tem muito a ver com a imutabilidade.

Tipos básicos do Java como String e Integer são final porque se espera que o conteúdo não possa ser modificado. O problema é que seu alguém pudesse criar uma subclasses de String, esta implementação poderia passar a ser mutável. Uma String mutável seria o caos na terra para implementações, pois vários pressupostos que os desenvolvedores usam no dia-a-dia seriam simplesmente desfeitos.


Método final

É um método que não pode ser sobrescrito nas subclasses.

Use para garantir que um determinado algoritmo não possa ser modificado pelas subclasses.


Atributo final

Um atributo final de uma classe pode ter seu valor atribuído uma única vez, seja na própria declaração ou no construtor.

Use isso para garantir que um valor ou referência de objeto não vai mudar. Voltamos à imutabilidade.

Se você tem um algoritmo que usa esse variável, você pode armazenar valores calculados sem a preocupação do valor mudar.


Variáveis final

Use para garantir que você não está modificando o valor indevidamente.

Referencia : https://pt.stackoverflow.com/questions/17015/qual-o-uso-de-uma-variável-estática-ou-final-em-java

<h2>< Private > linha 30 </h2> 

private

Somente classes no mesmo arquivo fonte enxergam. Use isso quando o método é feito apenas para uso dos outros métodos públicos da classe.

Referencia : https://pt.stackoverflow.com/questions/17015/qual-o-uso-de-uma-variável-estática-ou-final-em-java

<h2>< Map > linha 43</h2>

Essa interface é um objeto que mapeia valores para chaves, ou seja, através da chave consegue ser acessado o valor configurado, sendo que a chave não pode ser repetida ao contrário do valor, mas se caso tiver uma chave repetida é sobrescrito pela última chamada. Também faz parte do pacote java.util e não possui métodos da interface Collection.

Referencia : https://www.devmedia.com.br/conhecendo-a-interface-map-do-java/37463

