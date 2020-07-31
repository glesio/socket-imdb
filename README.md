# Socket on TCP/IP

Este projeto contempla a implementação de Socket Server, Socket Cliente e Scraping no [IMDB](https://www.imdb.com.br) que retorna os 50 primeiros títulos de filmes consultados por meio do protocolo estabelecido para comunicação com o servidor de socket.     

## Protocolo

As consultas enviadas para o servidor seguem o seguinte padrão: `<query length>:<query>`. O separador *:* não é contabilizado no tamanho da query enviada.

O resultado da consulta é enviado para o cliente seguindo o padrão `<payload length>:<payload>`. Caso o resultado da consulta seja mais de 1 registro, todos os registros devem ser agrupados em uma única resposta separada por *\n*.

> Para fechar uma conexão com o servidor enviar a query `:close`
 
## Estrutura do Projeto

Os pacotes de classes do projeto estão separados no seguinte padrão:
```
io.gles
 |- imdb (Consulta no IMDB)
 |- run (Classes de execução e exemplo do servidor e cliente)
 |- socket (Implementação do protocolo)
    |- client (Cliente do socket)
    |- protocol (Regras do Protocolo)
    |- server (Servidor do protocolo socket)
```

## Build

Como ferramenta de build é utilizado o *Gradle*.

Tasks:

* `build` Gera o pacote _fatjar_ do projeto, possibilitado iniciar o servidor e clientes para consulta dos títulos de filmes.
* `run` Inicia o servidor.
* `test` Executa os testes unitários.

## Como utilizar?

### Informações Gerais

A implementação faz uso de alguns valores padrões para a execução do servidor e cliente do protocolo.
Estes valores podem ser alterados por propriedades de sistema Ex:  `java -Dnome-propriedade=valor ...`

Tabela com as propriedades utilizadas, valores padrões e local de uso:

Propriedade | Valor | Local
--- | --- | ---
host | localhost | ClientRun, SearchRun
port | 1024 | ServerRun, ClientRun, SearchRun
mode | server | Main
    
### Código Fonte

No pacote `io.gles.run` existe a implementação das seguintes classes:

* ServerRun (Servidor socket)
* ClientRun (Cliente interativo onde o usuário pode digitar os títulos de filmes para consulta)
* SearchRun (Cliente que consulta o título do filme passando o titulo por argumento)

### Linha de comando

Após executar `gradle build` o arquivo `socket-imdb-1.0-SNAPSHOT.jar` será criado no diretório `build/libs` presente neste projeto. 

#### Servidor
    
Inicia o servidor na porta padrão 1024
`java -jar socket-imdb-1.0-SNAPSHOT.jar`

Inicia o servidor em uma porta alternativa
`java -Dport=9090 -jar socket-imdb-1.0-SNAPSHOT.jar`

Inicia o servidor pelo gradle
`gradle run`

#### Cliente

##### Telnet

Abrindo conexão com o servidor na porta 1024
`telnet localhost 1024`

>As consultas digitadas no telnet devem seguir o padrão d padrão do protocolo descrito.
>Para pesquisar o filme Matrix deve ser digitado o seguinte valor '6:Matrix' e pressionar <enter> para o título ser consultado.

>Para fechar a conexão digitar `:close` e pressionar <enter>


##### Interativo

Inicia o cliente para conectar no host `localhost` e porta `1024`
`java -Dmode=client -jar socket-imdb-1.0-SNAPSHOT.jar`

Inicia o cliente para conectar no host `localhost` e porta `9090`
`java -Dmode=client -Dport=9090 -jar socket-imdb-1.0-SNAPSHOT.jar`

Inicia o cliente para conectar no host `127.0.0.1` e porta `9090`
`java -Dmode=client -Dhost='127.0.0.1' -Dport=9090 -jar socket-imdb-1.0-SNAPSHOT.jar`

##### Search

Pesquisa filme com o título Matrix passado na linha de comando
`java -Dmode=search -jar socket-imdb-1.0-SNAPSHOT.jar Matrix`

Pesquisa filme com o título Matrix passado na linha de comando conectando no host `127.0.0.1` e porta `9090`
`java -Dmode=search -Dhost='127.0.0.1' -Dport=9090 -jar socket-imdb-1.0-SNAPSHOT.jar Matrix`
