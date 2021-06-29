package createUser;

import services.UserApi;

public class UserBaseTest {
    protected UserApi userApi = new UserApi();
}


//Json path  = Groovy's GPath
// https://www.javadoc.io/doc/io.rest-assured/json-path/3.0.0/io/restassured/path/json/JsonPath.html
// Первый/последний
// store.book[1..5].title
//условия в фигурных скобках

// Мачеры hamcrest
// Логически  (allOf, anyOff)
// Коллекция  (hasItem, hasKey)
// Текст  (startsWith, endsWith, containsString)
// Объекты (hasItem, hasKey, ...)    body("store.book.category", hasItem("fiction"))
//Поддерживает методы min, max, size

  /*      { "store": {
            "book": [
            { "category": "reference",
                    "author": "Nigel Rees",
                    "title": "Sayings of the Century",
                    "price": 8.95
            },
            { "category": "fiction",
                    "author": "J. R. R. Tolkien",
                    "title": "The Lord of the Rings",
                    "isbn": "0-395-19395-8",
                    "price": 22.99
            }
                    ],
            "bicycle": {
                "color": "red",
                        "price": 19.95
            }
        }
        }*/
