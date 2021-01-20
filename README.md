# Loteria
Projeto de loteria de apostas

Este projeto tem como objetivo demonstrar como funciona uma API REST que gera apostas aleatórias para um determinado solicitante. 

Foi desenvolvido utilizando IDE Eclipse e o framework Spring boot com Maven, JPA e Spring Data.

Ele possui 6 classes:

  ApostaController.java
  Usuario.java
  Aposta.java
  ApostaUtil.java
  
e duas intefaces:

  UsuarioRepository.java
  ApostaRepository.java
  
Classe Usuário:
 
A classe Usuario conterá dois atributos: idUsuario e email. A primeira será do tipo Long com a anotação @Id (chave primária) e @generatedValue…(o banco de dados se encarregará de gerar ids sequenciais), e a segunda será do tipo String com a anotação @Column…(atributo unique e não nulo). A classe terá anotação @Entity, ou seja, para que ela seja persistida como tabela no banco de dados, e a anotação @Data garante que os métodos getters e setters sejam gerados e encapsulados.

Classe Aposta:

A Classe Aposta seguirá o mesmo ritmo. Terá 3 atributos: idAposta que será a chave  primária e terá seus ids gerados automaticamente, usuario, do tipo Usuario, que terá anotações @ManyToOne..., @JoinColumn... para que a chave primária da classe Usuario seja a chave estrangeira da classe Aposta. Colocamos também a anotação @Table(unique… para que os atributos idUsuario e numeroGerado sejam um atributo unique composto, para que um mesmo e-mail não tenha duas apostas iguais.

Interface UsuarioRepository:

A interface UsuarioRepository será responsável por persistir os dados da classe Usuario na tabela usuario do banco de dados. Ela estende a classe JpaRepository, cujos parâmetros serão o tipo Usuario do pacote model e o tipo do atributo que contém a chave primária da mesma classe. Criamos um método que recebe uma String (email) e devolve uma lista do tipo Usuario.

Interface ApostaRespository:

A interface ApostaRepository também será da mesma forma. Criaremos um método que recebe um objeto do tipo Usuario e devolve uma lista do tipo Aposta.

Classe ApostaController:

Esta classe é onde terá nossos endpoints, que  consumirão nossa API REST e controlará o fluxo das requisições.

Primeiro de tudo, vamos declarar dois atributos do tipo ApostaRepository e UsuarioRepository, pois eles serão responsáveis por implementar os métodos destas classes para comunicação com o banco de dados. A anotação @Autowired implementa injeção de dependências do Spring. 

Ela terá dois métodos, um método para listar as apostas de um solicitante, passando como parâmetro uma String (e-mail), e a outra para cadastrar uma nova aposta, passando como parâmetro um objeto do tipo Usuário (e-mail).

O método novaAposta receberá um objeto do tipo Usuario e verificará se o usuário já existe na tabela usuario do banco de dados. Se não existir, a tabela usuário será alimentada com as informações do solicitante, que no caso é só o e-mail, e gerará uma nova aposta na tabela aposta do banco de dados. Caso contrário, se o usuário já existir, apenas a tabela aposta será alimentada junto com a chave primária da tabela usuario que é estrangeira na tabela aposta. A anotação @PostMaping indica que o solicitante enviará uma requisição para escrita de dados.Obs: (O método gerarNumeroNovaAposta() será explicado em mais detalhes logo adiante).

O método listarApostas receberá como parâmetro uma String (email) e retornará a lista de apostas do solicitante. Se o usuário não existir, será retornada uma mensagem com o conteúdo “Usuario não cadastrado”. A anotação @GetMapping(“{emailUsuario}) indica que enviaremos uma requisição de busca na tabela, passando como parâmetro a variável emailUsuario.

ApostaUtil:

E por último, vamos falar da nossa classe ApostaUtil, que conterá nossa regra de negócios. Nesta classe será implementado nosso código que gerará um número aleatório para apostas, no qual será utilizado o método novaAposta da classe ApostaController. Com isso, será gerado um array de seis posições, sendo que cada posição é resultado do sorteio de números de 0 a 60, utilizando a classe Random. Depois, os arrays serão ordenados para que, posteriormente, no banco de dados, seja mais fácil fazer a validação se existem números de apostas repetidos para o mesmo usuário.

Vale salientar, também, que aplicamos um método de recursividade para que se uma aposta gerada já existir para o usuário, que seja gerada uma nova aposta. O array será retornado para o tipo String.

Os métodos cadastrarUsuarioInexistente, CadastrarAposta e verificarExistenciaUsuario também estão nessa classe que serão usados nos métodos da classe ApostaController.


