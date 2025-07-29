# language: pt

Funcionalidade: Validação de Login
  Como um usuário padrão
  Quero acessar o sistema com login e senha
  Para validar se login será realizado com sucesso

  Contexto:
    Dado que o usuário acessa a página de login

  @positivo
  Cenário: Login com sucesso
    Quando o usuário preenche o campo usuário com "tomsmith"
    E preenche o campo senha com "SuperSecretPassword!"
    E clica no botão de login
    Então deve visualizar a mensagem "You logged into a secure area!"

  @negativo
  Cenário: Login com senha incorreta
    Quando o usuário preenche o campo usuário com "tomsmith"
    E preenche o campo senha com "123testando"
    E clica no botão de login
    Então deve visualizar a mensagem "Your password is invalid!"
