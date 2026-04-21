# AquaGuardService: Serviço RESTful para Monitoramento Hídrico (ESG)

Este projeto implementa um serviço RESTful em **Spring Boot** com **Java 17+** para monitoramento e gestão de recursos hídricos, alinhado ao tema ESG **"Acesso à água e preservação de recursos naturais"**.

## Contexto do Projeto

O **AquaGuardService** é a camada de serviço que dá suporte à arquitetura proposta no projeto anterior, o **AquaGuard AI - Sistema Inteligente de Prevenção de Enchentes**.

Enquanto o **AquaGuard AI** (documentado no PDF anexo) focava na visão geral e na aplicação de Inteligência Artificial para prevenção de enchentes, este projeto (AquaGuardService) concentra-se na **implementação prática** da API que coleta dados de sensores, gerencia alertas e fornece a base de dados para a análise preditiva.

## Requisitos Mandatórios

*   **Java 17+**
*   **Maven**
*   **Docker** e **Docker Compose**
*   **Postman** ou **Insomnia** (para testar os endpoints)

## 1. Estrutura do Projeto

O projeto segue a estrutura padrão de uma aplicação Spring Boot, com as seguintes pastas e arquivos chave:

```
AquaGuardService/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aquaguard/service/
│   │   │       ├── config/             # Configuração do Spring Security
│   │   ├── resources/
│   │   │   ├── db/migration/         # Scripts de migração Flyway
│   │   │   └── application.properties  # Configurações da aplicação
├── pom.xml                         # Dependências (Spring Boot, JPA, Oracle, Flyway, Security)
├── Dockerfile                      # Imagem Docker da Aplicação
├── docker-compose.yml              # Orquestração da Aplicação e do Banco de Dados Oracle
└── AquaGuardService_Postman_Collection.json # Collection para testes
```

## 2. Execução do Projeto (Docker Compose)

A execução do projeto utiliza `docker-compose` para orquestrar a aplicação Spring Boot e o banco de dados Oracle, conforme exigido pela atividade.

### Passo 2.1: Construir e Iniciar os Contêineres

1.  Navegue até o diretório raiz do projeto (`AquaGuardService`).
2.  Execute o comando para construir a imagem da aplicação e iniciar os serviços (Oracle e Aplicação):

    ```bash
    docker-compose up --build -d
    ```

    *   **Observação:** A primeira execução pode demorar, pois o Docker precisa baixar a imagem do Oracle Database (cerca de 3 GB).
    *   O serviço `oracle` será iniciado e o `app` aguardará o `oracle` estar saudável antes de iniciar.

### Passo 2.2: Verificar o Status

Verifique se os contêineres estão em execução:

```bash
docker-compose ps
```

Você deve ver `oracle-db` e `aquaguard-app` com status `Up`.

### Passo 2.3: Acessar a Aplicação

A aplicação estará acessível em `http://localhost:8080`.

## 3. Teste dos Endpoints (Postman/Insomnia)

1.  Importe o arquivo `AquaGuardService_Postman_Collection.json` no seu cliente REST (Postman ou Insomnia).
2.  A collection contém todos os endpoints implementados, incluindo:
    *   **Endpoints Públicos:** `POST /api/v1/leituras` (para simulação de dados IoT).
    *   **Endpoints Protegidos:** Para acesso a dados de Sensores e Alertas, exigindo autenticação.

### Credenciais de Teste (Basic Auth)

| Usuário | Senha | Papéis |
| :--- | :--- | :--- |
| `admin` | `adminpass` | `ADMIN`, `USER` |
| `user` | `userpass` | `USER` |

## 4. Funcionalidades Implementadas

O serviço oferece os seguintes endpoints RESTful:

| Recurso | Método | Endpoint | Descrição | Proteção |
| :--- | :--- | :--- | :--- | :--- |
| **Leitura** | `POST` | `/api/v1/leituras` | Registra uma nova leitura de sensor (simulação IoT). | Pública |
| **Sensor** | `POST` | `/api/v1/sensores` | Cadastra um novo sensor. | ADMIN |
| **Sensor** | `GET` | `/api/v1/sensores` | Lista todos os sensores. | USER |
| **Alerta** | `GET` | `/api/v1/alertas` | Lista todos os alertas. | USER |
| **Alerta** | `PUT` | `/api/v1/alertas/{id}/resolver` | Marca um alerta como resolvido. | ADMIN |
| **Relatório** | `GET` | `/api/v1/relatorios/consumo-mensal/{sensorId}` | Gera um relatório de consumo mensal. | USER |

## 5. Parar e Remover Contêineres

Para parar e remover os contêineres e a rede Docker:

```bash
docker-compose down
```

Para remover também o volume de dados do Oracle (limpeza total):

```bash
docker-compose down -v
```
