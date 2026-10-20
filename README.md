# API de Cadastro de Usuários — Testes Unitários com JUnit 5 e Mockito

## 📌 Sobre o Projeto

Este repositório tem como objetivo demonstrar a aplicação de **testes unitários em uma API REST de Cadastro de Usuários**, desenvolvida com Java e Spring Boot.

Partindo de uma API previamente implementada, o projeto foi utilizado como ambiente de estudo para compreender e aplicar conceitos de testes unitários, explorando diferentes camadas da aplicação e suas respectivas responsabilidades.

Durante o desenvolvimento, foram criadas classes de teste para os DTOs, Converters/Mappers, Service e Controller, utilizando JUnit 5 e Mockito para validar comportamentos, verificar regras de negócio e isolar dependências.

A implementação dos testes foi registrado por meio de commits incrementais neste repositório Git, permitindo acompanhar a evolução do aprendizado e a construção progressiva da suíte de testes.

## 🎯 Objetivos

- Compreender os fundamentos dos testes unitários no ecossistema Java.
- Aplicar o JUnit 5 na criação e execução de testes unitários.
- Utilizar o Mockito para simular dependências e verificar interações entre componentes.
- Validar a conversão de dados entre DTOs e entidades.
- Testar regras de negócio implementadas na camada Service.
- Verificar o comportamento dos endpoints da API na camada Controller.
- Aplicar boas práticas de organização, legibilidade e manutenção de testes.
- Registrar a evolução do desenvolvimento por meio de commits versionados.

## 🛠️ Tecnologias e Ferramentas

- **Java** — linguagem de programação utilizada no projeto.
- **Spring Boot** — framework utilizado na implementação da API REST.
- **JUnit 5** — framework para criação e execução dos testes automatizados.
- **Mockito** — biblioteca para criação de mocks e simulação de dependências.
- **Gradle** — ferramenta de gerenciamento de dependências e execução do projeto, conforme a configuração do repositório.
- **Git e GitHub** — ferramentas utilizadas para versionamento e armazenamento do código-fonte.

## 🧪 Estrutura e Organização dos Testes

Os testes foram organizados de acordo com as responsabilidades dos componentes da aplicação, buscando facilitar a leitura, a manutenção e a identificação dos cenários validados.

### 1. Fixtures de Request e Response

As fixtures são utilizadas para centralizar a criação e a preparação de objetos DTO que podem ser reutilizados em diferentes cenários de teste.

Essa abordagem reduz a repetição de código, facilita a montagem dos dados e contribui para que os testes fiquem mais objetivos e legíveis.

#### 📥 Request Fixtures

**Pacote:** `api/request`

| Classe | Responsabilidade |
|---|---|
| `EnderecoDTORequestFixture` | Centralizar a preparação de objetos de Request relacionados aos dados de endereço. |
| `UsuarioDTORequestFixture` | Centralizar a preparação de objetos de Request relacionados aos dados do usuário. |

Essas fixtures apoiam a criação dos dados utilizados nos cenários que envolvem o recebimento de informações para cadastro ou atualização de usuários.

#### 📤 Response Fixtures

**Pacote:** `api/response`

| Classe | Responsabilidade |
|---|---|
| `EnderecoDTOResponseFixture` | Centralizar a preparação de objetos de Response relacionados aos dados de endereço. |
| `UsuarioDTOResponseFixture` | Centralizar a preparação de objetos de Response relacionados aos dados do usuário. |

Essas fixtures auxiliam na preparação dos objetos utilizados na validação de dados retornados pela aplicação.

**Objetivo dessa organização:** manter a criação dos objetos de teste separada da lógica de verificação, permitindo reutilizar os mesmos dados em diferentes classes de teste.

### 2. Testes dos Converters e Mappers

**Pacote:** `api/converter`

Essa camada é responsável pelas transformações de dados entre os diferentes modelos utilizados pela aplicação.

Os testes dos Converters/Mappers têm como objetivo verificar se essas transformações respeitam os mapeamentos definidos e se os atributos são transferidos corretamente entre os objetos envolvidos.

#### `UsuarioConverterTest`

Classe de teste dedicada ao `UsuarioConverter`.

Seu objetivo é verificar o comportamento das conversões de dados relacionadas ao usuário, conforme as responsabilidades implementadas no Converter.

Os testes dessa classe podem contemplar, de acordo com os cenários implementados:

- Conversão entre os objetos de origem e destino.
- Correspondência dos atributos durante a transformação.
- Tratamento dos dados relacionados ao usuário.

#### `UsuarioMapperTest`

Classe de teste dedicada ao `UsuarioMapper`.

Seu objetivo é validar o mapeamento de dados relacionado às operações contempladas por esse Mapper, verificando se os atributos são transferidos para o objeto de destino conforme esperado.

#### `UsuarioUpdateMapperTest`

Classe de teste dedicada ao `UsuarioUpdateMapper`.

