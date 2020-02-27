package com.manegow.safeami.data

fun String.toAuthType(): AuthType{
    return when (this){
        AuthType.FACEBOOK.authValue -> AuthType.FACEBOOK
        AuthType.TWITTER.authValue -> AuthType.TWITTER
        AuthType.GOOGLE.authValue -> AuthType.GOOGLE
        AuthType.GITHUB.authValue -> AuthType.GITHUB
        else -> AuthType.EMAIL
    }
}

enum class AuthType(var authValue: String) {
    FACEBOOK("facebook.com"),
    GOOGLE("google.com"),
    GITHUB("github.com"),
    TWITTER("twitter.com"),
    EMAIL("")
}