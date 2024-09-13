package manchester.spark.ui

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import spark.R

class SignInScreen : AppCompatActivity() {private lateinit var transitionDrawable: TransitionDrawable
  private val handler = Handler()
  private var isTransitionForward = true // Indicates the direction of the transition
  private var currentTransitionIndex = 0

  private fun startTransition() {
    transitionDrawable.startTransition(6000)
    if (currentTransitionIndex < 3) {
      currentTransitionIndex++
    } else {
      currentTransitionIndex = 0
    }
    val backgroundGradient1 = ContextCompat.getDrawable(this, R.drawable.background_gradient_1)
    val backgroundGradient2 = ContextCompat.getDrawable(this, R.drawable.background_gradient_2)
    val backgroundGradient3 = ContextCompat.getDrawable(this, R.drawable.background_gradient_3)
    val backgroundGradient4 = ContextCompat.getDrawable(this, R.drawable.background_gradient_4)
    val group1 = arrayOf(backgroundGradient1, backgroundGradient2)
    val group2 = arrayOf(backgroundGradient2, backgroundGradient4)
    val group3 = arrayOf(backgroundGradient4, backgroundGradient3)
    val group4 = arrayOf(backgroundGradient3, backgroundGradient1)
    val transitionOrder = listOf(1,2,3,4)
    val transitionMap = mapOf(
      1 to group1,
      2 to group2,
      3 to group3,
      4 to group4,
    )

    transitionDrawable = TransitionDrawable(transitionMap[transitionOrder[currentTransitionIndex]])
    handler.postDelayed({
      window.setBackgroundDrawable(transitionDrawable)
      handler.postDelayed({
        startTransition()
      }, 500)
    }, 6000)
  }
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.sign_in_screen)


    val backgroundGradient1 = ContextCompat.getDrawable(this, R.drawable.background_gradient_1)
    val backgroundGradient2 = ContextCompat.getDrawable(this, R.drawable.background_gradient_2)
    val backgroundGradient3 = ContextCompat.getDrawable(this, R.drawable.background_gradient_3)
    val backgroundGradient4 = ContextCompat.getDrawable(this, R.drawable.background_gradient_4)
    val group1 = arrayOf(backgroundGradient1, backgroundGradient2)
    val group2 = arrayOf(backgroundGradient2, backgroundGradient4)
    val group3 = arrayOf(backgroundGradient4, backgroundGradient3)
    val group4 = arrayOf(backgroundGradient3, backgroundGradient1)
    val transitionOrder = listOf(1,2,3,4)
    val transitionMap = mapOf(
      1 to group1,
      2 to group2,
      3 to group3,
      4 to group4,
    )


    transitionDrawable = TransitionDrawable(transitionMap[transitionOrder[currentTransitionIndex]])
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.setBackgroundDrawable(transitionDrawable)

    startTransition()
    val signInGuestSelector = findViewById<View>(R.id.signInGuest)
    signInGuestSelector.setOnClickListener {
      val intent = Intent(this, SplashScreen::class.java)
      startActivity(intent)
    }
  }

}