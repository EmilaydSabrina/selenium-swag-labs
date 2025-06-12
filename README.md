# selenium-swag-labs
Projeto com testes automatizados para a plataforma Swag Labs. Utilizando Selenium e Java.

Swag Labs é um site de demonstração desenvolvido pela Sauce Labs para fins de testes automatizados. Ele simula uma loja virtual com funcionalidades como:

- Autenticação (login)
- Tela inicial - Listagem de produtos
- Carrinho de compras
- Finalização de compra (checkout)
- Logout

Foram criados dois documentos para organização e planejamento do projeto:
[Swag Labs -Roteiro](https://docs.google.com/spreadsheets/d/16XS63v6QGScx431QsICrOG694evS26oM/editusp=sharing&ouid=114784664121049974449&rtpof=true&sd=true) e 
[Identificação e Planejamento de Testes](https://docs.google.com/document/d/1p8H10PrEHsa9A97XkJTRZkm-eeK0ndq3qlfpuUfEGBY/edit?usp=sharing)

## Como o Roteiro está organizado:
- Os testes estão agrupados por CT (caso de teste principal).
- Cada CT contém vários subcasos numerados como #1.1, #1.2, etc.
- Para facilitar a navegação, foi utilizada a funcionalidade do Google Sheets para agrupamento de linhas – que permite expandir ou recolher subcasos clicando no ícone de seta ou clicando no símbolo de "+" à esquerda da numeração das linhas.

## Funcionalidades Testadas:
### O projeto cobre os seguintes cenários principais:

Login com usuário válido e inválido
Listagem e validação dos produtos
Adição e remoção de itens no carrinho
Validação do número de itens no carrinho
Validação completa do fluxo de checkout
Navegação entre páginas usando o menu lateral
Logout do usuário

## Como Executar o Projeto:
Antes de iniciar, certifique-se de que você tem as ferramentas abaixo instaladas no seu sistema:

## Pré-requisitos
- Java 17+
- Apache Maven
- Git
- Uma IDE (VS Code, IntelliJ, Eclipse, etc.)
- Navegador Google Chrome instalado (utilizado nos testes com ChromeDriver)

Passo a Passo:
1. Clone o repositório do projeto.
2. Importe o projeto para sua IDE favorita e instale as extensões:
- Java Extension Pack
- Maven for Java
3. Instale as dependências do projeto com Maven:
  mvn clean install
4. Execute os testes automatizados:
  mvn test
5. Você também pode executar uma classe de teste específica com o comando:
  mvn -Dtest=NomeDaClasseDeTeste

Observações:
Os testes são executados utilizando o ChromeDriver. Certifique-se de que:
O chromedriver esteja compatível com a versão do seu navegador Chrome.
O chromedriver esteja localizado no PATH do sistema ou configurado diretamente no código.

Independência dos Testes:
Todos os testes automatizados neste projeto foram desenvolvidos de forma independente e isolada. Isso significa que:
- Cada teste executa seu próprio cenário completo, desde o login até a verificação dos resultados.
- Não há dependência entre testes, ou seja, a execução de um não afeta o outro.
- Os testes podem ser executados individualmente ou em conjunto, com o mesmo resultado esperado.
- Isso permite uma manutenção mais simples e uma integração contínua mais confiável.
Essa abordagem garante que falhas em um teste não contaminem os demais.




