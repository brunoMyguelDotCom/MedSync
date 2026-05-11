# MedSync - Backend



#PATTERN DA SEGUNDA ETAPA:

# Padronização de Desenvolvimento da API

Este projeto utiliza uma arquitetura padronizada para facilitar manutenção, leitura do código e continuidade do desenvolvimento entre múltiplos desenvolvedores.

---

# Fluxo da Aplicação

Toda requisição segue o seguinte fluxo:

```text
Controller -> Service -> Repository -> Banco de Dados
```

Após buscar os dados:

```text
Banco de Dados -> Service -> Mapper -> DTO -> Controller -> Cliente
```

Cada camada possui uma responsabilidade específica.

---

# Controller

A camada `Controller` é responsável por receber requisições HTTP e retornar respostas para o cliente.

Ela NÃO deve conter regra de negócio.

## Exemplo

```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<ConsultaResponseDTO>> listarPorId(@PathVariable Long id) {

    var response = consultaService.listarPorId(id);

    return ResponseEntity.ok(response);
}
```

## O que acontece aqui?

1. O endpoint recebe o `id`
2. A controller chama o `Service`
3. O resultado é retornado usando `ResponseEntity`

---

# Por que usar ResponseEntity?

O `ResponseEntity` permite controlar:

* Status HTTP
* Headers
* Corpo da resposta

Sem ele, a API perde flexibilidade para respostas futuras.

## Exemplo

```java
return ResponseEntity.ok(response);
```

Retorna:

```http
200 OK
```

Outro exemplo:

```java
return ResponseEntity.status(HttpStatus.CREATED).body(response);
```

Retorna:

```http
201 CREATED
```

Isso melhora a padronização e deixa a API mais profissional.

---

# Service

A camada `Service` contém as regras de negócio da aplicação.

Responsabilidades:

* Validar regras
* Buscar dados
* Chamar repositories
* Converter entidades usando Mapper
* Retornar dados padronizados

## Exemplo

```java
public ApiResponse<ConsultaResponseDTO> listarPorId(Long id) {

    Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

    ConsultaResponseDTO dto = ConsultaMapper.toConsultaResponseDTO(consulta);

    return new ApiResponse<>(dto);
}
```

## Fluxo desse método

1. Busca consulta no banco
2. Caso não exista, lança erro
3. Converte Entidade para DTO
4. Retorna resposta padronizada

---

# Repository

A camada `Repository` é responsável pelo acesso ao banco de dados.

Exemplo:

```java
consultaRepository.findById(id)
```

Ela abstrai SQL manual e utiliza JPA/Spring Data.

---

# Mapper

O `Mapper` centraliza conversões entre:

* Entidades
* RequestDTO
* ResponseDTO

Isso evita repetição de código.

## Exemplo

```java
public static ConsultaResponseDTO toConsultaResponseDTO(Consulta consulta) {

    return new ConsultaResponseDTO(
            consulta.getId(),
            consulta.getPaciente().getNome(),
            consulta.getMedico().getNome(),
            consulta.getDataHora(),
            consulta.getStatus(),
            consulta.getObservacoes());
}
```

## Por que usar Mapper?

Sem Mapper:

* Conversões ficam espalhadas
* Código fica repetitivo
* Manutenção fica difícil

Com Mapper:

* Conversão fica centralizada
* Facilita manutenção
* Padroniza respostas

---

# DTOs

DTO significa:

```text
Data Transfer Object
```

São objetos usados para transportar dados.

## Tipos utilizados

### RequestDTO

Recebe dados enviados pelo cliente.

Exemplo:

```java
ConsultaRequestDTO
```

---

### ResponseDTO

Define os dados enviados ao cliente.

Exemplo:

```java
ConsultaResponseDTO
```

---

# Por que NÃO retornar Entidades?

Errado:

```java
return consulta;
```

Problemas:

* Exposição de dados desnecessários
* Acoplamento ao banco
* Risco de loops em relacionamentos
* Dificulta manutenção futura

Correto:

```java
return ConsultaMapper.toConsultaResponseDTO(consulta);
```

---

# ApiResponse

Toda resposta da API deve utilizar:

```java
ApiResponse<T>
```

## Estrutura

