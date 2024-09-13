package manchester.spark.ui

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import spark.R

class GameActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val playerCount = intent.getStringExtra("playerCount")
    val startingLife = intent.getStringExtra("startingLife")
    fun initContentView() {
      when {
        (playerCount == "4") -> {
          setContentView(R.layout.group_size_4)
        }
      }
    }

    fun initAppEnvironment() {
      window.statusBarColor = this.resources.getColor(R.color.black)
//      val windowInsetsController =
//        WindowCompat.getInsetsController(window, window.decorView)
//      // Configure the behavior of the hidden system bars.
//      windowInsetsController.systemBarsBehavior =
//        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//      windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun initLogoEvents() {
      val logoSelector = findViewById<View>(R.id.logoText)
      val dayNightSelector = findViewById<View>(R.id.dayNightIndicatorText)
      val dayNightAccentSelector = findViewById<View>(R.id.dayNightIndicatorAccent)
      val initiativeSelector = findViewById<View>(R.id.initiativeIndicatorText)
      val initiativeAccentSelector = findViewById<View>(R.id.initiativeIndicatorAccent)
      val monarchSelector = findViewById<View>(R.id.monarchIndicatorText)
      val monarchAccentSelector = findViewById<View>(R.id.monarchIndicatorAccent)
      val deathSelector = findViewById<View>(R.id.deathIndicatorText)
      val deathAccentSelector = findViewById<View>(R.id.deathIndicatorAccent)
      val cardSearchSelector = findViewById<View>(R.id.cardSearchIndicatorText)
      val cardSearchAccentSelector = findViewById<View>(R.id.cardSearchIndicatorAccent)
      val diceSelector = findViewById<View>(R.id.diceIndicatorText)
      val diceAccentSelector = findViewById<View>(R.id.diceIndicatorAccent)
      val gameLogSelector = findViewById<View>(R.id.gameLogIndicatorText)
      val gameLogAccentSelector = findViewById<View>(R.id.gameLogIndicatorAccent)
      val homeSelector = findViewById<View>(R.id.homeIndicatorText)
      val homeAccentSelector = findViewById<View>(R.id.homeIndicatorAccent)
//      val undoActionSelector = findViewById<View>(R.id.undoActionIndicatorText)
//      val undoActionAccentSelector = findViewById<View>(R.id.undoActionIndicatorAccent)
//      val testActionSelector = findViewById<View>(R.id.testActionIndicator2Text)
//      val testActionAccentSelector = findViewById<View>(R.id.testActionIndicator2Accent)
      val upperQuickActionGroupSelector = findViewById<View>(R.id.upperQuickActionGroup)
      val lowerQuickActionGroupSelector = findViewById<View>(R.id.lowerQuickActionGroup)
      val logoAccentSelector = findViewById<View>(R.id.accentOval)
      val fadeGroup = listOf(
        dayNightSelector,
        initiativeSelector,
        cardSearchSelector,
        monarchSelector,
        deathSelector,
        diceSelector,
        gameLogSelector,
        homeSelector,
//        undoActionSelector
      )
      val scaleGroup = listOf(
        dayNightAccentSelector,
        initiativeAccentSelector,
        cardSearchAccentSelector,
        monarchAccentSelector,
        deathAccentSelector,
        diceAccentSelector,
        gameLogAccentSelector,
        homeAccentSelector,
//        undoActionAccentSelector
      )
      dayNightSelector.setOnTouchListener { v, event ->
        v.performClick()
        true
      }
      var isCollapsed = true
      val fadeDuration = 500
      val initialAccentWidth = 30.toFloat()
      val expandedAccentWidth = 150.toFloat()
      val initialAccentHeight = 30.toFloat()
      val expandedAccentHeight = 90.toFloat()
      val animationDuration = 500L
      val scaleUpWidthAnimation = ValueAnimator.ofFloat(initialAccentWidth, expandedAccentWidth)
      val scaleUpHeightAnimation = ValueAnimator.ofFloat(initialAccentHeight, expandedAccentHeight)
      scaleUpWidthAnimation.duration = animationDuration
      scaleUpHeightAnimation.duration = animationDuration
      val scaleDownWidthAnimation = ValueAnimator.ofFloat(expandedAccentWidth, initialAccentWidth)
      val scaleDownHeightAnimation =
        ValueAnimator.ofFloat(expandedAccentHeight, initialAccentHeight)
      scaleDownWidthAnimation.duration = animationDuration
      scaleDownHeightAnimation.duration = animationDuration

      for (view in scaleGroup) {
        scaleUpWidthAnimation.addUpdateListener { animation ->
          val value = animation.animatedValue as Float
          view.layoutParams.width = value.toInt()
          view.requestLayout()
        }
        scaleDownWidthAnimation.addUpdateListener { animation ->
          val value = animation.animatedValue as Float
          view.layoutParams.width = value.toInt()
          view.requestLayout()
        }
        scaleUpHeightAnimation.addUpdateListener { animation ->
          val value = animation.animatedValue as Float
          view.layoutParams.height = value.toInt()
          view.requestLayout()
        }
        scaleDownHeightAnimation.addUpdateListener { animation ->
          val value = animation.animatedValue as Float
          view.layoutParams.height = value.toInt()
          view.requestLayout()
        }
      }
      val toggleViews = {
        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = fadeDuration.toLong()
        if (isCollapsed) {
          scaleGroup.forEach { view ->
            view.visibility = View.VISIBLE
            scaleUpWidthAnimation.start()
            scaleUpHeightAnimation.start()
          }
          fadeGroup.forEach { view ->
            view.visibility = View.VISIBLE
            view.startAnimation(fadeInAnimation)
          }
        } else {
          // Collapsing views
          scaleDownWidthAnimation.start()
          scaleDownHeightAnimation.start()
          fadeGroup.forEach { view ->
            view.visibility = View.INVISIBLE
          }
        }
        isCollapsed = !isCollapsed // Toggle the state
      }
      logoSelector.setOnClickListener {
        toggleViews()
      }
      homeSelector.setOnClickListener {
        val intent = Intent(this, SplashScreen::class.java)
        startActivity(intent)
      }
    }

    fun initFirstPlayer(): Int {
      return (1..(playerCount?.toInt()!!)).random()
    }
    initContentView()
    initAppEnvironment()
    initLogoEvents()
    val firstPlayer = initFirstPlayer()
    var currentPlayer = firstPlayer

    for (playerIndex in 1..playerCount.toString().toInt()) {
      val playerDrawerStub = "player${playerIndex}_Drawer"
      fun getPlayerDrawerSelector(playerDrawerStub: String): View? {
        val playerDrawerMap = mapOf(
          "player1_Drawer" to R.id.player1_Drawer,
          "player2_Drawer" to R.id.player2_Drawer,
          "player3_Drawer" to R.id.player3_Drawer,
          "player4_Drawer" to R.id.player4_Drawer,
        )
        return findViewById(playerDrawerMap[playerDrawerStub]!!)
      }

      fun getPlayerColor(playerDrawerStub: String): Int {
        val playerColorMap = mapOf(
          "player1_Drawer" to R.color.player1_color,
          "player2_Drawer" to R.color.player2_color,
          "player3_Drawer" to R.color.player3_color,
          "player4_Drawer" to R.color.player4_color,
        )
        return playerColorMap[playerDrawerStub]!!
      }

      val playerDrawerSelector = getPlayerDrawerSelector(playerDrawerStub)
      val playerDrawerLidSelector = playerDrawerSelector!!.findViewById<View>(R.id.drawerLid)
      val playerDrawerGutterSelector = playerDrawerSelector.findViewById<View>(R.id.drawerGutter)
      val playerDrawerBottomAccentSelector = playerDrawerSelector.findViewById<View>(R.id.drawerBottomAccent)
      val playerDrawerLidBarrierSelector = playerDrawerSelector.findViewById<View>(R.id.drawerLidBarrier)

//      val playerCorner1Selector = playerDrawerSelector.findViewById<View>(R.id.corner1)
//      val playerCorner2Selector = playerDrawerSelector.findViewById<View>(R.id.corner2)

      val playerHandleSelector = playerDrawerSelector.findViewById<View>(R.id.drawerHandle)
      val playerColor = getPlayerColor(playerDrawerStub)

      playerDrawerSelector.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
      playerDrawerLidBarrierSelector?.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
      playerDrawerBottomAccentSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
//      playerCorner1Selector?.backgroundTintList =
//        ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
//      playerCorner2Selector?.backgroundTintList =
//        ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
      playerHandleSelector?.findViewById<View>(R.id.accentColor)?.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this, playerColor))
