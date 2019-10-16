# Desafio para Recrutamento Sympla

Criado para desafiar e medir o nível de conhecimento do candidato a vaga de desenvolvedor Android na empresa Sympla, o projeto consiste em criar um projeto Android Nativo que tem como objetivo realizar a conversão de moedas.

### Linguagem Utilizada

Mesmo tendo domínio maior sobre a linguagem Java, optei por utilizar a linguagem Kotlin para desenvolvimento do projeto, pois como o objetivo é me desafiar, nada melhor do que sair da minha zona de conforto e utilizar um linguagem mais nova e que está em crescente no mercado.

### Arquitetura Utilizada

Foi utilizada a arquitetura MVC no projeto, entretanto, a estrutura de pastas utilizada tem uma certa identidade da minha parte, pois eu tento ao máximo separar as classes em pacotes que correspondem as suas especificidades.

A estrutura de pacotes ficou a seguinte:

- <b>adpter</b> : Onde estão todos os adapters da aplicação (listagens por exemplo).
- <b>controller</b> : Onde estão as Activities e ViewControllers do projeto.
- <b>model</b> : Onde estão os objetos de modelo do projeto.
- <b>service</b> : Onde estão as classes responsáveis pelos requests que o app realiza.
- <b>util</b> : Onde estão as classes utilitárias que a app utiliza.

Os arquivos de resource estão dispostos de acordo com os padrões recomendados pela plataforma, dentre elas foram usados os diretórios:

- <b>drawable</b> : Onde estão os arquivos de design da aplicação (imagens e formas criadas).
- <b>font</b> : Onde estão os arquivos de fonte usados na aplicação.
- <b>layout</b> : Onde estão os arquivos de layout de todas as telas, dialogs, rows, etc.
- <b>mipmap</b> : Onde estão os icones da aplicação
- <b>raw</b> : Onde está o gif utilizado na SplashScreen
- <b>values</b> : Onde estão os arquivos de cores, dimensões, textos e estilos da aplicação

### Sdks Utlizados

Além dos recursos nativos do Android, alguns sdks externos foram utilizados para auxílio no desenvolvimento da aplicação:

- <b>Glide</b> - Biblioteca usada para facilitar a exibição de imagens (via Url, gifs, etc).
- <b>Volley</b> - Biblioteca usada para facilitar as chamadas HTTP da aplicação.
- <b>ReactiveX</b> - Biblioteca utilizada para desenvolvimento reativo na aplicação.
- <b>Gson</b> - Biblioteca utilizada para falicitar o parser de Jsons em objetos.
- <b>WilliamChart</b> - Biblioteca utilizada para geração de gráficos na aplicação.

### Apis Utlizadas

Foram utlizadas duas apis na aplicação para recuperar dados relativos a converção e exibição de moedas:

- <b>Foreign exchange rates API</b> - Api utilizada para recuperar os valores de conversão das moedas disponíveis no app
  - https://exchangeratesapi.io/
- <b>Rest Countries API</b> - Api utilizada para recuperar os simbolos das moedas disponíveis no app
  - https://restcountries.eu/

### Versionamento

O app foi versionado utlizando a platafoma Git e apesar do meu conhecimento em algumas arquiteturas de versionamento, e sabendo que apenas eu estou desenvolvendo nesse projeto, optei por utilizar commits mais simples para ganhar em velocidade no desenvolvimento.
