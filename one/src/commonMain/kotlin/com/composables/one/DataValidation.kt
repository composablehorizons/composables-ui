package com.composables.one

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class ValidationType(val pattern: Regex)

object ValidationTypes {
    val Email = ValidationType(Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$"))
}

@Composable
fun rememberValidationState(
    type: ValidationType? = null,
    pattern: Regex? = null,
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE,
    required: Boolean = false,
): ValidationState {
    return remember {
        ValidationState(
            type = type,
            pattern = pattern,
            minLength = minLength,
            maxLength = maxLength,
            required = required
        )
    }
}

sealed class ValidationValue {
    data object Valid : ValidationValue()
    data object InvalidFormat : ValidationValue()
    data object LengthTooShort : ValidationValue()
    data object LengthTooLong : ValidationValue()
    data object RequiredValueMissing : ValidationValue()
}

class ValidationState(
    private val type: ValidationType?,
    private val pattern: Regex?,
    private val minLength: Int,
    private val maxLength: Int,
    private val required: Boolean,
) {
    var value by mutableStateOf<ValidationValue>(ValidationValue.Valid)
        private set

    init {
        validate("")
    }

    val isValid: Boolean by derivedStateOf { value == ValidationValue.Valid }

    internal fun validate(data: String) {
        value = when {
            required && data.isBlank() -> ValidationValue.RequiredValueMissing
            type != null && type.pattern.matches(data).not() -> ValidationValue.InvalidFormat
            data.length < minLength -> ValidationValue.LengthTooShort
            data.length > maxLength -> ValidationValue.LengthTooLong
            pattern != null && pattern.matches(data).not() -> ValidationValue.InvalidFormat
            else -> ValidationValue.Valid
        }
    }
}