//      val playerNameSelector = getPlayerNameSelector(playerDrawerStub)
      val dividerLineSelector = playerDrawerSelector.findViewById<View>(R.id.dividerLine)
      val obscuringLineSelector = playerDrawerSelector.findViewById<View>(R.id.obscuringLine)
      val lifeTotalTextSelector = playerDrawerSelector.findViewById<TextView>(R.id.lifeTotalText)
      val lifeTotalChangeTextSelector =
        playerDrawerSelector.findViewById<TextView>(R.id.lifeTotalChangeText)
      val infectDamageTextSelector =
        playerDrawerSelector.findViewById<TextView>(R.id.infectDamageText)
      val infectDamageChangeTextSelector =
        playerDrawerSelector.findViewById<TextView>(R.id.infectDamageChangeText)
//      val commanderDamageTextSelector = playerDrawerSelector?.findViewById<TextView>(R.id.commanderDamageText)
//      val commanderDamageChangeTextSelector =
//        playerDrawerSelector?.findViewById<TextView>(R.id.commanderDamageChangeText)
      val primaryCommanderDamageTextSelector =
        playerDrawerSelector.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
          ?.findViewById<TextView>(R.id.commanderDamageText)
      val primaryCommanderDamageChangeTextSelector =
        playerDrawerSelector.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
          ?.findViewById<TextView>(R.id.commanderDamageChangeText)
      val secondaryCommanderDamageTextSelector =
        playerDrawerSelector.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
          ?.findViewById<TextView>(R.id.commanderDamageText)
      val secondaryCommanderDamageChangeTextSelector =
        playerDrawerSelector.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
          ?.findViewById<TextView>(R.id.commanderDamageChangeText)
