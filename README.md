# MedSync - Backend

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

## Contrato da API (TODOS OS DESENVOLVEDORES VAO SEGUIR PARA EVITAR CONFLITO DE FUNCIONALIDADES)

### Padrão de Resposta

#### Sucesso

```json
{
  "timestamp": "2026-04-22T20:00:00Z",
  "status": 200,
  "message": "Sucesso",
  "data": {}
}
```

#### Erro

```json
{
  "timestamp": "2026-04-22T20:00:00Z",
  "status": 400,
  "message": "Erro de validação",
  "errors": [
    "Mensagem de erro"
  ]
}
```

---

### Convenções

* Datas: ISO 8601 (`yyyy-MM-ddTHH:mm:ss`)
* IDs: sempre do tipo: `Long`
* Status de consulta: sempre do tipo: `ENUM` como no exemplo a baixo

```java
enum StatusConsulta {
    AGENDADA,
    CANCELADA,
    CONCLUIDA
}
```

---

## DTOs:

### Paciente

**Request**

```json
{
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "44999999999",
  "email": "joao@email.com"
}
```

**Response**

```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "44999999999",
  "email": "joao@email.com"
}
```

---

### Médico

**Request**

```json
{
  "nome": "Dra. Ana",
  "crm": "12345-PR",
  "especialidadeId": 1
}
```

**Response**

```json
{
  "id": 1,
  "nome": "Dra. Ana",
  "crm": "12345-PR",
  "especialidade": {
    "id": 1,
    "nome": "Cardiologia"
  }
}
```

---

### Especialidade

```json
{
  "id": 1,
  "nome": "Cardiologia"
}
```

---

### Consulta

**Request**

```json
{
  "pacienteId": 1,
  "medicoId": 2,
  "dataHora": "2026-04-25T10:00:00",
  "observacoes": "Dor no peito"
}
```

**Response**

```json
{
  "id": 10,
  "paciente": {
    "id": 1,
    "nome": "João Silva"
  },
  "medico": {
    "id": 2,
    "nome": "Dra. Ana"
  },
  "dataHora": "2026-04-25T10:00:00",
  "status": "AGENDADA",
  "observacoes": "Dor no peito"
}
```

---

### Atualizar Status

```json
{
  "status": "CANCELADA"
}
```

---

### Disponibilidade

**Request**

```json
{
  "diaSemana": "MONDAY",
  "horarioInicio": "09:00",
  "horarioFim": "17:00"
}
```

**Response**

```json
{
  "id": 1,
  "diaSemana": "MONDAY",
  "horarioInicio": "09:00",
  "horarioFim": "17:00"
}
```

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

## Diretrizes de Desenvolvimento

* Não expor Entities diretamente
* Utilizar DTOs em todas as camadas externas
* Centralizar tratamento de erros (`@ControllerAdvice`)
* Seguir o contrato de API definido
* Manter regras de negócio exclusivamente na camada Service

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

## Estrutura do Commit

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
