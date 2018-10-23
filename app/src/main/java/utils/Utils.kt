package utils

/**
 * Class with common methods to be used in the entire project.
 */
object Utils {

    /*
        It is not recommend to leave console logs printing stuff around.
        All my prints are made using the debugLog method, and the bool 'isDebugEnabled' is
        responsible for allowing those prints or not. 'isDebugEnabled' is always TRUE during
        debugging, and it's necessarily FALSE when building a release apk.
     */
    private const val isDebugEnabled: Boolean = true

    fun debugLog(tag: String, obj: kotlin.Any) {
        if(isDebugEnabled) {
            System.out.println(tag)
            System.out.println(obj.toString())
        }
    }

}