```java
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ErrorResponse error;

}
```

---

# Por que usar ApiResponse?

O objetivo é padronizar TODAS as respostas da API.

Sem padronização:

* Cada endpoint retorna diferente
* Frontend precisa tratar vários formatos
* Dificulta manutenção

Com ApiResponse:

* Todas respostas seguem o mesmo padrão
* Facilita integração frontend
* Facilita tratamento de erros
* Facilita logs e monitoramento

---

# Exemplo de Sucesso

```json
{
  "success": true,
  "data": {
    "id": 1,
    "paciente": "João"
  },
  "error": null
}
```

---

# Exemplo de Erro

```json
{
  "success": false,
  "data": null,
  "error": {
    "message": "Consulta não encontrada"
  }
}
```

---

# Uso do var

Sempre que o tipo for evidente:

```java
var response = consultaService.listarTodos();
```

## Por que usar var?

* Reduz repetição
* Código fica mais limpo
* Mantém legibilidade

Evitar quando o tipo não estiver claro.

---

# Objetivo Final da Padronização

Esta estrutura foi criada para:

* Facilitar manutenção
* Melhorar leitura do código
* Reduzir duplicação
* Padronizar respostas
* Melhorar escalabilidade
* Facilitar entrada de novos desenvolvedores
* Organizar responsabilidades de cada camada


## Objetivo

Fornecer uma base sólida para:

* Gestão de pacientes, médicos e especialidades
* Agendamento de consultas com validações
* Controle de disponibilidade médica
* Padronização de respostas da API

---

## Arquitetura

Estrutura baseada em camadas:

* **Controller** → expõe endpoints REST
* **Service** → contém regras de negócio
* **Repository** → acesso a dados (JPA)
* **DTOs** → contrato de entrada/saída

---

## Regras de Negócio

Implementadas na camada de **Service**:

as verificacoes que devemos colocar em todas as entidades

* Impedir agendamentos em datas passadas
* Evitar conflito de horários
* Validar disponibilidade do médico
* Cancelamento permitido apenas com 24h de antecedência
* Restrições de exclusão com vínculos ativos

---

## Endpoints Principais

### Pacientes

* `POST /pacientes`
* `GET /pacientes`
* `GET /pacientes/{id}`
* `PUT /pacientes/{id}`
* `DELETE /pacientes/{id}`

### Médicos

* `POST /medicos`
* `GET /medicos`
* `GET /medicos/{id}`
* `PUT /medicos/{id}`
* `DELETE /medicos/{id}`

### Consultas

* `POST /consultas`
* `GET /consultas`
* `GET /consultas/{id}`
* `PATCH /consultas/{id}/status`
* `DELETE /consultas/{id}`

### Especialidades

* `POST /especialidades`
* `GET /especialidades`
* `GET /especialidades/{id}`
* `PUT /especialidades/{id}`
* `DELETE /especialidades/{id}`

### Disponibilidade

* `POST /medicos/{medicoId}/disponibilidades`
* `GET /medicos/{medicoId}/disponibilidades`
* `DELETE /disponibilidades/{id}`

---

# CRIACAO E MODO DE TRABALHO COM BRANCHS SEPARADAS:


## Convenções de Branch

As branches devem seguir o padrão:

* `feat/nome-da-feature`
* `fix/nome-do-bug`
* `refactor/melhoria`
* `chore/tarefa-tecnica`

Exemplos:

* `feat/paciente-crud`
* `feat/consulta-agendamento`
* `fix/validacao-horario`

(O caractere `/` faz parte do nome da branch e serve apenas para organização. Ele não cria pastas no projeto.)

---

## Fluxo Completo de Desenvolvimento

### 1. Atualizar a branch principal

Antes de iniciar qualquer tarefa:

```bash
git checkout main
git pull origin main
```

---

### 2. Criar uma nova branch

```bash
git checkout -b feat/nome-da-task
```

---

### 3. Desenvolver a funcionalidade

Realizar alterações no código normalmente.

---

### 4. Adicionar e commitar alterações

```bash
git add .
git commit -m "feat: descrição clara da alteração"
```

Evitar commits genéricos como "update" ou "teste", no final desse manual tem uma lista de auxilio para commits.

---

