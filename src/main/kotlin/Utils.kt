object Utils {
    fun loadResource(name: String): String {
        return Utils.javaClass.classLoader.getResourceAsStream(name).bufferedReader().readText()
    }
}