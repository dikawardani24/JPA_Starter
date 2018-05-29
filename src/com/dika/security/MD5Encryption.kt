package com.dika.security

import com.dika.Security
import com.dika.SystemException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MD5Encryption: Security<String, String>() {
    override fun secure(input: String): String {
        return try {
            MessageDigest.getInstance("MD5").run {
                update(input.toByteArray(), 0, input.length)
                 BigInteger(1, digest()).toString(16)
            }
        } catch (ne: NoSuchAlgorithmException) {
            throw SystemException("${ne.message}", ne)
        }
    }

    /**
     * Matching the encrypted string to the input string
     * @param   encrypted set the encrypted value
     * @param   input set the input to be matched
     */
    fun isMatched(encrypted: String, input: String): Boolean
            = secure(input) == encrypted
}