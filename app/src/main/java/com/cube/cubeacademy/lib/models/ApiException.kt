/**
 * ApiException represents an exception that occurs when interacting with a remote API.
 * It can encapsulate error details such as error codes, error messages, and additional information
 * specific to the API response.
 */
/**
 * ApiException represents an exception that occurs when interacting with a remote API.
 * It can encapsulate error details such as error codes, error messages, and additional information
 * specific to the API response.
 */
class ApiException(message: String) : Exception(message) {
     override var message: String? = null

    /**
     * Construct a new ApiException with an error message.
     *
     * @param message       A human-readable error message explaining the issue.
     * @param errorDetails  Additional error details or context for debugging.
     */
    constructor(message: String, errorDetails: String?) : this(message) {

        this.message = message
    }
}