### 5. Subir a branch para o repositório remoto

Push:

```bash
git push -u origin feat/nome-da-task
```

# PELO AMOR DE DEUS, nada de `PUSH ORIGIN MAIN`, isso quebra o codigo que ta na maquina de todo mundo


### 6. Abrir Pull Request (PR)
### Vou auxiliar um por um a fazer isso e a dar o push no primeiro enviado, ta bem tranquilo

No GitHub:

* Base: `main`
* Compare: sua branch (`feat/nome-da-task`)

Adicionar uma descrição clara do que foi feito e como testar.

---

### 7. Atualizar a branch com a main antes da revisão

VOU EVITAR AO MAXIMO, MAS PODE ACONTEDER DE DOIS ESTAREM DESENVOLVENDO AO MESMO TEMPO, O SEGUNDO QUE MANDAR VAI TER CODIGO QUEBRADO PORQUE O PRIMEIRO MEXEU

porem:

Se houver conflitos:

* Resolver localmente
* Testar novamente
* Commitar e dar push

---

### 8. Revisão de código

Aguardar aprovação do Pull Request.

Se houver solicitações de ajuste, nao vou ser cuzao, vou pedir alteracao so se quebrar codigo de outra pessoa:

* Corrigir na mesma branch
* Commitar e dar push novamente


---

### 9. Remover a branch

Após aprovado e no github

#### Remover localmente:

```bash
git branch -d feat/nome-da-task
```

isso faz com que voce nao tenha todas as branchs no teu pc atoa, so pra deixar limpo e nao ter perigo de mandar uma funcionalidade com nome de outra

---

# Boas Práticas:

## Nunca commitar diretamente na `main` 
* Sempre trabalhar com branches
* Manter commits pequenos e descritivos
* Atualizar a branch frequentemente com a `main`
* Evitar trabalhar em arquivos que outro desenvolvedor já está modificando
* Seguir o padrão definido de commits e branches, pago uma coca depois piazada, sei que 'e chato 
* NUNCA EM HIPOTESE ALGUMA, MANTER TUA BRANCH PARADA NO TEU PC UNS 3 DIAS, se voce comeca a desenvolver algo a nao terminou, avisa, sobe mesmo assim, mesmo incompleto e depois arrumamos. Se comecar e deixar parado por tempo no pc, o codigo atualiza por outra pessoa e o teu ja da conflito e te da mais trabalho

---

## Resumo Rápido

1. Atualizar `main`
2. Criar branch
3. Desenvolver
4. Commitar
5. Subir branch
6. Abrir PR
7. Corrigir se necessário
8. Deletar branch DO SEU PC 


# Convenção de Commits

Este projeto utiliza um padrão de commits para manter o histórico organizado, facilitar revisão de código e permitir entendimento rápido das mudanças.

---

## 📌 Estrutura do Commit

```bash
tipo: descrição curta e objetiva
```

Exemplo:

```bash
feat: adiciona endpoint de cadastro de paciente
```

---

## Tipos de Commit

### feat

Indica uma nova funcionalidade no sistema.

Exemplo:

```bash
feat: cria endpoint de agendamento de consulta
```

---

### fix

Correção de bug ou erro existente.

Exemplo:

```bash
fix: corrige validação de horário inválido
```

---

### refactor

Mudança no código que não altera comportamento, apenas melhora estrutura.

Exemplo:

```bash
refactor: reorganiza lógica de validação no service
```

---

### docs

Alterações em documentação.

Exemplo:

```bash
docs: atualiza README com instruções de uso
```

---

### perf

Melhoria de performance.

Exemplo:

```bash
perf: otimiza consulta ao banco de dados
```

---

### revert

Reverte um commit anterior.

Exemplo:

```bash
revert: desfaz alteração no endpoint de consulta
```

## Resumo

| Tipo     | Uso principal       |
| -------- | ------------------- |
| feat     | Nova funcionalidade |
| fix      | Correção de erro    |
| refactor | Melhoria interna    |
| chore    | Ajustes técnicos    |
| docs     | Documentação        |
| style    | Formatação          |
| test     | Testes              |
| perf     | Performance         |
| build    | Dependências/build  |
| ci       | Integração contínua |
| revert   | Reverter alterações |
