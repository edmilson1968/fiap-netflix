- Possibilidade de visualizar os filmes de um determinado gênero;
- Possibilidade de visualizar os detalhes de cada filme;
- Possibilidade de votar nos filmes que mais gostei;
- Possibilidade de marcar um filme ou série para ser visto no futuro;
- Possibilidade de buscar um filme por palavra-chave;
- Possibilidade de exibir os filmes mais vistos por categorias;
- Possibilidade de abrir um chamado técnico de algum problema que está acontecendo;
- Possibilidade de visualizar os filmes e séries que já foram assistido;

# Api Documentation dos microsserviços genéricos


## Version: 1.0

#### GET /v1/clientes
busca todos os clientes

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| offset | query |  | No | long |
| pageNumber | query |  | No | integer |
| pageSize | query |  | No | integer |
| paged | query |  | No | boolean |
| sort.sorted | query |  | No | boolean |
| sort.unsorted | query |  | No | boolean |
| unpaged | query |  | No | boolean |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Page«Cliente»](#page«cliente») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

#### POST /v1/clientes
adiciona um cliente

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| cliente | body | cliente | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Cliente](#cliente) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

#### GET /v1/clientes/{id}
busca um cliente

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Cliente](#cliente) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### POST /v1/clientes/{id}/chamados
abre um chamado para um cliente

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| chamado | body | chamado | Yes | string |
| id | path | id | Yes | long |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Chamado](#chamado) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Models


#### Chamado

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| cliente | long |  | No |
| dataAbertura | dateTime |  | No |
| dataFechamento | dateTime |  | No |
| descricao | string |  | No |
| id | string (uuid) |  | No |
| motivo | string |  | No |

#### Cliente

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| cpf | string |  | No |
| id | long |  | No |
| idade | integer |  | No |
| nome | string |  | No |

#### Pageable

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| offset | long |  | No |
| pageNumber | integer |  | No |
| pageSize | integer |  | No |
| paged | boolean |  | No |
| sort | [Sort](#sort) |  | No |
| unpaged | boolean |  | No |

#### Page«Cliente»

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| content | [ [Cliente](#cliente) ] |  | No |
| empty | boolean |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| number | integer |  | No |
| numberOfElements | integer |  | No |
| pageable | [Pageable](#pageable) |  | No |
| size | integer |  | No |
| sort | [Sort](#sort) |  | No |
| totalElements | long |  | No |
| totalPages | integer |  | No |

#### Sort

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| empty | boolean |  | No |
| sorted | boolean |  | No |
| unsorted | boolean |  | No |%


# Api Documentation para o microsserviço de Filmes


## Version: 1.0

#### GET /v1/filmes
busca todos os filmes. Também utiliza os parâmetros genero e título.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| genero | query | genero | No | string |
| offset | query |  | No | long |
| pageNumber | query |  | No | integer |
| pageSize | query |  | No | integer |
| paged | query |  | No | boolean |
| sort.sorted | query |  | No | boolean |
| sort.unsorted | query |  | No | boolean |
| titulo | query | titulo | No | string |
| unpaged | query |  | No | boolean |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Page«Filme»](#page«filme») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

#### POST /v1/filmes
adiciona um filme

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| cliente | body | cliente | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Filme](#filme) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### GET /v1/filmes-mais-vistos
filmes que já foram assistidos

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| offset | query |  | No | long |
| pageNumber | query |  | No | integer |
| pageSize | query |  | No | integer |
| paged | query |  | No | boolean |
| sort.sorted | query |  | No | boolean |
| sort.unsorted | query |  | No | boolean |
| unpaged | query |  | No | boolean |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Page«Filme»](#page«filme») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### GET /v1/filmes-sucessos
sucessos por categoria

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| genero | query | genero | Yes | string |
| offset | query |  | No | long |
| pageNumber | query |  | No | integer |
| pageSize | query |  | No | integer |
| paged | query |  | No | boolean |
| sort.sorted | query |  | No | boolean |
| sort.unsorted | query |  | No | boolean |
| unpaged | query |  | No | boolean |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Page«Filme»](#page«filme») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### GET /v1/filmes/{id}
busca um filme

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Filme](#filme) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Models


#### Filme

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| anoLancamento | integer |  | No |
| assistido | integer |  | No |
| detalhe | string |  | No |
| genero | string |  | No |
| id | long |  | No |
| likes | integer |  | No |
| lingua | string |  | No |
| tipo | string |  | No |
| titulo | string |  | No |

#### Pageable

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| offset | long |  | No |
| pageNumber | integer |  | No |
| pageSize | integer |  | No |
| paged | boolean |  | No |
| sort | [Sort](#sort) |  | No |
| unpaged | boolean |  | No |

#### Page«Filme»

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| content | [ [Filme](#filme) ] |  | No |
| empty | boolean |  | No |
| first | boolean |  | No |
| last | boolean |  | No |
| number | integer |  | No |
| numberOfElements | integer |  | No |
| pageable | [Pageable](#pageable) |  | No |
| size | integer |  | No |
| sort | [Sort](#sort) |  | No |
| totalElements | long |  | No |
| totalPages | integer |  | No |

#### Sort

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| empty | boolean |  | No |
| sorted | boolean |  | No |
| unsorted | boolean |  | No |%



# Api Documentation para o microsserviços de Clientes


## Version: 1.0

#### POST /v1/assistidos
marcar um filme já assistido

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| body | body | body | Yes | [Assistido](#assistido) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### POST /v1/chamados
abrir um chamado técnico

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| chamado | body | chamado | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Chamado](#chamado) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

#### GET /v1/chamados/{id}
busca um chamado

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Chamado](#chamado) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

#### PUT /v1/chamados/{id}
fechar um chamado técnico

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Chamado](#chamado) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### POST /v1/favoritos
marcar um filme como favorito

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| body | body | body | Yes | [Favorito](#favorito) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |


#### POST /v1/likes
marcar um filme com like

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| body | body | body | Yes | [Like](#like) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Models


#### Assistido

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| clienteId | long |  | No |
| filmeId | long |  | No |
| id | long |  | No |

#### Chamado

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| cliente | long |  | No |
| dataAbertura | dateTime |  | No |
| dataFechamento | dateTime |  | No |
| descricao | string |  | No |
| id | string (uuid) |  | No |
| motivo | string |  | No |

#### Favorito

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| clienteId | long |  | No |
| filmeId | long |  | No |
| id | long |  | No |

#### Like

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| clienteId | long |  | No |
| filmeId | long |  | No |
| id | long |  | No |%