//      val tz =
//        playerDrawerSelector?.findViewById<View>(R.id.drawerLowerTouchZone)
//      val upperTouchZoneSelector =
//        playerDrawerSelector?.findViewById<View>(R.id.drawerUpperTouchZone)
      val lifeTotalLowerTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.lifeTotal)
          ?.findViewById<FrameLayout>(R.id.lowerLifeTotalTouchZone)
      val primaryCommanderDamageLowerTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.primaryCommanderDamage)
          ?.findViewById<FrameLayout>(R.id.lowerPrimaryCommanderDamageTouchZone)
      val secondaryCommanderDamageLowerTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.secondaryCommanderDamage)
          ?.findViewById<FrameLayout>(R.id.lowerSecondaryCommanderDamageTouchZone)
      val infectDamageLowerTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.infectDamage)
          ?.findViewById<FrameLayout>(R.id.lowerInfectDamageTouchZone)
      val lifeTotalUpperTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.lifeTotal)
          ?.findViewById<FrameLayout>(R.id.upperLifeTotalTouchZone)
      val primaryCommanderDamageUpperTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.primaryCommanderDamage)
          ?.findViewById<FrameLayout>(R.id.upperPrimaryCommanderDamageTouchZone)
      val secondaryCommanderDamageUpperTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.secondaryCommanderDamage)
          ?.findViewById<FrameLayout>(R.id.upperSecondaryCommanderDamageTouchZone)
      val infectDamageUpperTouchZoneSelector =
        playerDrawerSelector.findViewById<View>(R.id.infectDamage)
          ?.findViewById<FrameLayout>(R.id.upperInfectDamageTouchZone)
      val lowerTouchZones = mapOf(
        "lifeTotal" to lifeTotalLowerTouchZoneSelector,
        "primaryCommanderDamage" to primaryCommanderDamageLowerTouchZoneSelector,
        "secondaryCommanderDamage" to secondaryCommanderDamageLowerTouchZoneSelector,
        "infectDamage" to infectDamageLowerTouchZoneSelector
      )
      val upperTouchZones = mapOf(
        "lifeTotal" to lifeTotalUpperTouchZoneSelector,
        "primaryCommanderDamage" to primaryCommanderDamageUpperTouchZoneSelector,
        "secondaryCommanderDamage" to secondaryCommanderDamageUpperTouchZoneSelector,
        "infectDamage" to infectDamageUpperTouchZoneSelector
      )
//      val touchZonesSelector = playerDrawerSelector?.findViewById<View>(R.id.drawerTouchZones)
      val turnChangeIndicatorSelector =
        playerDrawerSelector.findViewById<View>(R.id.drawerTurnChangeElement)
//
      lifeTotalTextSelector?.text = startingLife
      infectDamageTextSelector?.text = "0"
      primaryCommanderDamageTextSelector?.text = "0"
      secondaryCommanderDamageTextSelector?.text = "0"
      lifeTotalChangeTextSelector?.text = ""
      infectDamageChangeTextSelector?.text = ""
      primaryCommanderDamageChangeTextSelector?.text = ""
      secondaryCommanderDamageChangeTextSelector?.text = ""
