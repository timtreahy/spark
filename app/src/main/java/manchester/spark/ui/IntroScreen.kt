package manchester.spark.ui
//
//import android.animation.Animator
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.content.Intent
//import android.content.res.ColorStateList
//import android.os.Bundle
//import android.view.View
//import android.view.WindowManager
//import android.widget.Button
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import spark.R
//
//class IntroScreen : AppCompatActivity() {
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    var selectedGroupSize = "4"
//    var selectedStartingLife = "40"
//
//    fun initAppEnvironment() {
//      window.statusBarColor = this.resources.getColor(R.color.black)
//    }
//    fun initStartingLifeEvents() {
//      val twentyStartingLifeButtonSelector = findViewById<Button>(R.id.twentyStartingLifeButtonMain)
//      val twentyStartingLifeTextSelector = findViewById<TextView>(R.id.twentyStartingLifeText)
//      val thirtyStartingLifeButtonSelector = findViewById<Button>(R.id.thirtyStartingLifeButtonMain)
//      val thirtyStartingLifeTextSelector = findViewById<TextView>(R.id.thirtyStartingLifeText)
//      val fortyStartingLifeButtonSelector = findViewById<Button>(R.id.fortyStartingLifeButtonMain)
//      val fortyStartingLifeTextSelector = findViewById<TextView>(R.id.fortyStartingLifeText)
//      val oneHundredStartingLifeButtonSelector =
//        findViewById<Button>(R.id.oneHundredStartingLifeButtonMain)
//      val oneHundredStartingLifeTextSelector = findViewById<TextView>(R.id.oneHundredStartingLifeText)
//      val startingLifeButtonGroup = listOf(
//        twentyStartingLifeButtonSelector,
//        thirtyStartingLifeButtonSelector,
//        fortyStartingLifeButtonSelector,
//        oneHundredStartingLifeButtonSelector,
//      )
//      val startingLifeTextGroup = listOf(
//        twentyStartingLifeTextSelector,
//        thirtyStartingLifeTextSelector,
//        fortyStartingLifeTextSelector,
//        oneHundredStartingLifeTextSelector,
//      )
//      startingLifeButtonGroup.forEach { view ->
//        view.setOnClickListener { button ->
//          startingLifeButtonGroup.forEach { button ->
//            button?.backgroundTintList =
//              ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
//          }
//          startingLifeTextGroup.forEach { text ->
//            text?.setTextColor(ContextCompat.getColor(this, R.color.black))
//          }
//          button?.backgroundTintList =
//            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.manchester_pink))
//          val textID = button.tag
//          val textSelectorMap = mapOf(
//            "twentyStartingLifeText" to R.id.twentyStartingLifeText,
//            "thirtyStartingLifeText" to R.id.thirtyStartingLifeText,
//            "fortyStartingLifeText" to R.id.fortyStartingLifeText,
//            "oneHundredStartingLifeText" to R.id.oneHundredStartingLifeText,
//          )
//          val textSelector = textSelectorMap[textID]!!
//          val textView = findViewById<TextView>(textSelector)
//          textView?.setTextColor(ContextCompat.getColor(this, R.color.white))
//          selectedStartingLife = textView?.text.toString()
//        }
//      }
//    }
//    fun initGroupSizeEvents() {
//      val onePlayerButtonSelector = findViewById<Button>(R.id.onePlayerButtonMain)
//      val onePlayerTextSelector = findViewById<TextView>(R.id.onePlayerText)
//      val twoPlayerButtonSelector = findViewById<Button>(R.id.twoPlayerButtonMain)
//      val twoPlayerTextSelector = findViewById<TextView>(R.id.twoPlayerText)
//      val threePlayerButtonSelector = findViewById<Button>(R.id.threePlayerButtonMain)
//      val threePlayerTextSelector = findViewById<TextView>(R.id.threePlayerText)
//      val fourPlayerButtonSelector = findViewById<Button>(R.id.fourPlayerButtonMain)
//      val fourPlayerTextSelector = findViewById<TextView>(R.id.fourPlayerText)
//      val playerGroupButtonGroup = listOf(
//        onePlayerButtonSelector,
//        twoPlayerButtonSelector,
//        threePlayerButtonSelector,
//        fourPlayerButtonSelector,
//      )
//      val playerGroupTextGroup = listOf(
//        onePlayerTextSelector,
//        twoPlayerTextSelector,
//        threePlayerTextSelector,
//        fourPlayerTextSelector,
//      )
//      playerGroupButtonGroup.forEach { view ->
//        view.setOnClickListener { button ->
//          playerGroupButtonGroup.forEach { button ->
//            button?.backgroundTintList =
//              ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
//          }
//          playerGroupTextGroup.forEach { text ->
//            text?.setTextColor(ContextCompat.getColor(this, R.color.black))
//          }
//          button?.backgroundTintList =
//            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.manchester_pink))
//          val textID = button.tag
//          val textSelectorMap = mapOf(
//            "onePlayerText" to R.id.onePlayerText,
//            "twoPlayerText" to R.id.twoPlayerText,
//            "threePlayerText" to R.id.threePlayerText,
//            "fourPlayerText" to R.id.fourPlayerText,
//          )
//          val textSelector = textSelectorMap[textID]!!
//          val textView = findViewById<TextView>(textSelector)
//          textView?.setTextColor(ContextCompat.getColor(this, R.color.white))
//          selectedGroupSize = textView?.text.toString()
//        }
//      }
//    }
//
//    fun initStartGameEvents() {
//      val startButton = findViewById<Button>(R.id.startButton)
//      startButton.setOnClickListener {
//        val intent = Intent(this, GameActivity::class.java)
//        intent.putExtra("playerCount", selectedGroupSize)
//        intent.putExtra("startingLife", selectedStartingLife)
//        val accentOval = findViewById<View>(R.id.accentOval)
//        val scaleXAnimator = ObjectAnimator.ofFloat(accentOval, "scaleX", 1.0f, 4.0f)
//        val scaleYAnimator = ObjectAnimator.ofFloat(accentOval, "scaleY", 1.0f, 4.0f)
//        val animationDuration = 500L
//        scaleXAnimator.duration = animationDuration
//        scaleYAnimator.duration = animationDuration
//        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
//
//        animatorSet.addListener(object : Animator.AnimatorListener {
//          override fun onAnimationStart(animation: Animator) {}
//          override fun onAnimationEnd(animation: Animator) {
//            startActivity(intent)
//          }
//          override fun onAnimationCancel(animation: Animator) {}
//          override fun onAnimationRepeat(animation: Animator) {}
//        })
//        animatorSet.start()
//      }
//    }
//    setContentView(R.layout.activity_main)
//    initAppEnvironment()
//    initGroupSizeEvents()
//    initStartingLifeEvents()
//    initStartGameEvents()
//  }
//}
