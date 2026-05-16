package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Box
import com.composables.icons.lucide.Lucide
import com.composables.one.ValidationTypes
import com.composables.one.ValidationValue
import com.composables.one.components.GhostButton
import com.composables.one.components.Icon
import com.composables.one.components.PrimaryButton
import com.composables.one.components.Text
import com.composables.one.components.TextField
import com.composables.one.rememberValidationState
import com.composables.one.scaffolds.AuthScreenScaffold
import com.composables.one.styling.colors
import com.composables.one.styling.primary
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme

@Composable
fun AuthScreenScaffoldExample(navigateToHome: () -> Unit = {}, navigateToSignUp: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailValidationState = rememberValidationState(
        type = ValidationTypes.Email,
        required = true,
    )
    val passwordValidationState = rememberValidationState(
        minLength = 8,
        pattern = Regex("^(?=.*[!@#\$%^&*()_\\-+={}\\[\\]|:;\"'<>,.?/~`]).+$"),
        required = true,
    )

    val canSubmit by remember {
        derivedStateOf {
            emailValidationState.isValid && passwordValidationState.isValid
        }
    }

    AuthScreenScaffold(contentPadding = PaddingValues(top = 40.dp)) {
        AppLogo(Modifier.fillMaxWidth())
        Text(
            text = "Sign in to your account",
            style = Theme[textStyles][title],
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        TextField(
            label = { Text("Email") },
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            error = email.isNotBlank() && emailValidationState.isValid.not(),
            modifier = Modifier.semantics {
                contentType = ContentType.Username
            },
            validationState = emailValidationState,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            supportive = {
                val errorMessage by remember {
                    derivedStateOf {
                        when (emailValidationState.value) {
                            ValidationValue.RequiredValueMissing, ValidationValue.Valid -> ""
                            ValidationValue.InvalidFormat -> {
                                if (email.contains("@").not()) {
                                    "The email must contain the '@'"
                                } else {
                                    "Email address is invalid."
                                }
                            }

                            else -> error("We don't handle this case: ${emailValidationState.value}")
                        }
                    }
                }
                Text(errorMessage, modifier = Modifier.alpha(if (errorMessage.isNotBlank()) 1f else 0f))
            }
        )
        var showPassword by remember { mutableStateOf(false) }

        TextField(
            label = { Text("Password") },
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.semantics { contentType = ContentType.Password },
            error = password.isNotBlank() && passwordValidationState.isValid.not(),
            validationState = passwordValidationState,
            supportive = {
                val errorMessage by remember {
                    derivedStateOf {
                        when (passwordValidationState.value) {
                            ValidationValue.RequiredValueMissing, ValidationValue.Valid -> ""
                            ValidationValue.LengthTooShort -> "Must contain at at least 8 characters."
                            ValidationValue.InvalidFormat -> "Must contain at least one special character."
                            else -> error("We don't handle this case: ${passwordValidationState.value}")
                        }
                    }
                }
                Text(errorMessage, modifier = Modifier.alpha(if (errorMessage.isNotBlank()) 1f else 0f))
            }
        )

        Column {
            PrimaryButton(
                onClick = { navigateToHome() },
                modifier = Modifier.fillMaxWidth(),
                enabled = canSubmit,
            ) {
                Text("Log In")
            }
            Spacer(Modifier.height(24.dp))
            GhostButton(onClick = { navigateToSignUp() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sign up instead", modifier = Modifier.alpha(0.6f))
            }
        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIcon()
        Text("Instabox", fontWeight = FontWeight.Normal, fontSize = 24.sp)
    }
}

@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = Lucide.Box,
        contentDescription = null,
        tint = Theme[colors][primary]
    )
}
