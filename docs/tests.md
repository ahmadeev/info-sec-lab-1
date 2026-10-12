#### Ручные проверки API

1. Запрос данных без JWT-токена. Ожидается `401 Unauthorized`.

```powershell
curl.exe `
    --silent `
    --show-error `
    --include `
    'http://localhost:8080/api/data'
```

2. Вход с корректными учётными данными. Ожидается непустой токен.

```powershell
$credentials = @{
    login = 'student'
    password = 'student-password'
} | ConvertTo-Json -Compress

$token = (
    $credentials | curl.exe `
        --silent `
        --show-error `
        --request POST `
        'http://localhost:8080/auth/login' `
        --header 'Content-Type: application/json' `
        --data-binary '@-' |
        ConvertFrom-Json
).token

$token
```

3. Создание записи с SQL-инъекцией. Ожидается `201 Created`: строка сохраняется и не изменяет структуру БД.

```powershell
$sqlInjectionBody = @{
    title = 'SQL injection payload'
    content = "' OR '1'='1'; DROP TABLE notes; --"
} | ConvertTo-Json -Compress

$sqlInjectionBody | curl.exe `
    --silent `
    --show-error `
    --include `
    --request POST `
    'http://localhost:8080/api/data' `
    --header 'Content-Type: application/json' `
    --header "Authorization: Bearer $token" `
    --data-binary '@-'
```

4. Создание записи с HTML/XSS-содержимым. Ожидается `201 Created`; в ответе символы `<` и `>` представлены как `&lt;` и `&gt;`.

```powershell
$xssBody = @{
    title = '<b>Unsafe title</b>'
    content = "<script>alert('xss')</script>"
} | ConvertTo-Json -Compress

$xssBody | curl.exe `
    --silent `
    --show-error `
    --include `
    --request POST `
    'http://localhost:8080/api/data' `
    --header 'Content-Type: application/json' `
    --header "Authorization: Bearer $token" `
    --data-binary '@-'
```

5. Получение всей коллекции записей пользователя. Ожидается `200 OK` и массив ранее созданных записей: SQL-код остаётся данными, а HTML/XSS-содержимое возвращается в экранированном виде.

```powershell
curl.exe `
    --silent `
    --show-error `
    --include `
    'http://localhost:8080/api/data' `
    --header "Authorization: Bearer $token"
```
