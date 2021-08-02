package co.kr.mapo.project_fundegi.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import co.kr.mapo.project_fundegi.R
import co.kr.mapo.project_fundegi.ui.activity.MAIN_
import co.kr.mapo.project_fundegi.ui.activity.SPLASH_
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    // 토큰 생성
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // 토큰 값 따로 저장
//        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
//        val editor = pref.edit()
//        editor.putString("token", token).apply()
//        editor.commit()
//
//        Log.i("로그", "토큰 저장 성공적")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }


    // 메시지 수신
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


//        Log.d(TAG, "From: ${remoteMessage.from}")
//
//        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
//
//            remoteMessage.notification?.let {
//                Log.d(TAG, "Message Notification Body: ${it.body}")
//            }

        Log.d(TAG, "From: " + remoteMessage.from)
//         Notification 메시지를 수신할 경우는
//         remoteMessage.notification?.body!! 여기에 내용이 저장되어있다.
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)
        Log.e("[notification]", "${remoteMessage.notification}")
        Log.e("[notification-바디]", "${remoteMessage.notification?.body}")
        Log.e("[notification-타이틀]", "${remoteMessage.notification?.title}")
        Log.e("[notification-아이콘]", "${remoteMessage.notification?.imageUrl}")


        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage)
        } else {
            Log.i("수신에러 : ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("data값 :", remoteMessage.data.toString())
        }
    }


    // 알림 생성 (아이콘, 알림 소리 등)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RemoteCode, ID를 고유값으로 지정하여 알림이 개별 표시 되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        Log.e("[확인]", "$remoteMessage")

        // 일회용 PendingIntent
        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임
        val intent = Intent(this, SPLASH_::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack을 경로만 남김, A-B-C-D-B => A-B
        val pendingIntent =
            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)

        // 알림 채널 이름
        val channelId = getString(R.string.firebase_notification_channel_id)

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val drawable = resources.getDrawable(R.drawable.fundegi_wakeup_png) as BitmapDrawable
        val bitmap = drawable.bitmap
        //val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fundegi_wakeup_png)

        // 알림에 대한 UI 정보와 작업을 지정
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)     // 아이콘 설정
            .setLargeIcon(bitmap)
            .setContentTitle(remoteMessage.notification?.title)     // 제목
            .setContentText(remoteMessage.notification?.body)     // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri)     // 알림 소리
            .setContentIntent(pendingIntent)       // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notice",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())

    }

}