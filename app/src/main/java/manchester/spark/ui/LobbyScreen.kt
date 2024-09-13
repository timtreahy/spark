package manchester.spark.ui

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import spark.R

class LobbyScreen : AppCompatActivity() {
  private lateinit var transitionDrawable: TransitionDrawable
  private val handler = Handler()
  private var isTransitionForward = true // Indicates the direction of the transition
  private var currentTransitionIndex = 0
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.classic_screen)
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
      val intent = Intent(this, GameModeScreen::class.java)
      startActivity(intent)
    }
    val player1Card = findViewById<View>(R.id.player1Card)
    val player2Card = findViewById<View>(R.id.player2Card)
    val player3Card = findViewById<View>(R.id.player3Card)
    val player4Card = findViewById<View>(R.id.player4Card)
    val playerCards = mutableMapOf(
      1 to player1Card,
      2 to player2Card,
      3 to player3Card,
      4 to player4Card,
    )

    var activePlayers = 0

    fun assignPlayerCardHandler(playerCard: View) {
      val tag = playerCard.tag?.toString() ?: ""
      Log.d("TEST123", "$tag")
      if (tag == "nonactive") {
        playerCard.setOnClickListener {
          val parent = playerCard.parent as ConstraintLayout
          val index = parent.indexOfChild(playerCard)
          val layoutParams = playerCard.layoutParams
          val newPlayerCard = LayoutInflater.from(this).inflate(R.layout.guest_lobby_card, null)

          newPlayerCard.id = playerCard.id
          val username = newPlayerCard.findViewById<TextView>(R.id.username)
          username.text = "Player ${index + 1}"
          parent.removeView(playerCard)
          parent.addView(newPlayerCard, index, layoutParams)
          parent.requestLayout()
          assignPlayerCardHandler(newPlayerCard)
          val newTag = newPlayerCard.tag?.toString() ?: ""
          if (newTag == "active") {
            activePlayers++
            findViewById<TextView>(R.id.gameInformationText).text =
              "Detected: $activePlayers player Commander"
          }
        }
      }
      if (tag == "active") {
        playerCard.setOnClickListener {
          val parent = playerCard.parent as ConstraintLayout
          val index = parent.indexOfChild(playerCard)
          val layoutParams = playerCard.layoutParams
          val newPlayerCard = LayoutInflater.from(this).inflate(R.layout.add_lobby_card, null)

          newPlayerCard.id = playerCard.id

          parent.removeView(playerCard)
          parent.addView(newPlayerCard, index, layoutParams)
          parent.requestLayout()
          assignPlayerCardHandler(newPlayerCard)
          val newTag = newPlayerCard.tag?.toString() ?: ""
          if (newTag == "nonactive") {
            activePlayers--
            findViewById<TextView>(R.id.gameInformationText).text =
              "Detected: $activePlayers player Commander"
          }
        }
      }
    }

    playerCards.forEach { (_, playerCard) ->
      val tag = playerCard.tag?.toString() ?: ""
      if (tag == "active") {
        activePlayers++
        findViewById<TextView>(R.id.gameInformationText).text =
          "Detected: $activePlayers player Commander"
      }

      playerCard.setOnTouchListener { v, event ->
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
           if (tag == "nonactive") {
             val parent = playerCard.parent as ConstraintLayout
             val index = parent.indexOfChild(playerCard)
             val layoutParams = playerCard.layoutParams
             val newPlayerCard = LayoutInflater.from(this).inflate(R.layout.guest_lobby_card, null)

             newPlayerCard.id = playerCard.id

             parent.removeView(playerCard)
             val username = newPlayerCard.findViewById<TextView>(R.id.username)
             username.text = "Player ${index + 1}"
             parent.addView(newPlayerCard, index, layoutParams)
             assignPlayerCardHandler(newPlayerCard)
             parent.requestLayout()
             val newTag = newPlayerCard.tag?.toString() ?: ""
             if (newTag == "active") {
               activePlayers++
               findViewById<TextView>(R.id.gameInformationText).text =
                 "Detected: $activePlayers player Commander"
             }
           }
          }
          MotionEvent.ACTION_MOVE -> {
          }
          MotionEvent.ACTION_UP -> {
          }
          MotionEvent.ACTION_CANCEL -> {
          }
        }
        v.performClick()
        true
      }
    }
    val startButton = findViewById<View>(R.id.start)
    startButton.setOnClickListener {
      Log.d("TEST123", "start button")
      val intent = Intent(this, GameActivity::class.java)
      intent.putExtra("playerCount", "4")
      intent.putExtra("startingLife", "40")
      startActivity(intent)
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
