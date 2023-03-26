Descarga del repositorio de GitHub: https://github.com/SirEikir/reservas.git

Añadir la collecion de Peticiones en Postman: https://api.postman.com/collections/26322629-f79185b3-48f3-4a68-bc4e-38436846c1bc?access_key=PMAT-01GWFR0BDJ2ZKNSQ7TZ601C0XQ
Se dara un ejemplo para cada una de las peticiones ( en postman se ha establecido la varaible "http://localhost:8080" como {{host}} )
 
 Peticiones para HOTELES:
 
 CREAR UN HOTEL
 
 `-POST {{host}}/hotels`
Body-raw- JSON
```
{
    "name":"Casa Faustinos",
    "category":"5 Estrellas"
}
```

ACTUALIZAR DATOS DE UN HOTEL

 `-PUT {{host}}/hotels/{id}`
Body-raw- JSON
```
{
    "name":"Casa Modificada",
    "category":"Muchas Estrellas"
}
```


OBTENER LISTADO DE HOTELES

`-GET {{host}}/hotels`
NONE


OBTENER DATOS DE UN HOTEL SEGUN SU ID

`-GET {{host}}/hotels/{id}`



 Peticiones para DISPONIBILIDADES:
 
 CREAR UNA DISPONIBILIDAD
 
`-POST {{host}}/availabilities`
Body-raw- JSON
```
{
    "hotelId":3,
    "rooms":6,
    "initDate":"2023-03-01",
    "endDate":"2023-03-15"
}
```


OBTENER LISTADO DE DISPONIBILIDADES

`-GET {{host}}/availabilities/check?initDate=2023-03-03&endDate=2023-03-05`

Params: 
```
  "Key"=initDate "Value"=2023-03-03 (Este parametro es obligatorio)
  "Key"=endDate "Value"=2023-03-05 (Este parametro es obligatorio)
  "Key"=hotelName "Value"=2023-03-03 (Este parametro es usado como filtro)
  "Key"=hotelCategory "Value"=2023-03-03 (Este parametro es usado como filtro)
```


OBTENER DATOS DE UNA DISPONIBILIDAD SEGUN SU ID

`-GET {{host}}/availabilities?hotelId=1&date=2023-03-01`

Params: 
```
  "Key"=hotelId "Value"=1
  "Key"=date "Value"=2023-03-01
```



 Peticiones para RESERVAS:
 
 CREAR UNA RESERVA
 
 `-POST {{host}}/bookings?hotelId=3&initDate=2023-03-01&endDate=2023-03-15&email=example87658765@gmail.com`
 
Params:
```
  "Key"=hotelId "Value"=3
  "Key"=initDate "Value"=2023-03-01 
  "Key"=endDate "Value"=2023-03-15 
  "Key"=email "Value"=example87658765@gmail.com
```

OBTENER LISTADO DE RESERVAS SEGUN EL ID DEL HOTEL
`-GET {{host}}/bookings/check?hotelId=3&initDate=2023-03-01&endDate=2023-03-16`

Params:
```
  "Key"=hotelId "Value"=3
  "Key"=initDate "Value"=2023-03-01 
  "Key"=endDate "Value"=2023-03-15 
```


OBTENER DATOS DE UNA RESERVA SEGUN SU ID
`-GET {{{host}}/bookings/{id}`

CANCELAR DATOS DE UNA RESERVA SEGUN SU ID
`-DELETE {{{host}}/bookings/{id}`
