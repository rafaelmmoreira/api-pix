# Desafio Banco Lincoln
## API de pagamentos Pix

## 1. Requisitos
Esse projeto foi desenvolvido em Spring com Gradle utilizando Java 17.
Além disso, foi utilizado um banco de dados MySQL com as seguintes configurações:

```
url = jdbc:mysql://${MYSQL_HOST:localhost}:3306/pix
username = api-pix
password = password
driver = com.mysql.jdbc.Driver
```

## 2. Executando
Para compilar e executar no Windows, basta digitar na linha de comando:
```
./gradlew bootRun
```

## 3. Requisições da API

Conforme solicitado, a API trabalha com requisições em JSON e permite o cadastro de pagamentos realizando certas verificações. Todas as requisições devem ser enviadas para ```/pagamento``` e todos os dados da requisição devem estar no JSON.

### 3.1. GET
É esperado um entre dois campos: ID ou status (AGENDADO ou EFETUADO).
Em caso de sucesso, retorno 200 contendo uma lista (mesmo que haja apenas 1 pagamento detectado) de pagamentos. Os atributos de cada pagamento na lista são descritos na seção abaixo (POST).
Em caso de falha, pode ser retornado 400, e o campo "message" irá informar a causa do erro (campos faltando ou mal formatados) ou 404.


### 3.2. POST
Salva um novo pagamento no banco. São esperados os seguintes campos:
```
dataInclusao - Obrigatório, formato yyyy-mm-dd
dataPagamento - Obrigatório, formato yyyy-mm-dd
valor - Obrigatório, formato nnnnn,nn
chave - Obrigatório, formato +nnnnnnnnnnnnn (telefone no padrão internacional), abcabc@xyzxyz.def (email), CPF/CNPJ (apenas dígitos ou com pontuação completa) ou chave aleatória no formato definido pelo BACEN (com hífens).
descricao - Opcional
dataFinal - Opcional, dado de recorrência, formato yyyy-mm-dd
frequencia - Opcional, deve ser SEMANAL, MENSAL, TRIMESTRAL ou SEMESTRAL
```

Em caso de sucesso, o objeto criado será retornado incluindo três novos campos: ID, tipoChave (EMAIL, TELEFONE, ALEATORIA ou CPF) e status (EFETUADO ou AGENDADO). O campo "message" pode trazer informações úteis, como sinalizar um pagamento duplicado (mesma data, valor e destino).
Caso contrário, ela irá apresentar retorno 400, com campo "message" informando a causa do erro em caso de campos obrigatórios faltando ou em formato inadequado. 

### 3.3. PUT
Atualiza um pagamento, mas espera receber os dados completos do pagamento. Os novos dados irão sobrescrever completamente os dados originais. Campos "calculados" (como status ou tipo de chave) são recalculados.
Em caso de sucesso, retorna o objeto salvo. Em caso de falha, retorno 400 com campo "message" informando os dados omitidos ou com falhas de formatação ou 404 caso o ID original não exista.

### 4.4. PATCH
Atualiza um pagamento, bastando informar o ID e os campos a serem atualizados. Campos "calculados" (como status ou tipo de chave) são recalculados.
Em caso de sucesso, retorna o objeto salvo. Em caso de falha, retorno 400 com campo "message" informando os dados omitidos ou com falhas de formatação ou 404 caso o ID original não exista.

### 4.5. DELETE
Remove um pagamento. Requisição deve conter o ID.
Retorna 404 caso não encontre, 400 caso o campo ID esteja ausente ou com erro de formatação.
Por segurança, em caso de sucesso retorna o objeto deletado para ser verificado.

### 4. Verificações
A aplicação é capaz de detectar automaticamente o tipo de chave PIX (respeitando o formato fornecido). São feitas algumas verificações básicas de formato e validade de entradas, especialmente datas. 
Também são verificadas regras de negócio, como períodos e valores mínimos para realizar cada tipo de operação de recorrência.


### 5. Testes
Estive montando testes de integração utilizando JUnit, mas tive problemas com dependências na "reta final" e achei melhor removê-los a entregar não rodando adequadamente :( Seguirei estudando para encontrar os problemas e corrigí-los, mas para preservar a integridade da avaliação, optei por fazer um commit com o que tenho até agora (ou seja, sem eles) dentro do prazo combinado para entrega para "congelar" o que consegui fazer nesse prazo.
