## Banco `sample_mflix`

### Coleção `movies`

1. Faça uma consulta que retorne todos os filmes do ano de 1999.
```json
{ano: 1999}```

2. Faça uma consulta que retorne todos os filmes a partir de 2010, incluindo 2010.
```json
{ano: {$gte: 2010}}
```

3. Faça uma consulta que retorne todos os filmes a partir de 2010, incluindo 2010, e que o tempo de duração em minutos (`runtime`) seja menor ou igual a 150.
```json

{ano: {$gte: 2010}, runtime: {$lte: 150}}

```

4. Altere a consulta anterior para considerar somente filmes com rating do IMDb igual a 5.5.
```json
{ano: {$gte: 2010}, runtime: {$lte: 150}, "imdb.rating": 5.5}
```

5. Faça uma consulta que busque filmes disponibilizados no idioma francês, podendo também ter outros idiomas.
```json 
{languages: "French"}```

6. Faça uma consulta que busque somente os filmes que foram disponibilizados apenas no idioma francês.
```json 
{languages: ["French"]}```

7. Consulte os filmes que contenham exatamente 3 idiomas.
{languages: { $size: 3 }}

8. João pensou que, com esta consulta, poderia retornar os filmes que possuem mais de 4 writers:

```javascript
{ "writers": { $size: { $gt: 4 } } }
```

Essa consulta consegue realizar esse trabalho? Sim ou não? Faça o teste.
não, size não aceita outros atributos como $gt

9. Faça uma consulta que retorne todos os filmes que tiveram rating do IMDb 5.5 e venceram 4 prêmios.
```json
{"imdb.rating": 5.5, "awards.wins": {$eq: 4} }
```

10. Digite essa busca:

```javascript
{
  "title": { $exists: true },
  $expr: { $gt: [ { $strLenCP: "$title" }, 100 ] }
}
```

O que ela retorna?
filmes cujo o campo "title" exitem e tem o título > 100 caracteres

O que faz a primeira linha?
verifica se o filme tem o campo `title` preenchido

O que faz a segunda linha e por que a primeira linha é necessária?
ela garante que o titulo tenha mais de 100 caracteres, é necessária pois `$strLenCP` precisa receber uma string, se o documento não tiver title, pode gerar erro

## Banco `sample_restaurants`

11. Busque todos os restaurantes que estão no distrito do Brooklyn:

```json
{ "borough": "Brooklyn" }
```

12. Altere a consulta anterior para mostrar todos os restaurantes que estão no Brooklyn e que são especializados em comida judaica/kosher.
```json
{ borough: "Brooklyn", cuisine: "Jewish/Kosher" }
```

13. Altere a consulta anterior para que retorne todos os restaurantes, independentemente de distrito, que sejam de cozinha judaica ou irlandesa.
```json
{
    $or: [
    { cuisine: "Jewish/Kosher" },
    { cuisine: "Irish" }
]}
```

14. Retorne todos os restaurantes de cozinha americana que contenham um score (`grades.score`) 60.
```json
{
  cuisine: "American",
  "grades.score": 60
}
```

15. Modifique a consulta anterior para retornar todos os restaurantes que tenham algum score acima de 130, independentemente do tipo de cozinha.
```json
{
  "grades.score": {
    $gt: 130 
  }
}
``` 

16. Estude o operador `$regex` do MongoDB para procurar todos os restaurantes que tenham a substring `burger` em qualquer posição do campo `name`.
```json
{
	name: {
    $regex: "burger", $options: "i"
  }
}
```
17. Modifique a questão anterior para buscar todos os restaurantes que comecem com a string `Angel`, considerando que o `A` maiúsculo importa.
```json
{
	name: {
    $regex: "^Angel"
  }
}
```


18. Busque todos os restaurantes que estão no zipcode `11210`.
```json
{ "address.zipcode": "11210" }
```

19. Busque todos os restaurantes cujo `borough` seja `Manhattan` e `cuisine` seja `Steak`.
```json
{borough: "Manhattan", cuisine: "Steak"}
```

## Banco `sample_analytics`

20. Na coleção `customers`, faça uma consulta que busque o documento do usuário que possui o email:

```text
cooperalexis@hotmail.com
```
```json
{email: "cooperalexis@hotmail.com"}
```

21. Ainda na coleção `customers`, busque todos os clientes que usam email do Gmail.
```json
{email: {$regex: "@gmail.com$"}}
```

22. Supondo que existisse um campo `createdAt` no documento que representasse quando ele foi criado, com data e hora, para buscar os documentos criados a partir de 01/02/2024, faríamos:

```javascript
{ createdAt: { $gt: ISODate("2024-02-01") } }
```

Considerando esse aprendizado, faça uma busca de todos os clientes que nasceram após 01/01/1997.
```json

{ birthdate: { $gt: ISODate("1997-01-01") } }
```


23. Na coleção `accounts`, procure todas as contas que tenham somente dois produtos, ou seja, dois elementos no array `products`.
```json
{"products": {$size: 2}}
```

24. Na coleção `accounts`, procure todas as contas que tenham entre os produtos somente o produto `InvestmentStock`.

```json
{ products: ["InvestmentStock"] }
```

## Banco `sample_airbnb`

25. Procure todas as acomodações que possuam preço entre 1000 e 2000.
```json
{
  price: {
    $gte: 1000,
    $lt: 2000
  }
}
```

26. Procure todas as acomodações que possuam 14 camas ou custem por noite mais de 20.000.
```json
{
  $or: [
    {
      beds: 14
    },
    {
      price: {
        $gt: 20000
      }
    }
  ]
}
```

27. Consulta livre 1 na coleção de documentos do Airbnb, com algum conceito novo de sua livre escolha.

28. Consulta livre 2 na coleção de documentos do Airbnb, com algum conceito novo de sua livre escolha.

29. Consulta livre 3 na coleção de documentos do Airbnb, com algum conceito novo de sua livre escolha.

30. Faça uma consulta que retorne acomodações que possuam a comodidade `Wifi` e que tenham nota de avaliação (`review_scores.review_scores_rating`) maior ou igual a 90.

```json
{
  amenities: "Wifi",
  "review_scores.review_scores_rating": {
    $gte: 90
  }
}
```