//      playerDrawerSelector?.backgroundTintList =
//        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
//      lifeTotalTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.manchester_black))
      obscuringLineSelector?.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
      val defaultBackgroundColor = lifeTotalLowerTouchZoneSelector?.background
      val pressedBackgroundColor =
        ColorDrawable(Color.parseColor("#FF5733"))

      fun darkenColor(color: Int, percentage: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= percentage
        return Color.HSVToColor(hsv)
      }

      val pressedBackgroundColorDarker = darkenColor(pressedBackgroundColor.color, 0.5f)
      val delayMillis = 1500L
      val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
      val longPressDelayMillis = 1500L
      val longPressRunnable = Runnable {
      }

      fun resetPlayerDrawer(currentPlayerStub: String) {
        val playerDrawerSelector = getPlayerDrawerSelector(currentPlayerStub)
        val playerLidSelector = playerDrawerSelector?.findViewById<View>(R.id.drawerLid)
//        val playerNameSelector = getPlayerNameSelector(currentPlayerStub)
        val dividerLineSelector = playerDrawerSelector?.findViewById<View>(R.id.dividerLine)
        val obscuringLineSelector = playerDrawerSelector?.findViewById<View>(R.id.obscuringLine)
        val lifeTotalTextSelector = playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalText)
        val lifeTotalHelperTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalHelperText)
        val lifeTotalChangeTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalChangeText)
        val infectDamageTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageText)
        val infectDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageHelperText)
        val infectDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageChangeText)
        val primaryCommanderDamageTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageText)
        val primaryCommanderDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageHelperText)
        val primaryCommanderDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageChangeText)
        val secondaryCommanderDamageTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageText)
        val secondaryCommanderDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageHelperText)
        val secondaryCommanderDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageChangeText)
//        playerDrawerSelector?.backgroundTintList =
//          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        playerLidSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
//        playerNameSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        dividerLineSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        obscuringLineSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        lifeTotalTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        lifeTotalChangeTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        lifeTotalHelperTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        infectDamageTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        infectDamageChangeTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        infectDamageHelperTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.black))
        primaryCommanderDamageTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
        primaryCommanderDamageChangeTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
        primaryCommanderDamageHelperTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
        secondaryCommanderDamageTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
        secondaryCommanderDamageChangeTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
        secondaryCommanderDamageHelperTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.black
          )
        )
      }

      var count = 1
      while (count <= playerCount?.toInt()!!) {
        resetPlayerDrawer("player${count}_Drawer")
        count++
      }
      fun initPlayerTurn(nextPlayerStub: String) {
        val playerDrawerSelector = getPlayerDrawerSelector(nextPlayerStub)
        val playerLidSelector = playerDrawerSelector?.findViewById<View>(R.id.drawerLid)
//        val playerNameSelector = getPlayerNameSelector(nextPlayerStub)
        val dividerLineSelector = playerDrawerSelector?.findViewById<View>(R.id.dividerLine)
        val obscuringLineSelector = playerDrawerSelector?.findViewById<View>(R.id.obscuringLine)
        val lifeTotalTextSelector = playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalText)
        val lifeTotalHelperTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalHelperText)
        val lifeTotalChangeTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.lifeTotalChangeText)
        val infectDamageTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageText)
        val infectDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageHelperText)
        val infectDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<TextView>(R.id.infectDamageChangeText)
        val primaryCommanderDamageTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageText)
        val primaryCommanderDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageHelperText)
        val primaryCommanderDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerPrimaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageChangeText)
        val secondaryCommanderDamageTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageText)
        val secondaryCommanderDamageHelperTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageHelperText)
        val secondaryCommanderDamageChangeTextSelector =
          playerDrawerSelector?.findViewById<View>(R.id.drawerSecondaryCommanderDamageWidget)
            ?.findViewById<TextView>(R.id.commanderDamageChangeText)
//        playerDrawerSelector?.backgroundTintList =
//          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.manchester_pink))
        playerLidSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.manchester_pink))
//        playerNameSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        dividerLineSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        obscuringLineSelector?.backgroundTintList =
          ColorStateList.valueOf(ContextCompat.getColor(this, R.color.manchester_pink))
        lifeTotalTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        lifeTotalChangeTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        lifeTotalHelperTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        infectDamageTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        infectDamageChangeTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        infectDamageHelperTextSelector?.setTextColor(ContextCompat.getColor(this, R.color.white))
        primaryCommanderDamageTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
        primaryCommanderDamageChangeTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
        primaryCommanderDamageHelperTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
        secondaryCommanderDamageTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
        secondaryCommanderDamageChangeTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
        secondaryCommanderDamageHelperTextSelector?.setTextColor(
          ContextCompat.getColor(
            this,
            R.color.white
          )
        )
      }

      fun initNotificationScreen(nextPlayerStub: String) {
//        val reminderScreenSelector = findViewById<View>(R.id.notificationScreen)
//        val notificationsSelector = findViewById<View>(R.id.notifications)
//        notificationsSelector.visibility = View.VISIBLE
//        reminderScreenSelector.visibility = View.VISIBLE
      }

      val firstPlayerStub = "player${firstPlayer}_Drawer"
      initPlayerTurn(firstPlayerStub)
      val turnChangeHandler = Handler()
      var turnChangeTimerCancelled = false
      var turnChangeHoldInProgress = false
      var turnChangeIntention = false
