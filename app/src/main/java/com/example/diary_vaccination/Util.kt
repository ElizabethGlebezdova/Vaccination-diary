package com.example.diary_vaccination

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.diary_vaccination.database.EntryAll
import com.example.diary_vaccination.database.Patient
import com.example.diary_vaccination.database.Vaccine

fun parseListPatientsDB(users: List<Patient>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        users.forEach {
            append("${resources.getString(R.string.last_name)}: ${it.patientLastName}<br>")
            append("${resources.getString(R.string.name)}: ${it.patientName}<br>")
            append("${resources.getString(R.string.surname)}: ${it.patientSurname}<br>")
            append("${resources.getString(R.string.date_birth)} ${it.patientBirthday}<br><br>")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun parselistVaccinesDB (vaccines: List<Vaccine>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        vaccines.forEach {
            append("${resources.getString(R.string.select_header_vaccine)} ${it.vaccineName}<br>")
            if (it.vaccineComponent)
                append("${resources.getString(R.string.select_count_components)} 2<br>")
            else
                append("${resources.getString(R.string.select_count_components)} 1<br>")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun parselistTasksDB (vaccines: List<EntryAll>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        vaccines.forEach {
            append("${resources.getString(R.string.last_name)}: ${it.patient_last_name}<br>")
            append("${resources.getString(R.string.name)}: ${it.patient_name}<br>")
            append("${resources.getString(R.string.date_birth)} ${it.patient_birthday}<br>")
            append("${resources.getString(R.string.select_header_vaccine)}: ${it.vaccine_name}<br>")
            append("Компонет: ${it.vaccine_name}<br>")
            append("Дата: ${it.vaccine_date}<br>")
            append("Время: ${it.vaccine_time}<br><br>")

        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}