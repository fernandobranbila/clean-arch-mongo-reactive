# Testing

## Tecnologias

 * Gradle
 * Kotlin
 * Coroutines
 * Mongo
 * Spring Webflux
  
 ## API
 
  * Criar nova fazenda -> ``` v1/farms```
  * Encontrar fazenda  -> ``` v1/farms/{farmId} ```
  * Criar novo lote    -> ``` v1/farms/{farmId} ```
  * Registrar nova colheita -> ``` v1/farms/{farmId}/plots/{plotId}/harvests ```
  * Calcula produtividade de um lote - > ``` v1/farms/{farmId}/plots/{plotId}/harvests/productivity ```

      Recebe startDate e endDate como parâmetros opcionais  
      Se somente startDate informado = produtividade daquele dia em diante  
      Se somente endDate informado = produtividade até aquele dia  
      Se nenhuma informada = produtividade total  
      Se ambas informadas = produtividade entre aquele período  
      
  * Calcula produtividade de uma fazenda - > ``` v1/farms/{farmId}/plots/harvests/productivity ```
  
      Recebe startDate e endDate como parâmetros opcionais  
      Se somente startDate informado = produtividade daquele dia em diante  
      Se somente endDate informado = produtividade até aquele dia  
      Se nenhuma informada = produtividade total  
      Se ambas informadas = produtividade entre aquele período  
      
    Modelos podem ser consultados em **host da aplicação**/swagger-ui.html
      
## Como rodar

  ``` gradle build ```
 
  ``` docker-compose build```
  
  ``` docker-compose up```
  
  Porta default do app é 9011
  
  
  ## To-do Melhorias
  - [ ] Realizar cálculo no mongo com projection, group e sum
  - [ ] Refatorar testes geração de datas "Random"
  - [ ] Fix na conexão do banco no contexto de teste para testes de integração p/ não mockar database
