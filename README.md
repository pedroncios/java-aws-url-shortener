# Encurtador de URLs usando AWS Lambda e S3

**Sistema de encurtamento de URLs** utilizando a AWS como infraestrutura serverless.

O objetivo é permitir que os usuários criem URLs curtas que redirecionem para URLs originais, com um tempo de expiração configurável.

O sistema é composto por duas funções `Lambda`: 
1. A primeira função é responsável por **gerar e armazenar** os links encurtados em um `bucket S3`, junto com informações como a URL original e o tempo de expiração; 

2. A segunda função **gerencia o redirecionamento**, verificando o código da URL curta e validando se ainda está dentro do prazo de expiração antes de redirecionar o usuário.