package com.heifetz.heifetz.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.android.AndroidAuthSession
import com.dropbox.client2.session.AccessTokenPair
import com.dropbox.client2.session.AppKeyPair
import com.heifetz.heifetz.APP_PREFERENCES
import com.heifetz.heifetz.PREFERENCES_CODE
import com.heifetz.heifetz.PREFERENCES_DB_ACCESS_TOKEN
import com.heifetz.heifetz.PREFERENCES_STOP
import java.io.*
import java.lang.Exception


const val APP_KEY = "o6d81c21xgslkg8"
const val APP_SECRET = "o6d81c21xgslkg8"

fun dropBoxAuth(context: Context): DropboxAPI<AndroidAuthSession> {
    val mDBApi: DropboxAPI<AndroidAuthSession>

    val settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    val dbAccessToken = settings.getString(PREFERENCES_DB_ACCESS_TOKEN, null)

    val appKeys = AppKeyPair(APP_KEY, APP_SECRET)
    val session: AndroidAuthSession

    session = if (dbAccessToken == null) {
        AndroidAuthSession(appKeys)
    } else {
        AndroidAuthSession(appKeys, AccessTokenPair(dbAccessToken, APP_SECRET))
    }

    mDBApi = DropboxAPI(session)
//    mDBApi.session.startOAuth2Authentication(context)

    return mDBApi
}

fun getAccessToken(context: Context, mDBApi: DropboxAPI<AndroidAuthSession>) {
    if (mDBApi.session.authenticationSuccessful()) {

        mDBApi.session.finishAuthentication()
        val settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = settings.edit()
        val accessToken = mDBApi.session.oAuth2AccessToken
        editor.putString(PREFERENCES_DB_ACCESS_TOKEN, accessToken)
        editor.apply()
    }
}

fun uploadFile(mDBApi: DropboxAPI<AndroidAuthSession>, context: Context) {
    val bufferedWriter = BufferedWriter(OutputStreamWriter(context.openFileOutput("asd", MODE_PRIVATE)))
    bufferedWriter.write("asdqwfv")
    bufferedWriter.close()

    val file = File("asd")
    val fio = context.openFileInput("asd")
    val response = mDBApi.putFile("/asd.txt", fio, file.length(), null, null)
//
//    Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev)
}