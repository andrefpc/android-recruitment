# Desafio técnico para Recrutamento Sympla

Criado para desafiar e medir o nível de conhecimento do candidato a vaga de desenvolvedor Android na empresa Sympla, o projeto consiste em criar um projeto Android Nativo que tem como objetivo realizar a conversão de moedas.

## Telas da aplicação

#### Splash Screen

Tela reponsável pela apresentação do app que irá redirecionar automaticamente para tela principal da aplicação.

<img src="/screenshoots/splash.png" height="400" />

#### Main Screen

Tela reponsável pela funcionalidade principal da aplicação, que é a conversão monetária.

Ela é dividida em duas partes, uma onde aparece o valor inserido convertido para a moeda selecionada e outra para exibir o multiplicador de conversão em todas as moedas.

Para adicionar o valor, basta o usuário digitar o valor sem precisar adicionar pontuações, que o número será exibido e os valores vão sendo convertidos automaticamente.

Na parte superior da tela, o usuário pode alternar ambas as moedas de conversão clicando nos seus respectivos campos e irá aparecer a listagem de todas as moedas disponíveis para conversão, após selecionar alguma delas, a tela irá ser atualizada com as imagens, nomes e valores correspondentes.

Ao clicar no botão centralizado na parte superior da tela, o usuário alterna automaticamente as moedas atualizando as conversões.

Ao clicar no botão "voltar" nativo do celular, uma alerta irá aparecer para o usuário confirmar se ele deseja sair do app.

Ao clicar em algum dos itens da lista de conversão, o usuário é redirecionado para a tela de Histórico, para poder visualizar os histórico de conversão dessa moedas.

<img src="/screenshoots/main1.png" height="400" />   <img src="/screenshoots/main2.png" height="400" />   <img src="/screenshoots/main4.png" height="400" /> <img src="/screenshoots/main3.png" height="400" />

#### History Screen

Tela reponsável por exibir o histórico de valores de conversão das moedas selecionadas desde o mesmo dia do mês anterior até hoje.

Nela é exibido um gráfico e uma listagem para visualização desse histórico.

<img src="/screenshoots/history.png" height="400" /> 

## Linguagem

Mesmo tendo domínio maior sobre a linguagem Java, optei por utilizar a linguagem Kotlin para desenvolvimento do projeto, pois como o objetivo é me desafiar, nada melhor do que sair da minha zona de conforto e utilizar um linguagem mais nova e que está em crescente no mercado.

## Arquitetura

Foi utilizada a arquitetura MVC no projeto, entretanto, a estrutura de pastas utilizada tem uma certa identidade da minha parte, pois eu tento ao máximo separar as classes em pacotes que correspondem as suas especificidades.

A estrutura de pacotes ficou a seguinte:

- <b>adapter</b> : Onde estão todos os adapters da aplicação (listagens por exemplo).
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

## Sdks

Além dos recursos nativos do Android, alguns sdks externos foram utilizados para auxílio no desenvolvimento da aplicação:

- <b>Glide</b> : Biblioteca usada para facilitar a exibição de imagens (via Url, gifs, etc).
- <b>Volley</b> : Biblioteca usada para facilitar as chamadas HTTP da aplicação.
- <b>ReactiveX</b> : Biblioteca utilizada para desenvolvimento reativo na aplicação.
- <b>Gson</b> : Biblioteca utilizada para falicitar o parser de Jsons em objetos.
- <b>WilliamChart</b> : Biblioteca utilizada para geração de gráficos na aplicação.

## Apis

Foram utlizadas duas apis na aplicação para recuperar dados relativos a converção e exibição de moedas:

- <b>Foreign exchange rates API</b> - Api utilizada para recuperar os valores de conversão das moedas disponíveis no app
  - https://exchangeratesapi.io/
- <b>Rest Countries API</b> - Api utilizada para recuperar os simbolos das moedas disponíveis no app
  - https://restcountries.eu/
  
## Testes

Além de testar o app por contra própria e testar com amigos, criei um Teste de Instrumentação automatizado simples, nomeado por MainScreenTest, para testar alguns fluxos do app:

- <b>changeFromCurrency</b> : Testa se a funcionalidade de trocar a moeda de origem está funcionando como esperado.
- <b>changeToCurrency</b> : Testa se a funcionalidade de trocar a moeda de destino está funcionando como esperado. 
- <b>openHistory</b> : Testa se ao clicar em um item da lista de conversão, o usuário é redirecionado para a tela de histórico de conversão.
- <b>changeButton</b> : Testa se o botão de trocar as moedas simultâneamente está funcionando com o esperado. 

## Versionamento

O app foi versionado utlizando a platafoma Git e apesar do meu conhecimento em algumas arquiteturas de versionamento, e sabendo que apenas eu estou desenvolvendo nesse projeto, optei por utilizar commits mais simples para ganhar em velocidade no desenvolvimento.
