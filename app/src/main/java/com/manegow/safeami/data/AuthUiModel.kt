package com.manegow.safeami.data

import com.manegow.safeami.util.Event

data class AuthUiModel (
    val showProgress: Boolean,
    val error: Event<Pair<AuthType, MaterialDialogContent>>?,
    val success: Boolean ,
    val showAllLinkprovider: Event<Pair<List<String>, MaterialDialogContent>>?
)