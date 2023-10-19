package fixture.client

interface Client {
    /**
     * @param version версия апи
     * @param path путь к ресурсу
     * @param request тело запроса
    **/
    suspend fun sendAndReceive(version: String, path: String, request: String) : String
}