//      var lifeTotalTextSelectorHoldInProgress = false
//      val lifeTotalTextSelectorHoldHandler = Handler()
//      var lifeTotalTextSelectorHoldTimerCancelled = false
//      var lifeTotalTextSelectorinfectDamagelse
//      val lifeTotalTextSelectorHoinfectDamainfectDamageler()
//      var lifeTotalTextSelectorHoldTimerCancelled = false
//      var lifeTotalTextinfectDamageold = false
//      vacommanderTotalTextSelectorHoinfectDamacommanderDamageler()
//      vacommanderTotalTextSelectorHoldTimerCancelled = false
      var lifeTotalTextcommanderDamageold = false
      var intentionalHoldInProgress = false
      val intentionalHoldHandler = Handler()
      var intentionalHoldTimerCancelled = false
      var intentionalHold = false
      val shortHoldDuration = 500L
      val longHoldDuration = 1000L
      fun setSpannableTextWithKeywords(
        textView: TextView,
        fullText: String,
        keywords: List<String>
      ) {
        if (fullText != null) {
          val spannableString = SpannableString(fullText)
          for (keyword in keywords) {
            val start = fullText.indexOf(keyword)
            val end = start + keyword.length
            if (start > -1 && end > -1) {
              spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
              )

              spannableString.setSpan(
                object : ClickableSpan() {
                  override fun onClick(widget: View) {
                    Toast.makeText(widget.context, "$keyword Clicked", Toast.LENGTH_SHORT).show()
                  }
                },
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
              )
            }
          }
          textView.text = spannableString
          textView.movementMethod = LinkMovementMethod.getInstance()
          textView.isClickable = true
        }
      }

//      val dayNotificationTextSelector = findViewById<TextView>(R.id.dayNotificationText)
//      val nightNotificationTextSelector = findViewById<TextView>(R.id.nightNotificationText)
//      val initiativeNotificationTextSelector =
//        findViewById<TextView>(R.id.initiativeNotificationText)
//      val monarchNotificationTextSelector = findViewById<TextView>(R.id.monarchNotificationText)
//      val manaCryptNotificationTextSelector = findViewById<TextView>(R.id.manaCryptNotificationText)
//      val viewableTextStrings = listOf(
//        dayNotificationTextSelector,
//        nightNotificationTextSelector,
//        initiativeNotificationTextSelector,
//        monarchNotificationTextSelector,
//        manaCryptNotificationTextSelector,
//      )
      val keywords =
        listOf("Monarch", "Initiative", "Day", "Transform", "Night", "Undercity", "Mana Crypt")
