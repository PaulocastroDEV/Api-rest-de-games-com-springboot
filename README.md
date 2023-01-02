# Api de games

Nesse projeto foi desenvolvido uma api, no qual podemos cadastrar um game, editar um game ou partes dele, ver os games cadastrados ou um game especifico,  e também deletar um game, o famoso Conhecido Crud(Create, Read, Update, Delete).

# Game-ms

## Endpoints

### BaseUrl: /games
-Post: Create();
-Get: findAll();
-get/{id}: findById;
-Put/{id}: update();
-Path/{id}: updatepart();
-Delete/{id}: delete();

### Model

```Json
{
	"id": 1,
	"nome": "minecraft",
	"lançamento": 10,
	"genero": "acao",
}
