package com.mirogal.cocktail.presentation.constant

enum class AuthDataValidStatus(val key: String) {

    LOGIN_VALID_PASSWORD_VALID("valid_valid"),
    LOGIN_VALID_PASSWORD_INVALID("valid_invalid"),
    LOGIN_INVALID_PASSWORD_VALID("invalid_valid"),
    LOGIN_INVALID_PASSWORD_INVALID("invalid_invalid")

}
