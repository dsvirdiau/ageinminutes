package com.geekfreak.ageinminutes

import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith( AndroidJUnit4::class )
class MainActivityTest {
   private val selectedDate = "10/3/1990"

   @get:Rule
   var activityRule: ActivityScenarioRule<MainActivity> =
      ActivityScenarioRule( MainActivity::class.java)

   @Test
   fun setDate( ) {
      val year = 1990
      val month = 3
      val day = 10

      onView( withId( R.id.selectDateButton ) ).perform( click() )

      onView( withClassName( Matchers.equalTo( DatePicker::class.qualifiedName ) ) )
         .perform( PickerActions.setDate(year, month, day ) )

      onView( withId( android.R.id.button1 ) ).perform( click() )
   }

   @Test
   fun checkSelectedDate( ) {
      setDate()

      onView( withId( R.id.selectedDateValueTextView ) )
         .check( matches( withText( selectedDate ) ) )
   }

   @Test
   fun checkAgeInMinutes( ) {
      checkSelectedDate()

      //create a formatter
      val sdf = SimpleDateFormat( "dd/MM/yyyy", Locale.ENGLISH )

      //format the selected date
      val formattedDate = sdf.parse( selectedDate )

      //calculate selected date in minutes. This selected date is from 1 Jan 1970
      val selectedDateInMinutes = formattedDate!!.time / 60000

      //we get the current date in milliseconds and format it using the above formatter
      val currentDate = sdf.parse( sdf.format( System.currentTimeMillis() ) )

      //we then convert the date to minutes. This current date is from 1 Jan 1970
      val currentDateToMinutes = currentDate!!.time / 60000

      //then to find the age in minutes from the selected date(dob) till today
      //we calculate the difference
      val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes

      onView( withId( R.id.minutesTillDateValueTextView ) )
         .check( matches( withText( differenceInMinutes.toString() ) ) )
   }
}