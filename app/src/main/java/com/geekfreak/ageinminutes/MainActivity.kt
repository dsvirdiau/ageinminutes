package com.geekfreak.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.geekfreak.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding;

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate( layoutInflater )
      setContentView(binding.root)

      binding.selectDateButton.setOnClickListener { view ->
         showDatePicker( view )
      }
   }

   private fun showDatePicker(view: View ) {
      val myCalendar = Calendar.getInstance()
      var year = myCalendar.get( Calendar.YEAR )
      val month = myCalendar.get( Calendar.MONTH )
      val dayOfMonth = myCalendar.get( Calendar.DAY_OF_MONTH )

      val datePickerDialog = DatePickerDialog( this,
         DatePickerDialog.OnDateSetListener {
               view, selectedYear, selectedMonth, selectedDayOfMonth ->

            //create a value to put selected date in dd/MM/yyyy format using string interpolation
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

            //display the selected date
            binding.selectedDateValueTextView.text = selectedDate

            //calculate age in minutes from the selected date to current date

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

            //display the age in minutes
            binding.minutesTillDateValueTextView.text = differenceInMinutes.toString()
         }
         , year
         , month
         , dayOfMonth )

      datePickerDialog.datePicker.maxDate = Date().time
      datePickerDialog.show()
   }
}