//      for (viewableTextView in viewableTextStrings) {
//        setSpannableTextWithKeywords(
//          viewableTextView,
//          viewableTextView?.text.toString(), keywords
//        )
//      }

      turnChangeIndicatorSelector?.visibility = View.INVISIBLE
      val animatorDuration = longHoldDuration
      val scaleAnimator = ValueAnimator.ofFloat(1f, 2000f)
      scaleAnimator.duration = animatorDuration
      scaleAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Float
        turnChangeIndicatorSelector?.layoutParams?.width = animatedValue.toInt()
        turnChangeIndicatorSelector?.layoutParams?.height = animatedValue.toInt()
        turnChangeIndicatorSelector?.requestLayout()
      }
      lowerTouchZones.forEach { (key, tz) ->
        tz?.setOnTouchListener { v, event ->
          when (event.action) {
            MotionEvent.ACTION_DOWN -> {
              tz.background = ColorDrawable(pressedBackgroundColorDarker)
              vibrator.vibrate(10)
              turnChangeHoldInProgress = false
              turnChangeIntention = false

              if (currentPlayer != playerIndex) {
                turnChangeIndicatorSelector?.visibility = View.VISIBLE
                scaleAnimator.start()
                turnChangeIndicatorSelector?.backgroundTintList =
                  ColorStateList.valueOf(
                    ContextCompat.getColor(
                      this,
                      R.color.manchester_pink
                    )
                  )
                dividerLineSelector.visibility = View.INVISIBLE
                obscuringLineSelector.visibility = View.INVISIBLE
                turnChangeHandler.postDelayed({
                  turnChangeTimerCancelled = false
                  if (!turnChangeHoldInProgress) {
                    turnChangeIntention = true
                    runOnUiThread {
                    }
                  }
                }, 400L)
              }
              true
            }

            MotionEvent.ACTION_MOVE -> {
              if (!intentionalHoldInProgress && intentionalHold) {
                intentionalHoldHandler.removeCallbacksAndMessages(null)
                intentionalHoldTimerCancelled = true
                if (currentPlayer != playerIndex) {
                  resetPlayerDrawer(playerDrawerStub)
                }
              }
              if (!turnChangeHoldInProgress && turnChangeIntention) {
                runOnUiThread {
                }
                turnChangeHandler.removeCallbacksAndMessages(null)
                turnChangeTimerCancelled = true
              }
              true
            }

            MotionEvent.ACTION_UP -> {
              tz.background = defaultBackgroundColor
              intentionalHoldHandler.removeCallbacksAndMessages(null)
              intentionalHoldTimerCancelled = true
              turnChangeTimerCancelled = true
              turnChangeHandler.removeCallbacksAndMessages(null)
              turnChangeIndicatorSelector?.visibility = View.INVISIBLE
              scaleAnimator.cancel()
              turnChangeIndicatorSelector?.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
              dividerLineSelector.visibility = View.VISIBLE
              obscuringLineSelector.visibility = View.VISIBLE
              turnChangeIndicatorSelector?.layoutParams?.width = 0
              turnChangeIndicatorSelector?.layoutParams?.height = 0
              turnChangeIndicatorSelector?.requestLayout()

              if (!intentionalHoldInProgress) {
                if (!intentionalHoldTimerCancelled) {
                  turnChangeHandler.removeCallbacksAndMessages(null)
                  if (currentPlayer != playerIndex) {
                    resetPlayerDrawer(playerDrawerStub)
                  }
                }
              }

              if (!turnChangeHoldInProgress) {
                if (turnChangeIntention) {
                  runOnUiThread {
                    if (currentPlayer != playerIndex) {
                      var count = 1
                      while (count <= playerCount.toInt()) {
                        resetPlayerDrawer("player${count}_Drawer")
                        count++
                      }
                      val nextPlayerDrawerStub = "player${playerIndex}_Drawer"
//                    initNotificationScreen(nextPlayerDrawerStub)
                      initPlayerTurn(nextPlayerDrawerStub)
                      tz.backgroundTintList =
                        ColorStateList.valueOf(
                          ContextCompat.getColor(
                            this,
                            R.color.transparent
                          )
                        )
                      currentPlayer = playerIndex
                    }
                  }
                } else {
                  runOnUiThread {
                    tz.backgroundTintList =
                      ColorStateList.valueOf(
                        ContextCompat.getColor(
                          this,
                          R.color.transparent
                        )
                      )
                    val widgetTextSelectorMap = mapOf(
                      "lifeTotal" to lifeTotalTextSelector,
                      "primaryCommanderDamage" to primaryCommanderDamageTextSelector,
                      "secondaryCommanderDamage" to secondaryCommanderDamageTextSelector,
                      "infectDamage" to infectDamageTextSelector,
                    )
                    val widgetTextSelector = widgetTextSelectorMap[key]
                    val widgetChangeTextSelectorMap = mapOf(
                      "lifeTotal" to lifeTotalChangeTextSelector,
                      "primaryCommanderDamage" to primaryCommanderDamageChangeTextSelector,
                      "secondaryCommanderDamage" to secondaryCommanderDamageChangeTextSelector,
                      "infectDamage" to infectDamageChangeTextSelector,
                    )
                    val widgetChangeTextSelector = widgetChangeTextSelectorMap[key]
                    val currentWidgetChange =
                      widgetChangeTextSelector?.text?.toString()?.toIntOrNull() ?: 0
                    val newWidgetValue = currentWidgetChange - 1
                    widgetChangeTextSelector?.text = newWidgetValue.toString()
                    if (widgetChangeTextSelector?.text == "0") {
                      widgetChangeTextSelector.text = ""
                    } else if (newWidgetValue > 0) {
                      widgetChangeTextSelector?.text =
                        "+${widgetChangeTextSelector?.text}"
                    }
                    val finalizeChangeRunnable = Runnable {
                      if (widgetTextSelector?.text.toString() == "") {
                        widgetTextSelector?.text = "0"
                      }
                      val finalLifeChange = widgetChangeTextSelector?.text.toString().toInt()
                      val currentPlayerLife = widgetTextSelector?.text.toString().toInt()
                      val newPlayerLife = currentPlayerLife + finalLifeChange
                      widgetTextSelector?.text = newPlayerLife.toString()
                      widgetChangeTextSelector?.text = ""
                    }
                    turnChangeHandler.removeCallbacks(finalizeChangeRunnable)
                    turnChangeHandler.postDelayed(finalizeChangeRunnable, 500L)
                  }
                }
                turnChangeHoldInProgress = false
                turnChangeIntention = false
              }
              v.performClick()
              true
            }

            else -> {
              true
            }
          }
          v.performClick()
          true
        }
      }
      upperTouchZones.forEach { (key, tz) ->
        tz?.setOnTouchListener { v, event ->
          when (event.action) {
            MotionEvent.ACTION_DOWN -> {
              tz.background = ColorDrawable(pressedBackgroundColorDarker)
              vibrator.vibrate(10)
              turnChangeHoldInProgress = false
              turnChangeIntention = false

              if (currentPlayer != playerIndex) {
                turnChangeIndicatorSelector?.visibility = View.VISIBLE
                scaleAnimator.start()
                turnChangeIndicatorSelector?.backgroundTintList =
                  ColorStateList.valueOf(
                    ContextCompat.getColor(
                      this,
                      R.color.manchester_pink
                    )
                  )
                dividerLineSelector.visibility = View.INVISIBLE
                obscuringLineSelector.visibility = View.INVISIBLE
                turnChangeHandler.postDelayed({
                  turnChangeTimerCancelled = false
                  if (!turnChangeHoldInProgress) {
                    turnChangeIntention = true
                    runOnUiThread {
                    }
                  }
                }, 400L)
              }
              true
            }

            MotionEvent.ACTION_MOVE -> {
              if (!intentionalHoldInProgress && intentionalHold) {
                intentionalHoldHandler.removeCallbacksAndMessages(null)
                intentionalHoldTimerCancelled = true
                if (currentPlayer != playerIndex) {
                  resetPlayerDrawer(playerDrawerStub)
                }
              }
              if (!turnChangeHoldInProgress && turnChangeIntention) {
                runOnUiThread {
                }
                turnChangeHandler.removeCallbacksAndMessages(null)
                turnChangeTimerCancelled = true
              }
              true
            }

            MotionEvent.ACTION_UP -> {
              tz.background = defaultBackgroundColor
              intentionalHoldHandler.removeCallbacksAndMessages(null)
              intentionalHoldTimerCancelled = true
              turnChangeTimerCancelled = true
              turnChangeHandler.removeCallbacksAndMessages(null)
              turnChangeIndicatorSelector?.visibility = View.INVISIBLE
              scaleAnimator.cancel()
              turnChangeIndicatorSelector?.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
              dividerLineSelector.visibility = View.VISIBLE
              obscuringLineSelector.visibility = View.VISIBLE
              turnChangeIndicatorSelector?.layoutParams?.width = 0
              turnChangeIndicatorSelector?.layoutParams?.height = 0
              turnChangeIndicatorSelector?.requestLayout()

              if (!intentionalHoldInProgress) {
                if (!intentionalHoldTimerCancelled) {
                  turnChangeHandler.removeCallbacksAndMessages(null)
                  if (currentPlayer != playerIndex) {
                    resetPlayerDrawer(playerDrawerStub)
                  }
                }
              }

              if (!turnChangeHoldInProgress) {
                if (turnChangeIntention) {
                  runOnUiThread {
                    if (currentPlayer != playerIndex) {
                      var count = 1
                      while (count <= playerCount.toInt()) {
                        resetPlayerDrawer("player${count}_Drawer")
                        count++
                      }
                      val nextPlayerDrawerStub = "player${playerIndex}_Drawer"
//                    initNotificationScreen(nextPlayerDrawerStub)
                      initPlayerTurn(nextPlayerDrawerStub)
                      tz.backgroundTintList =
                        ColorStateList.valueOf(
                          ContextCompat.getColor(
                            this,
                            R.color.transparent
                          )
                        )
                      currentPlayer = playerIndex
                    }
                  }
                } else {
                  runOnUiThread {
                    tz.backgroundTintList =
                      ColorStateList.valueOf(
                        ContextCompat.getColor(
                          this,
                          R.color.transparent
                        )
                      )
                    val widgetTextSelectorMap = mapOf(
                      "lifeTotal" to lifeTotalTextSelector,
                      "primaryCommanderDamage" to primaryCommanderDamageTextSelector,
                      "secondaryCommanderDamage" to secondaryCommanderDamageTextSelector,
                      "infectDamage" to infectDamageTextSelector,
                    )
                    val widgetTextSelector = widgetTextSelectorMap[key]
                    val widgetChangeTextSelectorMap = mapOf(
                      "lifeTotal" to lifeTotalChangeTextSelector,
                      "primaryCommanderDamage" to primaryCommanderDamageChangeTextSelector,
                      "secondaryCommanderDamage" to secondaryCommanderDamageChangeTextSelector,
                      "infectDamage" to infectDamageChangeTextSelector,
                    )
                    val widgetChangeTextSelector = widgetChangeTextSelectorMap[key]
                    val currentWidgetChange =
                      widgetChangeTextSelector?.text?.toString()?.toIntOrNull() ?: 0
                    val newWidgetValue = currentWidgetChange + 1
                    widgetChangeTextSelector?.text = newWidgetValue.toString()
                    if (widgetChangeTextSelector?.text == "0") {
                      widgetChangeTextSelector.text = ""
                    } else if (newWidgetValue > 0) {
                      widgetChangeTextSelector?.text =
                        "+${widgetChangeTextSelector?.text}"
                    }
                    val finalizeChangeRunnable = Runnable {
                      if (widgetTextSelector?.text.toString() == "") {
                        widgetTextSelector?.text = "0"
                      }
                      val finalLifeChange = widgetChangeTextSelector?.text.toString().toInt()
                      val currentPlayerLife = widgetTextSelector?.text.toString().toInt()
                      val newPlayerLife = currentPlayerLife + finalLifeChange
                      widgetTextSelector?.text = newPlayerLife.toString()
                      widgetChangeTextSelector?.text = ""
                    }
                    turnChangeHandler.removeCallbacks(finalizeChangeRunnable)
                    turnChangeHandler.postDelayed(finalizeChangeRunnable, 500L)
                  }
                }
                turnChangeHoldInProgress = false
                turnChangeIntention = false
              }
              v.performClick()
              true
            }

            else -> {
              v.performClick()
              true
            }
          }
          v.performClick()
          true
        }
      }