Seu objetivo é verificar o mapeamento de dados utilizado no processo de atualização de usuários.

Essa classe merece destaque por tratar especificamente do fluxo de atualização, que pode possuir regras de mapeamento diferentes daquelas utilizadas na criação de um usuário.

**Importância dos testes dessa camada:** garantir a consistência das transformações de dados e reduzir o risco de erros no preenchimento dos objetos utilizados pelas demais camadas da aplicação.

### 3. Testes da Service

**Pacote:** `business/`

#### `UsuarioServiceTest`

Classe responsável pelos testes unitários da camada de serviço da API de Cadastro de Usuários.

A Service concentra as operações de negócio relacionadas ao cadastro, à atualização, à consulta e à exclusão de usuários.

Os testes dessa classe têm como objetivo verificar os comportamentos implementados na camada de negócio, isolando suas dependências sempre que necessário por meio de mocks.

As operações associadas aos endpoints da API incluem:

- **Cadastro de usuário:** verificar o comportamento da operação de gravação dos dados.
- **Atualização de usuário:** verificar a execução das regras relacionadas à alteração dos dados cadastrados.
- **Consulta por e-mail:** verificar o comportamento da busca de um usuário utilizando seu endereço de e-mail.
- **Exclusão de usuário:** verificar o comportamento da operação de remoção dos dados.

Além dos cenários de sucesso, a suíte pode contemplar situações excepcionais, como usuários não encontrados e falhas nas operações, conforme as regras implementadas na Service e os casos efetivamente cobertos pelos testes.

A utilização do Mockito permite configurar o comportamento das dependências e verificar suas interações com a Service, sem exigir a execução de toda a infraestrutura da aplicação.

**Importância dos testes dessa camada:** validar as regras de negócio de maneira isolada, garantindo que os comportamentos esperados sejam respeitados diante dos diferentes cenários previstos.

### 4. Testes da Controller

**Pacote:** `api/`

#### `UsuarioControllerTest`

Classe responsável pelos testes da Controller, componente que disponibiliza os endpoints REST e recebe as requisições destinadas à API de Cadastro de Usuários.

Os testes dessa classe têm como objetivo verificar o comportamento dos endpoints, incluindo o tratamento das requisições, as interações com a Service e as respostas produzidas pela aplicação, conforme a estratégia de teste utilizada.

A Controller disponibiliza os seguintes endpoints funcionais:

| Operação HTTP | Método | Finalidade |
|---|---|---|
| `POST` | `gravaDadosUsuario` | Receber os dados necessários para cadastrar um usuário. |
| `PUT` | `atualizaDadosUsuario` | Receber os dados necessários para atualizar um usuário. |
| `GET` | `buscaUsuarioPorEmail` | Consultar um usuário a partir do seu e-mail. |
| `DELETE` | `deletaDadosUsuario` | Solicitar a exclusão de um usuário. |

Os testes da Controller podem abranger, conforme os cenários implementados:

- Verificação do comportamento de cada endpoint.
- Validação dos dados retornados nas respostas.
- Verificação dos códigos de status HTTP.
- Validação dos parâmetros e dos objetos recebidos nas requisições.
- Verificação das chamadas realizadas à camada Service.
- Tratamento dos cenários de sucesso e de erro.

**Importância dos testes dessa camada:** verificar se a interface HTTP da aplicação se comporta conforme esperado e se as requisições são encaminhadas corretamente à camada responsável pelas regras de negócio.

## 🧩 Estratégia de Testes

A abordagem adotada consiste em testar os componentes individualmente, respeitando suas responsabilidades e isolando as dependências sempre que necessário.

O JUnit 5 fornece a estrutura para organizar, executar e verificar os testes. O Mockito permite simular comportamentos de componentes externos à unidade em teste e verificar as interações realizadas.

De forma geral, a estratégia contempla:

- **Preparação:** criação dos objetos e configuração dos comportamentos esperados das dependências.
- **Execução:** chamada ao método ou componente que está sendo testado.
- **Verificação:** validação dos resultados obtidos e das interações realizadas.

Essa organização pode ser expressa pelo padrão **Arrange, Act, Assert (AAA)**, que contribui para a clareza e a manutenção dos testes.

## 📚 Aprendizados

O desenvolvimento deste projeto proporcionou a oportunidade de estudar e aplicar conceitos fundamentais de testes automatizados em aplicações Java com Spring Boot, incluindo:

- Criação e organização de testes unitários com JUnit 5.
- Uso de mocks e stubs com Mockito.
- Isolamento de dependências durante a execução dos testes.
- Validação de conversões entre DTOs e entidades.
- Testes das regras de negócio na camada Service.
- Testes da camada Controller e de seus endpoints.
- Reutilização de dados de teste por meio de fixtures.
- Versionamento incremental do aprendizado utilizando Git.

A construção progressiva da suíte de testes também permitiu acompanhar a evolução do projeto e consolidar o conhecimento por meio da implementação prática.
