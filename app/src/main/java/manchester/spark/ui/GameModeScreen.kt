package manchester.spark.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import spark.R

class GameModeScreen : AppCompatActivity() {
  private lateinit var transitionDrawable: TransitionDrawable
  private val handler = Handler()
  private var isTransitionForward = true // Indicates the direction of the transition
  private var currentTransitionIndex = 0
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.game_mode_screen)
    val backgroundGradient1 = ContextCompat.getDrawable(this, R.drawable.background_gradient_1)
    val backgroundGradient2 = ContextCompat.getDrawable(this, R.drawable.background_gradient_2)
    val backgroundGradient3 = ContextCompat.getDrawable(this, R.drawable.background_gradient_3)
    val backgroundGradient4 = ContextCompat.getDrawable(this, R.drawable.background_gradient_4)
    val group1 = arrayOf(backgroundGradient1, backgroundGradient2)
    val group2 = arrayOf(backgroundGradient2, backgroundGradient3)
    val group3 = arrayOf(backgroundGradient3, backgroundGradient4)
    val group4 = arrayOf(backgroundGradient4, backgroundGradient1)
    val transitionOrder = listOf(1, 2, 3, 4)
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
    val backButtonSelector = findViewById<View>(R.id.back)
    backButtonSelector.setOnClickListener {
      val intent = Intent(this, SplashScreen::class.java)
      startActivity(intent)
    }
    val classicButtonSelector = findViewById<View>(R.id.unrankedLocal)
    classicButtonSelector.setOnClickListener {
      val intent = Intent(this, LobbyScreen::class.java)
      startActivity(intent)
    }
    val joinGameTextSelector: EditText = findViewById(R.id.joinGameText)
    fun hideKeyboard(context: Context, view: View) {
      val imm: InputMethodManager =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    val filter = InputFilter { source, _, _, dest, _, _ ->
      for (i in source.indices) {
        if (Character.isLetter(source[i])) {
          val charArray = CharArray(1)
          source[i].uppercaseChar().let {
            charArray[0] = it
          }
          return@InputFilter if (dest.length < 4) {
            String(charArray)
          } else {
            ""
          }
        }
      }
      ""
    }
    joinGameTextSelector.filters = arrayOf(filter)
    val joinGameConfirmation: View = findViewById(R.id.joinGameConfirmation)

    joinGameTextSelector.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
      override fun afterTextChanged(s: Editable?) {
        val inputLength = s?.length ?: 0
        if (inputLength == 4) {
          joinGameConfirmation.visibility = View.VISIBLE
        } else {
          joinGameConfirmation.visibility = View.GONE
        }
      }
    })
    val parentLayout: View = findViewById(R.id.gameModeScreen)
    parentLayout.setOnClickListener {
      joinGameTextSelector.clearFocus()
      hideKeyboard(this, parentLayout)
    }
  }

  private fun startTransition() {
    transitionDrawable.startTransition(3000)
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
    val transitionOrder = listOf(1, 2, 3, 4)
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
    }, 3000)
  }
}