//      val maxLidHeight = playerDrawerSelector.height
//      playerDrawerLidSelector.layoutParams.height = maxLidHeight
//      playerDrawerLidBarrierSelector.layoutParams.height = 1
//      playerDrawerLidSelector.requestLayout()
      playerHandleSelector.setOnTouchListener { v, event ->
//        Log.d("TEST123", "In player handle")
        var startY = 0f
//        var initialX = 0f
//        var initialY = 0f
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
//            initialX = event.rawX
//            Log.d("TEST123", initialX.toString())
//            initialY = event.rawY
            startY = event.y
            true
          }

          MotionEvent.ACTION_MOVE -> {
            val minLidHeight = 400
            val maxLidHeight = playerDrawerSelector.height
            val maxBottomHeight = maxLidHeight - minLidHeight
            val oldLidHeight = playerDrawerLidSelector.height
            val oldBottomHeight = playerDrawerLidBarrierSelector.height


            // Finger moved
            val currentY = event.y
            val deltaY = currentY - startY

            if (deltaY > 0) {
              // Moving right
              if (oldBottomHeight + 50 > maxBottomHeight) {
                playerDrawerLidSelector.layoutParams.height = minLidHeight
                playerDrawerLidBarrierSelector.layoutParams.height = maxBottomHeight
              } else {
                playerDrawerLidSelector.layoutParams.height = oldLidHeight - deltaY.toInt()
                playerDrawerLidBarrierSelector.layoutParams.height = oldBottomHeight + deltaY.toInt()
                Log.d("MyApp", "Moving left")
              }
            }
            else if (deltaY < 0) {
              // Moving left
              if (oldLidHeight + 50 > maxLidHeight) {
                playerDrawerLidSelector.layoutParams.height = maxLidHeight
                playerDrawerLidBarrierSelector.layoutParams.height = 0
              } else {
                playerDrawerLidSelector.layoutParams.height = oldLidHeight - deltaY.toInt()
                playerDrawerLidBarrierSelector.layoutParams.height = oldBottomHeight + deltaY.toInt()
                Log.d("MyApp", "Moving left")
              }
            }

            startY = currentY
            playerDrawerLidSelector.requestLayout()
            true
          }

          MotionEvent.ACTION_UP -> {
            true
          }
        }
        v.performClick()
        true
      }
//      val reminderScreenSelector = findViewById<View>(R.id.notificationScreen)
//      reminderScreenSelector.setOnClickListener {
//        val notificationsSelector = findViewById<View>(R.id.notifications)
//        notificationsSelector.visibility = View.GONE
//        reminderScreenSelector.visibility = View.GONE
//      }
    }
  }
}
