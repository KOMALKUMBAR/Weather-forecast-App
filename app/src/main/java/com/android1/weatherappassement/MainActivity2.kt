package com.android1.weatherappassement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import com.android1.weatherappassement.databinding.ActivityMain2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity2 : AppCompatActivity() {
 private val binding:ActivityMain2Binding by lazy {
     ActivityMain2Binding.inflate(layoutInflater)
 }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main2)
        //binding=ActivityMainBinding.inflate(layoutInflater)
        //https://api.openweathermap.org/data/2.5/weather?q=pune&appid=f28aedb244f1cc1400ff29e92e68e53b
        setContentView(binding.root)
        fecthWetherData("pune")
        SerchCity()
    }

    private fun SerchCity() {
        val serchCity= binding.serachView
        serchCity.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    fecthWetherData(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
              return true
            }

        })
    }

    private fun fecthWetherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(ApiInterface::class.java)
        val response = retrofit.getwetherData(cityName, "f28aedb244f1cc1400ff29e92e68e53b", "metric")
        response.enqueue(object : Callback<JsonData> {
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                Log.d("TAG", "onResponse: "+response.body())
                val responesebody = response.body()
                if (response.isSuccessful && responesebody != null) //check the null safeti
                {
                    val temperture = responesebody.main.temp
                    val  humidity= responesebody.main.humidity
                    val windspace=responesebody.wind.speed
                    val sunrise=responesebody.sys.sunrise.toLong()
                    val sunset=responesebody.sys.sunset.toLong()
                    val seaLevel=responesebody.main.pressure
                    val condition=responesebody.weather.firstOrNull()?.main?:"unkonw"
                    val max=responesebody.main.temp_min
                    val min=responesebody.main.temp_max

                    binding.tmperture.text = "$temperture °C"
                    binding.Humidity.text=humidity.toString()
                    binding.WindSpeed.text="$windspace m/s"
                    binding .wether.text=condition
                    binding.max.text="$max °C"
                    binding.min.text="$min °C"
                    binding.sunrise.text="${time(sunrise)}"
                    binding.sunset.text="${time(sunset)}"
                    binding.seatemp.text="$seaLevel  hp"
                    binding.Condition.text=condition
                    binding.location.text="$cityName"
                    binding.Date.text=dayName(System.currentTimeMillis())
                        binding.Day.text=data()

                    changeImagebackground(condition)
                }
            }


            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.d("TAG", "onFailure: "+t.message)
            }

        })


    }

    private fun changeImagebackground(condition: String) {
       when(condition){
           "clear Sky","Sonny","Clear"->{
               binding.root.setBackgroundResource(R.drawable.sunny_background)
               binding.lottiAnimationView.setAnimation(R.raw.sun)
           }
           "partly Clouds","Clouds","Overcast","mist","Foggy"->{
               binding.root.setBackgroundResource(R.drawable.colud_background)
               binding.lottiAnimationView.setAnimation(R.raw.cloud)
           }
           "Light rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
               binding.root.setBackgroundResource(R.drawable.rain_background)
               binding.lottiAnimationView.setAnimation(R.raw.rain)
           }
           "Light Snow","Moderate Snow","Heavy Snow"->{
               binding.root.setBackgroundResource(R.drawable.snow_background)
               binding.lottiAnimationView.setAnimation(R.raw.snow)
           }
           else-> {
               binding.root.setBackgroundResource(R.drawable.sunny_background)
             binding.lottiAnimationView.setAnimation(R.raw.sun)
           }

           }
        binding.lottiAnimationView.playAnimation()

    }

    private fun data():String {
        val sdf= SimpleDateFormat("dd MMM yyy", Locale.getDefault())
        return sdf.format(Date())

    }
    private fun time(timestamp:Long):String {
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))

    }

    fun dayName(timestamp: Long):String{
        val sdf= SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }


}


