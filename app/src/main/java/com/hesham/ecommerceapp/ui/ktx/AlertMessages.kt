package com.hesham.ecommerceapp.ui.ktx

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.geniusforapp.fancydialog.builders.FancyDialogBuilder
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.domain.entities.APIErrorException
import com.hesham.ecommerceapp.domain.entities.CantReachServerException
import com.hesham.ecommerceapp.domain.entities.ConflictException
import com.hesham.ecommerceapp.domain.entities.ForbiddenException
import com.hesham.ecommerceapp.domain.entities.InternalServerErrorException
import com.hesham.ecommerceapp.domain.entities.LimitException
import com.hesham.ecommerceapp.domain.entities.NotFoundedException
import com.hesham.ecommerceapp.domain.entities.RecordExpiredException
import com.hesham.ecommerceapp.domain.entities.UnauthorizedException
import com.hesham.ecommerceapp.domain.entities.handleHttpExceptions
import timber.log.Timber

fun Fragment.showError(throwable: Throwable?) {
    throwable?.printStackTrace()
    requireContext().showError(throwable)
}

fun Context.showError(throwable: Throwable?) {
    throwable?.printStackTrace()
    Timber.d(throwable)
    getErrorMessage(this, throwable).takeIf { it.isNotEmpty() }?.let {
        alerter(getString(R.string.text_sorry), it)
    }
}

fun Context.alerter(title: String, message: String) {
    FancyDialogBuilder(this, R.style.AppFancyDialogStyle)
        .withImageIcon(R.drawable.ic_alert_discount)
        .withTitle(title)
        .withSubTitle(message)
        .withTitleTypeFace(R.font.bold)
        .withSubTitleTypeFace(R.font.reguler)
        .withPositive(android.R.string.ok) { w, d -> d.dismiss() }
        .show()
}

fun Fragment.alerter(title: String, message: String) {
    requireContext().alerter(title, message)
}

fun getErrorMessage(context: Context, throwable: Throwable?): String {
    return when (throwable ?: Throwable()) {
        is ForbiddenException -> context.getString(R.string.text_message_forbidden)
        is ConflictException -> context.getString(R.string.text_message_record_exist)

        is InternalServerErrorException -> context.getString(R.string.text_message_internal_server_error)

        is LimitException -> context.getString(R.string.error_limit)
        is RecordExpiredException -> context.getString(R.string.error_record_is_expired)

        is NotFoundedException -> context.getString(R.string.error_not_founded_exception)

        is CantReachServerException -> showErrorInternet(context, throwable)
        else -> context.getString(R.string.text_message_generic_error)
    }
}

fun showErrorInternet(context: Context, throwable: Throwable?): String {
    FancyDialogBuilder(context, R.style.AppFancyDialogStyle)
        .withCanCancel(false)
        .withImageIcon(R.drawable.ic_network_error)
        .withTitleTypeFace(R.font.bold)
        .withSubTitleTypeFace(R.font.reguler)
        .withTitle(R.string.text_title_internet_connection)
        .withSubTitle(R.string.text_sub_title_internet_connection)
        .withPositive(android.R.string.cancel) { w, d -> d.dismiss() }
        .show()
    return ""
}